package com.mycompany.tamagotchi;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.Timer; // Added import for Timer
import java.awt.event.ActionListener; // Added import for ActionListener
import java.awt.event.ActionEvent; // Added import for ActionEvent

public class UI extends javax.swing.JFrame {

    private TamagotchiBase tamagotchi;
    private javax.swing.JLabel labelImagenMascota;
    private transient BufferedImage backgroundImageForMascota;
    private Thread hiloAccionEspecialUI;
    private RelojEspecial relojEspecial;
    private Timer updateTimer; // Added Timer for periodic UI updates

    public UI(TamagotchiBase tamagotchi, String imagePath, String backgroundPath) {
        this.tamagotchi = tamagotchi;

        // Load background image for Mascota BEFORE initComponents
        try {
            File bgFile = new File(backgroundPath);
            if (bgFile.exists()) {
                this.backgroundImageForMascota = ImageIO.read(bgFile);
            } else {
                System.err.println("Background image file not found: " + backgroundPath + " at " + bgFile.getAbsolutePath());
                this.backgroundImageForMascota = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.backgroundImageForMascota = null;
        }

        initComponents(); // NetBeans initializes components, Mascota panel will use custom painting

        // Manually initialize labelImagenMascota
        this.labelImagenMascota = new JLabel();
        // NOTE: The following lines that set BorderLayout for Mascota are now removed:
        // this.Mascota.setLayout(new java.awt.BorderLayout());
        // this.Mascota.add(this.labelImagenMascota, java.awt.BorderLayout.CENTER);

        // Load and set the pet image for labelImagenMascota
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.err.println("Image file not found at: " + imageFile.getAbsolutePath());
                labelImagenMascota.setText("<html>Imagen no encontrada en:<br>" + imageFile.getAbsolutePath() + "</html>");
            } else {
                BufferedImage originalImage = ImageIO.read(imageFile);
                if (originalImage == null) {
                    System.err.println("Could not read image file (null): " + imagePath + " at " + imageFile.getAbsolutePath());
                    labelImagenMascota.setText("<html>No se pudo leer la imagen:<br>" + imagePath + "</html>");
                } else {
                    int petImageWidth = 453; // Target width for pet image
                    int petImageHeight = 429; // Target height for pet image

                    int originalWidth = originalImage.getWidth();
                    int originalHeight = originalImage.getHeight();

                    double scaleFactor = Math.min(1.0 * petImageWidth / originalWidth, 1.0 * petImageHeight / originalHeight);

                    int newWidth = (int) (originalWidth * scaleFactor);
                    int newHeight = (int) (originalHeight * scaleFactor);

                    Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    labelImagenMascota.setIcon(new ImageIcon(scaledImage));
                    labelImagenMascota.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                    labelImagenMascota.setVerticalAlignment(javax.swing.JLabel.CENTER);
                    
                    // Add labelImagenMascota to Mascota panel using AbsoluteLayout
                    // Position it at (0,0) with its scaled dimensions.
                    // Ensure these coordinates and dimensions are appropriate for your layout.
                    Mascota.add(this.labelImagenMascota, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, petImageWidth, petImageHeight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            File imageFile = new File(imagePath); 
            labelImagenMascota.setText("<html>Error al cargar imagen:<br>" + imagePath + "<br>Abs: " + imageFile.getAbsolutePath() + "<br>Detalles: " + e.getMessage() + "</html>");
        }

        // Configurar alineación para el JLabel Accion para el texto HTML
        Accion.setVerticalAlignment(JLabel.TOP);
        Accion.setHorizontalAlignment(JLabel.CENTER); // Centra el bloque de texto HTML si es más estrecho que el JLabel

        actualizarLabelsInfo(); // Mostrar estado inicial
        actualizarLabelAccion("..."); // Inicializar texto de acción
        this.setTitle("Cuidando a " + tamagotchi.getNombre());
        iniciarRelojEspecial(); 

        // Initialize and start the UI update timer
        updateTimer = new Timer(1000, new ActionListener() { // 1000 ms = 1 second
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLabelsInfo();
            }
        });
        updateTimer.start();
    }

    private void iniciarRelojEspecial() { // ADDED: New method to setup and start RelojEspecial
        relojEspecial = new RelojEspecial(this, this.tamagotchi);
        hiloAccionEspecialUI = new Thread(relojEspecial);
        hiloAccionEspecialUI.start();
    }

    // This method is now called by RelojEspecial via SwingUtilities.invokeLater
    public void ejecutarYActualizarAccionEspecial() { // MODIFIED: Made public to be callable from RelojEspecial
        if (tamagotchi != null && tamagotchi.estaActivo()) {
            String mensajePrioritario = tamagotchi.getMensajePrioritario();
            String textoFinal;
            if (mensajePrioritario != null) {
                textoFinal = mensajePrioritario;
            } else {
                textoFinal = tamagotchi.realizarAccionEspecial();
            }
            actualizarLabelAccion(textoFinal);
            System.out.println("UI actualizó acción/estado: " + textoFinal);
        }
    } 

    // Método para actualizar los JLabels con la información del Tamagotchi
    public void actualizarLabelsInfo() {
        if (tamagotchi == null) return;

        SwingUtilities.invokeLater(() -> { // Asegurar que se ejecuta en el EDT
            TestNombre.setText(tamagotchi.getNombre());
            TextFelicidad.setText("Felicidad: " + tamagotchi.getFelicidad());
            TextHambre.setText("Hambre: " + tamagotchi.getHambre());
            TextEnergia.setText("Energia: " + tamagotchi.getEnergia());
            // Aquí también podrías actualizar el JPanel Mascota si quieres cambiar su apariencia
            // y mostrar mensajes de estado general en algún JLabel dedicado.
        });
    }

    public void actualizarLabelAccion(String textoAccion) {
        SwingUtilities.invokeLater(() -> {
            if (Accion != null) {
                int labelWidth = Accion.getWidth();
                // Si el ancho es 0, puede que el componente aún no se haya dibujado. Usar ancho de diseño.
                // De AbsoluteConstraints(523, 10, 120, 110), el ancho es 120.
                if (labelWidth == 0) {
                    labelWidth = 120;
                }

                // El ancho del div HTML debe ser menor que el ancho del JLabel
                // para permitir que el texto se centre y tenga espacio respecto a los bordes del bocadillo.
                // Ajusta este valor según la forma visual de dialogo.png
                int divWidth = labelWidth - 20; // Ej: 100px si labelWidth es 120px (10px de margen a cada lado)

                // Padding superior para posicionar el texto dentro del bocadillo.
                // Ajusta este valor para centrar visualmente el bloque de texto o posicionarlo como desees.
                int paddingTop = 10; // píxeles

                String displayText = (textoAccion == null || textoAccion.trim().isEmpty()) ? "" : textoAccion;
                
                // Construir el texto HTML
                // padding-left y padding-right en 0 porque el text-align:center del div y
                // el setHorizontalAlignment(JLabel.CENTER) del JLabel se encargan del centrado horizontal.
                String textoHtml = "<html><div style='width: " + divWidth + "px; text-align: center; padding-top: " + paddingTop + "px; padding-left: 0px; padding-right: 0px;'>"
                                 + displayText
                                 + "</div></html>";
                Accion.setText(textoHtml);
            }
        });
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonAlimentar = new javax.swing.JButton();
        Mascota = new javax.swing.JPanel();
        Accion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        BotonJugar = new javax.swing.JButton();
        BotonDormir = new javax.swing.JButton();
        TestNombre = new javax.swing.JLabel();
        TextFelicidad = new javax.swing.JLabel();
        TextHambre = new javax.swing.JLabel();
        TextEnergia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BotonAlimentar.setText("Alimentar");
        BotonAlimentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAlimentarActionPerformed(evt);
            }
        });

        Mascota.setBackground(new java.awt.Color(102, 255, 102));
        Mascota.setForeground(new java.awt.Color(255, 255, 255));
        Mascota.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Accion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Accion.setText("asdads");
        Mascota.add(Accion, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 180, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/tamagotchi/dialogo.png"))); // NOI18N
        Mascota.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 190, 180));

        BotonJugar.setText("Jugar");
        BotonJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonJugarActionPerformed(evt);
            }
        });

        BotonDormir.setText("Dormir");
        BotonDormir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDormirActionPerformed(evt);
            }
        });

        TestNombre.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TestNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TestNombre.setText("NombreMascota");

        TextFelicidad.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TextFelicidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextFelicidad.setText("Felicidad: xx");

        TextHambre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TextHambre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextHambre.setText("Hambre: xx");

        TextEnergia.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TextEnergia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextEnergia.setText("Energia: xx");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TestNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TextHambre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TextEnergia, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TextFelicidad, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(BotonAlimentar)
                        .addGap(63, 63, 63)
                        .addComponent(BotonJugar)
                        .addGap(48, 48, 48)
                        .addComponent(BotonDormir)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Mascota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextEnergia)
                    .addComponent(TextHambre)
                    .addComponent(TextFelicidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Mascota, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TestNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAlimentar)
                    .addComponent(BotonJugar)
                    .addComponent(BotonDormir))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonAlimentarActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("asdasd");
        if (tamagotchi == null) return;
        String[] comidas = tamagotchi.getComidas();
        if (comidas == null || comidas.length == 0) {
            JOptionPane.showMessageDialog(this, tamagotchi.getNombre() + " no tiene comidas definidas.", "Sin Comidas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String comidaSeleccionada = (String) JOptionPane.showInputDialog(
                this,
                "Elige una comida para " + tamagotchi.getNombre() + ":",
                "Alimentar a " + tamagotchi.getNombre(),
                JOptionPane.PLAIN_MESSAGE,
                null,
                comidas,
                comidas[0]);
        if (comidaSeleccionada != null && !comidaSeleccionada.isEmpty()) {
            tamagotchi.alimentar(comidaSeleccionada);
            actualizarLabelsInfo();
            // Mostrar mensaje de acción inmediatamente después de la interacción
            actualizarLabelAccion(tamagotchi.getNombre() + " ha comido " + comidaSeleccionada.toLowerCase() + "."); 
        }
    }

    private void BotonJugarActionPerformed(java.awt.event.ActionEvent evt) {
        if (tamagotchi == null) return;
        String[] juegos = tamagotchi.getJuegos();
        if (juegos == null || juegos.length == 0) {
            JOptionPane.showMessageDialog(this, tamagotchi.getNombre() + " no tiene juegos definidos.", "Sin Juegos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String juegoSeleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Elige un juego para " + tamagotchi.getNombre() + ":",
                "Jugar con " + tamagotchi.getNombre(),
                JOptionPane.PLAIN_MESSAGE,
                null,
                juegos,
                juegos[0]);
        if (juegoSeleccionado != null && !juegoSeleccionado.isEmpty()) {
            tamagotchi.jugar(juegoSeleccionado);
            actualizarLabelsInfo();
            // Mostrar mensaje de acción inmediatamente después de la interacción
            actualizarLabelAccion(tamagotchi.getNombre() + " ha jugado a " + juegoSeleccionado.toLowerCase() + ".");
        }
    }

    private void BotonDormirActionPerformed(java.awt.event.ActionEvent evt) {
        if (tamagotchi == null) return;
        tamagotchi.dormir();
        actualizarLabelsInfo();
        // Mostrar mensaje de acción inmediatamente después de la interacción
        // El mensaje de "está durmiendo" se manejará por getMensajePrioritario en el siguiente ciclo de RelojEspecial
        actualizarLabelAccion(tamagotchi.getNombre() + " se ha ido a dormir."); 
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        TamagotchiBase mascota = null;
        String imagePath = ""; 
        String backgroundPath = ""; // Path for the background image

        String[] tiposMascota = {"Perro", "Gato", "Muñeca", "Cuyo"};
        String tipoSeleccionado = (String) JOptionPane.showInputDialog(
                null, 
                "Elige tu mascota:", 
                "Bienvenido a Tamagotchi", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                tiposMascota, 
                tiposMascota[0] 
        );

        if (tipoSeleccionado == null) {
            System.out.println("No se seleccionó mascota. Saliendo.");
            System.exit(0); 
            return; 
        }

        String nombre = JOptionPane.showInputDialog(null, "Ingresa el nombre de tu mascota:", "Nombre de la Mascota", JOptionPane.PLAIN_MESSAGE);

        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Mascota Sin Nombre"; 
        }

        switch (tipoSeleccionado) {
            case "Perro":
                mascota = new Perro(nombre);
                imagePath = "Imagenes/perro.png";
                backgroundPath = "Imagenes/FondoPerro.png";
                break;
            case "Gato":
                mascota = new Gato(nombre);
                imagePath = "Imagenes/gato.png";
                backgroundPath = "Imagenes/FondoGato.png";
                break;
            case "Muñeca":
                mascota = new Muñeca(nombre);
                imagePath = "Imagenes/muñeca.png";
                backgroundPath = "Imagenes/FondoMuñeca.png";
                break;
            case "Cuyo":
                mascota = new Cuyo(nombre);
                imagePath = "Imagenes/cuyo.png";
                backgroundPath = "Imagenes/FondoCuyo.png";
                break;
            default:
                System.out.println("Tipo de mascota inválido. Saliendo.");
                JOptionPane.showMessageDialog(null, "Tipo de mascota inválido. La aplicación se cerrará.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
                return;
        }

        final TamagotchiBase mascotaFinal = mascota;
        final String finalImagePath = imagePath;
        final String finalBackgroundPath = backgroundPath; // Make backgroundPath final for lambda

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (mascotaFinal != null) {
                    new UI(mascotaFinal, finalImagePath, finalBackgroundPath).setVisible(true); // Pass backgroundPath
                } else {
                     JOptionPane.showMessageDialog(null, "Error al crear la mascota. La aplicación se cerrará.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Accion;
    private javax.swing.JButton BotonAlimentar;
    private javax.swing.JButton BotonDormir;
    private javax.swing.JButton BotonJugar;
    private javax.swing.JPanel Mascota;
    private javax.swing.JLabel TestNombre;
    private javax.swing.JLabel TextEnergia;
    private javax.swing.JLabel TextFelicidad;
    private javax.swing.JLabel TextHambre;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (tamagotchi != null) {
                tamagotchi.detenerRelojes();
            }
            
            // Stop the RelojEspecial and its thread
            if (relojEspecial != null) {
                relojEspecial.detener();
            }
            if (hiloAccionEspecialUI != null && hiloAccionEspecialUI.isAlive()) {
                hiloAccionEspecialUI.interrupt();
                try {
                    hiloAccionEspecialUI.join(1000); 
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    System.err.println("Interrupción al esperar que hiloAccionEspecialUI termine.");
                }
            }

            // Stop the UI update timer
            if (updateTimer != null && updateTimer.isRunning()) {
                updateTimer.stop();
                System.out.println("UI update timer detenido.");
            }

            System.out.println("RelojEspecial y relojes de Tamagotchi detenidos. Saliendo.");
        }
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            // System.exit(0); 
        }
    }
}

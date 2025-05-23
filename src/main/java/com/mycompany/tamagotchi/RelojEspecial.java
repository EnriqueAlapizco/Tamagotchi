package com.mycompany.tamagotchi;

import javax.swing.SwingUtilities;
import java.util.Random;

public class RelojEspecial implements Runnable {
    private UI ui;
    private volatile boolean activo = true;
    private Random random = new Random();
    private TamagotchiBase tamagotchi; // To check if tamagotchi is active

    public RelojEspecial(UI ui, TamagotchiBase tamagotchi) {
        this.ui = ui;
        this.tamagotchi = tamagotchi;
    }

    @Override
    public void run() {
        while (activo && tamagotchi.estaActivo()) {
            try {
                // Intervalo aleatorio entre 5 y 10 segundos (5000 a 10000 ms)
                int intervalo = 5000 + random.nextInt(5001);
                Thread.sleep(intervalo);

                if (activo && tamagotchi.estaActivo()) { // Double check before UI update
                    // Llamar al método en la UI para ejecutar la acción y actualizar el label
                    // Asegurarse de que la actualización de la UI se haga en el Event Dispatch Thread
                    SwingUtilities.invokeLater(() -> ui.ejecutarYActualizarAccionEspecial());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
                System.out.println("RelojEspecial interrumpido para " + (tamagotchi != null ? tamagotchi.getNombre() : "N/A"));
                break; // Exit loop if interrupted
            }
        }
        System.out.println("RelojEspecial para " + (tamagotchi != null ? tamagotchi.getNombre() : "N/A") + " terminado.");
    }

    public void detener() {
        this.activo = false;
    }
}

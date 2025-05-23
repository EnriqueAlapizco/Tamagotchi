/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;

public abstract class TamagotchiBase implements TamagotchiActions {
    protected String nombre;
    protected int hambre;
    protected int energia;
    protected int felicidad;
    private volatile boolean activo; // Indica si el Tamagotchi está en funcionamiento
    protected volatile boolean estaDurmiendo; // NUEVO: Estado de sueño - CAMBIADO A PROTECTED

    // Constantes para umbrales críticos
    private static final int UMBRAL_HAMBRE_CRITICA = 25;
    private static final int UMBRAL_ENERGIA_CRITICA = 25;
    private static final int UMBRAL_FELICIDAD_CRITICA = 25;

    // Hilos y Relojes
    private Thread hiloHambre;
    private Thread hiloEnergia;
    private Thread hiloFelicidad;
    private RelojHambre relojHambre;
    private RelojEnergia relojEnergia;
    private RelojFelicidad relojFelicidad;

    public TamagotchiBase(String nombre) {
        this.nombre = nombre;
        this.hambre = 50; // Valor inicial (0=muy hambriento, 100=satisfecho)
        this.energia = 50; // Valor inicial
        this.felicidad = 50; // Valor inicial
        this.activo = true; // El Tamagotchi comienza activo
        this.estaDurmiendo = false; // NUEVO: Inicialmente no está durmiendo

        // Inicializar y arrancar los hilos de necesidades
        iniciarRelojes();
    }

    private void iniciarRelojes() {
        relojHambre = new RelojHambre(this);
        hiloHambre = new Thread(relojHambre);
        hiloHambre.start();

        relojEnergia = new RelojEnergia(this);
        hiloEnergia = new Thread(relojEnergia);
        hiloEnergia.start();

        relojFelicidad = new RelojFelicidad(this);
        hiloFelicidad = new Thread(relojFelicidad);
        hiloFelicidad.start();
    }

    public void detenerRelojes() {
        this.activo = false; // Marcar como inactivo para que los bucles de los runnables terminen

        if (relojHambre != null) relojHambre.detener();
        if (relojEnergia != null) relojEnergia.detener();
        if (relojFelicidad != null) relojFelicidad.detener();

        try {
            if (hiloHambre != null && hiloHambre.isAlive()) {
                hiloHambre.interrupt(); // Interrumpir si está dormido
                hiloHambre.join(1000); // Esperar un poco a que termine
            }
            if (hiloEnergia != null && hiloEnergia.isAlive()) {
                hiloEnergia.interrupt();
                hiloEnergia.join(1000);
            }
            if (hiloFelicidad != null && hiloFelicidad.isAlive()) {
                hiloFelicidad.interrupt();
                hiloFelicidad.join(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupción al detener los relojes de " + nombre);
        }
        System.out.println("Relojes para " + nombre + " detenidos.");
    }

    public boolean estaActivo() {
        return activo;
    }

    // Métodos para que los relojes modifiquen los atributos
    // Estos aseguran que los valores no bajen de 0 ni suban de 100

    // Renombrado y lógica invertida: Alimentar AUMENTA el hambre hacia 100 (satisfecho)
    public synchronized void aumentarHambrePorAlimentacion(int cantidad) {
        if (!activo) return;
        this.hambre = Math.min(100, this.hambre + cantidad);
        this.estaDurmiendo = false; // Alimentarse despierta
        verificarEstadoGeneral();
    }

    // Renombrado y lógica invertida: El tiempo DISMINUYE el hambre hacia 0 (más hambriento)
    public synchronized void disminuirHambrePorPasoTiempo(int cantidad) {
        if (!activo) return;
        this.hambre = Math.max(0, this.hambre - cantidad);
        // No cambia el estado de sueño por el paso del tiempo
        verificarEstadoGeneral();
    }

    public synchronized void disminuirEnergia(int cantidad) {
        if (!activo) return;
        this.energia = Math.max(0, this.energia - cantidad);
        // No cambia el estado de sueño por el paso del tiempo
        verificarEstadoGeneral();
    }

    public synchronized void disminuirFelicidad(int cantidad) {
        if (!activo) return;
        this.felicidad = Math.max(0, this.felicidad - cantidad);
        // No cambia el estado de sueño por el paso del tiempo
        verificarEstadoGeneral();
    }
    
    // Getters para los atributos para que los relojes puedan leerlos si es necesario (aunque no lo hacen directamente ahora)
    public int getHambre() {
        return hambre;
    }

    public int getEnergia() {
        return energia;
    }

    public int getFelicidad() {
        return felicidad;
    }
    
    public String getNombre() {
        return nombre;
    }

    public boolean estaDurmiendo() { // NUEVO: Getter para estaDurmiendo
        return estaDurmiendo;
    }

    // Método para obtener el mensaje prioritario según el estado
    public String getMensajePrioritario() {
        if (!activo) return nombre + " ya no está activo.";
        if (estaDurmiendo) return nombre + " está durmiendo... Zzz...";
        
        if (hambre < UMBRAL_HAMBRE_CRITICA) return "¡Tengo mucha hambre!";
        if (energia < UMBRAL_ENERGIA_CRITICA) return "¡Necesito dormir! Estoy muy cansado.";
        if (felicidad < UMBRAL_FELICIDAD_CRITICA) return "Me siento triste y aburrido.";
        
        return null; // No hay mensaje prioritario, se puede mostrar acción especial
    }

    public void mostrarEstado() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Hambre: " + hambre);
        System.out.println("Energia: " + energia);
        System.out.println("Felicidad: " + felicidad);
    }

    public abstract String[] getJuegos();
    public abstract String[] getComidas();
    
    // Implementación de la nueva acción especial en la clase base
    // Las subclases deben proporcionar su propia lógica para esta acción.
    @Override
    public abstract String realizarAccionEspecial();
    
    
    protected boolean tieneEnergiaPara(int cantidad) {
        if (energia < cantidad) {
            System.out.println(nombre + " no tiene suficiente energía para hacer esto.");
            return false;
        }
        return true;
    }

    // Lógica invertida: "Tiene hambre" significa que no está lleno (hambre < 100)
    // Este método determina si se puede alimentar.
    protected boolean puedeSerAlimentado() {
        if (hambre >= 100) {
            System.out.println(nombre + " está lleno, no quiere comer más.");
            // Considerar mostrar este mensaje en la UI también si es relevante
            // if (ui != null) ui.mostrarMensaje(nombre + " está lleno.");
            return false;
        }
        return true;
    }
    
    protected void aumentarFelicidad(int valor) {
        if (!activo) return;
        felicidad = Math.min(100, felicidad + valor);
        this.estaDurmiendo = false; // Jugar/interactuar despierta
        verificarEstadoGeneral();
    }

    protected void aumentarEnergia(int valor) {
        if (!activo) return;
        energia = Math.min(100, energia + valor);
        // Aumentar energía (ej. por dormir) no necesariamente despierta si ya estaba durmiendo.
        // El método dormir() se encargará de setear estaDurmiendo = true.
        verificarEstadoGeneral();
    }

    // Renombrado y lógica invertida: Jugar DISMINUYE el hambre hacia 0 (más hambriento)
    protected synchronized void disminuirHambrePorJuego(int valor) {
        if (!activo) return;
        this.hambre = Math.max(0, this.hambre - valor); // Jugar da hambre (hacia 0)
        this.estaDurmiendo = false; // Jugar despierta
        verificarEstadoGeneral();
    }
    
    public void verificarEstadoGeneral() {
        if (!activo) return;
        // Lógica simple para mensajes de estado.
        // Con la nueva escala de hambre (0=muy hambriento, 100=satisfecho):
        // hambre < 20 sigue siendo "muy mal" en términos de hambre.
        // hambre < 40 sigue siendo "necesita atención" en términos de hambre.
        // hambre > 80 sigue siendo "estado genial" en términos de hambre (está satisfecho).
        // Por lo tanto, la lógica de los umbrales no necesita cambiar aquí.
        if (hambre < 20 && energia < 20 && felicidad < 20) {
            System.out.println(nombre + " está muy mal! Necesita atención urgente.");
        } else if (hambre < 40 || energia < 40 || felicidad < 40) {
            System.out.println(nombre + " necesita atención. Revisa sus niveles.");
        } else if (hambre > 80 && energia > 80 && felicidad > 80) {
            System.out.println(nombre + " se siente genial!");
        }
        // La UI se actualiza a través de notificarUiActualizacionEstado() en los métodos que cambian los atributos.
        // Si quieres que esta función específicamente fuerce una actualización o muestre un mensaje en la UI:
        // if (ui != null) { /* ui.mostrarMensajeEstado(...) o similar */ }
    }
}

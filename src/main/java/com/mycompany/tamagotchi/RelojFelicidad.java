package com.mycompany.tamagotchi;

public class RelojFelicidad implements Runnable {
    private TamagotchiBase tamagotchi;
    private volatile boolean running = true;
    private static final int DECREMENTO_FELICIDAD = 2; // Valor de ejemplo, ajustable
    private static final long INTERVALO_FELICIDAD = 2500; // 25 segundos, valor de ejemplo

    public RelojFelicidad(TamagotchiBase tamagotchi) {
        this.tamagotchi = tamagotchi;
    }

    @Override
    public void run() {
        while (running && tamagotchi.estaActivo()) {
            try {
                Thread.sleep(INTERVALO_FELICIDAD);
                if (tamagotchi.estaActivo()) { // Re-chequear estado
                    tamagotchi.disminuirFelicidad(DECREMENTO_FELICIDAD);
                    // System.out.println(tamagotchi.getNombre() + " - Felicidad: " + tamagotchi.getFelicidad()); // Para depuración
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaurar estado de interrupción
                running = false; // Salir del bucle si el hilo es interrumpido
            }
        }
        // System.out.println("RelojFelicidad para " + tamagotchi.getNombre() + " detenido."); // Para depuración
    }

    public void detener() {
        running = false;
    }
}

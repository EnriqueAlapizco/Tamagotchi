package com.mycompany.tamagotchi;

public class RelojEnergia implements Runnable {
    private TamagotchiBase tamagotchi;
    private volatile boolean running = true;
    private static final int DECREMENTO_ENERGIA = 3; // Valor de ejemplo, ajustable
    private static final long INTERVALO_ENERGIA = 20000; // 20 segundos, valor de ejemplo

    public RelojEnergia(TamagotchiBase tamagotchi) {
        this.tamagotchi = tamagotchi;
    }

    @Override
    public void run() {
        while (running && tamagotchi.estaActivo()) {
            try {
                Thread.sleep(INTERVALO_ENERGIA);
                if (tamagotchi.estaActivo()) { // Re-chequear estado
                    tamagotchi.disminuirEnergia(DECREMENTO_ENERGIA);
                    // System.out.println(tamagotchi.getNombre() + " - Energía: " + tamagotchi.getEnergia()); // Para depuración
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaurar estado de interrupción
                running = false; // Salir del bucle si el hilo es interrumpido
            }
        }
        // System.out.println("RelojEnergia para " + tamagotchi.getNombre() + " detenido."); // Para depuración
    }

    public void detener() {
        running = false;
    }
}

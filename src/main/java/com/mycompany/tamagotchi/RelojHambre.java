package com.mycompany.tamagotchi;

public class RelojHambre implements Runnable {
    private TamagotchiBase tamagotchi;
    private volatile boolean running = true;
    private static final int DECREMENTO_HAMBRE = 5; // El hambre DISMINUYE con el tiempo (hacia 0, más hambriento)
    private static final long INTERVALO_HAMBRE = 15000; // 15 segundos

    public RelojHambre(TamagotchiBase tamagotchi) {
        this.tamagotchi = tamagotchi;
    }

    @Override
    public void run() {
        while (running && tamagotchi.estaActivo()) {
            try {
                Thread.sleep(INTERVALO_HAMBRE);
                if (tamagotchi.estaActivo()) {
                    tamagotchi.disminuirHambrePorPasoTiempo(DECREMENTO_HAMBRE); // Llamar al método corregido
                    // System.out.println(tamagotchi.getNombre() + " - Hambre DISMINUIDA a: " + tamagotchi.getHambre()); // Para depuración
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
        // System.out.println("RelojHambre para " + tamagotchi.getNombre() + " detenido.");
    }

    public void detener() {
        running = false;
    }
}

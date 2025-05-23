/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;
import java.util.Random;

public class Perro extends TamagotchiBase {
    
    private final String[] comportamientos = {
            "ladra emocionado",
            "mueve la cola sin parar",
            "persigue su propia cola"
        };
    
    public Perro(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        if (!puedeSerAlimentado()) return; // Changed from tieneHambre()

        switch (comida.toLowerCase()) {
            case "croquetas":
                aumentarHambrePorAlimentacion(20); // Changed from disminuirHambre
                aumentarEnergia(10);
                aumentarFelicidad(5);
                break;
            case "hueso":
                aumentarHambrePorAlimentacion(10); // Changed from disminuirHambre
                aumentarEnergia(5);
                aumentarFelicidad(10);
                break;
            case "carne":
                aumentarHambrePorAlimentacion(25); // Changed from disminuirHambre
                aumentarEnergia(15);
                aumentarFelicidad(15);
                break;
            default:
                System.out.println("Comida no reconocida.");
        }
    }

    @Override
    public void jugar(String juego) {
        switch (juego.toLowerCase()) {
            case "buscar la pelota":
                if (!tieneEnergiaPara(15)) return;
                aumentarFelicidad(20);
                disminuirEnergia(15);
                disminuirHambrePorJuego(10); // Changed from aumentarHambreJugar
                break;
            case "correr":
                if (!tieneEnergiaPara(20)) return;
                aumentarFelicidad(25);
                disminuirEnergia(20);
                disminuirHambrePorJuego(20); // Changed from aumentarHambreJugar
                break;
            case "dar la pata":
                if (!tieneEnergiaPara(5)) return;
                aumentarFelicidad(10);
                disminuirEnergia(5);
                disminuirHambrePorJuego(5); // Changed from aumentarHambreJugar
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        this.energia = 100;
        this.estaDurmiendo = true; // Marcar como durmiendo
        System.out.println(nombre + " duerme profundamente. ¡Zzz!");
    }

    @Override
    public String realizarAccionEspecial() {
        Random random = new Random();
        int index = random.nextInt(comportamientos.length);
        return nombre + " " + comportamientos[index] + "."; // MODIFIED: Return only the special action message
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Buscar la pelota", "Correr", "Dar la pata"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Croquetas", "Hueso", "Carne"};
    }
}


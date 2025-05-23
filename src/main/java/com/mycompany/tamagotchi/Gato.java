/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;
import java.util.Random;

public class Gato extends TamagotchiBase {
    
     private final String[] comportamientos = {
        "maúlla mirando la ventana",
        "salta sobre una caja vacía",
        "se acurruca y ronronea"
    };
     
    public Gato(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        if (!puedeSerAlimentado()) return; // Changed from tieneHambre()

        switch (comida.toLowerCase()) {
            case "atún":
                aumentarHambrePorAlimentacion(20); // Changed from disminuirHambre
                aumentarEnergia(10);
                aumentarFelicidad(10);
                break;
            case "leche":
                aumentarHambrePorAlimentacion(10); // Changed from disminuirHambre
                aumentarEnergia(5);
                aumentarFelicidad(5);
                break;
            case "pollo":
                aumentarHambrePorAlimentacion(25); // Changed from disminuirHambre
                aumentarEnergia(15);
                aumentarFelicidad(10);
                break;
            default:
                System.out.println("Comida no reconocida.");
        }
    }

    @Override
    public void jugar(String juego) {
        switch (juego.toLowerCase()) {
            case "cazar ratón":
                if (!tieneEnergiaPara(15)) return;
                aumentarFelicidad(20);
                disminuirEnergia(15);
                disminuirHambrePorJuego(10); // Changed from aumentarHambreJugar
                break;
            case "saltar cajas":
                if (!tieneEnergiaPara(10)) return;
                aumentarFelicidad(15);
                disminuirEnergia(10);
                disminuirHambrePorJuego(15); // Changed from aumentarHambreJugar
                break;
            case "seguir luz":
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
        System.out.println(nombre + " se acurruca y duerme plácidamente.");
    }

    @Override
    public String realizarAccionEspecial() {
        Random random = new Random();
        int index = random.nextInt(comportamientos.length);
        return nombre + " " + comportamientos[index] + "."; // MODIFIED: Return only the special action message
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Cazar ratón", "Saltar cajas", "Seguir luz"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Atún", "Leche", "Pollo"};
    }
}


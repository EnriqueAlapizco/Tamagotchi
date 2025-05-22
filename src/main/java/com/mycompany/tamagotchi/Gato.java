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
        if (!tieneHambre()) return;

        switch (comida.toLowerCase()) {
            case "atún":
                reducirHambre(20);
                aumentarEnergia(10);
                aumentarFelicidad(10);
                break;
            case "leche":
                reducirHambre(10);
                aumentarEnergia(5);
                aumentarFelicidad(5);
                break;
            case "pollo":
                reducirHambre(25);
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
                reducirEnergia(15);
                aumentarHambre(10);
                break;
            case "saltar cajas":
                if (!tieneEnergiaPara(10)) return;
                aumentarFelicidad(15);
                reducirEnergia(10);
                aumentarHambre(15);
                break;
            case "seguir luz":
                if (!tieneEnergiaPara(5)) return;
                aumentarFelicidad(10);
                reducirEnergia(5);
                aumentarHambre(5);
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        energia = 100;
        System.out.println(nombre + " se acurruca y duerme plácidamente.");
    }

    @Override
    public void comportamientoEspecial() {
        Random random = new Random();
        int index = random.nextInt(comportamientos.length);
        System.out.println(nombre + " " + comportamientos[index] + ".");
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


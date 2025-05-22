/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;


public class Muñeca extends TamagotchiBase {

    public Muñeca(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        if (!tieneHambre()) return;

        switch (comida.toLowerCase()) {
            case "galletas":
                reducirHambre(10);
                aumentarEnergia(5);
                aumentarFelicidad(10);
                break;
            case "pastel":
                reducirHambre(15);
                aumentarEnergia(10);
                aumentarFelicidad(15);
                break;
            case "jugo":
                reducirHambre(5);
                aumentarEnergia(10);
                aumentarFelicidad(5);
                break;
            default:
                System.out.println("Comida no reconocida.");
        }
    }

    @Override
    public void jugar(String juego) {
        switch (juego.toLowerCase()) {
            case "fiesta de té":
                if (!tieneEnergiaPara(10)) return;
                aumentarFelicidad(20);
                reducirEnergia(10);
                aumentarHambre(5);
                break;
            case "desfile de modas":
                if (!tieneEnergiaPara(15)) return;
                aumentarFelicidad(25);
                reducirEnergia(15);
                aumentarHambre(10);
                break;
            case "bailar":
                if (!tieneEnergiaPara(20)) return;
                aumentarFelicidad(15);
                reducirEnergia(20);
                aumentarHambre(20);
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        energia = 100;
        System.out.println(nombre + " se acuesta en su cama rosa y duerme.");
    }

    @Override
    public void comportamientoEspecial() {
        System.out.println(nombre + " dice que está aburrida... o tal vez feliz.");
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Fiesta de té", "Desfile de modas", "Bailar"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Galletas", "Pastel", "Jugo"};
    }
}


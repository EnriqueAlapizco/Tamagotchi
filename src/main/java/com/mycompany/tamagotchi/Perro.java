/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;

public class Perro extends TamagotchiBase {

    public Perro(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        if (!tieneHambre()) return;

        switch (comida.toLowerCase()) {
            case "croquetas":
                reducirHambre(20);
                aumentarEnergia(10);
                aumentarFelicidad(5);
                break;
            case "hueso":
                reducirHambre(10);
                aumentarEnergia(5);
                aumentarFelicidad(10);
                break;
            case "carne":
                reducirHambre(25);
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
                reducirEnergia(15);
                aumentarHambre(10);
                break;
            case "correr":
                if (!tieneEnergiaPara(20)) return;
                aumentarFelicidad(25);
                reducirEnergia(20);
                aumentarHambre(20);
                break;
            case "dar la pata":
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
        System.out.println(nombre + " duerme profundamente. ¡Zzz!");
    }

    @Override
    public void comportamientoEspecial() {
        System.out.println(nombre + " mueve la cola y ladra felizmente.");
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


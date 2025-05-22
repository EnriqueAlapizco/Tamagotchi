/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;

public class Gato extends TamagotchiBase {

    public Gato(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        switch (comida.toLowerCase()) {
            case "pescado":
                hambre -= 20;
                energia += 10;
                felicidad += 5;
                break;
            case "leche":
                hambre -= 10;
                energia += 5;
                felicidad += 10;
                break;
            case "atun enlatado":
                hambre -= 25;
                energia += 15;
                felicidad += 15;
                break;
            default:
                System.out.println("Comida no reconocida.");
        }
    }

    @Override
    public void jugar(String juego) {
        switch (juego.toLowerCase()) {
            case "perseguir luz":
                felicidad += 20;
                energia -= 15;
                break;
            case "cazar raton de juguete":
                felicidad += 15;
                energia -= 10;
                break;
            case "esconderse":
                felicidad += 10;
                energia -= 5;
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        energia = 100;
        System.out.println(nombre + " está durmiendo acurrucado como un gato.");
    }

    @Override
    public void comportamientoEspecial() {
        System.out.println(nombre + " maúlla y ronronea suavemente.");
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Perseguir luz", "Cazar raton de juguete", "Esconderse"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Pescado", "Leche", "Atun enlatado"};
    }
}

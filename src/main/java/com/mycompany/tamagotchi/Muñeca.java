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
        switch (comida.toLowerCase()) {
            case "te":
                hambre -= 10;
                energia += 10;
                felicidad += 15;
                break;
            case "pastel":
                hambre -= 20;
                energia += 5;
                felicidad += 20;
                break;
            case "dulces":
                hambre -= 15;
                energia += 15;
                felicidad += 10;
                break;
            default:
                System.out.println("Comida no reconocida.");
        }
    }

    @Override
    public void jugar(String juego) {
        switch (juego.toLowerCase()) {
            case "jugar a las muñecas":
                felicidad += 25;
                energia -= 10;
                break;
            case "contar historias":
                felicidad += 15;
                energia -= 5;
                break;
            case "bailar":
                felicidad += 20;
                energia -= 15;
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        energia = 100;
        System.out.println(nombre + " duerme tranquila y feliz.");
    }

    @Override
    public void comportamientoEspecial() {
        System.out.println(nombre + " dice que esta aburrida o feliz, y a veces llora.");
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Jugar a las muñecas", "Contar historias", "Bailar"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Te", "Pastel", "Dulces"};
    }
}

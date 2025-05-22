/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;

public class Cuyo extends TamagotchiBase {

    public Cuyo(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        switch (comida.toLowerCase()) {
            case "zanahoria":
                hambre -= 20;
                energia += 10;
                felicidad += 5;
                break;
            case "heno":
                hambre -= 15;
                energia += 15;
                felicidad += 10;
                break;
            case "lechuga":
                hambre -= 10;
                energia += 5;
                felicidad += 15;
                break;
            default:
                System.out.println("Comida no reconocida.");
        }
    }

    @Override
    public void jugar(String juego) {
        switch (juego.toLowerCase()) {
            case "roer madera":
                felicidad += 25;
                energia -= 15;
                break;
            case "explorar":
                felicidad += 15;
                energia -= 10;
                break;
            case "correr en rueda":
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
        System.out.println(nombre + " duerme en su casita.");
    }

    @Override
    public void comportamientoEspecial() {
        System.out.println(nombre + " hace cuy cuy y disfruta roer la madera.");
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Roer madera", "Explorar", "Correr en rueda"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Zanahoria", "Heno", "Lechuga"};
    }
}

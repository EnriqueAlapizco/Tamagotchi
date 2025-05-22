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
        switch (comida.toLowerCase()) {
            case "croquetas":
                hambre -= 20;
                energia += 10;
                felicidad += 5;
                break;
            case "hueso":
                hambre -= 10;
                energia += 5;
                felicidad += 15;
                break;
            case "pollo":
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
            case "lanzar pelota":
                felicidad += 20;
                energia -= 15;
                break;
            case "perseguir cola":
                felicidad += 10;
                energia -= 5;
                break;
            case "buscar":
                felicidad += 15;
                energia -= 10;
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        energia = 100;
        System.out.println(nombre + " está durmiendo como un buen perrito.");
    }

    @Override
    public void comportamientoEspecial() {
        System.out.println(nombre + " ladra felizmente y persigue su cola.");
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Lanzar pelota", "Perseguir cola", "Buscar"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Croquetas", "Hueso", "Pollo"};
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;
import java.util.Random;

public class Cuyo extends TamagotchiBase {

     private final String[] comportamientos = {
        "hace 'cuy cuy' mientras corre",
        "da vueltas en círculos",
        "salta como si estuviera feliz"
    };
     
    public Cuyo(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        if (!tieneHambre()) return;

        switch (comida.toLowerCase()) {
            case "zanahoria":
                reducirHambre(15);
                aumentarEnergia(10);
                aumentarFelicidad(10);
                break;
            case "lechuga":
                reducirHambre(10);
                aumentarEnergia(5);
                aumentarFelicidad(5);
                break;
            case "pepino":
                reducirHambre(20);
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
            case "rodar en la rueda":
                if (!tieneEnergiaPara(15)) return;
                aumentarFelicidad(20);
                reducirEnergia(15);
                aumentarHambre(20);
                break;
            case "explorar laberinto":
                if (!tieneEnergiaPara(20)) return;
                aumentarFelicidad(25);
                reducirEnergia(20);
                aumentarHambre(10);
                break;
            case "correr en círculo":
                if (!tieneEnergiaPara(10)) return;
                aumentarFelicidad(15);
                reducirEnergia(10);
                aumentarHambre(15);
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        energia = 100;
        System.out.println(nombre + " duerme mientras hace sonidos de 'cuy cuy'.");
    }

    @Override
    public void comportamientoEspecial() {
        Random random = new Random();
        int index = random.nextInt(comportamientos.length);
        System.out.println(nombre + " " + comportamientos[index] + ".");
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Rodar en la rueda", "Explorar laberinto", "Correr en círculo"};
    }

    @Override
    public String[] getComidas() {
        return new String[]{"Zanahoria", "Lechuga", "Pepino"};
    }
}


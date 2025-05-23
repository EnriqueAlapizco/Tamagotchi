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
        "salta como si estuviera feliz",
        "se pone a roer un trozo de madera imaginario" // NUEVA ACCIÓN
    };
     
    public Cuyo(String nombre) {
        super(nombre);
    }

    @Override
    public void alimentar(String comida) {
        if (!puedeSerAlimentado()) return; // Changed from tieneHambre()

        switch (comida.toLowerCase()) {
            case "zanahoria":
                aumentarHambrePorAlimentacion(15); // Changed from reducirHambreAlimentar
                aumentarEnergia(10);
                aumentarFelicidad(10);
                break;
            case "lechuga":
                aumentarHambrePorAlimentacion(10); // Changed from reducirHambreAlimentar
                aumentarEnergia(5);
                aumentarFelicidad(5);
                break;
            case "pepino":
                aumentarHambrePorAlimentacion(20); // Changed from reducirHambreAlimentar
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
                disminuirEnergia(15);
                disminuirHambrePorJuego(20); // Changed from aumentarHambreJugar
                break;
            case "explorar laberinto":
                if (!tieneEnergiaPara(20)) return;
                aumentarFelicidad(25);
                disminuirEnergia(20);
                disminuirHambrePorJuego(10); // Changed from aumentarHambreJugar
                break;
            case "correr en círculo":
                if (!tieneEnergiaPara(10)) return;
                aumentarFelicidad(15);
                disminuirEnergia(10);
                disminuirHambrePorJuego(15); // Changed from aumentarHambreJugar
                break;
            default:
                System.out.println("Juego no reconocido.");
        }
    }

    @Override
    public void dormir() {
        this.energia = 100;
        this.estaDurmiendo = true; // Marcar como durmiendo
        System.out.println(nombre + " duerme mientras hace sonidos de 'cuy cuy'.");
    }

    @Override
    public String realizarAccionEspecial() {
        Random random = new Random();
        int index = random.nextInt(comportamientos.length);
        return nombre + " " + comportamientos[index] + ".";
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


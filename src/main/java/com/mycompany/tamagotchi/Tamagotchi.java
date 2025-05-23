/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tamagotchi;

import java.util.*;

public class Tamagotchi {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TamagotchiBase mascota = null;

        System.out.println("Bienvenido a Tamagotchi");
        System.out.println("Elige tu mascota:");
        System.out.println("1. Perro");
        System.out.println("2. Gato");
        System.out.println("3. Muñeca");
        System.out.println("4. Cuyo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        System.out.print("Ingresa el nombre de tu mascota: ");
        String nombre = scanner.nextLine();

        switch (opcion) {
            case 1: mascota = new Perro(nombre); break;
            case 2: mascota = new Gato(nombre); break;
            case 3: mascota = new Muñeca(nombre); break;
            case 4: mascota = new Cuyo(nombre); break;
            default:
                System.out.println("Opcion invalida.");
                return;
        }

        int eleccion;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Alimentar");
            System.out.println("2. Jugar");
            System.out.println("3. Dormir");
            System.out.println("4. Mostrar estado");
            System.out.println("5. Accion especial");
            System.out.println("0. Salir");

            eleccion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (eleccion) {
                case 1:
                    String[] comidas = mascota.getComidas();
                    System.out.println("Comidas disponibles:");
                    for (int i = 0; i < comidas.length; i++) {
                        System.out.println((i + 1) + ". " + comidas[i]);
                    }
                    System.out.print("Elige una comida: ");
                    int comidaIndex = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    if (comidaIndex >= 1 && comidaIndex <= comidas.length) {
                        mascota.alimentar(comidas[comidaIndex - 1]);
                    } else {
                        System.out.println("Opcion de comida invalida.");
                    }
                    break;

                case 2:
                    String[] juegos = mascota.getJuegos();
                    System.out.println("Juegos disponibles:");
                    for (int i = 0; i < juegos.length; i++) {
                        System.out.println((i + 1) + ". " + juegos[i]);
                    }
                    System.out.print("Elige un juego: ");
                    int juegoIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (juegoIndex >= 1 && juegoIndex <= juegos.length) {
                        mascota.jugar(juegos[juegoIndex - 1]);
                    } else {
                        System.out.println("Opcion de juego invalida.");
                    }
                    break;

                case 3:
                    mascota.dormir();
                    break;
                case 4:
                    mascota.mostrarEstado();
                    break;
                case 5:
                    mascota.realizarAccionEspecial();
                    break;
                case 0:
                    System.out.println("Hasta luego");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (eleccion != 0);

        scanner.close();
    }
}
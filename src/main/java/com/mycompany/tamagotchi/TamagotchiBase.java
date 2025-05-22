/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tamagotchi;


public abstract class TamagotchiBase implements TamagotchiActions {
    protected String nombre;
    protected int hambre;
    protected int energia;
    protected int felicidad;

    public TamagotchiBase(String nombre) {
        this.nombre = nombre;
        this.hambre = 50;
        this.energia = 50;
        this.felicidad = 50;
    }

    public void mostrarEstado() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Hambre: " + hambre);
        System.out.println("Energia: " + energia);
        System.out.println("Felicidad: " + felicidad);
    }

    public abstract String[] getJuegos();
    public abstract String[] getComidas();
    
    
    protected boolean tieneEnergiaPara(int cantidad) {
        if (energia < cantidad) {
            System.out.println(nombre + " no tiene suficiente energía para hacer esto.");
            return false;
        }
        return true;
    }

    protected boolean tieneHambre() {
        if (hambre <= 0) {
            System.out.println(nombre + " no tiene hambre.");
            return false;
        }
        return true;
    }
    
    protected void aumentarFelicidad(int valor) {
        felicidad = Math.min(100, felicidad + valor);
    }

    protected void aumentarEnergia(int valor) {
        energia = Math.min(100, energia + valor);
    }

    protected void aumentarHambre(int valor) {
        hambre = Math.min(100, hambre + valor);
    }

    protected void reducirEnergia(int valor) {
        energia = Math.max(0, energia - valor);
    }

    protected void reducirHambre(int valor) {
        hambre = Math.max(0, hambre - valor);
    }
    

    void comportamientoEspecial() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

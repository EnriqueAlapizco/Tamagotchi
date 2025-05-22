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

    void comportamientoEspecial() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

package org.example.proyecto_final_javefx.model;

//atributos
public class Estudiante extends Usuario {
    private double descuento;

    //constructor
    public Estudiante(String nombre,String id, int edad, int telefono, double descuento) {
        super(nombre, id, edad, telefono);
        this.descuento = descuento;
    }

    //get y set
    public double getDescuento() {
        return descuento;
    }
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    //to string
    @Override
    public String toString() {
        return "Estudiante{" +
                "descuento=" + descuento +
                "} " + super.toString();
    }
}

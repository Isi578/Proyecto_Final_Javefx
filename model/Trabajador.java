package org.example.proyecto_final_javefx.model;


public class Trabajador extends Usuario{
    private String beneficios;

    public Trabajador(String nombre, String id, int edad, int telefono, String beneficios) {
        super(nombre, id, edad, telefono);
        this.beneficios = beneficios;
    }
}

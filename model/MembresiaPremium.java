package org.example.proyecto_final_javefx.model;

public class MembresiaPremium extends Membresia{
    private String mquinas;
    // clases grupales


    public MembresiaPremium(tipoMembresia tipoMembresia, double costo, String inicio, String fin, boolean estado, String mquinas) {
        super(tipoMembresia, costo, inicio, fin, estado);
        this.mquinas = mquinas;
    }
}
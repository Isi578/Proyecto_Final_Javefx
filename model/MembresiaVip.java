package org.example.proyecto_final_javefx.model;
public class MembresiaVip extends Membresia{
    private String maquinas;
    private boolean spa;
    //asignar entrenador
    //asociar clases grupales

    public MembresiaVip(tipoMembresia tipoMembresia, double costo, String inicio, String fin, boolean estado, String maquinas,boolean spa) {
        super(tipoMembresia, costo, inicio, fin, estado);
        this.maquinas = maquinas;
        this.spa = spa;
    }
}

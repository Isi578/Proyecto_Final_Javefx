package org.example.proyecto_final_javefx.model;

//atributos
public class MembresiaBasica extends Membresia {
    private String maquinas;

    //constructor
    public MembresiaBasica(double costo, String inicio, String fin, boolean estado, String maquinas) {
        super(costo, inicio, fin, estado);
        this.maquinas = maquinas;
    }

    //get y set
    public String getMaquinas() {
        return maquinas;
    }
    public void setMaquinas(String maquinas) {
        this.maquinas = maquinas;
    }

    //to string
    @Override
    public String toString() {
        return "MembresiaBasica{" +
                "maquinas='" + maquinas + '\'' +
                "} " + super.toString();
    }
}
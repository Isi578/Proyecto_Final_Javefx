package org.example.proyecto_final_javefx.model;


//atributos
public class Membresia {
    private tipoMembresia tipoMembresia;
    private double costo;
    private String inicio;
    private String fin;
    private boolean estado;

    //contructor
    public Membresia (tipoMembresia tipoMembresia, double costo, String inicio, String fin, boolean estado){
        this.tipoMembresia = tipoMembresia;
        this.costo = costo;
        this. inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }
    //get y set
    public tipoMembresia getTipoMembresia (){
        return tipoMembresia;
    }
    public void setTipoMembresia (tipoMembresia tipoMembresia){
        this.tipoMembresia = tipoMembresia;
    }

    public double getCosto (){
        return costo;
    }
    public void setCosto (double costo){
        this.costo = costo;
    }
    public String getInicio() {
        return inicio;
    }
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }
    public void setFin(String fin) {
        this.fin = fin;
    }

    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    //to string
    @Override
    public String toString() {
        return "Membresia{" +
                "tipoMembresia=" + tipoMembresia +
                "costo=" + costo + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", estado=" + estado +
                '}';
    }
}


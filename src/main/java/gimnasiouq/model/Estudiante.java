package gimnasiouq.model;

public class Estudiante extends Usuario {
    private double descuento;

    public Estudiante(String nombre, String id, int edad, String telefono, String membresia, double descuento) {
        super(nombre, id, edad, telefono, membresia);
        this.descuento = descuento;
    }
//Get y Set
    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}

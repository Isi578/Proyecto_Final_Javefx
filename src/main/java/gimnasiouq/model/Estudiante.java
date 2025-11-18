package gimnasiouq.model;

//atributos
    public class Estudiante extends Usuario {
        private double descuento;

//constructor
    public Estudiante(String nombre, String id, int edad, String telefono, String membresia, double descuento) {
        super(nombre, id, edad, telefono, membresia);
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

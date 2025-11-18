package gimnasiouq.model;

    public class Trabajador extends Usuario{

//constructor
    public Trabajador(String nombre, String identificacion, int edad, String celular, String membresia) {
        super(nombre, identificacion, edad, celular, membresia);
    }

//to string
    @Override
    public String toString() {
        return "Trabajador{} " + super.toString();
    }
}

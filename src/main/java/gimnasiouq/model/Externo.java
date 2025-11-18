package gimnasiouq.model;

    public class Externo extends Usuario {

//constructor
    public Externo(String nombre, String identificacion, int edad, String celular, String membresia) {
        super(nombre, identificacion, edad, celular, membresia);
    }

// to string
        @Override
        public String toString() {
            return "Externo{} " + super.toString();
        }
    }

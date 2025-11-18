package gimnasiouq.model;

//atributos
    public class Trabajador extends Usuario{
        private String beneficios;

//constructor
    public Trabajador(String nombre, String id, int edad, String telefono, String membresia, String beneficios) {
        super(nombre, id, edad, telefono, membresia);
        this.beneficios = beneficios;
    }

//get y set
    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

//to string
    @Override
    public String toString() {
        return "Trabajador{" +
                "beneficios='" + beneficios + '\'' +
                "} " + super.toString();
    }
}

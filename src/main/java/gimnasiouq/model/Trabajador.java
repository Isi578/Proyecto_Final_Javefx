package gimnasiouq.model;

public class Trabajador extends Usuario{
    private String beneficios;

    public Trabajador(String nombre, String id, int edad, String telefono, String membresia, String beneficios) {
        super(nombre, id, edad, telefono, membresia);
        this.beneficios = beneficios;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }
}

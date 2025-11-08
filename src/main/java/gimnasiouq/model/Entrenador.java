package gimnasiouq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Entrenador {

    // Lista estática para almacenar todos los entrenadores registrados
    private static final List<Entrenador> entrenadoresRegistrados = new ArrayList<>();

    // Atributos
    private String nombre;
    private String id;
    private int edad;
    private String correo;
    private String cargo;

    // Constructor
    public Entrenador(String nombre, String id, int edad, String correo, String cargo) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.correo = correo;
        this.cargo = cargo;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }


    public boolean registrarEntrenador() {
        if (verificarRegistro(this.id)) {
            System.out.println("El entrenador con ID: " + this.id + " ya se encuentra registrado.");
            return false;
        }
        entrenadoresRegistrados.add(this);
        System.out.println("Entrenador con ID: " + this.id + " ha sido registrado exitosamente.");
        return true;
    }

    public static boolean verificarRegistro(String idVerificar) {
        return entrenadoresRegistrados.stream().anyMatch(e -> e.getId().equals(idVerificar));
    }


    public static String modificarRegistro(String id, String nuevoNombre, int nuevaEdad, String nuevoCorreo, String nuevoCargo) {
        Optional<Entrenador> entrenadorOpt = entrenadoresRegistrados.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (entrenadorOpt.isPresent()) {
            Entrenador entrenador = entrenadorOpt.get();
            entrenador.setNombre(nuevoNombre);
            entrenador.setEdad(nuevaEdad);
            entrenador.setCorreo(nuevoCorreo);
            entrenador.setCargo(nuevoCargo);
            return "Se ha modificado exitosamente el registro del entrenador con ID: " + id;
        } else {
            return "No se pudo modificar. No existe un entrenador con el ID: " + id;
        }
    }

    /**
     * Elimina un entrenador del registro.
     *
     * @param id El ID del entrenador a eliminar.
     * @return Un mensaje indicando si la eliminación fue exitosa o no.
     */
    public static String eliminarEntrenador(String id) {
        boolean removed = entrenadoresRegistrados.removeIf(e -> e.getId().equals(id));
        if (removed) {
            return "Se ha eliminado exitosamente el entrenador con ID: " + id;
        } else {
            return "No se pudo eliminar. No existe un entrenador con el ID: " + id;
        }
    }

    /**
     * Devuelve la lista de todos los entrenadores registrados.
     *
     * @return Una lista de {@link Entrenador}.
     */
    public static List<Entrenador> getEntrenadoresRegistrados() {
        return new ArrayList<>(entrenadoresRegistrados);
    }

    @Override
    public String toString() {
        return "Entrenador{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}

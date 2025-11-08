package org.example.proyecto_final_javefx.model;

//atributos
public class Usuario {
    private String nombre;
    private String id;
    private int edad;
    private int telefono;

    //constuctor
    public Usuario (String nombre,String id, int edad, int telefono){
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.telefono = telefono;
    }

    //get y set
    public String getNombre (){
        return nombre;
    }
    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    //to string
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", edad=" + edad +
                ", telefono=" + telefono +
                '}';
    }

    public boolean verificarRegistro(String idVerificar) {
        if (this.id.equals(idVerificar)) {
            System.out.println("El usuario con ID: " + idVerificar + " ya se encuentra registrado");
            return true;
        } else {
            System.out.println("El usuario con ID: " + idVerificar + " no se encuentra registrado");
            return false;
        }
    }

    //metodo para registrar un entrenador
    public String registrarUsuario (String id){
        if (!verificarRegistro(id)) {
            this.id = id;
            return "Se ha registrado exitosamente el usuario:\n" +
                    "Nombre: " + this.nombre + "\n" +
                    "ID: " + this.id + "\n" +
                    "Edad: " + this.edad + "\n";
        } else {
            return "No se pudo registrar. Ya existe un usuario con el ID: " + id;
        }
    }
}

package gimnasiouq.model;
//atributos
public class ControlAcceso {
    private Usuario usuario;
    private String fecha;
    private String hora;

//cosntructor
    public ControlAcceso(Usuario usuario, String fecha, String hora) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
    }

//get y set
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

//to string
    @Override
    public String toString() {
        return "ControlAcceso{" +
                "usuario=" + usuario +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}

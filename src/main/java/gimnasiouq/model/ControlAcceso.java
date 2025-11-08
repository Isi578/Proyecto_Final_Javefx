package gimnasiouq.model;

public class ControlAcceso {
    private Usuario usuario;
    private String fecha;
    private String hora;

    public ControlAcceso(Usuario usuario, String fecha, String hora) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
    }

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
}

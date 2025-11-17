package gimnasiouq.model;
//atributos
public class ControlAcceso {
    private Usuario usuario;
    private String fecha;
    private String hora;
    private String tipoMembresia;
    private boolean estado;

//cosntructor
    public ControlAcceso(Usuario usuario, String fecha, String hora, String tipoMembresia, boolean estado) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.tipoMembresia = tipoMembresia;
        this.estado = estado;
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

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "ControlAcceso{" +
                "usuario=" + usuario +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", tipoMembresia='" + tipoMembresia + '\'' +
                ", estado=" + estado +
                '}';
    }
}

package org.example.proyecto_final_javefx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Gimnasio {
    private List<Usuario> listaUsuarios;
    private List<Recepcionista> listaRecepcionista;
    private List<Administador> listaAdministrador;
    private List<ReservaClase> listaReservaClases;
    private List<Entrenador> listaEntrenador;
    private List<ControlAcceso> listaRegistrosAcceso;

    public Gimnasio() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRecepcionista = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();
        this.listaReservaClases = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = new ArrayList<>();
    }

    private LocalDate parseFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
            } catch (DateTimeParseException ex) {
                return null;
            }
        }
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Recepcionista> getListaRecepcionista() {
        return listaRecepcionista;
    }

    public void setListaRecepcionista(List<Recepcionista> listaRecepcionista) {
        this.listaRecepcionista = listaRecepcionista;
    }

    public List<Administador> getListaAdministrador() {
        return listaAdministrador;
    }

    public void setListaAdministrador(List<Administador> listaAdministrador) {
        this.listaAdministrador = listaAdministrador;
    }

    public List<ReservaClase> getListaReservaClases() {
        return listaReservaClases;
    }

    public void setListaClases(List<ReservaClase> listaReservaClases) {
        this.listaReservaClases = listaReservaClases;
    }

    public List<Entrenador> getListaEntrenador() {
        return listaEntrenador;
    }

    public void setListaEntrenador(List<Entrenador> listaEntrenador) {
        this.listaEntrenador = listaEntrenador;
    }

    public void setListaRegistrosAcceso(List<ControlAcceso> listaRegistrosAcceso) {
        this.listaRegistrosAcceso = listaRegistrosAcceso;
    }

}
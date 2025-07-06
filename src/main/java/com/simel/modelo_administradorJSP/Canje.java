package com.simel.modelo_administradorJSP;

import java.util.Date;

public class Canje {
    private String alumnoNombre;
    private String premioNombre;
    private String codigoCanje;
    private String estado;
    private Date fechaCanje;

    // Getters y setters
    public String getAlumnoNombre() {
        return alumnoNombre;
    }
    public void setAlumnoNombre(String alumnoNombre) {
        this.alumnoNombre = alumnoNombre;
    }

    public String getPremioNombre() {
        return premioNombre;
    }
    public void setPremioNombre(String premioNombre) {
        this.premioNombre = premioNombre;
    }

    public String getCodigoCanje() {
        return codigoCanje;
    }
    public void setCodigoCanje(String codigoCanje) {
        this.codigoCanje = codigoCanje;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCanje() {
        return fechaCanje;
    }
    public void setFechaCanje(Date fechaCanje) {
        this.fechaCanje = fechaCanje;
    }
}

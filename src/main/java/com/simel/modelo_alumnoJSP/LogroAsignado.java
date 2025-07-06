package com.simel.modelo_alumnoJSP;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogroAsignado {
    private String nombreLogro;
    private String nombreCurso;
    private int puntos;
    private LocalDateTime fechaAsignacion;

    // Getters y Setters
    public String getNombreLogro() {
        return nombreLogro;
    }

    public void setNombreLogro(String nombreLogro) {
        this.nombreLogro = nombreLogro;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    // ✅ Método para obtener solo la fecha formateada como String
    public String getFechaSoloFormato() {
        if (fechaAsignacion != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fechaAsignacion.format(formatter);
        } else {
            return "";
        }
    }
}

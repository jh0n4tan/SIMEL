package com.simel.modelo_alumnoJSP;

public class Reto {
    private int id;
    private String nombre;
    private String descripcion;
    private int puntos;
    private int idGradoSeccion; // <-- nombre más correcto

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getIdGradoSeccion() {
        return idGradoSeccion;
    }

    public void setIdGradoSeccion(int idGradoSeccion) {
        this.idGradoSeccion = idGradoSeccion;
    }
}

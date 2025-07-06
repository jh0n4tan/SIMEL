package com.simel.modelo_administradorJSP;

public class Reto {

    private int id;
    private String nombre;
    private String descripcion;
    private int puntos;
    private int idGradoSeccion;

    // 👇 Nuevos campos para mostrar en el frontend
    private int grado;
    private String seccion;

    public Reto() {
    }

    public Reto(String nombre, String descripcion, int puntos, int idGradoSeccion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.idGradoSeccion = idGradoSeccion;
    }

    // Getters y setters
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

    // 👇 Getters y setters nuevos
    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}

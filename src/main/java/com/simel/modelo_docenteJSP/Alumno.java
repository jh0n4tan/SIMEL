package com.simel.modelo_docenteJSP;

public class Alumno {
    private int id;
    private String nombre;
    private int puntos;
    private String medalla; // Nuevo campo

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public String getMedalla() { return medalla; }
    public void setMedalla(String medalla) { this.medalla = medalla; }
}


package com.simel.modelo_administradorJSP;

public class AlumnoAsignacion {

    private int id;
    private String nombre;
    private int idLogin;
    private int idGradoSeccion;
    private int puntosTotales;

    // Constructor vacío
    public AlumnoAsignacion() {
    }

    // Constructor completo
    public AlumnoAsignacion(int id, String nombre, int idLogin, int idGradoSeccion, int puntosTotales) {
        this.id = id;
        this.nombre = nombre;
        this.idLogin = idLogin;
        this.idGradoSeccion = idGradoSeccion;
        this.puntosTotales = puntosTotales;
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

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public int getIdGradoSeccion() {
        return idGradoSeccion;
    }

    public void setIdGradoSeccion(int idGradoSeccion) {
        this.idGradoSeccion = idGradoSeccion;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }
}

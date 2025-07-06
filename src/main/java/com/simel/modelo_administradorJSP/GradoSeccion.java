package com.simel.modelo_administradorJSP;

public class GradoSeccion {

    private int id;
    private int grado;
    private String seccion;

    public GradoSeccion() {
    }

    public GradoSeccion(int id, int grado, String seccion) {
        this.id = id;
        this.grado = grado;
        this.seccion = seccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

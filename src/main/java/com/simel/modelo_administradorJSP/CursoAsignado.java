package com.simel.modelo_administradorJSP;

public class CursoAsignado {

    private String docenteNombre;
    private String cursoNombre;
    private int grado;
    private String seccion;

    public CursoAsignado() {
    }

    public CursoAsignado(String docenteNombre, String cursoNombre, int grado, String seccion) {
        this.docenteNombre = docenteNombre;
        this.cursoNombre = cursoNombre;
        this.grado = grado;
        this.seccion = seccion;
    }

    public String getDocenteNombre() {
        return docenteNombre;
    }

    public void setDocenteNombre(String docenteNombre) {
        this.docenteNombre = docenteNombre;
    }

    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
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

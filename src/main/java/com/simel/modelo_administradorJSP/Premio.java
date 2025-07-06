package com.simel.modelo_administradorJSP;

public class Premio {

    private int id;
    private String nombre;
    private String descripcion;
    private int puntosRequeridos;
    private String tipo;  // "físico" o "digital"
    private String imagen; // ruta relativa de la imagen

    public Premio() {
    }

    public Premio(String nombre, String descripcion, int puntosRequeridos, String tipo, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    // Getters y setters...
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

    public int getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(int puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

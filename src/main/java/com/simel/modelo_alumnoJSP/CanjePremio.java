package com.simel.modelo_alumnoJSP;

public class CanjePremio {
    private String codigo;  // código de canje
    private String estado;  // estado del canje (e.g. 'Canje digital exitoso')
    private String nombre;  // nombre del premio (de la tabla premio)
    private String tipo;    // tipo de premio ('digital' o 'físico')

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

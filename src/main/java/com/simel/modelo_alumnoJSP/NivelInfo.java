package com.simel.modelo_alumnoJSP;

public class NivelInfo {
    private int nivel;
    private int puntosActuales;
    private int puntosMinimos;
    private int puntosMaximos;
    private String nombreNivel;
    private String colorBarra;  // NUEVO

    public NivelInfo(int nivel, int puntosActuales, int puntosMinimos, int puntosMaximos) {
        this.nivel = nivel;
        this.puntosActuales = puntosActuales;
        this.puntosMinimos = puntosMinimos;
        this.puntosMaximos = puntosMaximos;
        this.nombreNivel = obtenerNombreNivel(nivel);
        this.colorBarra = obtenerColorBarra(nivel);  // NUEVO
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntosActuales() {
        return puntosActuales;
    }

    public int getPuntosMinimos() {
        return puntosMinimos;
    }

    public int getPuntosMaximos() {
        return puntosMaximos;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public String getColorBarra() {
        return colorBarra;
    }

    public int getPorcentajeAvance() {
        return (int) ((double) (puntosActuales - puntosMinimos) / (puntosMaximos - puntosMinimos) * 100);
    }

    private String obtenerNombreNivel(int nivel) {
        switch (nivel) {
            case 0: return "Principiante";
            case 1: return "Aprendiz";
            case 2: return "Intermedio";
            case 3: return "Avanzado";
            case 4: return "Experto";
            default: return "Maestro";
        }
    }

    private String obtenerColorBarra(int nivel) {
        switch (nivel) {
            case 0: return "bg-danger";    // rojo
            case 1: return "bg-warning";   // amarillo
            case 2: return "bg-info";      // celeste
            case 3: return "bg-primary";   // azul
            case 4: return "bg-success";   // verde
            default: return "bg-dark";     // gris oscuro
        }
    }
}


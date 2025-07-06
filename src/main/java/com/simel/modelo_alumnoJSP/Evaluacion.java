package com.simel.modelo_alumnoJSP;

public class Evaluacion {
    private double notaEva1;
    private double notaEva2;
    private double notaEva3;
    private double promedio;
    private String comentario;

    // Constructor
    public Evaluacion(double notaEva1, double notaEva2, double notaEva3, double promedio, String comentario) {
        this.notaEva1 = notaEva1;
        this.notaEva2 = notaEva2;
        this.notaEva3 = notaEva3;
        this.promedio = promedio;
        this.comentario = comentario;
    }

    // Getters y Setters
    public double getNotaEva1() { return notaEva1; }
    public void setNotaEva1(double notaEva1) { this.notaEva1 = notaEva1; }

    public double getNotaEva2() { return notaEva2; }
    public void setNotaEva2(double notaEva2) { this.notaEva2 = notaEva2; }

    public double getNotaEva3() { return notaEva3; }
    public void setNotaEva3(double notaEva3) { this.notaEva3 = notaEva3; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}

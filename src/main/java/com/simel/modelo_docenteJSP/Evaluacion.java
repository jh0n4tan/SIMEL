
package com.simel.modelo_docenteJSP;

public class Evaluacion {
    private double nota1;
    private double nota2;
    private double nota3;
    private double promedio;   // Nuevo
    private String comentario;

    // getters y setters
    public double getNota1() { return nota1; }
    public void setNota1(double nota1) { this.nota1 = nota1; }
    public double getNota2() { return nota2; }
    public void setNota2(double nota2) { this.nota2 = nota2; }
    public double getNota3() { return nota3; }
    public void setNota3(double nota3) { this.nota3 = nota3; }
    
    public double getPromedio() { return promedio; }    // Nuevo
    public void setPromedio(double promedio) { this.promedio = promedio; }  // Nuevo
    
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}


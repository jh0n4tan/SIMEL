
package com.simel.modelo_docenteJSP;

import java.sql.Timestamp;

public class LogroAsignado {
    private int id;
    private String nombreAlumno;
    private String nombreCurso;
    private int grado;
    private String seccion;
    private String nombreLogro;
    private int puntos;
    private String comentario;
    private Timestamp fechaAsignacion;

    // Getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public int getGrado() { return grado; }
    public void setGrado(int grado) { this.grado = grado; }

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }

    public String getNombreLogro() { return nombreLogro; }
    public void setNombreLogro(String nombreLogro) { this.nombreLogro = nombreLogro; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Timestamp getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(Timestamp fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
}


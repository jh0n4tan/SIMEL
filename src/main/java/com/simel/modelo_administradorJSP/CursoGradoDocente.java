
package com.simel.modelo_administradorJSP;

public class CursoGradoDocente {
    private int id;
    private int idDocente;
    private int idCurso;
    private int idGradoSeccion;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdDocente() { return idDocente; }
    public void setIdDocente(int idDocente) { this.idDocente = idDocente; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public int getIdGradoSeccion() { return idGradoSeccion; }
    public void setIdGradoSeccion(int idGradoSeccion) { this.idGradoSeccion = idGradoSeccion; }
}

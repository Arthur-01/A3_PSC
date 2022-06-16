package a3;

import java.io.Serializable;
import java.util.ArrayList;

public class Aluno extends Pessoa implements Serializable {

    private int matricula;
    private ArrayList<Curso> cursos;

    public Aluno() {
        cursos = new ArrayList<Curso>();
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setCurso(Curso curso) {
        cursos.add(curso);
    }

}

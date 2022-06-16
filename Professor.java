package a3;

import java.io.Serializable;
import java.util.ArrayList;

public class Professor extends Pessoa implements Serializable {

    private int codigoFuncionario;
    private ArrayList<Curso> cursos;

    public Professor() {
        cursos = new ArrayList<Curso>();
    }

    public int getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(int codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public int getCurso(int pk) {
        return cursos.get(pk).getCodigo();
    }

    public void setCurso(Curso curso) {
        cursos.add(curso);
    }

}

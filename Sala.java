package a3;

import java.io.Serializable;

public class Sala implements Serializable {

    private String nome;
    private String local;
    private String periodo;
    private int capacidadeTotal;
    private Curso curso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(int capacidadeTotal) {
        this.capacidadeTotal = capacidadeTotal;
    }

    public String getCurso() {
        return curso.getNome();
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

}

package a3;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {

    private String nome;
    private String descricao;
    private int cargaHoraria;
    private int codigo;
    private Sala sala;
    private Professor professor;
    private ArrayList<Aluno> alunos;

    public Curso() {
        alunos = new ArrayList<Aluno>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void addAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getLocalSala() {
        return sala.getLocal();
    }

    public int getQuantidadeTotalSala() {
        return sala.getCapacidadeTotal();
    }

    public int getQuantidadeAtualSala() {
        return sala.getCapacidadeTotal() - alunos.size();
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getCodigoProfessor() {
        return professor.getCodigoFuncionario();
    }

    public void imprimirTela() {
        System.out.println("\nNome: " + getNome() + "\nProfessor: " + professor.getNome() + "\nAlunos:");
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println((i+1) + " - " + alunos.get(i).getNome());
        }
    }

    @Override
    public String toString() {
        return "\nNome: " + nome + "\nDescrição: " + getDescricao() + "\nProfessor: " + professor.getNome()
        + "\nLocal: " + getLocalSala() + "\nVagas abertas: " + getQuantidadeAtualSala();
    }

}

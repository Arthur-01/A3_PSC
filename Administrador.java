package a3;

public class Administrador extends Pessoa {
    private int login;
    private int senha;

    public Administrador(String nome, int login, int senha){
        super.setNome(nome);
        this.login = login;
        this.senha = senha;
    }
    
    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }
    
    
}

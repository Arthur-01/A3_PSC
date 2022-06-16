package a3;

/**
 *
 * @author arthu
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class A3 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Instanciando objetos e variáveis...
        Scanner entrada = new Scanner(System.in);
        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<Sala> salas = new ArrayList<>();
        ArrayList<Curso> cursos = new ArrayList<>();
        int escolha = 0, pk = 0, pk2 = 0; //pk seria uma espécie de primary key dentro do meu código, para relacionar um objeto com outro.

        //RECUPERA OS OBJETOS DENTRO DO ARQUIVO BINÁRIO...
        String[] arquivos = {"dadosAlunos.dat", "dadosProfessores.dat", "dadosSalas.dat", "dadosCursos.dat"};
        for (int i = 0; i < 4; i++) { //Laço de repetição que busca os dados nos respectivos arquivos e popula seu arraylist...
            try {
                File arq = new File(arquivos[i]);//Abre os arquivos para operações...
                if (arq.exists()) {
                    ObjectInputStream objInput = null;//Instancia a abertura do fluxo de entrada de bytes
                    switch (i) {
                        case 0:
                            objInput = new ObjectInputStream(new FileInputStream(arquivos[i]));//Abre o fluxo de entrada de bytes
                            alunos = (ArrayList<Aluno>) objInput.readObject();//Lê os bytes e recupera os objetos
                        case 1:
                            objInput = new ObjectInputStream(new FileInputStream(arquivos[i]));
                            professores = (ArrayList<Professor>) objInput.readObject();
                        case 2:
                            objInput = new ObjectInputStream(new FileInputStream(arquivos[i]));
                            salas = (ArrayList<Sala>) objInput.readObject();
                        case 3:
                            objInput = new ObjectInputStream(new FileInputStream(arquivos[i]));
                            cursos = (ArrayList<Curso>) objInput.readObject();
                    }
                    objInput.close();//Fecha o fluxo
                }
            } catch (IOException erro1) {
                System.out.printf("Erro: %s", erro1.getMessage());
            } catch (ClassNotFoundException erro2) {
                System.out.printf("Erro: %s", erro2.getMessage());
            }
        }

        //Nome do administrador, seu login e senha para acesso ao sistema...
        Administrador adm = new Administrador("ADM", 123456, 123456);

        ///////////////////////////////////////////ESCOLHAS DO ADMINISTRADOR DA INSTITUIÇÃO (PRINCIPAL)/////////////////////////////////////////
        //Inicio com login e senha, caso digite o errado não dará acesso ao sistema...
        System.out.println("Digite seu login: ");
        escolha = entrada.nextInt();
        if (escolha == adm.getLogin()) {
            System.out.println("Digite sua senha: ");
            escolha = entrada.nextInt();
        } else {
            System.out.println("\nLogin inexistente no sistema.");
            return;
        }
        if (escolha == adm.getSenha()) {
            System.out.println("Olá, " + adm.getNome());
            System.out.println("\tAcesso total ao sistema");
            //Inicia o loop...
            while (true) {
                System.out.println("\n1 - Cadastrar Professor");
                System.out.println("2 - Cadastrar Aluno");
                System.out.println("3 - Matricular Aluno em um Curso");
                System.out.println("4 - Cadastrar Cursos");
                System.out.println("5 - Cadastrar Salas");
                System.out.println("6 - Listar Cursos");
                System.out.println("10 - Sair");
                System.out.print("Digite o que deseja fazer: ");
                escolha = entrada.nextInt();
                entrada.nextLine();
                switch (escolha) {
                    //Cadastra os professores e grava no arquivo binário de persistência...
                    case 1:
                        gravarProfessores(professores, entrada);
                        int numArray = 1;
                        gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
                        break;
                    //Cadastra o aluno e grava no arquivo binário de persistência...
                    case 2:
                        gravarAlunos(alunos, entrada, pk, pk2);
                        numArray = 0;
                        gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
                        break;
                    //Matricula um aluno em um curso...    
                    case 3:
                        //Caso não tenha alunos cadastrados e nem cursos, o sistema avisa e volta...
                        if (alunos.isEmpty()) {
                            System.out.println("\nSem alunos cadastrados.");
                            break;
                        } else if (cursos.isEmpty()) {
                            System.out.println("\nSem cursos cadastrados.");
                            break;
                        }
                        System.out.print("Digite o número de matrícula: ");
                        pk = entrada.nextInt();
                        for (int i = 0; i < alunos.size(); i++) {
                            //Caso o usuário tenha a matrícula registrada no sistema ele da boas vindas e guarda a posição do array em que o usuário está...
                            if (alunos.get(i).getMatricula() == pk) {
                                System.out.println("\nAluno: " + alunos.get(i).getNome());
                                pk2 = i;
                                break;
                            }
                        }
                        //Caso o usuário coloque uma matrícula que não existe ele dá uma resposta e para o sistema...
                        if (alunos.get(pk2).getMatricula() != pk) {
                            System.out.println("\nMatrícula não encontrada no sistema.");
                            break;
                        }
                        //Mostra os cursos e matricula o aluno caso já não esteja matriculado...
                        mostrarCursos(cursos);
                        matriculaAlunos(alunos, cursos, entrada, pk, pk2);
                        numArray = 0;
                        gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
                        numArray = 3;
                        gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
                        break;
                    //Cadastro de cursos...
                    case 4:
                            //Caso não tenha salas cadastradas e nem professores, o sistema avisa...
                            if (salas.isEmpty()) {
                                System.out.println("\nSem salas cadastradas.");
                                break;
                            } else if (professores.isEmpty()) {
                                System.out.println("\nSem professores cadastrados.");
                                break;
                            } else {
                                //Cadastra o curso e já grava no arquivo binário de persistência...
                                numArray = 0;
                                gravarCursos(cursos, salas, professores, alunos, entrada, numArray);
                                break;
                            }
                    //Cadastro de salas...
                    case 5:
                            gravarSalas(salas, entrada);
                            numArray = 2;
                            gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
                            break;
                    //Listagem de todos os cursos com o nome do curso, professor e alunos matriculados nele...
                    case 6:
                        for (int i = 0; i < cursos.size(); i++) {
                            cursos.get(i).imprimirTela();
                        }
                        break;
                }
                //Saída do loop...
                if (escolha == 10) {
                    System.out.println("\nAté mais!");
                    break;
                }
            }
        //Resposta caso o login esteja certo e a senha não...
        } else {
            System.out.println("\nSenha inexistente no sistema.");
        }
    }

    public static int gravarAlunos(ArrayList<Aluno> alunos, Scanner entrada, int pk, int pk2) {
        Aluno novoA = new Aluno();
        boolean ok;

        System.out.println("\n\tCadastro de Aluno");

        System.out.print("\nNome: ");
        novoA.setNome(entrada.nextLine());

        System.out.print("\nCPF: ");
        novoA.setCpf(entrada.nextLine());

        System.out.print("\nEndereço: ");
        novoA.setEndereco(entrada.nextLine());

        System.out.print("\nE-mail: ");
        novoA.setEmail(entrada.nextLine());

        novoA.setMatricula(alunos.size() + 1);//O número de matrícula é o tamanho do vetor + 1...

        //Laço para tratar o erro caso o usuário digite uma letra ao invés de um número...
        do {
            try {
                ok = true;
                System.out.print("\nNúmero do Celular: ");
                novoA.setCelular(entrada.nextInt());

            } catch (InputMismatchException e) {
                System.out.println("Oops, digite apenas números.");
                entrada.nextLine();//Limpa o buffer
                ok = false;
            }
        } while (!ok);
        alunos.add(novoA);
        System.out.println("Aluno cadastrado com sucesso!");
        System.out.println("O número de matrícula do aluno é: " + alunos.get(alunos.size() - 1).getMatricula());
        return pk;
    }

    public static void gravarProfessores(ArrayList<Professor> professores, Scanner entrada) {
        Professor novoP = new Professor();
        boolean ok;

        System.out.println("\n\tCadastro de Professor");
        
        System.out.print("\nNome: ");
        novoP.setNome(entrada.nextLine());

        System.out.print("\nCPF: ");
        novoP.setCpf(entrada.nextLine());

        System.out.print("\nEndereço: ");
        novoP.setEndereco(entrada.nextLine());

        System.out.print("\nE-mail: ");
        novoP.setEmail(entrada.nextLine());

        novoP.setCodigoFuncionario(professores.size() + 1);//Denomina o código de funcionário como o tamanho da lista + 1...

        //Laço para caso o usuário digite uma letra no lugar de um número...
        do {
            try {
                ok = true;
                System.out.print("\nNúmero do Celular: ");
                novoP.setCelular(entrada.nextInt());
            } catch (InputMismatchException e) {
                System.out.println("Oops, digite apenas números.");
                entrada.nextLine();
                ok = false;
            }
        } while (!ok);
        professores.add(novoP);
        System.out.println("Professor cadastrado com sucesso!");
        System.out.println("O código de funcionário do professor é: " + professores.get(professores.size() - 1).getCodigoFuncionario());
    }

    public static void gravarSalas(ArrayList<Sala> salas, Scanner entrada) {
        Sala novaS = new Sala();
        boolean ok;

        System.out.println("\n\tCadastro de Sala");

        System.out.print("\nNome da sala: ");
        novaS.setNome(entrada.nextLine());

        System.out.print("\nLocal da sala: ");
        novaS.setLocal(entrada.nextLine());

        System.out.print("\nPeríodo que a sala estará disponível(Ex: Manhã): ");
        novaS.setPeriodo(entrada.nextLine());

        for (int i = 0; i < salas.size(); i++) {
            //Caso o nome dessa sala seja igual a de outra e o período também, ela não deixa adicionar para não ter duplicidade...
            if (novaS.getNome().equalsIgnoreCase(salas.get(i).getNome()) && novaS.getPeriodo().equalsIgnoreCase(salas.get(i).getPeriodo())) {
                System.out.println("Sala existente nesse período...");
                return;
            }
        }

        //Laço para caso o usuário digite uma letra no lugar de um número...
        do {
            try {
                ok = true;
                System.out.print("\nEscreva a capacidade total da sala: ");
                novaS.setCapacidadeTotal(entrada.nextInt());
            } catch (InputMismatchException e) {
                System.out.println("Oops, digite apenas números.");
                ok = false;
            }
        } while (!ok);

        salas.add(novaS);
        System.out.println("Sala adicionada com sucesso!");
    }

    public static void gravarCursos(ArrayList<Curso> cursos, ArrayList<Sala> salas, ArrayList<Professor> professores, ArrayList<Aluno> alunos, Scanner entrada, int numArray) {
        Curso novoC = new Curso();
        int lido;

        System.out.println("\n\tCadastro de Curso");

        System.out.print("\nEscreva o nome do curso: ");
        novoC.setNome(entrada.nextLine());

        System.out.print("\nEscreva a descrição do curso: ");
        novoC.setDescricao(entrada.nextLine());

        try {//Início do tratamento caso digite letra no lugar de um número...
            System.out.print("\nEscreva o código do curso: ");
            novoC.setCodigo(entrada.nextInt());
            entrada.nextLine();

            System.out.print("\nEscreva a carga horária do curso: ");
            novoC.setCargaHoraria(entrada.nextInt());

            System.out.println("\nSalas disponíveis: ");
            for (int i = 0; i < salas.size(); i++) {//Laço para percorrer todas as salas...
                System.out.print("\nSala " + i + " - ");
                System.out.println(salas.get(i).getNome());
                System.out.println(salas.get(i).getLocal());
                System.out.println(salas.get(i).getCapacidadeTotal());
            }
            System.out.println("\nEscreva a sala em que será dado o curso: ");
            lido = entrada.nextInt();

            try {//Caso a sala não esteja alocada para nenhum curso ele grava o mesmo, se não ele não deixa adicionar...
                salas.get(lido).getCurso();
                System.out.println("\nSala já alocada para outro curso.");
                return;
            } catch (NullPointerException e) {
                novoC.setSala(salas.get(lido));
                salas.get(lido).setCurso(novoC);
            }

            System.out.println("\nProfessores disponíveis: ");
            for (int i = 0; i < professores.size(); i++) {//Laço para percorrer todos os professores...
                System.out.print("\nProfessor " + i + " - ");
                System.out.println(professores.get(i).getNome());
                System.out.println(professores.get(i).getCodigoFuncionario());
            }
            System.out.println("\nEscreva o professor que coordenará o curso: ");
            lido = entrada.nextInt();
            
            //Após isso, ele adiciona o professor e grava no arquivo binário de persistência...
            novoC.setProfessor(professores.get(lido));
            professores.get(lido).setCurso(novoC);
            cursos.add(novoC);
            numArray = 1;
            gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
            numArray = 2;
            gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
            numArray = 3;
            gravarArquivoBinario(alunos, professores, salas, cursos, numArray);
            System.out.println("\nCurso adicionado com sucesso!");
            
        } catch (InputMismatchException e) {
            System.out.println("Oops, digito inadequado.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Código inexistente no sistema.");
        }
    }

    public static void mostrarCursos(ArrayList<Curso> cursos) {
        //Laço que passa por todos os cursos e mostra o toString dele...
        for (int i = 0; i < cursos.size(); i++) {
            //Mostra os dados do curso e a quantidade de vagas abertas...
            System.out.println("\nCurso " + i + " - " + cursos.get(i).toString());
        }
    }

    public static void matriculaAlunos(ArrayList<Aluno> alunos, ArrayList<Curso> cursos, Scanner entrada, int pk, int pk2) {
        int lido;
        //Pega o número do curso no array e guarda na variável lido...
        System.out.print("\nDigite o número do curso para matricular: ");
        lido = entrada.nextInt();
        try {
            //Vê se o aluno não está matriculado no curso...
            if (cursos.get(lido).getAlunos().get(pk2).getMatricula() != pk) {
                cursos.get(lido).addAluno(alunos.get(pk2));
                alunos.get(pk2).setCurso(cursos.get(lido));
                System.out.println("\nO aluno foi matriculado ao curso " + cursos.get(lido).getNome() + "\nLocal da sala: " + cursos.get(lido).getLocalSala());
                //Se estiver, o sistema avisa ele...
            } else {
                System.out.println("Este aluno já está matriculado neste curso.");
            }
            //Se for a primeira matricula do aluno no curso ele consegue se matricular, se não ele recebe um aviso de que não pode...
        } catch (IndexOutOfBoundsException e) {
            //If para ver se a sala tem vaga para o aluno, caso tenha adiciona ele...
            if (cursos.get(lido).getQuantidadeAtualSala() <= cursos.get(lido).getQuantidadeTotalSala() && cursos.get(lido).getQuantidadeAtualSala() > 0) {
                cursos.get(lido).addAluno(alunos.get(pk2));
                alunos.get(pk2).setCurso(cursos.get(lido));
                System.out.println("\nO aluno foi matriculado ao curso " + cursos.get(lido).getNome() + "\nLocal da sala: " + cursos.get(lido).getLocalSala());
                //Se não, ele não adiciona e fala que não tem mais vagas...
            } else {
                System.out.println("\nO curso escolhido não tem mais vagas.");
            }
        }
    }

    public static void gravarArquivoBinario(ArrayList<Aluno> alunos, ArrayList<Professor> professores, ArrayList<Sala> salas, ArrayList<Curso> cursos, int numArray) {
        String[] arquivos = {"dadosAlunos.dat", "dadosProfessores.dat", "dadosSalas.dat", "dadosCursos.dat"};
        ArrayList[] lista = {alunos, professores, salas, cursos};
        //Loop para percorrer apenas o arquivo que quiser quando chamar o método...
        for (int i = numArray; i <= numArray; i++) {
            //Cria o objeto "arq" para manipular os arquivos a serem criados...
            File arq = new File(arquivos[i]);
            try {
                arq.delete();
                arq.createNewFile();
                //Associa objOutput ao um fluxo de saída de bytes...
                ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(arquivos[i]));
                //Método para transferir os dados da "lista" para o arquivo binário...
                objOutput.writeObject(lista[i]);
                //fecha o fluxo de saída...
                objOutput.close();
            } catch (IOException erro) {
                System.out.printf("Erro: %s", erro.getMessage());
            }
        }
    }
}
import java.util.Scanner;

public class CadastroDeCliente {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // Lê a linha de entrada
        System.out.print("Informe o primeiro nome do cliente e o email (separados por espaço): ");
        String nome = scanner.next();
        String email = scanner.next();

        // TO DO: Crie o objeto cliente com os dados fornecidos:
        Cliente cliente = new Cliente(nome, email);
        
        // Exibe a mensagem formatada conforme solicitado
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso! Email: " + cliente.getEmail());

        scanner.close();
    }
}

// TO DO: Crie a classe que representa um Cliente:
class Cliente{
  
  private String nome;
  private String email;
  
  // TO DO: Crie o construtor para inicializar os atributos:
  public Cliente(String nome, String email){
    this.nome = nome;
    this.email = email;
  }
  
  // TO DO: Implemente os métodos getters:
  public String getNome(){
    return nome;
  }
  
  public String getEmail(){
    return email;
  }
}
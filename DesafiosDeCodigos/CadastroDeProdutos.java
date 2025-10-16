import java.util.Scanner;

public class CadastroDeProdutos {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        // Lendo a linha inteira e separando os dados
        System.out.print("Informe o nome, preço e categoria do produto (separados por espaço): ");
        String nome = scanner.next();
        double preco = scanner.nextDouble();
        String categoria = scanner.next();

        // TO DO: Crie o objeto Produto
        Produto produto = new Produto(nome, preco, categoria);

        // TO DO: Exiba as informações no formato solicitado:
        System.out.println(produto.toString());

        scanner.close();
    }
}

class Produto{
  
    private String nome;
    private double preco;
    private String categoria;
    
    // TO DO: Implemente seu Construtor:
    public Produto(String nome, double preco, String categoria){
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }
    
    // TO DO: Adicione o Método para exibir o produto no formato desejado. O preco deve ser retornado em duas casas decimais:
    @Override
    public String toString(){
        return "Produto: " + nome + " | " +
            "Categoria: " + categoria + " | " +
            "Preco: R$ " + String.format("%.2f", preco);
    }
}
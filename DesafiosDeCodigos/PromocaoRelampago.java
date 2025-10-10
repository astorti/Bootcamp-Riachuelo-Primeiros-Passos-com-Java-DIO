import java.math.BigDecimal;
import java.util.Scanner;

public class PromocaoRelampago {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o valor da compra: ");
        String valorCompra = scanner.nextLine();
        System.out.println(calcularDesconto(valorCompra));
        scanner.close();
    }

    public static String calcularDesconto(String valorCompra) {
        BigDecimal valor = new BigDecimal(valorCompra);
        BigDecimal descontoPercentual = new BigDecimal("0");
        
        BigDecimal semDesconto = new BigDecimal("50");
        BigDecimal desconto10 = new BigDecimal("100");
        BigDecimal desconto20 = new BigDecimal("101");
       
        // TO DO: Verifique se o valor é menor que 50.00:
        if(valor.compareTo(semDesconto) < 0) {
          descontoPercentual = descontoPercentual.add(new BigDecimal("0"));
        } 
        
        // TO DO: Verifique se o valor é entre 50.00 e 100.00 (inclusive):
        if ((valor.compareTo(semDesconto) >= 0) && (valor.compareTo(desconto10) <= 0)) {
          descontoPercentual = descontoPercentual.add(new BigDecimal("10"));
        } 
        
        // TO DO: Caso contrário, o valor é maior que 100.00:
        if (valor.compareTo(desconto20) >= 0) {
          descontoPercentual = descontoPercentual.add(new BigDecimal("20"));
        }
        
        // TO DO: Retorne a string formatada com o desconto aplicado, conforme exigido no desafio:
        return "Desconto de " + descontoPercentual + "%";      
    }
}


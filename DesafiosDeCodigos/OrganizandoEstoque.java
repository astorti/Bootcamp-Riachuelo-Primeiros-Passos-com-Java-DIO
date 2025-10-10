import java.util.LinkedHashMap;
import java.util.Map;

public class OrganizandoEstoque {

    public static void main(String[] args){

        String entrada = "LOJA3:20,LOJA3:15,LOJA4:7";
        String saida = organizarEstoque(entrada);

        System.out.println(saida);
    }

    public static String organizarEstoque(String entrada) {
        // TO DO: Crie um mapa (LinkedHashMap) para armazenar o total de cada código mantendo a ordem de aparição
        Map<String, Integer> codigoLojas = new LinkedHashMap<>();

        String[] lojas = entrada.split(",");

        for (String loja : lojas) {
            String[] partes = loja.split(":");
            String codigo = partes[0].trim();
            int quantidade = Integer.parseInt(partes[1].trim());

            // TO DO: Atualize a quantidade total no mapa (soma com o valor atual, se já existir)
            codigoLojas.put(codigo, codigoLojas.getOrDefault(codigo, 0) + quantidade);
          
        }

        StringBuilder sb = new StringBuilder();

        // TO DO: Itere sobre os pares do mapa e monta a string no formato "codigo:quantidade"
        for (Map.Entry<String, Integer> valor : codigoLojas.entrySet()) {
            sb.append(valor.getKey());
            sb.append(":");
            sb.append(valor.getValue());
            sb.append(",");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); 
        }

        return sb.toString();
    }
}

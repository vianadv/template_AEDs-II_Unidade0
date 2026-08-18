package aula1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Program {
    static void main() {
        String desc = IO.readln("Qual a descriçao do produto");
        double preco = Double.parseDouble(IO.readln("Preco do produto: "));
        double margemLucro = Double.parseDouble(IO.readln("Margem de lucro: "));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataEmTexto = IO.readln("Digite a data(dd/MM/yyyy): ");
        LocalDate data = LocalDate.parse(dataEmTexto,format);
        Produto produto = new ProdutoPerecivel(desc,preco,margemLucro,data);

        System.out.println(produto.valorVenda());
    }
}

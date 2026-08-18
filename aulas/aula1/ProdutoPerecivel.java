package aula1;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProdutoPerecivel extends Produto{
    private static final double DESCONTO = 0.25;
    private static final int PRAZO_DESCONTO = 7;
    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate validade) {
        super(desc, precoCusto, margemLucro);
        if (validade.isBefore(LocalDate.now()))
            throw new IllegalStateException("Data de validade nao pode ser menor que o dia de hoje");
        this.dataDeValidade = validade;
    }

    @Override
    public double valorVenda(){
        if (dataDeValidade.isBefore(LocalDate.now()))
            throw new IllegalStateException("Produto vencido");

        if (ChronoUnit.DAYS.between(LocalDate.now()
                ,dataDeValidade)
                <= PRAZO_DESCONTO)
            return (precoCusto *(1+margemLucro))* (1- DESCONTO);

        return (precoCusto *(1+margemLucro));
    }

    @Override
    public String toString() {
        NumberFormat moeda = NumberFormat.getCurrencyInstance();

        return String.format("NOME: " + descricao + ": " + moeda.format(valorVenda()));
    }
}

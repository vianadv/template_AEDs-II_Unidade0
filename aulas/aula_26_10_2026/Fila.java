package aula_26_10_2026;

import aula_06_10_2026.Celula;

import java.util.function.Function;
import java.util.function.Predicate;

public class Fila<E> {
    private Celula<E> inicio;
    private Celula<E> fim;
    private int tamanho;

    public Fila(){
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public boolean isVazia(){
        return tamanho == 0;
    }

    public void enfileirar(E item){
        Celula<E> celula = new Celula(item);

        if(isVazia()) {
            inicio = celula;
            fim = celula;
        }

        fim.setProximo(celula);
        fim = celula;
        tamanho++;
    }

    public E desenfilerar(){
        if (isVazia())  throw new IllegalStateException("A Fila esta vazia");

        E dado = inicio.getItem();
        inicio = inicio.getProximo();
        tamanho--;
        return dado;
    }

    public E consultarInicio(){
        return inicio.getItem();
    }

    public double calcularValorMedio(Function<E, Double> extrator, int quantidade){
        if (quantidade <=0) throw new IllegalArgumentException("Quantidade inválida");
        if (quantidade > tamanho)   throw new IllegalStateException("A fila é menor que a quantidade requerida");

        Celula<E> tempC;
        tempC = inicio;
        double valores = 0;

        for (int i = 0; i < quantidade; i++) {
            valores += extrator.apply(tempC.getItem());
            tempC = tempC.getProximo();
        }
        return valores/quantidade;
    }

    public Fila<E> filtrar(Predicate<E> condicional, int quantidade){
        if (quantidade <=0) throw new IllegalArgumentException("Quantidade inválida");
        if (quantidade > tamanho)   throw new IllegalStateException("A fila é menor que a quantidade requerida");

        Fila<E> fila = new Fila<>();
        Celula<E> tempC;
        tempC = inicio;

        for (int i = 0; i < quantidade; i++) {
            if (condicional.test(tempC.getItem()))
                fila.enfileirar(tempC.getItem());
            tempC = tempC.getProximo();
        }
        return fila;
    }
}

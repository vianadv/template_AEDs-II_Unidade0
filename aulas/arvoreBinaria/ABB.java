package arvoreBinaria;

import java.util.Comparator;

public class ABB<K,V> {
    No<K,V> raiz;
    Comparator<K> comparador;
    static int tamanho;

    public ABB(Comparator<K> comparador){
        this.comparador = comparador;
        raiz = null;
        tamanho =0;
    }

    public boolean vazia(){
        return tamanho ==0;
    }

    public V pesquisar(K chave){
        if (vazia()) throw new IllegalStateException("arvore vazia");
        if (chave == null) throw new IllegalArgumentException("chave invalida");

        return pesquisar(raiz,chave);
    }

    private V pesquisar(No<K,V> no, K procurado){
        if (no == null) throw new IllegalArgumentException("Nodo invalido");
        int result = comparador.compare(no.getKey(),procurado);

        if(result >0) return pesquisar(no.getEsquerda(),procurado);
        if(result <0) return pesquisar(no.getDireita(),procurado);
        return no.getItem();
    }

    public void inserir(K chave, V item){
        if (chave == null || item == null) throw new IllegalStateException("argumento invalido");
        inserir(raiz,chave,item);
    }

    protected No<K,V> inserir(No<K,V> no, K chave, V item){
        if (no == null) return new No<>(chave,item);
        int result = comparador.compare(no.getKey(),chave);

        if (result == 0) throw new IllegalStateException("Esse nodo ja existe na arvore");
        if (result > 0) no.setEsquerda(inserir(no.getEsquerda(),chave,item));
        if (result < 0) no.setDireita(inserir(no.getDireita(),chave,item));

        return no;
    }
}

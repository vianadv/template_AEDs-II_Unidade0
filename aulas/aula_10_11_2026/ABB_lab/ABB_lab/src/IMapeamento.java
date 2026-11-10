package aula_10_11_2026.ABB_lab.ABB_lab.src;

public interface IMapeamento<K, V> extends IMedicao {

    public int inserir(K chave, V item);
    public V pesquisar(K chave);
    public V remover(K chave);
    public int tamanho();
    public String percorrer();
}
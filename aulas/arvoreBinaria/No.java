package arvoreBinaria;

public class No<K,V> {
    K chave;
    V item;
    No<K,V> direita;
    No<K,V> esquerda;

    public No(K chave, V item){
        this.chave= chave;
        this.item = item;
        direita = null;
        esquerda = null;
    }

    public V getItem(){
        return this.item;
    }

    public void setItem(V item){
        this.item = item;
    }

    public K getKey(){
        return this.chave;
    }

    public void setKey(K key){
        this.chave = key;
    }

    public No<K, V> getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No<K, V> esquerda) {
        this.esquerda = esquerda;
    }

    public No<K, V> getDireita() {
        return direita;
    }

    public void setDireita(No<K, V> direita) {
        this.direita = direita;
    }
}

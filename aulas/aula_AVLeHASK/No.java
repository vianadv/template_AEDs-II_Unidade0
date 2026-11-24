package aula_AVLeHASK;

public class No<K, V> {

	private K chave;             // chave identificadora do item armazenado no nodo da árvore.
	private V item;              // contém os dados do item armazenado no nodo da árvore.
	private No<K, V> direita;    // referência ao nodo armazenado, na árvore, à direita do nó em questão.
	private No<K, V> esquerda;   // referência ao nodo armazenado, na árvore, à esquerda do nó em questão.
	private int altura;          // altura da subárvore enraizada neste nó (folha = 1; árvore vazia = 0).

	public No(K chave, V item) {
		setChave(chave);
		setItem(item);
		setDireita(null);
		setEsquerda(null);
		altura = 1;
	}

	public V getItem() {
		return item;
	}

	public void setItem(V item) {
		this.item = item;
	}

	public K getChave() {
		return chave;
	}

	public void setChave(K chave) {
		this.chave = chave;
	}

	public No<K, V> getDireita() {
		return direita;
	}

	public void setDireita(No<K, V> direita) {
		this.direita = direita;
	}

	public No<K, V> getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(No<K, V> esquerda) {
		this.esquerda = esquerda;
	}

	/**
	 * Retorna a altura de uma subárvore. Uma subárvore vazia (null) tem altura 0.
	 * @param no raiz da subárvore.
	 * @return a altura da subárvore.
	 */
	private static int altura(No<?, ?> no) {
		return (no == null) ? 0 : no.altura;
	}

	public int getAltura() {
		return altura;
	}

	/**
	 * Recalcula a altura deste nó a partir das alturas, já atualizadas, de seus filhos.
	 */
	public void setAltura() {
		altura = 1 + Math.max(altura(esquerda), altura(direita));
	}

	/**
	 * Fator de balanceamento: altura da subárvore esquerda menos altura da subárvore direita.
	 * Valores 2 indicam desbalanceamento à esquerda; -2, à direita.
	 * @return o fator de balanceamento deste nó.
	 */
	public int getFatorBalanceamento() {
		return altura(esquerda) - altura(direita);
	}
}

package aula_10_11_2026.ABB_lab.ABB_lab.src;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class ABB<K, V> implements IMapeamento<K, V> {

	// atributos
	private long comparacoes;
	private long inicio;
	private long termino;
	
	/**
	 * Construtor da classe.
	 * @param comparador o comparador a ser utilizado para organizar os elementos da árvore.  
	 */
	public ABB(Comparator<K> comparador) {

	}
	
	@Override
	public long getComparacoes() {
		return comparacoes;
	}

	@Override
	public double getTempo() {
		return (termino - inicio) / 1_000_000;
	}

	@Override
	public int inserir(K chave, V item) {
		return 0;
	}

	@Override
	public V pesquisar(K chave) {
		return null;
	}

	@Override
	public V remover(K chave) {
		return null;
	}

	@Override
	public int tamanho() {
		return 0;
	}

	@Override
	public String percorrer() {
		return "";
	}
}

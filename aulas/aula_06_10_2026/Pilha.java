package aula_06_10_2026;

import java.util.NoSuchElementException;

public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;
	private int tamanho;

	public Pilha() {

		Celula<E> sentinela = new Celula<E>();
		fundo = sentinela;
		topo = sentinela;
		tamanho ++;
	}

	public boolean vazia() {
		return fundo == topo;
	}

	public void empilhar(E item) {

		topo = new Celula<E>(item, topo);
	}

	public E desempilhar() {

		E desempilhado = consultarTopo();
		topo = topo.getProximo();
		return desempilhado;

	}

	public E consultarTopo() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na pilha!");
		}

		return topo.getItem();

	}

	/**
	 * Cria e devolve uma nova pilha contendo os primeiros numItens elementos
	 * do topo da pilha atual.
	 * 
	 * Os elementos são mantidos na mesma ordem em que estavam na pilha original.
	 * Caso a pilha atual possua menos elementos do que o valor especificado,
	 * uma exceção será lançada.
	 *
	 * @param numItens o número de itens a serem copiados da pilha original.
	 * @return uma nova instância de Pilha<E> contendo os numItens primeiros elementos.
	 * @throws IllegalArgumentException se a pilha não contém numItens elementos.
	 */
	public Pilha<E> subPilha(int numItens) {
		if (this.tamanho< numItens) throw new IllegalArgumentException("Pilha nao contém elementos!");

		Pilha<E> novaPilha = new Pilha<E>();
		Pilha<E> pilhaAuxiliar = new Pilha<E>();

		for (int i = 0; i < numItens; i++) {
			pilhaAuxiliar.empilhar(this.desempilhar());
		}
		for (int i = 0; i < numItens; i++) {
			E item = pilhaAuxiliar.desempilhar();
			novaPilha.empilhar(item);
			this.empilhar(item);
		}

		return novaPilha;
	}
}
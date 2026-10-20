package aula_06_10_2026;

import aula_06_10_2026.Celula;

import java.util.NoSuchElementException;

public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;
	private int tamanho; // <-- ADICIONE ISSO AQUI

	public Pilha() {
		Celula<E> sentinela = new Celula<E>();
		fundo = sentinela;
		topo = sentinela;
		tamanho = 0;
	}

	public boolean vazia() {
		return fundo == topo;
	}

	public void empilhar(E item) {
		topo = new Celula<E>(item, topo);
		tamanho++;
	}

	public E desempilhar() {
		E desempilhado = consultarTopo();
		topo = topo.getProximo();
		tamanho--;
		return desempilhado;
	}

	public E consultarTopo() {
		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na pilha!");
		}
		return topo.getItem();
	}

	public Pilha<E> subPilha(int numItens) {
		if (this.tamanho < numItens) {
			throw new IllegalArgumentException("Pilha nao contém elementos suficientes!");
		}

		Pilha<E> novaPilha = new Pilha<E>();
		Pilha<E> pilhaAuxiliar = new Pilha<E>();

		Celula<E> atual = this.topo;
		for (int i = 0; i < numItens; i++) {
			pilhaAuxiliar.empilhar(atual.getItem());
			atual = atual.getProximo();
		}

		for (int i = 0; i < numItens; i++) {
			novaPilha.empilhar(pilhaAuxiliar.desempilhar());
		}

		return novaPilha;
	}
}

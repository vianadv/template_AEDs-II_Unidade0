import java.util.NoSuchElementException;

public class Lista<E> {

	private Celula<E> primeiro;
	private Celula<E> ultimo;
	private int tamanho;
	
	/** Quantidade de comparações (chamadas a equals) feitas pela última pesquisa ou remoção por elemento. */
	private long comparacoes;
	
	public Lista() {
		
		Celula<E> sentinela = new Celula<>();
		
		this.primeiro = this.ultimo = sentinela;
		this.tamanho = 0;
	}
	
	public boolean vazia() {
		
		return (this.primeiro == this.ultimo);
	}
	
	public void inserir(E novo, int posicao) {
		
		Celula<E> anterior, novaCelula, proximaCelula;
		
		if ((posicao < 0) || (posicao > this.tamanho))
			throw new IndexOutOfBoundsException("Não foi possível inserir o item na lista: "
					+ "a posição informada é inválida!");
		
		anterior = this.primeiro;
		for (int i = 0; i < posicao; i++)
			anterior = anterior.getProximo();
				
		novaCelula = new Celula<>(novo);
			
		proximaCelula = anterior.getProximo();
			
		anterior.setProximo(novaCelula);
		novaCelula.setProximo(proximaCelula);
			
		if (posicao == this.tamanho)  // a inserção ocorreu na última posição da lista
			this.ultimo = novaCelula;
			
		this.tamanho++;		
	}
	
	public void inserirFinal(E novo) {
		
		Celula<E> novaCelula = new Celula<>(novo);
		
		this.ultimo.setProximo(novaCelula);
		this.ultimo = novaCelula;
		
		this.tamanho++;
	}
	
	private E removerProxima(Celula<E> anterior) {
		
		Celula<E> celulaRemovida, proximaCelula;
		
		celulaRemovida = anterior.getProximo();
		
		proximaCelula = celulaRemovida.getProximo();
				
		anterior.setProximo(proximaCelula);
		celulaRemovida.setProximo(null);
				
		if (celulaRemovida == this.ultimo)
			this.ultimo = anterior;
				
		this.tamanho--;
				
		return (celulaRemovida.getItem());	
	}
	
	public E remover(int posicao) {
		
		Celula<E> anterior;
		
		if (vazia())
			throw new IllegalStateException("Não foi possível remover o item da lista: "
					+ "a lista está vazia!");
		
		if ((posicao < 0) || (posicao >= this.tamanho ))
			throw new IndexOutOfBoundsException("Não foi possível remover o item da lista: "
					+ "a posição informada é inválida!");
			
		anterior = this.primeiro;
		for (int i = 0; i < posicao; i++)
			anterior = anterior.getProximo();
				
		return (removerProxima(anterior));
	}
	
	public E remover(E elemento) {
		
		Celula<E> anterior;
		
		comparacoes = 0;
		anterior = this.primeiro;
		while ((anterior.getProximo() != null) && !igual(anterior.getProximo().getItem(), elemento))
			anterior = anterior.getProximo();
		
		if (anterior.getProximo() == null)
			throw new NoSuchElementException("Item não encontrado!");
		else {
			return (removerProxima(anterior));
		}
	}
	
	public E pesquisar(E procurado) {
		
		Celula<E> aux;
		
		comparacoes = 0;
		aux = this.primeiro.getProximo();
		
		while (aux != null) {
			if (igual(aux.getItem(), procurado))
				return aux.getItem();
			aux = aux.getProximo();
		}
		
		throw new NoSuchElementException("Item não encontrado!");
	}
	
	@Override
	public String toString() {
		
		Celula<E> aux;
		String listaString = "A lista está vazia!\n";
		
		if (!vazia()) {
			listaString = "";
		
			aux = this.primeiro.getProximo();
		
			while (aux != null) {
				listaString += aux.getItem() + "\n";
				aux = aux.getProximo();
			}
		}
		return listaString;
	}
	
	public int tamanho() {
		return tamanho;
	}
	
	/** Compara dois elementos com equals, contabilizando uma comparação. */
	private boolean igual(E elemento, E outro) {
		comparacoes++;
		return elemento.equals(outro);
	}
	
	/**
	 * @return a quantidade de comparações (chamadas a equals) realizadas pela última
	 *         chamada a pesquisar(E) ou a remover(E).
	 */
	public long getComparacoes() {
		return comparacoes;
	}
}

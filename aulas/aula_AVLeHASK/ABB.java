import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Árvore binária de busca genérica. Os itens (tipo V) são organizados pela chave (tipo K),
 * de acordo com o comparador informado na criação da árvore.
 */
public class ABB<K, V> implements IMapeamento<K, V> {

	/** Raiz da árvore. */
	protected No<K, V> raiz;

	/** Comparador utilizado para organizar as chaves na árvore. */
	protected Comparator<K> comparador;

	/** Quantidade de itens armazenados na árvore. */
	protected int tamanho;

	// atributos de medição
	private long comparacoes;
	private long inicio;
	private long termino;

	/** Implementado
	 * Construtor da classe. Cria uma árvore vazia que organiza os elementos pela
	 * ordem natural das chaves. As chaves devem implementar Comparable.
	 */
	@SuppressWarnings("unchecked")
	public ABB() {
        this((a, b) -> ((Comparable<K>) a).compareTo(b));
	}

	/** Implementado
	 * Construtor da classe. Cria uma árvore vazia.
	 * @param comparador o comparador a ser utilizado para organizar os elementos da árvore.
	 */
	public ABB(Comparator<K> comparador) {
        if (comparador == null) throw new IllegalArgumentException("Comparador nulo.");
        this.comparador = comparador;
        raiz = null;
        tamanho = 0;
	}

	/** @return true se a árvore estiver vazia; false, caso contrário. */
	public boolean vazia() {
		return (raiz == null);
	}

	//#region Medição
	/** Zera a contagem de comparações e registra o início de uma operação. */
	protected void iniciarMedicao() {
		comparacoes = 0;
		inicio = System.nanoTime();
	}

	/** Registra o término de uma operação. */
	protected void finalizarMedicao() {
		termino = System.nanoTime();
	}

	/**
	 * Compara duas chaves usando o comparador da árvore, contabilizando uma comparação.
	 * @return valor negativo, zero ou positivo, conforme chave1 seja menor, igual ou maior que chave2.
	 */
	protected int comparar(K chave1, K chave2) {
		comparacoes++;
		return comparador.compare(chave1, chave2);
	}

	@Override
	public long getComparacoes() {
		return comparacoes;
	}

	@Override
	public double getTempo() {
		return (termino - inicio) / 1_000_000.0;
	}
	

	//#region Pesquisa
	@Override
	public V pesquisar(K chave) {

		iniciarMedicao();
		try {
			return pesquisar(raiz, chave);
		} finally {
			finalizarMedicao();
		}
	}

	/** Implementado
	 * Método recursivo responsável por localizar um item na árvore.
	 * @param raizArvore raiz da árvore ou subárvore em que o item será procurado.
	 * @param chave chave do item procurado.
	 * @return o item encontrado.
	 * @throws NoSuchElementException caso a chave não esteja na árvore.
	 */
	protected V pesquisar(No<K, V> raizArvore, K chave) {
        if (raizArvore == null) throw new NoSuchElementException("Chave não encontrada.");
        int cmp = comparar(chave, raizArvore.getChave());
        if (cmp == 0) return raizArvore.getItem();
        return pesquisar(cmp < 0 ? raizArvore.getEsquerda() : raizArvore.getDireita(), chave);
	}
	

	// Implementado
	@Override
	public int inserir(K chave, V item) {
        iniciarMedicao();
        try {
            raiz = inserir(raiz, chave, item);
            return ++tamanho;
        } finally { finalizarMedicao(); }
	}

	/** Implementado
	 * Método recursivo responsável por adicionar um item à árvore.
	 * @param raizArvore raiz da árvore ou subárvore em que o item será adicionado.
	 * @param chave chave do item que deverá ser adicionado à árvore.
	 * @param item item que deverá ser adicionado à árvore.
	 * @return a raiz atualizada da árvore ou subárvore em que o item foi adicionado.
	 * @throws IllegalArgumentException caso a chave já esteja na árvore.
	 */
	protected No<K, V> inserir(No<K, V> raizArvore, K chave, V item) {
        if (raizArvore == null) return new No<>(chave, item);
        int cmp = comparar(chave, raizArvore.getChave());
        if (cmp < 0) raizArvore.setEsquerda(inserir(raizArvore.getEsquerda(), chave, item));
        else if (cmp > 0) raizArvore.setDireita(inserir(raizArvore.getDireita(), chave, item));
        else throw new IllegalArgumentException("Chave repetida.");
        raizArvore.setAltura();
        return raizArvore;
	}

	// Implementado
	@Override
	public V remover(K chave) {
        iniciarMedicao();
        try {
            // Pesquisa interna: não reinicia a medição.
            V item = pesquisar(raiz, chave);
            raiz = remover(raiz, chave);
            tamanho--;
            return item;
        } finally { finalizarMedicao(); }
	}

	/** Implementado
	 * Método recursivo responsável por localizar um item na árvore e retirá-lo da árvore.
	 * @param raizArvore raiz da árvore ou subárvore da qual o item será retirado.
	 * @param chaveRemover chave do item que deverá ser localizado e removido da árvore.
	 * @return a raiz atualizada da árvore ou subárvore da qual o item foi retirado.
	 * @throws NoSuchElementException caso a chave não esteja na árvore.
	 */
	protected No<K, V> remover(No<K, V> raizArvore, K chaveRemover) {
        if (raizArvore == null) throw new NoSuchElementException("Chave não encontrada.");
        int cmp = comparar(chaveRemover, raizArvore.getChave());
        if (cmp < 0) raizArvore.setEsquerda(remover(raizArvore.getEsquerda(), chaveRemover));
        else if (cmp > 0) raizArvore.setDireita(remover(raizArvore.getDireita(), chaveRemover));
        else {
            if (raizArvore.getEsquerda() == null) return raizArvore.getDireita();
            if (raizArvore.getDireita() == null) return raizArvore.getEsquerda();
            raizArvore.setEsquerda(removerNoAntecessor(raizArvore, raizArvore.getEsquerda()));
        }
        raizArvore.setAltura();
        return raizArvore;
	}

	/** Implementado
	 * Método recursivo responsável por localizar na árvore ou subárvore o antecessor do nó que deverá ser retirado.
	 * O antecessor corresponde ao nó que armazena a maior chave dentre as chaves menores do que a chave
	 * que deverá ser retirada. Depois de localizado, o antecessor substitui o nó que deverá ser retirado.
	 * @param itemRetirar referência ao nó que armazena o item que deverá ser retirado da árvore.
	 * @param raizArvore raiz da árvore ou subárvore em que o antecessor deverá ser localizado.
	 * @return a raiz atualizada da árvore ou subárvore após a remoção do antecessor.
	 */
	protected No<K, V> removerNoAntecessor(No<K, V> itemRetirar, No<K, V> raizArvore) {
        if (raizArvore.getDireita() == null) {
            itemRetirar.setChave(raizArvore.getChave());
            itemRetirar.setItem(raizArvore.getItem());
            return raizArvore.getEsquerda();
        }
        raizArvore.setDireita(removerNoAntecessor(itemRetirar, raizArvore.getDireita()));
        raizArvore.setAltura();
        return raizArvore;
	}
	

	//#region Percurso
	@Override
	public int tamanho() {
		return tamanho;
	}

	/**
	 * Percorre a árvore em ordem e retorna os itens, um por linha, em ordem crescente de chave.
	 */
	@Override
	public String percorrer() {

		StringBuilder conteudo = new StringBuilder();
		percorrer(raiz, conteudo);
		return conteudo.toString();
	}

	private void percorrer(No<K, V> raizArvore, StringBuilder conteudo) {

		if (raizArvore != null) {
			percorrer(raizArvore.getEsquerda(), conteudo);
			conteudo.append(raizArvore.getItem()).append("\n");
			percorrer(raizArvore.getDireita(), conteudo);
		}
	}

	@Override
	public String toString() {
		return percorrer();
	}
	
}

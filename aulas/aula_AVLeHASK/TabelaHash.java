import java.util.NoSuchElementException;

/**
 * Tabela hash com endereçamento em separado (encadeamento): cada posição da tabela
 * referencia uma lista encadeada com as entradas cujas chaves foram mapeadas para ela.
 */
public class TabelaHash<K, V> implements IMapeamento<K, V> {

	private Lista<Entrada<K, V>>[] tabelaHash; /// tabela que referenciará todas as listas lineares encadeadas.
	                                           /// Nesse caso, estamos utilizando uma tabela hash com endereçamento em separado,
	                                           /// ou seja, os itens são armazenados em listas lineares encadeadas.

	private int capacidade; /// tamanho da tabela hash.
	                        /// deve ser um número primo grande para diminuirmos a probabilidade de colisões.

	// atributos de medição
	private long comparacoes;
	private long inicio;
	private long termino;

	/** Implementado
	 * Construtor da classe.
	 * Esse método é responsável por inicializar a tabela hash que trabalha com endereçamento em separado.
	 * Assim, esse método atribui, ao atributo "capacidade", dessa classe, o valor passado por meio do parâmetro "capacidade".
	 * Esse método também cria um vetor, de tamanho "capacidade", de listas lineares; e o atribui ao atributo "tabelaHash".
	 * Adicionalmente, cada posição do vetor é inicializada com uma lista encadeada vazia.
	 * @param capacidade quantidade de posições da tabela hash. Cada posição é uma lista encadeada.
	 * @throws IllegalArgumentException caso a capacidade seja um número não positivo.
	 */
	@SuppressWarnings("unchecked")
	public TabelaHash(int capacidade) {
        if (capacidade < 1) throw new IllegalArgumentException("Capacidade inválida.");
        this.capacidade = capacidade;
        tabelaHash = (Lista<Entrada<K, V>>[]) new Lista[capacidade];
        for (int i = 0; i < capacidade; i++) tabelaHash[i] = new Lista<>();
	}

	/**
	 * Esse método implementa a função de transformação da tabela hash,
	 * ou seja, calcula a posição, na tabela hash, em que o item,
	 * que possui a chave informada por meio do parâmetro "chave", deve ser encontrado.
	 * A função de transformação utilizada corresponde ao resto da divisão do hashCode de "chave" pelo tamanho da tabela hash.
	 * @param chave chave da qual desejamos saber a posição na tabela hash.
	 * @return a posição, no intervalo [0, capacidade - 1], que o item deve ocupar na tabela hash.
	 */
	private int funcaoHash(K chave) {
		return Math.floorMod(chave.hashCode(), capacidade);
	}

	//#region Medição
	/** Zera a contagem de comparações e registra o início de uma operação. */
	private void iniciarMedicao() {
		comparacoes = 0;
		inicio = System.nanoTime();
	}

	/** Registra o término de uma operação. */
	private void finalizarMedicao() {
		termino = System.nanoTime();
	}

	@Override
	public long getComparacoes() {
		return comparacoes;
	}

	@Override
	public double getTempo() {
		return (termino - inicio) / 1_000_000.0;
	}
	//#endregion

	/** Implementado
	 * Método responsável por inserir um novo item na tabela hash.
	 * Não é permitido inserir, nessa tabela hash, mais de um item com uma mesma chave.
	 * O item é inserido no final da lista encadeada associada à posição calculada pela função hash.
	 * @param chave chave do item que deve ser inserido na tabela hash.
	 * @param item referência ao item que deve ser inserido na tabela hash.
	 * @return a posição na tabela hash em que o novo item foi inserido.
	 * @throws IllegalArgumentException no caso de um item, com a mesma chave, já existir na tabela.
	 */
	@Override
	public int inserir(K chave, V item) {
        iniciarMedicao();
        try {
            int pos = funcaoHash(chave);
            Lista<Entrada<K, V>> lista = tabelaHash[pos];
            try {
                lista.pesquisar(new Entrada<>(chave, null));
                throw new IllegalArgumentException("Chave repetida.");
            } catch (NoSuchElementException ausente) {
                lista.inserirFinal(new Entrada<>(chave, item));
            } finally { comparacoes = lista.getComparacoes(); }
            return pos;
        } finally { finalizarMedicao(); }
	}

	/** Implementado
	 * Método responsável por localizar, na tabela hash, o item
	 * cuja chave corresponde à que foi passada como parâmetro para esse método.
	 * @param chave chave do item que deve ser localizado na tabela hash.
	 * @return uma referência ao item encontrado.
	 * @throws NoSuchElementException caso o item não seja localizado na tabela hash.
	 */
	@Override
	public V pesquisar(K chave) {
        iniciarMedicao();
        try {
            Lista<Entrada<K, V>> lista = tabelaHash[funcaoHash(chave)];
            try { return lista.pesquisar(new Entrada<>(chave, null)).getValor(); }
            finally { comparacoes = lista.getComparacoes(); }
        } finally { finalizarMedicao(); }
	}

	/** Implementado
	 * Método responsável por remover, da tabela hash, o item
	 * cuja chave corresponde à que foi passada como parâmetro para esse método.
	 * @param chave chave do item que deve ser removido da tabela hash.
	 * @return uma referência ao item removido.
	 * @throws NoSuchElementException caso o item não seja localizado na tabela hash.
	 */
	@Override
	public V remover(K chave) {
        iniciarMedicao();
        try {
            Lista<Entrada<K, V>> lista = tabelaHash[funcaoHash(chave)];
            try { return lista.remover(new Entrada<>(chave, null)).getValor(); }
            finally { comparacoes = lista.getComparacoes(); }
        } finally { finalizarMedicao(); }
	}

	//#region Herança Object
	@Override
	public String toString() {
		return percorrer();
	}

	/**
	 * Método responsável por percorrer todo o conteúdo da tabela hash e retornar sua representação, em string.
	 * A string inclui o índice da tabela hash e seu correspondente conteúdo.
	 * Se a posição da tabela hash estiver vazia, é incluída uma mensagem explicativa.
	 * Caso contrário, para todos os itens, armazenados na lista encadeada
	 * associada a uma posição da tabela hash, são incluídos seus dados, sempre usando
	 * o polimorfismo do toString.
	 */
	@Override
	public String percorrer() {

		StringBuilder conteudo = new StringBuilder();
		conteudo.append("Tabela com ").append(capacidade).append(" posições e ").append(tamanho()).append(" itens\n");
		for (int i = 0; i < capacidade; i++) {
			conteudo.append("Posição[").append(i).append("]: ");
			if (tabelaHash[i].vazia())
				conteudo.append("vazia\n");
			else
				conteudo.append(tabelaHash[i]).append("\n");
		}
		return conteudo.toString();
	}

	/**
	 * Retorna o tamanho da tabela hash. O tamanho é a quantidade de itens efetivamente
	 * armazenados no momento, ou seja, pode ser um valor inclusive maior do que a sua
	 * capacidade inicial, dado o tratamento de colisões por lista encadeada.
	 * @return Inteiro, não negativo, com a quantidade de itens armazenados na tabela.
	 */
	@Override
	public int tamanho() {
		int tamanho = 0;
		for (Lista<Entrada<K, V>> lista : tabelaHash) {
			tamanho += lista.tamanho();
		}
		return tamanho;
	}
}

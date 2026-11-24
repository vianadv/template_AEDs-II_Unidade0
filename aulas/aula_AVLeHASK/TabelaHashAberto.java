import java.util.NoSuchElementException;

/**
 * Tabela hash com endereçamento aberto e rehashing linear (sondagem linear).
 * Remoções são lógicas: a entrada é marcada como removida e continua ocupando a posição,
 * para não interromper as sequências de sondagem de outras chaves.
 */
public class TabelaHashAberto<K, V> implements IMapeamento<K, V> {

	private Entrada<K, V>[] tabelaHash;

	private int capacidade; /// tamanho da tabela hash.
	                        /// deve ser um número primo grande para diminuirmos a probabilidade de colisões.

	// atributos de medição
	private long comparacoes;
	private long inicio;
	private long termino;

	/**
	 * Construtor da classe.
	 * Esse método é responsável por inicializar a tabela hash que trabalha com endereçamento em aberto e rehashing.
	 * Assim, esse método atribui, ao atributo "capacidade", dessa classe, o valor passado por meio do parâmetro "capacidade".
	 * Esse método também cria um vetor, de tamanho "capacidade", de entradas; e o atribui ao atributo "tabelaHash".
	 * Adicionalmente, cada posição do vetor é inicializada com null.
	 * @param capacidade tamanho da tabela hash.
	 * @throws IllegalArgumentException caso a capacidade seja menor do que 1.
	 */
	@SuppressWarnings("unchecked")
	public TabelaHashAberto(int capacidade) {
        if (capacidade < 1) throw new IllegalArgumentException("Capacidade inválida.");
        this.capacidade = capacidade;
        tabelaHash = (Entrada<K, V>[]) new Entrada[capacidade];
        // Java inicializa todas as posições do vetor com null.
	}

	/**
	 * Esse método implementa a função de transformação da tabela hash,
	 * ou seja, calcula a posição, na tabela hash, em que o item,
	 * que possui a chave informada por meio do parâmetro "chave", deve ser encontrado.
	 * A função de transformação utilizada corresponde ao resto da divisão do hashCode de "chave"
	 * pelo tamanho da tabela hash, deslocado de "tentativas" posições (sondagem linear).
	 * @param chave chave da qual desejamos saber a posição na tabela hash.
	 * @param tentativas quantidade de tentativas já realizadas com o objetivo de se localizar o item na tabela hash.
	 * @return a posição, no intervalo [0, capacidade - 1], a ser examinada nesta tentativa.
	 */
	private int funcaoHash(K chave, int tentativas) {
		return (Math.floorMod(chave.hashCode(), capacidade) + tentativas) % capacidade;
	}

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

	/**
	 * Método responsável por inserir um novo item na tabela hash.
	 * Não é permitido inserir, nessa tabela hash, mais de um item com uma mesma chave.
	 * O item é armazenado na primeira posição livre (vazia ou com entrada removida) da sequência de sondagem,
	 * mas a sondagem continua até encontrar uma posição vazia, para garantir que a chave não existe mais adiante.
	 * @param chave chave do item que deve ser inserido na tabela hash.
	 * @param item referência ao item que deve ser inserido na tabela hash.
	 * @return a posição na tabela hash em que o novo item foi inserido.
	 * @throws IllegalArgumentException caso um item com a mesma chave já exista na tabela.
	 * @throws IllegalStateException caso a tabela esteja cheia.
	 */
	@Override
	public int inserir(K chave, V item) {
        iniciarMedicao();
        try {
            int livre = -1;
            for (int t = 0; t < capacidade; t++) {
                int pos = funcaoHash(chave, t);
                Entrada<K, V> entrada = tabelaHash[pos];
                if (entrada == null) {
                    if (livre == -1) livre = pos;
                    break;
                }
                if (entrada.isRemovida()) {
                    if (livre == -1) livre = pos;
                    // Continua procurando possíveis chaves duplicadas.
                } else {
                    comparacoes++;
                    if (chave.equals(entrada.getChave())) throw new IllegalArgumentException("Chave repetida.");
                }
            }
            if (livre == -1) throw new IllegalStateException("Tabela cheia.");
            tabelaHash[livre] = new Entrada<>(chave, item);
            return livre;
        } finally { finalizarMedicao(); }
	}

	/**
	 * Método responsável por localizar, na tabela hash, o item
	 * cuja chave corresponde à que foi passada como parâmetro para esse método.
	 * @param chave chave do item que deve ser localizado na tabela hash.
	 * @return uma referência ao item encontrado.
	 * @throws NoSuchElementException caso o item não seja localizado na tabela hash.
	 */
	@Override
	public V pesquisar(K chave) {
        iniciarMedicao();
        try { return tabelaHash[localizar(chave)].getValor(); }
        finally { finalizarMedicao(); }
	}

	/**
	 * Método responsável por remover, da tabela hash, o item
	 * cuja chave corresponde à que foi passada como parâmetro para esse método.
	 * A remoção é lógica: a entrada é marcada como removida.
	 * @param chave chave do item que deve ser removido da tabela hash.
	 * @return uma referência ao item removido.
	 * @throws NoSuchElementException caso o item não seja localizado na tabela hash.
	 */
	@Override
	public V remover(K chave) {
        iniciarMedicao();
        try {
            Entrada<K, V> entrada = tabelaHash[localizar(chave)];
            entrada.setRemovida(true);
            return entrada.getValor();
        } finally { finalizarMedicao(); }
	}

	//#region Herança Object
	@Override
	public String toString() {
		return percorrer();
	}
	//#endregion

	/**
	 * Método responsável por percorrer todo o conteúdo da tabela hash e retornar sua representação, em string.
	 * A string inclui o índice da tabela hash e seu correspondente conteúdo.
	 * Se a posição da tabela hash estiver vazia, é incluída uma mensagem explicativa.
	 * Caso contrário, para cada posição da tabela hash, são incluídos seus dados, sempre usando
	 * o polimorfismo do toString.
	 */
	@Override
	public String percorrer() {

		StringBuilder conteudo = new StringBuilder();
		for (int i = 0; i < capacidade; i++) {
			conteudo.append("Posição[").append(i).append("]: ");
			if ((tabelaHash[i] == null) || (tabelaHash[i].isRemovida())) {
				conteudo.append("vazia\n");
			} else {
				conteudo.append(tabelaHash[i]).append("\n");
			}
		}
		return conteudo.toString();
	}

	@Override
	public int tamanho() {
		int tamanho = 0;
		for (int i = 0; i < capacidade; i++) {
			if ((tabelaHash[i] != null) && !(tabelaHash[i].isRemovida())) {
				tamanho++;
			}
		}
		return tamanho;
	}
    private int localizar(K chave) {
        for (int t = 0; t < capacidade; t++) {
            int pos = funcaoHash(chave, t);
            Entrada<K, V> entrada = tabelaHash[pos];
            if (entrada == null) break;
            if (!entrada.isRemovida()) {
                comparacoes++;
                if (chave.equals(entrada.getChave())) return pos;
            }
        }
        throw new NoSuchElementException("Chave não encontrada.");
    }
}

/**
 * Mapeamento de chaves (tipo K) para itens (tipo V).
 * Não são permitidas chaves repetidas.
 */
public interface IMapeamento<K, V> extends IMedicao {

	/**
	 * Insere um item associado à chave informada.
	 * @return nas tabelas hash, a posição da tabela em que o item foi inserido;
	 *         nas árvores, a quantidade de itens armazenados após a inserção.
	 * @throws IllegalArgumentException caso já exista um item com a mesma chave.
	 */
	public int inserir(K chave, V item);

	/**
	 * @return o item associado à chave informada.
	 * @throws java.util.NoSuchElementException caso a chave não seja encontrada.
	 */
	public V pesquisar(K chave);

	/**
	 * Remove o item associado à chave informada.
	 * @return o item removido.
	 * @throws java.util.NoSuchElementException caso a chave não seja encontrada.
	 */
	public V remover(K chave);

	/** @return a quantidade de itens armazenados. */
	public int tamanho();

	/** @return a representação, em texto, de todos os itens armazenados. */
	public String percorrer();
}

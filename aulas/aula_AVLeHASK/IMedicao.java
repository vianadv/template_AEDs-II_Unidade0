/**
 * Medição de desempenho da última operação (inserir, pesquisar ou remover)
 * realizada sobre uma estrutura de dados.
 */
public interface IMedicao {

	/**
	 * @return a quantidade de comparações de chaves realizadas na última operação.
	 */
	public long getComparacoes();

	/**
	 * @return o tempo de execução da última operação, em milissegundos.
	 */
	public double getTempo();
}

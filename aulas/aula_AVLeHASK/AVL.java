import java.util.Comparator;

/**
 * Árvore binária de busca balanceada AVL. Herda da ABB a pesquisa, o percurso e a medição;
 * a inserção e a remoção reaproveitam os métodos da ABB e rebalanceiam cada subárvore
 * no retorno da recursão.
 */
public class AVL<K, V> extends ABB<K, V> {

	/**
	 * Construtor da classe.
	 * Cria uma árvore AVL vazia que organiza os elementos pela ordem natural das chaves.
	 */
	public AVL() {
		super();
	}

	/**
	 * Construtor da classe.
	 * Cria uma árvore AVL vazia.
	 * @param comparador o comparador a ser utilizado para organizar os elementos da árvore.
	 */
	public AVL(Comparator<K> comparador) {
		super(comparador);
	}

	/** Implementado
	 * Método recursivo responsável por adicionar um item à árvore.
	 * @param raizArvore raiz da árvore ou subárvore em que o item será adicionado.
	 * @param chave chave do item que deverá ser adicionado à árvore.
	 * @param item item que deverá ser adicionado à árvore.
	 * @return a raiz atualizada da árvore ou subárvore balanceada AVL em que o item foi adicionado.
	 */
	@Override
	protected No<K, V> inserir(No<K, V> raizArvore, K chave, V item) {
        // A recursão da ABB chama os métodos sobrescritos da AVL (polimorfismo).
        // Assim, cada chamada balanceia sua própria subárvore ao retornar,
        // desde o nó alterado até a raiz, e não apenas a raiz da árvore.
        return balancear(super.inserir(raizArvore, chave, item));
	}

	/** Implementado
	 * Método recursivo responsável por localizar na árvore ou subárvore o antecessor do nó que deverá ser retirado.
	 * @param itemRetirar referência ao nó que armazena o item que deverá ser retirado da árvore.
	 * @param raizArvore raiz da árvore ou subárvore em que o antecessor do nó que deverá ser retirado deverá ser localizado.
	 * @return a raiz atualizada da árvore ou subárvore balanceada AVL após a remoção do antecessor do nó que foi retirado da árvore.
	 */
	@Override
	protected No<K, V> removerNoAntecessor(No<K, V> itemRetirar, No<K, V> raizArvore) {
        return balancear(super.removerNoAntecessor(itemRetirar, raizArvore));
	}

	/** Implementado
	 * Método recursivo responsável por localizar um item na árvore e retirá-lo da árvore.
	 * @param raizArvore raiz da árvore ou subárvore da qual o item será retirado.
	 * @param chaveRemover chave do item que deverá ser localizado e removido da árvore.
	 * @return a raiz atualizada da árvore ou subárvore balanceada AVL da qual o item foi retirado.
	 */
	@Override
	protected No<K, V> remover(No<K, V> raizArvore, K chaveRemover) {
        return balancear(super.remover(raizArvore, chaveRemover));
	}

	/** Implementado
	 * Retorna uma lista com os itens cujas chaves estão no intervalo [deOnde, ateOnde],
	 * em ordem crescente de chave. Subárvores inteiramente fora do intervalo não são visitadas.
	 * @param deOnde início do intervalo (inclusive).
	 * @param ateOnde fim do intervalo (inclusive).
	 * @return lista com os itens do intervalo, em ordem crescente de chave (vazia, se não houver itens).
	 * @throws IllegalArgumentException caso deOnde seja maior do que ateOnde.
	 */
	public Lista<V> recortar(K deOnde, K ateOnde) {
        iniciarMedicao();
        try {
            if (comparar(deOnde, ateOnde) > 0) throw new IllegalArgumentException("Intervalo inválido.");
            Lista<V> resultado = new Lista<>();
            recortar(raiz, deOnde, ateOnde, resultado);
            return resultado;
        } finally { finalizarMedicao(); }
	}

	/** Implementado
	 * Retorna o item de menor chave maior ou igual à chave informada (função teto),
	 * percorrendo um único caminho a partir da raiz.
	 * @param chave chave de referência.
	 * @return o item de menor chave maior ou igual a chave.
	 * @throws java.util.NoSuchElementException caso todas as chaves da árvore sejam menores do que chave.
	 */
	public V teto(K chave) {
        iniciarMedicao();
        try {
            No<K, V> atual = raiz, candidato = null;
            while (atual != null) {
                int cmp = comparar(chave, atual.getChave());
                if (cmp == 0) return atual.getItem();
                if (cmp < 0) {
                    candidato = atual;
                    atual = atual.getEsquerda();
                } else atual = atual.getDireita();
            }
            if (candidato == null) throw new java.util.NoSuchElementException("Não existe teto.");
            return candidato.getItem();
        } finally { finalizarMedicao(); }
	}

	/** Implementado
	 * Verifica o fator de balanceamento da raiz da subárvore e, se for 2 ou -2,
	 * aplica a rotação simples ou dupla adequada. Caso contrário, apenas atualiza a altura da raiz.
	 * @param raizArvore raiz da subárvore a ser balanceada.
	 * @return a raiz da subárvore balanceada.
	 */
	private No<K, V> balancear(No<K, V> raizArvore) {
        if (raizArvore == null) return null;
        raizArvore.setAltura();
        int fator = raizArvore.getFatorBalanceamento();
        if (fator == 2) {
            if (raizArvore.getEsquerda().getFatorBalanceamento() == -1)
                raizArvore.setEsquerda(rotacionarEsquerda(raizArvore.getEsquerda()));
            return rotacionarDireita(raizArvore);
        }
        if (fator == -2) {
            if (raizArvore.getDireita().getFatorBalanceamento() == 1)
                raizArvore.setDireita(rotacionarDireita(raizArvore.getDireita()));
            return rotacionarEsquerda(raizArvore);
        }
        return raizArvore;
	}

	/** Implementado
	 * Rotação simples à direita em torno do nó p.
	 * @param p raiz da subárvore desbalanceada à esquerda.
	 * @return a nova raiz da subárvore (o antigo filho à esquerda de p).
	 */
	private No<K, V> rotacionarDireita(No<K, V> p) {
        No<K, V> novaRaiz = p.getEsquerda();
        p.setEsquerda(novaRaiz.getDireita());
        novaRaiz.setDireita(p);
        p.setAltura();
        novaRaiz.setAltura();
        return novaRaiz;
	}

	/** Implementado
	 * Rotação simples à esquerda em torno do nó p.
	 * @param p raiz da subárvore desbalanceada à direita.
	 * @return a nova raiz da subárvore (o antigo filho à direita de p).
	 */
	private No<K, V> rotacionarEsquerda(No<K, V> p) {
        No<K, V> novaRaiz = p.getDireita();
        p.setDireita(novaRaiz.getEsquerda());
        novaRaiz.setEsquerda(p);
        p.setAltura();
        novaRaiz.setAltura();
        return novaRaiz;
	}
    // Percurso em ordem com poda das subárvores fora do intervalo.
    private void recortar(No<K, V> no, K deOnde, K ateOnde, Lista<V> resultado) {
        if (no == null) return;
        int inicio = comparar(no.getChave(), deOnde);
        int fim = comparar(no.getChave(), ateOnde);
        if (inicio > 0) recortar(no.getEsquerda(), deOnde, ateOnde, resultado);
        if (inicio >= 0 && fim <= 0) resultado.inserirFinal(no.getItem());
        if (fim < 0) recortar(no.getDireita(), deOnde, ateOnde, resultado);
    }
}

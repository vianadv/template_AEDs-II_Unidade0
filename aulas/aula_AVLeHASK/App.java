import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

public class App {

	/** Capacidades das tabelas hash: números primos próximos de 1 e de 2 vezes a quantidade de produtos (7.750). */
	static final int CAPACIDADE_HASH = 7753;
	static final int CAPACIDADE_HASH_ABERTO = 15511;

	static Scanner teclado;

	static AVL<String, Produto> produtosPorNome;                   // chave: descrição, sem diferenciar maiúsculas de minúsculas
	static AVL<Integer, Produto> produtosPorId;                    // chave: id
	static TabelaHash<Integer, Produto> produtosPorIdHash;         // chave: id (endereçamento em separado)
	static TabelaHashAberto<Integer, Produto> produtosPorIdHashAberto; // chave: id (endereçamento aberto)

	static String lerTexto(String mensagem) {
		System.out.print(mensagem);
		return teclado.nextLine().trim();
	}

	/** Lê um número inteiro digitado pelo usuário. Retorna -1 se o texto digitado não for um inteiro válido. */
	static int lerInteiro(String mensagem) {
		try {
			return Integer.parseInt(lerTexto(mensagem));
		} catch (NumberFormatException excecao) {
			return -1;
		}
	}

	/**
	 * Lê os produtos de um arquivo-texto no formato:
	 * N (quantidade de produtos), seguida de uma linha por produto: tipo;descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]
	 * @return um vetor com os produtos lidos, ou vazio em caso de problemas com o arquivo.
	 */
	static Produto[] lerProdutos(String nomeArquivo) {

		try (Scanner arquivo = new Scanner(new File(nomeArquivo), Charset.forName("UTF-8"))) {
			Produto[] produtos = new Produto[Integer.parseInt(arquivo.nextLine().trim())];
			for (int i = 0; i < produtos.length; i++) {
				produtos[i] = Produto.criarDoTexto(arquivo.nextLine());
			}
			return produtos;
		} catch (IOException excecao) {
			System.out.println("Não foi possível ler o arquivo " + nomeArquivo + ".");
			return new Produto[0];
		}
	}

	/** Insere todos os produtos na estrutura, usando a chave que extratorDeChave extrai de cada produto. */
	static <K> void inserirProdutos(IMapeamento<K, Produto> estrutura, Produto[] produtos, Function<Produto, K> extratorDeChave) {
		for (Produto produto : produtos) {
			estrutura.inserir(extratorDeChave.apply(produto), produto);
		}
	}

	/** Pesquisa a chave na estrutura e exibe as comparações e o tempo da pesquisa. Retorna o produto, ou null se não for encontrado. */
	static <K> Produto pesquisar(String nomeEstrutura, IMapeamento<K, Produto> estrutura, K chave) {

		Produto produto;
		try {
			produto = estrutura.pesquisar(chave);
		} catch (NoSuchElementException excecao) {
			produto = null;
		}
		System.out.printf("%-30s pesquisa - comparações: %4d | tempo: %.4f ms%n", nomeEstrutura, estrutura.getComparacoes(), estrutura.getTempo());
		return produto;
	}

	/** Remove a chave da estrutura e exibe as comparações e o tempo da remoção. */
	static <K> void remover(String nomeEstrutura, IMapeamento<K, Produto> estrutura, K chave) {

		try {
			estrutura.remover(chave);
		} catch (NoSuchElementException excecao) {
			System.out.println(nomeEstrutura + ": produto não encontrado.");
		}
		System.out.printf("%-30s remoção  - comparações: %4d | tempo: %.4f ms%n", nomeEstrutura, estrutura.getComparacoes(), estrutura.getTempo());
	}

	static void procurarPorNome() {
        String nome = lerTexto("Nome do produto: ");
        Produto produto = pesquisar("Árvore AVL", produtosPorNome, nome);
        if (produto != null) System.out.println(produto);
        else {
            System.out.println("Produto não encontrado.");
            try {
                System.out.println("Próximo produto: " + produtosPorNome.teto(nome));
            } catch (NoSuchElementException e) {
                System.out.println("Não existe produto seguinte.");
            }
        }
	}

	/** Procura o id nas três estruturas organizadas por id, para comparar seu desempenho. */
	static void procurarPorId() {

		int id = lerInteiro("Id do produto: ");
		Produto produto = pesquisar("Árvore AVL", produtosPorId, id);
		pesquisar("Hash (endereçamento separado)", produtosPorIdHash, id);
		pesquisar("Hash (endereçamento aberto)", produtosPorIdHashAberto, id);
		System.out.println((produto != null) ? produto : "Produto não encontrado.");
	}

	/** Remove o produto de todas as estruturas. */
	static void removerDeTodas(Produto produto) {

		if (produto == null) {
			System.out.println("Produto não encontrado.");
			return;
		}
		remover("Árvore AVL por nome", produtosPorNome, produto.descricao);
		remover("Árvore AVL por id", produtosPorId, produto.idProduto);
		remover("Hash (endereçamento separado)", produtosPorIdHash, produto.idProduto);
		remover("Hash (endereçamento aberto)", produtosPorIdHashAberto, produto.idProduto);
		System.out.println("Produto removido: " + produto);
	}

	static int menu() {
		System.out.println("\nAEDs II COMÉRCIO DE COISINHAS");
		System.out.println("=============================");
		System.out.println("1 - Listar todos os produtos, em ordem alfabética");
		System.out.println("2 - Procurar produto, por nome");
		System.out.println("3 - Procurar produto, por id");
		System.out.println("4 - Remover produto, por nome");
		System.out.println("5 - Remover produto, por id");
		System.out.println("6 - Recortar a lista de produtos, por nome");
		System.out.println("7 - Recortar a lista de produtos, por id");
		System.out.println("0 - Finalizar");
		return lerInteiro("Digite sua opção: ");
	}

	public static void main(String[] args) {

		teclado = new Scanner(System.in, Charset.forName("UTF-8"));
		Produto[] produtos = lerProdutos("produtos.txt");

		produtosPorNome = new AVL<>(String.CASE_INSENSITIVE_ORDER);
		produtosPorId = new AVL<>();
		produtosPorIdHash = new TabelaHash<>(CAPACIDADE_HASH);
		produtosPorIdHashAberto = new TabelaHashAberto<>(CAPACIDADE_HASH_ABERTO);

		inserirProdutos(produtosPorNome, produtos, p -> p.descricao);
		inserirProdutos(produtosPorId, produtos, p -> p.idProduto);
		inserirProdutos(produtosPorIdHash, produtos, p -> p.idProduto);
		inserirProdutos(produtosPorIdHashAberto, produtos, p -> p.idProduto);

		int opcao;
		do {
			opcao = menu();
			switch (opcao) {
				case 1 -> System.out.println(produtosPorNome.percorrer());
				case 2 -> procurarPorNome();
				case 3 -> procurarPorId();
				case 4 -> removerDeTodas(pesquisar("Árvore AVL por nome", produtosPorNome, lerTexto("Nome do produto: ")));
				case 5 -> removerDeTodas(pesquisar("Árvore AVL por id", produtosPorId, lerInteiro("Id do produto: ")));
				case 6 -> recortarProdutos(produtosPorNome, lerTexto("Nome inicial: "), lerTexto("Nome final: "));
                case 7 -> recortarProdutos(produtosPorId, lerInteiro("Id inicial: "), lerInteiro("Id final: "));
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
				default -> System.out.println("Opção inválida.");
			}
		} while (opcao != 0);

		teclado.close();
	}
    static <K> void recortarProdutos(AVL<K, Produto> arvore, K inicio, K fim) {
        try {
            Lista<Produto> resultado = arvore.recortar(inicio, fim);
            System.out.println(resultado);
            System.out.println("Produtos encontrados: " + resultado.tamanho());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Comparações: " + arvore.getComparacoes());
            System.out.printf("Tempo: %.4f ms%n", arvore.getTempo());
        }
    }
}

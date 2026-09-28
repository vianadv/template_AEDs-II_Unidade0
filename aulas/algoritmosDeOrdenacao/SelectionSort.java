package algoritmosDeOrdenacao;
/**
 * ============================================================================
 * ALGORITMO: SELECTION SORT (Ordenação por Seleção)
 * ============================================================================
 * A IDEIA: Imagina que você quer organizar pessoas por altura. Você olha para
 * a fila inteira, acha a menor pessoa de todas e joga ela lá para o primeiro lugar.
 * Depois, olha para o resto, acha a segunda menor e bota no segundo lugar.
 * Você vai "selecionando" o menor do bloco restante e colocando na posição certa.
 *
 * QUANDO USAR: Bom para quando fazer trocas na memória é muito caro,
 * pois ele faz no máximo 'n' trocas.
 *
 * COMPLEXIDADE (BIG-O):
 * - Pior Caso: O(n²)
 * - Melhor Caso: O(n²) - Ele é burro e roda todos os loops mesmo se já estiver ordenado.
 *
 *  NOTAÇÃO TETA (Θ) - LIMITES ASSINTÓTICOS EXATOS:
 *  * - Melhor Caso:  Θ(n²)  -> Mesmo ordenada, ele varre os laços até o fim de forma burra.
 *  * - Caso Médio:   Θ(n²)  -> Lista em ordem aleatória.
 *  * - Pior Caso:    Θ(n²)  -> Lista invertida ou qualquer outro estado.
 * ============================================================================
 */
public class SelectionSort {
    public static void ordenar(int[] vetor) {
        int n = vetor.length;

        // Passa por cada posição do vetor (menos a última, que vai sobrar certa automaticamente)
        for (int i = 0; i < n - 1; i++) {
            int indiceDoMenor = i; // Fingimos provisoriamente que o atual 'i' é o menor de todos

            // Procura se tem alguém menor ainda no resto do vetor que vem pela frente
            for (int j = i + 1; j < n; j++) {
                if (vetor[j] < vetor[indiceDoMenor]) {
                    indiceDoMenor = j; // Atualiza o índice caso ache alguém menor
                }
            }

            // Depois de olhar todo mundo, troca o menor de verdade com a posição atual 'i'
            int temporario = vetor[indiceDoMenor];
            vetor[indiceDoMenor] = vetor[i];
            vetor[i] = temporario;
        }
    }
}


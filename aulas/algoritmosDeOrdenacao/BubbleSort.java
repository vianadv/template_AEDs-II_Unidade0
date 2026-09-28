package algoritmosDeOrdenacao;

/**
 * ============================================================================
 * ALGORITMO: BUBBLE SORT (Ordenação por Bolha)
 * ============================================================================
 * A IDEIA: É o mais simples e o mais lento. Ele finge que os números são bolhas.
 * Os números maiores são "mais pesados" e vão flutuando para o final da fila.
 * Ele compara de dois em dois vizinhos e os troca de lugar se o da esquerda
 * for maior que o da direita.
 *
 * QUANDO USAR: apenas se exigir explicitamente. É horrível
 * para listas grandes.
 *
 * NOTAÇÃO TETA (Θ) - LIMITES ASSINTÓTICOS EXATOS:
 * - Melhor Caso:  Θ(n)   -> Se a lista já estiver ordenada (precisa de flag de otimização).
 * - Caso Médio:   Θ(n²)  -> Lista em ordem aleatória.
 * - Pior Caso:    Θ(n²)  -> Lista totalmente invertida.
 * ============================================================================
 */
public class BubbleSort {
    public static void ordenar(int[] vetor) {
        int n = vetor.length;

        // Esse primeiro laço serve para repetir o processo de varredura várias vezes
        for (int i = 0; i < n - 1; i++) {

            // Esse segundo laço anda pelo vetor comparando os vizinhos bucha com bucha
            // O "- i" serve para não olhar o final do vetor que já está ordenado
            for (int j = 0; j < n - 1 - i; j++) {

                // Se o número atual (j) for MAIOR que o próximo (j + 1), eles trocam de lugar
                if (vetor[j] > vetor[j + 1]) {
                    int temporario = vetor[j]; // Guarda o atual num copo vazio
                    vetor[j] = vetor[j + 1];   // Passa o menor para a esquerda
                    vetor[j + 1] = temporario; // Passa o maior para a direita
                }
            }
        }
    }
}



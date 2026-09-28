package algoritmosDeOrdenacao;

/**
 * ============================================================================
 * ALGORITMO: QUICK SORT (Ordenação Rápida)
 * ============================================================================
 * A IDEIA: É o algoritmo de ordenação geral mais rápido na prática. Ele escolhe
 * um número qualquer da lista e chama de PIVÔ. Ele joga todo mundo menor que
 * o pivô para a esquerda dele, e todo mundo maior para a direita. O pivô fica
 * isolado no meio exato dele. Depois, ele faz o mesmo processo recursivo com os
 * dois subblocos restantes.
 *
 * QUANDO USAR: É o padrão da indústria. Não gasta memória extra como
 * o Merge Sort, mas pode falhar se o pivô for muito mal escolhido.
 *
 * COMPLEXIDADE (BIG-O):
 * - Pior Caso: O(n²) - Acontece se a lista já estiver ordenada e você escolher sempre o pior pivô.
 * - Melhor Caso: O(n log n) - Desempenho excelente.
 *
 *  NOTAÇÃO TETA (Θ) - LIMITES ASSINTÓTICOS EXATOS:
 *  * - Melhor Caso:  Θ(n log n) -> Quando o pivô divide a lista exatamente ao meio.
 *  * - Caso Médio:   Θ(n log n) -> Casos aleatórios do dia a dia.
 *  * - Pior Caso:    Θ(n²)      -> Quando o pivô é sempre o maior ou menor elemento
 *  *                               (ex: processar lista já ordenada usando o último elemento como pivô).
 *  * ==
 * ============================================================================
 */
public class QuickSort {
    // Método principal que será chamado recursivamente
    public static void ordenar(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            // Acha a posição correta onde o pivô vai estacionar e separa os lados
            int indicePivo = particionar(vetor, inicio, fim);

            // Aplica o Quick Sort para o pedaço da esquerda (menores que o pivô)
            ordenar(vetor, inicio, indicePivo - 1);
            // Aplica o Quick Sort para o pedaço da direita (maiores que o pivô)
            ordenar(vetor, indicePivo + 1, fim);
        }
    }

    // Faz a mágica de separar menores para a esquerda e maiores para a direita
    private static int particionar(int[] vetor, int inicio, int fim) {
        int pivo = vetor[fim]; // Estratégia simples: pegamos o último elemento como pivô
        int i = (inicio - 1);  // Ponteiro dos elementos menores

        for (int j = inicio; j < fim; j++) {
            // Se o número atual (j) for menor ou igual ao pivô definido
            if (vetor[j] <= pivo) {
                i++; // Avança o limite dos menores

                // Troca o número atual com o elemento da posição i
                int temporario = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = temporario;
            }
        }

        // Por fim, coloca o pivô no seu lugar correto (logo após os menores)
        int temporario = vetor[i + 1];
        vetor[i + 1] = vetor[fim];
        vetor[fim] = temporario;

        return i + 1; // Retorna a posição final do pivô para sabermos onde quebrar o vetor
    }
}


package algoritmosDeOrdenacao;


/**
 * ============================================================================
 * ALGORITMO: INSERTION SORT (Ordenação por Inserção)
 * ============================================================================
 * A IDEIA: Pense em como você organiza cartas de baralho na mão. Você pega uma
 * carta nova da mesa e vai empurrando as outras cartas da sua mão para o lado
 * até achar o lugar exato dela e "inserir" ali no meio.
 *
 * QUANDO USAR; Excelente para listas pequenas ou para listas que já estão
 * quase totalmente ordenadas (ele é super rápido nesses cenários).
 *
 * COMPLEXIDADE (BIG-O):
 * - Pior Caso: O(n²) - Se a lista estiver invertida.
 * - Melhor Caso: O(n) - Se a lista já estiver perfeitamente ordenada.
 *
 * * NOTAÇÃO TETA (Θ) - LIMITES ASSINTÓTICOS EXATOS:
 *  * - Melhor Caso:  Θ(n)   -> Se a lista já estiver perfeitamente ordenada.
 *  * - Caso Médio:   Θ(n²)  -> Lista em ordem aleatória.
 *  * - Pior Caso:    Θ(n²)  -> Lista totalmente invertida.
 * ============================================================================
 */
public class InsertionSort {
    public static void ordenar(int[] vetor) {
        int n = vetor.length;

        // Começamos da segunda carta (índice 1), porque a primeira está "organizada" sozinha
        for (int i = 1; i < n; i++) {
            int chave = vetor[i]; // Guarda o número que vamos tentar encaixar no lugar certo
            int j = i - 1;

            // Fica empurrando os números maiores para a direita para abrir espaço
            while (j >= 0 && vetor[j] > chave) {
                vetor[j + 1] = vetor[j]; // Joga o número maior para frente
                j--; // Anda para trás para testar o próximo elemento
            }

            // Coloca a nossa "chave" (o número guardado) na vaga que sobrou limpa
            vetor[j + 1] = chave;
        }
    }
}

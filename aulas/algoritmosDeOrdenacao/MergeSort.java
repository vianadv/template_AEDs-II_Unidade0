package algoritmosDeOrdenacao;

/**
 * ============================================================================
 * ALGORITMO: MERGE SORT (Ordenação por Mistura)
 * ============================================================================
 * A IDEIA: Ele usa a estratégia "Dividir para Conquistar". Ele corta a lista ao
 * meio repetidamente (usando recursão) até ter várias listinhas de 1 número só.
 * Depois, ele vem juntando (mesclando) essas listas de duas em duas, olhando
 * quem é menor e já salvando em ordem.
 *
 * QUANDO USAR: Quando você precisa de uma garantia de tempo rápido,
 * não importa como a lista venha bagunçada. Mas atenção: ele gasta memória extra!
 *
 * COMPLEXIDADE (BIG-O):
 * - Pior Caso: O(n log n) - Muito rápido!
 * - Melhor Caso: O(n log n) - Demora o mesmo tempo sempre.
 *
 *  NOTAÇÃO TETA (Θ) - LIMITES ASSINTÓTICOS EXATOS:
 *  * - Melhor Caso:  Θ(n log n)
 *  * - Caso Médio:   Θ(n log n)
 *  * - Pior Caso:    Θ(n log n) -> O tempo é estritamente o mesmo para qualquer cenário.
 *  * =
 * ============================================================================
 */
public class MergeSort {
    // Esse método divide a lista na metade recursivamente
    public static void ordenar(int[] vetor, int esquerda, int direita) {
        // Se a lista só tiver 1 elemento (esquerda == direita), ela já está pronta.
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2; // Acha o ponto central da lista

            ordenar(vetor, esquerda, meio);     // Divide e quebra a metade da esquerda
            ordenar(vetor, meio + 1, direita); // Divide e quebra a metade da direita

            juntar(vetor, esquerda, meio, direita); // Intercala as metades ordenando
        }
    }

    // Esse método junta as duas metades organizando os valores
    private static void juntar(int[] vetor, int esquerda, int meio, int direita) {
        // Calcula o tamanho de dois vetores temporários para servir de rascunho
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        int[] esqVetor = new int[n1];
        int[] dirVetor = new int[n2];

        // Copia os dados originais do vetor para as listas de rascunho
        for (int i = 0; i < n1; ++i) esqVetor[i] = vetor[esquerda + i];
        for (int j = 0; j < n2; ++j) dirVetor[j] = vetor[meio + 1 + j];

        int i = 0, j = 0;
        int k = esquerda; // Ponteiro do vetor original

        // Compara o rascunho da esquerda com o da direita e põe o menor de volta no vetor
        while (i < n1 && j < n2) {
            if (esqVetor[i] <= dirVetor[j]) {
                vetor[k] = esqVetor[i];
                i++;
            } else {
                vetor[k] = dirVetor[j];
                j++;
            }
            k++;
        }

        // Se sobrou algum número esquecido no vetor da esquerda, joga direto no final
        while (i < n1) {
            vetor[k] = esqVetor[i];
            i++; k++;
        }

        // Se sobrou algum número esquecido no vetor da direita, joga direto no final
        while (j < n2) {
            vetor[k] = dirVetor[j];
            j++; k++;
        }
    }
}


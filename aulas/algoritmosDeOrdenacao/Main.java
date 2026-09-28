package algoritmosDeOrdenacao;

import java.util.Arrays;

/**
 * ============================================================================
 * CLASSE DE TESTE PRINCIPAL
 * ============================================================================
 * objetivo: Serve para provar que os algoritmos funcionam na
 * prática ou rodar um teste rápido caso o professor peça para debugar um vetor.
 * ============================================================================
 */
public class Main {

    // Método auxiliar para criar uma cópia limpa e bagunçada do vetor a cada teste
    public static int[] obterVetorBaguncado() {
        return new int[]{12, 5, 3, 1, 8, 7, 2, 4};
    }

    public static void main(String[] args) {
        int[] vetor;

        System.out.println("=== TESTANDO OS ALGORITMOS DE ORDENAÇÃO ===");

        // --------------------------------------------------------------------
        // TESTE 1: BUBBLE SORT
        // --------------------------------------------------------------------
        vetor = obterVetorBaguncado();
        BubbleSort.ordenar(vetor);
        System.out.println("Bubble Sort:    " + Arrays.toString(vetor));

        // --------------------------------------------------------------------
        // TESTE 2: SELECTION SORT
        // --------------------------------------------------------------------
        vetor = obterVetorBaguncado();
        SelectionSort.ordenar(vetor);
        System.out.println("Selection Sort: " + Arrays.toString(vetor));

        // --------------------------------------------------------------------
        // TESTE 3: INSERTION SORT
        // --------------------------------------------------------------------
        vetor = obterVetorBaguncado();
        InsertionSort.ordenar(vetor);
        System.out.println("Insertion Sort: " + Arrays.toString(vetor));

        // --------------------------------------------------------------------
        // TESTE 4: MERGE SORT
        // --------------------------------------------------------------------
        vetor = obterVetorBaguncado();
        // O Merge Sort precisa receber os índices das pontas: 0 até (tamanho - 1)
        MergeSort.ordenar(vetor, 0, vetor.length - 1);
        System.out.println("Merge Sort:     " + Arrays.toString(vetor));

        // --------------------------------------------------------------------
        // TESTE 5: QUICK SORT
        // --------------------------------------------------------------------
        vetor = obterVetorBaguncado();
        // O Quick Sort também precisa receber os índices das pontas: 0 até (tamanho - 1)
        QuickSort.ordenar(vetor, 0, vetor.length - 1);
        System.out.println("Quick Sort:     " + Arrays.toString(vetor));
    }
}

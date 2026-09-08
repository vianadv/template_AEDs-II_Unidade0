package aula4_01_09_2026;
public class AlgortmoBubbleSort {
            public static void bubbleSort(int[] array) {
                int n = array.length;

                for (int i = 0; i < n - 1; i++) {

                    for (int j = 0; j < n - i - 1; j++) {

                        if (array[j] > array[j + 1]) {

                            int temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;
                        }
                    }
                }
            }

            public static void main(String[] args) {
                int[] meuArray = {64, 34, 25, 12, 22, 11, 90};

                bubbleSort(meuArray);

                System.out.print("Array ordenado: ");
                for (int num : meuArray) {
                    System.out.print(num + " ");
                }
            }
        }

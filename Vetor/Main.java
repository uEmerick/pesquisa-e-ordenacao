package Vetor;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        Random gerador = new Random();
        int TL = 10;
        int vetor[] = new int[TL];
    
        for(int i=0; i<vetor.length; i++)
            vetor[i] = gerador.nextInt(100);

        System.out.println("Vetor antes da ordenação:");
        for(int i=0; i<vetor.length; i++)
            System.out.print(vetor[i] + " ");

        quickSortSemPivo(vetor);
    }

    public static void quickSortSemPivo(int[] vetor) 
    {
        int temp;
        
        for(int j=vetor.length-1; j>0; j--)
        {
            for(int i=0; i < j; i++)
            {
                if(vetor[i] > vetor[j])
                {
                    temp = vetor[i];
                    vetor[i] = vetor[j];
                    vetor[j] = temp;
                }
            }
        }

        System.out.println("\nVetor após a ordenação:");
        for(int k=0; k<vetor.length; k++)
            System.out.print(vetor[k] + " ");
    }
}

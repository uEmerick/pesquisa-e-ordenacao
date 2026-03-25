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

        quickSortSemPivo(vetor, TL);

        System.out.println("\nVetor depois da ordenação:");
        for(int i=0; i<vetor.length; i++)
            System.out.print(vetor[i] + " ");
    }

    public static void quickSortSemPivo(int[] vetor, int TL)
    {
        quickSP(vetor, 0,TL-1);
    }

    public static void quickSP(int vet[], int inicio, int fim)
    {
        int i = inicio, j = fim, aux;
        boolean flag = true;

        while(i < j)
        {
            if(flag)
                while(i < j && vet[i] <= vet[j])
                    i++;

            else
            {
                while(i < j && vet[j] >= vet[i])
                    j--;
            }

            aux = vet[i];
            vet[i] = vet[j];
            vet[j] = aux;

            flag = !flag;
        }
        if(inicio < i-1)
            quickSP(vet, inicio, i-1);
        if(j+1 < fim)
            quickSP(vet, j+1, fim);
    }

    public static void quickSortComPivo(int[] vetor, int TL)
    {
        quickSP(vetor, 0,TL-1);
    }

    public static void quickCP(int vet[], int inicio, int fim)
    {
        int i = inicio, j = fim, pivo = vet[(inicio+fim)/2], aux;

        while(i <= j)
        {
            while(vet[i] < pivo)
                i++;

            while(vet[j] > pivo)
                j--;

            if (i <= j)
            {
                aux = vet[i];
                vet[i] = vet[j];
                vet[j] = aux;
                i++; j--;
            }
        }
        if(inicio < j)
            quickCP(vet, inicio, j);
        if(i < fim)
            quickCP(vet, i, fim);
    }
}

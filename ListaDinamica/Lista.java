package ListaDinamica;
public class Lista
{
    private No inicio;
    private No fim;

    public Lista()
    {
        this.inicio = null;
        this.fim = null;
    }

    public void setInicio(No inicio)
    {
        this.inicio = inicio;
    }

    public void setFim(No fim)
    {
        this.fim = fim;
    }

    public No getInicio()
    {
        return inicio;
    }

    public No getFim()
    {
        return fim;
    }

    public void adicionar(int valor)
    {
        No novoNo = new No(valor);

        if(inicio == null)
            inicio = fim = novoNo;
        else
        {
            fim.setProx(novoNo);
            fim = novoNo;
        }
    }

    public void imprimir()
    {
        No atual = inicio;

        while(atual != null)
        {
            System.out.println(atual.getValor());
            atual = atual.getProx();
        }
    }

    public void remover(int valor)
    {
        No atual = inicio;
        No anterior = null;

        while(atual != null)
        {
            if(atual.getValor() == valor)
            {
                if(anterior == null)
                    inicio.setProx(atual.getProx());
                else
                    anterior.setProx(atual.getProx());
            }
            anterior = atual;
            atual = atual.getProx();
        }
    }

    private void particao(Lista lista1, Lista lista2, int seq)
    {
        int turno = 1;
        No aux = this.inicio;
        No novoNo;
        No fimLista1 = null;
        No fimLista2 = null;

        while(aux != null)
        {
            for(int i=0; i<seq && aux != null; i++)
            {
                novoNo = new No(aux.getValor());

                if(turno == 1)
                {
                    if(lista1.getInicio() == null)
                        lista1.inicio = lista1.fim = novoNo;
                    else
                    {
                        fimLista1.setProx(novoNo);
                        novoNo.setAnt(fimLista1);
                    }
                    fimLista1 = novoNo;
                }
                else
                {
                    if(lista2.inicio == null)
                        lista2.inicio = lista2.fim = novoNo;
                    else
                    {
                        fimLista2.setProx(novoNo);
                        novoNo.setAnt(fimLista2);
                    }
                    fimLista2 = novoNo;
                }

                aux = aux.getProx();
            }

            if(turno == 1)
                turno = 2;
            else
                turno = 1;
        }
    }

    private void fusao(Lista lista1, Lista lista2, int seq, int TL)
    {
        int i, j, k=0;
        No aux1 = lista1.inicio;
        No aux2 = lista2.inicio;
        No auxK = this.inicio;

        while(k < TL)
        {
            i = 0;
            j = 0;

            while(i < seq && j < seq && aux1 != null && aux2 != null)
            {
                if(aux1.getValor() < aux2.getValor())
                {
                    auxK.setValor(aux1.getValor());
                    aux1 = aux1.getProx();
                    i++;
                }
                else
                {
                    auxK.setValor(aux2.getValor());
                    aux2 = aux2.getProx();
                    j++;
                }
                auxK = auxK.getProx();
                k++;
            }

            while(i < seq && aux1 != null)
            {
                auxK.setValor(aux1.getValor());
                aux1 = aux1.getProx();
                auxK = auxK.getProx();
                i++;
                k++;
            }

            while(j < seq && aux2 != null)
            {
                auxK.setValor(aux2.getValor());
                aux2 = aux2.getProx();
                auxK = auxK.getProx();
                j++;
                k++;
            }
        }
    }

    public void mergeSort()
    {
        int TL = tamanhoLista();
        int seq=1;
        Lista lista1 = new Lista();
        Lista lista2 = new Lista();

        if(TL < 1)
        {
            while(seq < TL)
            {
                lista1.limpar();
                lista2.limpar();

                particao(lista1, lista2, seq);
                fusao(lista1, lista2, seq, TL);

                seq *= 2;
            }
        }
    }

    public int tamanhoLista()
    {
        int tamanho = 0;
        No aux = inicio;
        while( aux != null)
        {
            tamanho++;
            aux = aux.getProx();
        }
        return tamanho;
    }

    public void limpar()
    {
        inicio = null;
        fim = null;
    }

//    public quickSortSemPivo()
//    {
//
//    }
}
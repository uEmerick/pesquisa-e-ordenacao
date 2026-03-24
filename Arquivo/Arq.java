package Arquivo;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Arq
{
    private RandomAccessFile arquivo;


    public Arq(String nomeArquivo)
    {
        try
        {
            arquivo = new RandomAccessFile(nomeArquivo, "rw");
        }
        catch(IOException e)
        {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

    public RandomAccessFile getArquivo()
    {
        return arquivo;
    }

    public void truncate(long pos)
    {
        try
        {
            arquivo.setLength(pos* Registro.length());
        }
        catch(IOException e)
        {
            System.out.println("Erro ao truncar o arquivo: " + e.getMessage());
        }
    }

    public boolean eof()
    {
        boolean eof = false;
        try
        {
            if(arquivo.getFilePointer() == arquivo.length())
                eof = true;
        }
        catch(IOException e)
        {
            System.out.println("Erro ao verificar o fim do arquivo: " + e.getMessage());
        }
        return eof;
    }

    public int filesize()
    {
        int size = 0;
        try
        {
            size = (int) (arquivo.length() / Registro.length());
        }
        catch(IOException e)
        {
            System.out.println("Erro ao obter o tamanho do arquivo: " + e.getMessage());
        }
        return size;
    }

    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        }
        catch(IOException e)
        {
            System.out.println("Erro ao posicionar o ponteiro do arquivo: " + e.getMessage());
        }
    }

    public void inserirRegNoFinal(Registro reg)
    {
        try
        {
            arquivo.seek(filesize());
            reg.gravarNoArquivo(arquivo);
        }
        catch(IOException e)
        {
            System.out.println("Erro ao inserir registro: " + e.getMessage());
        }
    }

    public void fecharArquivo()
    {
        try
        {
            arquivo.close();
        }
        catch(IOException e)
        {
            System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
        }
    }

    public void exibirRegistros()
    {
        try
        {
            arquivo.seek(0);
            while(!eof())
            {
                Registro reg = new Registro();
                reg.leDoArq(arquivo);
                System.out.println("Número: " + reg.getNumero());
            }
        }
        catch(IOException e)
        {
            System.out.println("Erro ao exibir registros: " + e.getMessage());
        }
    }

    private void particao(int seq,int TL, Registro reg1, Registro reg2)
    {
        int k = 0;
        int pParticao1 = TL;
        int pParticao2 = TL + (TL/2) + (TL%2);

        while(k < TL)
        {
            for(int i=0;i < seq && k < TL; i++)
            {
                seekArq(k);
                reg1.leDoArq(arquivo);
                seekArq(pParticao1);
                reg1.gravarNoArquivo(arquivo);
                k++; pParticao1++;
            }
            for(int j=0; j<seq && k < TL; j++)
            {
                seekArq(k);
                reg2.leDoArq(arquivo);
                seekArq(pParticao2);
                reg2.gravarNoArquivo(arquivo);
                k++; pParticao2++;
            }
        }
    }

    private void fusao(int seq, int TL, Registro reg1, Registro reg2)
    {
        int i, j, k=0;
        int pParticao1 = TL;
        int pParticao2 = TL + (TL/2) + (TL%2);
        int fimParticao1 = pParticao2;
        int fimParticao2 = TL + TL;

        while(k<TL)
        {
            i = 0;
            j = 0;
            
            if (pParticao1 < fimParticao1) 
            {
                seekArq(pParticao1);
                reg1.leDoArq(arquivo);
            }

            if (pParticao2 < fimParticao2) 
            {
                seekArq(pParticao2);
                reg2.leDoArq(arquivo);
            }

            while(i < seq && j < seq && pParticao1 < fimParticao1 && pParticao2 < fimParticao2)
            {
                if(reg1.getNumero() < reg2.getNumero())
                {
                    seekArq(k);
                    reg1.gravarNoArquivo(arquivo);
                    pParticao1++;
                    i++;

                    if (i < seq && pParticao1 < fimParticao1) 
                    {
                        seekArq(pParticao1);
                        reg1.leDoArq(arquivo);
                    }
                }
                else
                {
                    seekArq(k);
                    reg2.gravarNoArquivo(arquivo);
                    pParticao2++;
                    j++;

                    if(j < seq && pParticao2 < fimParticao2) 
                    {
                        seekArq(pParticao2);
                        reg2.leDoArq(arquivo);
                    }
                }
                k++;
            }

            while(i < seq && pParticao1 < fimParticao1)
            {
                seekArq(k);
                reg1.gravarNoArquivo(arquivo);
                pParticao1++;
                i++;
                k++;

                if(i < seq && pParticao1 < fimParticao1) 
                {
                    seekArq(pParticao1);
                    reg1.leDoArq(arquivo);
                }
            }

            while(j < seq && pParticao2 < fimParticao2)
            {
                seekArq(k);
                reg2.gravarNoArquivo(arquivo);
                pParticao2++;
                j++;
                k++;

                if(j < seq && pParticao2 < fimParticao2)
                {
                    seekArq(pParticao2);
                    reg2.leDoArq(arquivo);
                }
            }

        }
    }

    public void mergeSort() // Com apenas um arquivo
    {
        int TL = filesize();

        if(TL > 1)
        {
            int seq=1;
            Registro reg1 = new Registro();
            Registro reg2 = new Registro();

            while(seq < TL)
            {
                particao(seq,TL, reg1,reg2);
                fusao(seq, TL, reg1, reg2);
                seq *= 2;
            }
            truncate(TL);
        }
    }
}
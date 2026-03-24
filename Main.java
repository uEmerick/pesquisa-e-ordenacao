import Arquivo.Arq;
import Arquivo.Registro;
import ListaDinamica.Lista;

public class Main {
    public static void main(String[] args) {
        // Teste da classe Registro
        Registro reg1 = new Registro(123);
        Registro reg2 = new Registro(456);

        // Teste da classe Arq
        Arq arq = new Arq("arquivo.dat");
        arq.inserirRegNoFinal(reg1);
        arq.inserirRegNoFinal(reg2);
        arq.exibirRegistros();
        arq.fecharArquivo();

        // Teste da classe Lista
        Lista lista = new Lista();
        lista.adicionar(10);
        lista.adicionar(20);
        lista.adicionar(30);
        System.out.println("Lista após adição:");
        lista.imprimir();

        lista.remover(20);
        System.out.println("Lista após remoção de 20:");
        lista.imprimir();
    }
}

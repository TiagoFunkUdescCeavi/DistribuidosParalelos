package Principal;

import ComThread.NumeroPrimoThread;
import SemThread.NumeroPrimo;
import java.util.ArrayList;

public class Principal {

    public static void main(String[] args) {
        final int inicio = 2;
        final int fim = 1000000;
        
        testarComNThread(inicio, fim);
    }
    
    private static void testarSemThread(int inicio, int fim){
        new NumeroPrimo().buscarNumerosPrimos(inicio, fim);
    }
    
    private static void testarCom1Thread(int inicio, int fim){
        NumeroPrimoThread npt = new NumeroPrimoThread();
        npt.setIntervalo( inicio, fim );
        npt.start();
    }
    
    private static void testarCom2Thread(int inicio, int fim){
        NumeroPrimoThread npt1 = new NumeroPrimoThread();
        npt1.setIntervalo( inicio, (inicio+fim)/2 );
        npt1.start();
        NumeroPrimoThread npt2 = new NumeroPrimoThread();
        npt2.setIntervalo( (inicio+fim)/2+1, fim );
        npt2.start();
    }
    
    private static void testarComNThread( int inicio, int fim ){
        int i;
        NumeroPrimoThread np;
        ArrayList<NumeroPrimoThread> list = new ArrayList<>();
        
        for( i = inicio; i < fim; i += 10 ){
            np = new NumeroPrimoThread();
            np.setIntervalo(i, i+10);
            list.add( np );
        }
        
        for( NumeroPrimoThread n: list ){
            n.start();
        }
    }
}

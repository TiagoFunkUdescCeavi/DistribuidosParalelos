package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadSocket extends Thread{
    
    private Socket socket;
    private List<ObservadorConexao> listaObs = new ArrayList<>();
    
    public ThreadSocket(Socket soket) {
        this.socket = soket;
    }
    
    public void adicionarObservador(ObservadorConexao obs){
        listaObs.add(obs);
    }
    
    public void adicionarObservador( List<ObservadorConexao> listaObs){
        this.listaObs.addAll(listaObs);
    }
    
    public void removerObservador(ObservadorConexao obs){
        listaObs.remove(obs);
    }

    @Override
    public void run(){
        ObjectInputStream input;
        String mensagem;
        
        try {
            input = new ObjectInputStream( socket.getInputStream() );
            
            System.out.println("esperando mensagem");
            mensagem = input.readUTF();
            System.out.println("lido " + mensagem);
            
            System.out.println("Numero obs: " + listaObs.size());
            for(ObservadorConexao obs: listaObs){
                obs.encaminharMensagem(mensagem);
            }
        } catch (IOException ex) {
            for(ObservadorConexao obs: listaObs){
                obs.avisarErroIOException();
            }
        }
        
    }
}
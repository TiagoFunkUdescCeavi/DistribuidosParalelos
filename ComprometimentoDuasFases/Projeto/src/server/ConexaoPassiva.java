package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConexaoPassiva extends Conexao{
    
    private int port;
    
    private Socket socket;
    private ServerSocket serverSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    public ConexaoPassiva(int timeout, String port) throws IOException {
        super(timeout);
        try{
            this.port = Integer.parseInt(port);
        }catch(NumberFormatException ex ){
            throw new IllegalArgumentException("Parâmetro porta para SocketThread deve ser um número.");
        }
    }
    
    @Override
    public void enviar( String mensagem ) throws IOException{
        output.writeUTF( mensagem );
        output.flush();
    }
    
    @Override
    public void fecharConexao() throws IOException {
        System.out.println("Fechando a conexão.");
        input.close();
        output.close();
        socket.close();
    }

    @Override
    public void run(){
        String mensagem = "";
        
        try {
            serverSocket = new ServerSocket( port );
            socket = serverSocket.accept();
            socket.setSoTimeout( super.timeout );

            output = new ObjectOutputStream( socket.getOutputStream() );
            input  = new ObjectInputStream( socket.getInputStream() );
            
            for (int i = 0; i < 2; i++) {
                try{
                    mensagem = input.readUTF();
                    if( mensagem != null && !mensagem.isEmpty() ){
                        for( ObservadorConexao obs : listaObservadores ){
                            obs.encaminharMensagem( mensagem, this );
                        }
                    }
                }catch (SocketTimeoutException ex ){
                    for( ObservadorConexao obs : listaObservadores ){
                        obs.avisarTimeout( this );
                    }
                }
                Thread.sleep(100);
            }
            
        } catch (java.io.EOFException ex){
            System.out.println("Conexão fechada (Linux).");
        } catch (java.net.SocketException ex){
            System.out.println("Conexão fechada (Windows).");
        }catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

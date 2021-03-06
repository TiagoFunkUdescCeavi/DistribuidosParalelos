package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DaoContato {
    
    private final static String DAO = "contato.txt";
    
    public static void adicionarContato(String token1, String token2) throws IOException{
        BufferedWriter bw = new BufferedWriter( new FileWriter( DAO, true) );
        bw.write( token1+";"+token2 );
        bw.newLine();
        bw.write( token2+";"+token1 );
        bw.newLine();
        bw.close();
    }
    
    public static boolean buscarContato( String token1, String token2 ) throws FileNotFoundException, IOException{
        String linha;
        String[] valores;
        BufferedReader br = new BufferedReader( new FileReader( DAO ) );
        
        while( (linha = br.readLine() ) != null ){
            valores = linha.split(";");
            if( valores[0].equals( token1 ) && valores[1].equals( token2 ) ){
                return true;
            }
        }
        br.close();
        return false;
    }

    public static List<String> lerTodosContatos(String token) throws FileNotFoundException, IOException {
        String linha;
        String[] valores;
        List<String> contatos = new ArrayList<>();
        BufferedReader br = new BufferedReader( new FileReader( DAO ) );
        
        while( (linha = br.readLine() ) != null ){
            valores = linha.split(";");
            if( valores[0].equals( token ) ){
                contatos.add( valores[1] );
            }
        }
        br.close();
        return contatos;
    }
}

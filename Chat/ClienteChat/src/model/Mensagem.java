package model;

public class Mensagem {
	
	private String texto;
	private boolean clienteEnviou;
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public boolean isClienteEnviou() {
		return clienteEnviou;
	}
	public void setClienteEnviou(boolean clienteEnviou) {
		this.clienteEnviou = clienteEnviou;
	}

}
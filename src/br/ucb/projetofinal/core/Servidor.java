package br.ucb.projetofinal.core;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import br.ucb.projetofinal.jobs.ServerHandlerJob;

public class Servidor{
	
	public static void main(String [] args) throws IOException {

		
		System.out.println("Esperando por clientes. . .");
		
		// cirando socket para permitir que receba novas conexoes do cliente
		ServerSocket ss = new ServerSocket(9804);
		
		while (true){
			
			// apos contato o socket de comunicacao (cliente - servidor) e criado
			Socket soc = ss.accept();
			System.out.println("Conexao estabelecida com o cliente! " + soc);
			
			//criando o ConversationHandlerJob
			ServerHandlerJob handler = new ServerHandlerJob(soc);
			
			// damos inicio ao serverr
			handler.start();
		}
	}
}
	


package br.ucb.projetofinal.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import br.ucb.projetofinal.jobs.ConversationHandlerJob;


public class ServidorChat {
	// atributos
	public static ArrayList<String> userNames = new ArrayList<String>();
	public static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();

	public static void main(String[] args) throws IOException {
		System.out.println("Esperando por clientes. . .");
		
		// cirando socket para permitir que receba novas conexoes do cliente
		ServerSocket ss = new ServerSocket(9800);
		
		while (true){
			
			// apos contato o socket de comunicacao (cliente - servidor) e criado
			Socket soc = ss.accept();
			System.out.println("Conexao estabelecida com o cliente! " + soc);
			
			//criando o ConversationHandlerJob
			ConversationHandlerJob handler = new ConversationHandlerJob(soc);
			
			// damos inicio ao serverr
			handler.start();
		}
	}	
}

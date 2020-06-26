package br.ucb.projetofinal.jobs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import br.ucb.projetofinal.core.ServidorChat;

public class ConversationHandlerJob extends Thread {

	// atributos
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String userName;
	private BufferedReader userInput;
	String clientMsg = "";
	
	
	// Construtores
	public ConversationHandlerJob(Socket socket) throws IOException{
		setSocket(socket);
	}
	
	// Getters & Setters
	private Socket getSocket() {
		return socket;
	}
	private void setSocket(Socket socket) {
		this.socket = socket;
	}
	private BufferedReader getIn() {
		return in;
	}
	private void setIn(BufferedReader in) {
		this.in = in;
	}
	private PrintWriter getOut() {
		return out;
	}
	private void setOut(PrintWriter out) {
		this.out = out;
	}
	private String getUserName() {
		return userName;
	}
	private void setUserName(String userName) {
		this.userName = userName;
	}
	public BufferedReader getUserInput() {
		return userInput;
	}
	public void setUserInput(BufferedReader userInput) {
		this.userInput = userInput;
	}

	// Metodos
	@Override
	public void run() {
		super.run();
		
		// incniciando o inputStrem e o output
		try {
			
			// iniciando in
			setIn(new BufferedReader (new InputStreamReader(getSocket().getInputStream())));
			
			// iniciando out com auto flush (envia automaticamente os dados do printwriter, sem precisar de dar um flush)
			setOut(new PrintWriter(getSocket().getOutputStream(), true));
			
			//userinput
			setUserInput(new BufferedReader(new InputStreamReader(System.in)));
			
			// msg pra pedir um username
			//getOut().println("NAMEREQUIRED");
			
			// pega o nome do usuario
			setUserName(getIn().readLine());
			
			// nome nulo, mata thread
			if(getUserName() == null){
				return;
			}
			
			
			// add cliente no servidor pra ele ter a referencia das msgs, ou poder ver as msgs
			// ServidorChat.printWriters.add(getOut());
		
			// receber a msg do cliente
			getOut().println("Bem vindo ao chat, " + getUserName() + ". Fale conosco: ");
			
			// envio de msg para o cliente
			
			do{
				clientMsg = in.readLine();
				System.out.println(getUserName() + ": " + clientMsg);
				
				if(clientMsg.equalsIgnoreCase("sair")) {
					getOut().println("Tchau ate mais se cuida");
				}else {
					getOut().println(getUserInput().readLine());
					
				}
				
			}while(!clientMsg.equalsIgnoreCase("sair"));
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package br.ucb.projetofinal.jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import br.ucb.projetofinal.core.ServidorChat;
import br.ucb.projetofinal.models.Produto;
import br.ucb.projetofinal.models.Usuario;

public class ServerHandlerJob extends Thread {
	
	// atributos 
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;	
	private ObjectOutputStream OutObject;
	int opcao = 0;
	String str = "";
	String str3 = "";
	
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	long idAtual = 0;
	
	// construtores
	public ServerHandlerJob(Socket socket) throws IOException{
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
	public ObjectOutputStream getOutObject() {
		return OutObject;
	}
	public void setOutObject(ObjectOutputStream OutObject) {
		this.OutObject = OutObject;
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
			
			// criando enviador de objeto
			setOutObject(new ObjectOutputStream(getSocket().getOutputStream()));
			
			// recebendo dados do login
			// se str (nome) nao for nulo, cria o usuario
			str = getIn().readLine();
			usuarios.add(new Usuario(str));
			idAtual = usuarios.get(usuarios.size() - 1).getId();
			for (Usuario usuario : usuarios) {
				System.out.println("nome: "+usuario.getNome()+" ID: "+usuario.getId());
			}
			do {
				opcao = Integer.parseInt(in.readLine());
				
				switch(opcao) {
					case 1:
						String str2[] = in.readLine().split(":");
						System.out.println(str2[0]+" "+str2[1]+" "+str2[2]+" ");
						for (Usuario usuario : usuarios) {
							if(usuario.getId() == idAtual) {
								usuario.getProdutos().add(new Produto(str2[0],Integer.parseInt(str2[1]), Integer.parseInt(str2[2])));
							}
						}
						
						break;
					case 2:
						for (Usuario usuario : usuarios) {
							if(usuario.getId() == idAtual) {
								getOutObject().reset();
								getOutObject().writeObject(usuario);
							}
						}
						str3 = in.readLine();
						for (Usuario usuario : usuarios) {
	                        if (usuario.getId() == idAtual) {
	                            ArrayList<Produto> toRemove = new ArrayList<>();
	                            for (Produto produto : usuario.getProdutos()) {
	                                if (produto.getCod() == Integer.parseInt(str3)) {
	                                    toRemove.add(produto);
	                                }
	                            }
	                            usuario.getProdutos().removeAll(toRemove);
	                        }
	                    }
						break;
					case 3:
						for (Usuario usuario : usuarios) {
							if(usuario.getId() == idAtual) {
								getOutObject().reset();
								getOutObject().writeObject(usuario);
							}
						}
						break;
				}
		
				System.out.println("opcao: "+ in.readLine());
				str3 = "";
			} while(opcao != 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

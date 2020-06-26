package br.ucb.projetofinal.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ucb.projetofinal.models.Produto;
import br.ucb.projetofinal.models.Usuario;

public class Cliente{
	// atributos do cliente
	public static BufferedReader inChat;
	public static PrintWriter outChat;
	public static String userName;
	public static PrintWriter out;
	public static BufferedReader in;	
	public static BufferedReader userInput;
	public static ObjectInputStream inObject;
	
		
	public static void main(String [] args) throws IOException, ClassNotFoundException{
		// atributos
		int opcao = 0;
		String str = "";
		
		Cliente cliente = new Cliente();
		
		cliente.startCliente();

		do {
			
			opcao = cliente.menuPrincipal();
			
			switch(opcao) {
				case 1:
					cliente.cadastrarProduto();
					break;
				case 2:
					cliente.removerProduto();
					break;
				case 3:
					cliente.listarProdutos();
					break;	
				case 4:
					try {
						cliente.startChat();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
			}
			// transformando opcao em str
			str = "" + opcao;
			
			// mandando info para o servidor
			out.println(str);
		}while (opcao != 0);
		
		
		
		// fechar conexoes
		// soc.close();
		userInput.close();
		in.close();
		out.close();
	}
	
	// metodos cliente
	
	// começar uma instancia de cliente
	public void startCliente() throws UnknownHostException, IOException {
		String str = "";
		
		// criando socket para conexoes com servidor
		Socket soc = new Socket("localhost",9804);
		
		// ler buffer do console
		userInput = new BufferedReader(new InputStreamReader(System.in));
		
		// receptor de info do servidor
		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		
		//envidor de info para servidor
		out = new PrintWriter (soc.getOutputStream(), true);
		
		// criando receptor de objeto
		inObject = new ObjectInputStream(soc.getInputStream());
		
		// criando cadastro
		do {
			System.out.println("CADASTRO\nInsina o nome do login: ");
			str = userInput.readLine();
			userName = str;
			if(str.contentEquals("")){
				System.out.println("ERRO\nNome precisa ser preenchido");
			}
		}while (str.contentEquals(""));
		
		// mandando info pro servidor
		out.println(str);
	}
	
	// menu principal retorna a opcao escolhida
	public int menuPrincipal() throws NumberFormatException, IOException {
		int opcao = 0;
		String str = "";
		
		// menu principal
		// Runtime.getRuntime().exec("cls");
		System.out.println("MENU\nEscolha uma das opcoes: \n"
				+ "1 - Cadastrar produto;\n"
				+ "2 - Remover produto;\n"
				+ "3 - Listar Produtos;\n"
				+ "4 - Chat Online;\n"
				+ "0 - SAIR.");
		
		opcao = Integer.parseInt(userInput.readLine());
		
		// transformando opcao em str
		str = "" + opcao;
			
		// mandando info para o servidor
		out.println(str);
					
					
		return opcao;
	}
	
	// cadastrar um produto
	public void cadastrarProduto() throws IOException {
		System.out.println("Informe o nome do produto: ");
		String nomeProd = userInput.readLine();
		System.out.println("Informe a quantidade disponivel em estoque: ");
		int qntdProd = Integer.parseInt(userInput.readLine());
		System.out.println("Informe o codigo unico do produto: ");
		int codProd = Integer.parseInt(userInput.readLine());
		out.println(nomeProd + ":" + qntdProd + ":" + codProd);
	}
	
	// remover um produto
	public void removerProduto() throws ClassNotFoundException, IOException {
		Usuario usuario = null;
		boolean existe = false;
		
		usuario = (Usuario)inObject.readObject();
		if(usuario.getProdutos().isEmpty()) {
			System.out.println("Nenhum produto cadastrado!!");
		}else {
			System.out.println("----------------------------------------------");
			System.out.println("Codigo\t\tNome\t\tQuantidade\n");
			for (Produto produto : usuario.getProdutos()) {
				System.out.println(produto.getCod() + "\t\t" + produto.getNome() + "\t\t" + produto.getQntd());
			}
			System.out.println("----------------------------------------------");
		}
		System.out.println("Informe o codigo do produto que deseja excluir: ");
		int codExclu = Integer.parseInt(userInput.readLine());
		
		for (Produto produto : usuario.getProdutos()) {
			if(codExclu == produto.getCod()) {
				out.println(codExclu);
				existe = true;
			}
		}
		if(!existe) {
			System.out.println("Produto NÃO encontrado!!");
			out.println(0);
		}
	}
	
	// listar produto
	public void listarProdutos() throws ClassNotFoundException, IOException {
		Usuario usuario = null;
		
		usuario = (Usuario)inObject.readObject();
		if(usuario.getProdutos().isEmpty()) {
			System.out.println("Nenhum produto cadastrado!!");
		}else {
			System.out.println("----------------------------------------------");
			System.out.println("Codigo\t\tNome\t\tQuantidade\n");
			for (Produto produto : usuario.getProdutos()) {
				System.out.println(produto.getCod() + "\t\t" + produto.getNome() + "\t\t" + produto.getQntd());
			}
			System.out.println("----------------------------------------------");
		}
	}
	
	// comecar chat
	public void startChat() throws Exception{
		
		// iniciando o socket
		Socket socChat = new Socket ("localhost", 9800);
		
		// receber dados
		inChat = new BufferedReader(new InputStreamReader(socChat.getInputStream()));
		
		// mandar dado com autoflush
		outChat = new PrintWriter(socChat.getOutputStream(), true);
		
		String resposta = "";
		String serverMsg = "";
		
		// logica de envio e recepcao de msgs
		System.out.println("Você pode sair do chat apenas digitando 'sair'!");
		outChat.println(userName);
		System.out.println(inChat.readLine());
		do{
			
			resposta = userInput.readLine();
			outChat.println(resposta);

			serverMsg = inChat.readLine();
			System.out.println("Loja: " + serverMsg);
			
		}while(!resposta.equalsIgnoreCase("sair"));
	}
}


package br.ucb.projetofinal.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Usuario implements Serializable{
	
	// atributo Serializable
	private static final long serialVersionUID = 1L;
	
	// atributos
	private String nome;
	private int  id;
	private ArrayList<Produto> produtos;
	
	// construtores
	public Usuario (String nome) {
		setNome(nome);
		setId();
		produtos = new ArrayList<Produto>();
	}
	// getters & setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if(nome.isEmpty()) {
			throw new IllegalArgumentException("Nome precisa ser preenchido");
		}else {
			this.nome = nome;
		}
	}
	public long getId() {
		return id;
	}
	private void setId() {
		this.id = gerarIdAleatorio();
	}
	public ArrayList<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(Produto produto) {
		this.produtos.add(produto);
	}
	
	// metodos
	private static int gerarIdAleatorio() {
		Random random = new Random();
		int numero = random.nextInt(100);
		return numero;
	}
}

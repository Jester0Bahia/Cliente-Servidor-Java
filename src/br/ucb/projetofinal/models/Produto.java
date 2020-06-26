package br.ucb.projetofinal.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Produto implements Serializable{
	
	// atributo Serializable
	private static final long serialVersionUID = 1L;
	
	// atributos
	private String nome;
	private int qntd;
	private int cod;
	
	// construtores
	public Produto (String nome, int qntd, int cod) {
		setNome(nome);
		setQntd(qntd);
		setCod(cod);
	}

	
	// getters & setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQntd() {
		return qntd;
	}
	public void setQntd(int qntd) {
		this.qntd = qntd;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}	
	
	// metodos
	
}

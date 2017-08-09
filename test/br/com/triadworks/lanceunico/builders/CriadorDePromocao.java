package br.com.triadworks.lanceunico.builders;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;

public class CriadorDePromocao {
	
	private Promocao promocao;
	
	public CriadorDePromocao para(String nome){
		this.promocao = new Promocao(nome);
		return this;
	}
	
	public CriadorDePromocao comLance(Cliente cliente, double valor){
		this.promocao.registra(new Lance(cliente, valor));
		return this;
	}
	
	public Promocao criar(){
		return this.promocao;
	}

}

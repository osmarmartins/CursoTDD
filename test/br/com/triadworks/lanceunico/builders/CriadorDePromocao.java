package br.com.triadworks.lanceunico.builders;

import java.util.Date;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;

public class CriadorDePromocao {
	
	private Promocao promocao;
	
	public CriadorDePromocao para(String nome){
		this.promocao = new Promocao(nome);
		this.promocao.setValorMaximo(60000000.0);
		return this;
	}
	
	public CriadorDePromocao comLance(Cliente cliente, double valor){
		this.promocao.registra(new Lance(cliente, valor));
		return this;
	}
	
	public Promocao criar(){
		return this.promocao;
	}

	public CriadorDePromocao naData(Date data){
		promocao.setData(data);
		return this;
	}

	public CriadorDePromocao comValorMaximo(double valorMaximo) {
		promocao.setValorMaximo(valorMaximo);
		return this;
	}

	public CriadorDePromocao comStatus(Status status) {
		this.promocao.setStatus(status);
		return this;
	}
}

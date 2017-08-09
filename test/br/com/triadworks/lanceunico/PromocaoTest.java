package br.com.triadworks.lanceunico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.builders.CriadorDePromocao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;

public class PromocaoTest {

	private Cliente rafael;
	private Cliente rommel;
	
	@Before
	public void setUp(){
		rafael = new Cliente("Rafael");
		rommel = new Cliente("Rommel");
	}
	
	@Test
	public void deveRegistrarUmLance(){
		Promocao promocao = new CriadorDePromocao()
		.para("XBox")
		.comLance(rafael, 20.20)
		.criar();

		assertEquals(1, promocao.getLances().size());
		assertEquals(20.20, promocao.getLances().get(0).getValor(), 0.0001);
		
	}
	
	@Test
	public void deveRegistrarVariosLances(){
		Promocao promocao = new CriadorDePromocao()
		.para("XBox")
		.comLance(rafael, 20.20)
		.comLance(rommel, 30.10)
		.criar();

		assertEquals(2, promocao.getLances().size());
		assertEquals(20.20, promocao.getLances().get(0).getValor(), 0.0001);
		assertEquals(30.10, promocao.getLances().get(1).getValor(), 0.0001);
		
	}
	
	@Test
	public void naoDeveRegistrarDoisLancesSeguidosDoMesmoCliente(){
		
		Promocao promocao = new CriadorDePromocao()
								.para("iPad Mini")
								.comLance(rafael, 1000.0)
								.comLance(rafael, 1200.0)
								.criar();
		
		List<Lance> lances = promocao.getLances();
		
		assertEquals(1, lances.size());
		assertEquals(1000.0, lances.get(0).getValor(), 0.0001);
		
	}
	
	@Test
	public void naoDeveRegistrarMaisDeCincoLancesDoMesmoCliente(){
		Promocao promocao = new CriadorDePromocao()
									.para("iPad Mini")
									.comLance(rafael, 1000.0)
									.comLance(rommel, 1200.0)
									.comLance(rafael, 1001.0)
									.comLance(rommel, 1201.0)
									.comLance(rafael, 1002.0)
									.comLance(rommel, 1202.0)
									.comLance(rafael, 1003.0)
									.comLance(rommel, 1203.0)
									.comLance(rafael, 1004.0)
									.comLance(rommel, 1204.0)
									.comLance(rafael, 1005.0)
									.criar();
		
		List<Lance> lances = promocao.getLances();
		
		assertEquals(10, lances.size());
		Lance ultimo = lances.get(lances.size()-1);
		assertEquals(1204.0, ultimo.getValor(), 0.0001);
		
	}
	
}

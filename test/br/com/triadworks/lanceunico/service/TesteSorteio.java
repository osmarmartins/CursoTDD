package br.com.triadworks.lanceunico.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.builders.CriadorDePromocao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Promocao;

public class TesteSorteio {

	private Sorteio sorteio;
	private Cliente rafael;
	private Cliente rommel;
	private Cliente handerson;

	@Before
	public void setUp() {
		this.rafael = new Cliente("Rafael");
		this.rommel = new Cliente("Rommel");
		this.handerson = new Cliente("Handerson");

		this.sorteio = new Sorteio();

		System.out.println("@Before");
	}

	@After
	public void tearDown() {
		System.out.println("@After");
	}

	@Test
	public void deveSortearLancesEmOrdemCrescente() {

		System.out.println("-> Test1");

		Promocao promocao = new CriadorDePromocao()
									.para("XBox")
									.comLance(rafael, 250.0)
									.comLance(rommel, 300.0)
									.comLance(handerson, 400.0)
									.criar();

		sorteio.sorteia(promocao);

		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;

		assertEquals(maiorEsperado, sorteio.getMaiorDeTodos(), 0.0001);
		assertEquals(menorEsperado, sorteio.getMenorDeTodos(), 0.0001);

	}

	@Test
	public void deveSortearLAncesEmOrdemDecrescente() {

		System.out.println("-> Test2");

		Promocao promocao = new CriadorDePromocao()
									.para("XBox")
									.comLance(rafael, 400.0)
									.comLance(rommel, 300.0)
									.comLance(handerson, 250.0)
									.criar();

		sorteio.sorteia(promocao);

		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;

		assertEquals(maiorEsperado, sorteio.getMaiorDeTodos(), 0.0001);
		assertEquals(menorEsperado, sorteio.getMenorDeTodos(), 0.0001);

	}

	@Test
	public void deveSortearPromocaoComUmLance() {

		System.out.println("-> Test3");

		Promocao promocao = new CriadorDePromocao()
								.para("XBox")
								.comLance(handerson, 600.0)
								.criar();

		sorteio.sorteia(promocao);

		assertEquals(600.0, sorteio.getMaiorDeTodos(), 0.0001);
		assertEquals(600.0, sorteio.getMenorDeTodos(), 0.0001);

	}

	@Test
	public void deveSortearLancesComOrdemAleatoria() {

		System.out.println("-> Test4");

		Promocao promocao = new CriadorDePromocao()
								.para("XBox")
								.comLance(rafael, 1050.0)
								.comLance(rommel, 2990.99)
								.comLance(handerson, 24.70)
								.comLance(rafael, 477.0)
								.comLance(rommel, 1.25)
								.criar();

		sorteio.sorteia(promocao);

		double maiorEsperado = 2990.99;
		double menorEsperado = 1.25;

		assertEquals(maiorEsperado, sorteio.getMaiorDeTodos(), 0.0001);
		assertEquals(menorEsperado, sorteio.getMenorDeTodos(), 0.0001);

	}

	@Test
	public void deveSortearOsTresMenoresLances() {

		System.out.println("-> Test5");

		Promocao promocao = new CriadorDePromocao()
								.para("XBox")
								.comLance(rafael, 20.0)
								.comLance(rommel, 100.0)
								.comLance(handerson, 240.70)
								.comLance(rafael, 477.0)
								.comLance(rommel, 1.25)
								.criar();

		sorteio.sorteia(promocao);

		assertEquals(3, sorteio.getMenores().size());
		assertEquals(1.25, sorteio.getMenores().get(0).getValor(), 0.0001);
		assertEquals(20.0, sorteio.getMenores().get(1).getValor(), 0.0001);
		assertEquals(100.0, sorteio.getMenores().get(2).getValor(), 0.0001);

	}

	@Test
	public void deveSortearOsMenoresLancesComPromocaoMenorQueTresLances() {

		System.out.println("-> Test6");

		Promocao promocao = new CriadorDePromocao()
								.para("XBox")
								.comLance(rafael, 300.0)
								.comLance(rommel, 1.25)
								.criar();
						
		sorteio.sorteia(promocao);

		assertEquals(2, sorteio.getMenores().size());
		assertEquals(1.25, sorteio.getMenores().get(0).getValor(), 0.0001);
		assertEquals(300.0, sorteio.getMenores().get(1).getValor(), 0.0001);

	}

	@Test
	public void naoDeveSortearPromocaoSemLances() {

		System.out.println("-> Test7");

		Promocao promocao = new Promocao("Xbox");
		sorteio.sorteia(promocao);

		assertEquals(0, sorteio.getMenores().size());

	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveRegistrarLanceMaiorQueOPermitido(){

		Promocao promocao = new CriadorDePromocao()
								.para("XBox")
								.comValorMaximo(200.0)
								.comLance(rafael, 300.0)
								.criar();

	}

}

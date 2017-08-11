package br.com.triadworks.lanceunico.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import static org.mockito.Mockito.*;
import br.com.triadworks.lanceunico.builders.CriadorDePromocao;
import br.com.triadworks.lanceunico.dao.PromocaoDao;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.util.DateUtils;

public class EncerradorDePromocoesTest {
	
	@Test
	public void deveEncerrarPromocoesForaDaVigencia(){
		
		Date antiga = DateUtils.novaData("01/05/2017");
		
		Promocao ps3 = new CriadorDePromocao()
						.para("Playstation 3")
						.naData(antiga)
						.criar();

		Promocao tv = new CriadorDePromocao()
		.para("TV Led 32' ")
		.naData(antiga)
		.criar();
		
		List<Promocao> promocoes = Arrays.asList(ps3, tv);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();
		
		assertTrue("Promoção PS3 encerrada", ps3.isEncerrada());
		assertTrue("Promoção TV encerrada", tv.isEncerrada());
	}

	@Test
	public void naoDeveEncerrarPromocoesAindaVigentes(){
		
		Date ontem = DateUtils.novaData("08/08/2017");
		Date hoje = DateUtils.novaData("09/08/2017");
		Date antiga = DateUtils.novaData("01/05/2017");
		
		Promocao ps3 = new CriadorDePromocao()
						.para("Playstation 3")
						.naData(ontem)
						.criar();

		Promocao tv = new CriadorDePromocao()
						.para("TV Led 32' ")
						.naData(hoje)
						.criar();
		
		Promocao xbox = new CriadorDePromocao()
						.para("xBox")
						.naData(antiga)
						.criar();
		
		List<Promocao> promocoes = Arrays.asList(ps3, tv, xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		int qtd = encerrador.encerra();
		
		assertTrue("Promoção PS3 não encerrada", ps3.isAberta());
		assertTrue("Promoção TV não encerrada", tv.isAberta());
		assertTrue("Promoção xbox encerrada", xbox.isEncerrada());
		assertEquals(1, qtd);
	
	}
	
	@Test
	public void deveAtualizarPromocoesEncerradas(){
		Date antiga = DateUtils.novaData("01/05/2017");
		
		Promocao xbox = new CriadorDePromocao()
						.para("xBox")
						.naData(antiga)
						.criar();
		
		List<Promocao> promocoes = Arrays.asList(xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();
		
		verify(daoFalso, times(1)).atualiza(xbox);
		
	}
	
	@Test
	public void deveGarantirQueContinuaProcessamentoAposErro(){
		
		Date antiga = DateUtils.novaData("01/05/2017");
		
		Promocao tv = new CriadorDePromocao()
						.para("TV Led 32' ")
						.naData(antiga)
						.criar();
		
		Promocao xbox = new CriadorDePromocao()
						.para("xBox")
						.naData(antiga)
						.criar();
		
		List<Promocao> promocoes = Arrays.asList(tv, xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		doThrow(new RuntimeException()).when(daoFalso).atualiza(tv);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		int encerradas = encerrador.encerra();
		
		verify(daoFalso).atualiza(xbox);
		assertEquals("Encerradas", 1, encerradas);
	
	}
	
	@Test
	public void deveGarantirQueContinuaProcessamentoAposErroDeTodasAsPromocoes(){
		
		Date antiga = DateUtils.novaData("01/05/2017");
		
		Promocao tv = new CriadorDePromocao()
						.para("TV Led 32' ")
						.naData(antiga)
						.criar();
		
		Promocao xbox = new CriadorDePromocao()
						.para("xBox")
						.naData(antiga)
						.criar();
		
		List<Promocao> promocoes = Arrays.asList(tv, xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		doThrow(new RuntimeException()).when(daoFalso).atualiza(tv);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		int encerradas = encerrador.encerra();
		
		verify(daoFalso).atualiza(xbox);
		assertEquals("Encerradas", 1, encerradas);
	
	}
	
	
}

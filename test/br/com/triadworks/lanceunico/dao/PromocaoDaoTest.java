package br.com.triadworks.lanceunico.dao;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.builders.CriadorDePromocao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;
import br.com.triadworks.lanceunico.util.JPAUtil;
import static org.junit.Assert.*;

public class PromocaoDaoTest {

	private EntityManager entityManager;
	
	@Before
	public void setUp(){
		entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
	}
	
	@After
	public void tearDown(){
		entityManager.getTransaction().rollback();
		entityManager.close();
	}
	
	@Test
	public void deveContarPromocoesEncerradas(){
		
		Promocao aberta = new CriadorDePromocao()
							.para("Notebook Dell")
							.comStatus(Status.ABERTA)
							.criar();
		
		Promocao encerrada = new CriadorDePromocao()
							.para("TV Led 32' ")
							.comStatus(Status.ENCERRADA)
							.criar();
		
		entityManager.persist(aberta);
		entityManager.persist(encerrada);
		
		PromocaoDao dao = new PromocaoDao(entityManager);
		Long total = dao.totalDeEncerradas();
		
		Long totalEsperado = 1L;
		assertEquals(totalEsperado, total);
		
	}

	
	@Test
	public void deveContarZeroPromocoesEncerradas(){
		
		Promocao aberta = new CriadorDePromocao()
							.para("Notebook Dell")
							.comStatus(Status.ABERTA)
							.criar();
		
		Promocao encerrada = new CriadorDePromocao()
							.para("TV Led 32' ")
							.comStatus(Status.ABERTA)
							.criar();
		
		entityManager.persist(aberta);
		entityManager.persist(encerrada);
		
		PromocaoDao dao = new PromocaoDao(entityManager);
		Long total = dao.totalDeEncerradas();
		
		Long totalEsperado = 0L;
		assertEquals(totalEsperado, total);
		
	}
	
	@Test
	public void deveRemoverUmaPromocao(){
		
		Promocao promocao = new CriadorDePromocao()
							.para("Notebook Dell")
							.criar();
		
		entityManager.persist(promocao);
		
		PromocaoDao dao = new PromocaoDao(entityManager);
		dao.remove(promocao);
		
		Promocao promocaoDoBanco = dao.carrega(promocao.getId());
		assertNull(promocaoDoBanco);
		
	}
	
	@Test
	public void deveRegistrarNovoLanceNaPromocao(){
		Cliente rafael = new Cliente("Rafael");
		
		Promocao promocao = new CriadorDePromocao()
								.para("Apple TV")
								.criar();
		
		entityManager.persist(rafael);
		entityManager.persist(promocao);
		
		Integer id = promocao.getId();
		Lance lance = new Lance(rafael, 100.0);
		
		PromocaoDao dao = new PromocaoDao(entityManager);
		dao.registraLance(id, lance);
		
		Promocao promocaoDoBanco = dao.carrega(id);
		assertEquals(1, promocaoDoBanco.getLances().size());
	}
	
}

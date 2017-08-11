package br.com.triadworks.lanceunico.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.util.JPAUtil;

public class ClienteDaoTest {
	
	private EntityManager em;
	
	@Before
	public void setUp(){
		em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
	}
	
	@After
	public void tearDown(){
		em.getTransaction().rollback();
		em.close();
	}

	@Test
	public void deveEncontrarClientePorEmail() {

		Cliente principe = new Cliente("Principe do Oceano", "principe@oceano.com");
		em.persist(principe);
		
		ClienteDao dao = new ClienteDao(em);
		Cliente clienteDoBanco = dao.buscaPorEmail("principe@oceano.com");
		
		assertEquals(principe.getNome(), clienteDoBanco.getNome());
		assertEquals(principe.getEmail(), clienteDoBanco.getEmail());
		

	}

	@Test
	public void naoDeveEncontrarClientePorEmail() {

		ClienteDao dao = new ClienteDao(em);
		Cliente clienteDoBanco = dao.buscaPorEmail("principe@oceano.com");
		
		assertNull(clienteDoBanco);

	}
	
}

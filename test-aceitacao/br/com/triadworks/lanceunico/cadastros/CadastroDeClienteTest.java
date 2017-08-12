package br.com.triadworks.lanceunico.cadastros;

import org.junit.Test;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CadastroDeClienteTest {

	@Test
	public void deveAdicionarNovoCliente(){
		System.setProperty("webdriver.gecko.driver", "/home/tdd07/geckodriver");
		
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/lance-unico/pages/clientes/novo.xhtml");
		
		WebElement nome = driver.findElement(By.name("nome"));
		nome.sendKeys("Bruce Wayne");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("bruce@wayne.com");
		
		WebElement botao = driver.findElement(By.id("btn-salvar"));
		botao.click();
		
		assertTrue(driver.getPageSource().contains("Bruce Wayne"));
		assertTrue(driver.getPageSource().contains("bruce@wayne.com"));
		
		driver.close();
				
		
			
	}
}

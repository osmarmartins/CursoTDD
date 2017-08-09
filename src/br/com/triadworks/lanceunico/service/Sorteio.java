package br.com.triadworks.lanceunico.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;

public class Sorteio {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> menores;

	public void sorteia(Promocao promocao) {

		encontraMenorEMaiorLanceNa(promocao);

		encontraTresMenoresLances(promocao);
	}

	private void encontraTresMenoresLances(Promocao promocao) {
		menores = new ArrayList<Lance>(promocao.getLances());
		Collections.sort(menores, new Comparator<Lance>() {

			@Override
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor())
					return -1;
				if (o1.getValor() > o2.getValor())
					return 1;
				return 0;
			}
		});

		int tamanho = menores.size() > 3 ? 3 : menores.size();
		menores = menores.subList(0, tamanho);
	}

	private void encontraMenorEMaiorLanceNa(Promocao promocao) {
		for (Lance lance : promocao.getLances()) {

			if (lance.getValor() > maiorDeTodos) {
				maiorDeTodos = lance.getValor();
			}

			if (lance.getValor() < menorDeTodos) {
				menorDeTodos = lance.getValor();
			}
		}
	}

	public List<Lance> getMenores() {
		return menores;
	}

	public double getMaiorDeTodos() {
		return maiorDeTodos;
	}

	public double getMenorDeTodos() {
		return menorDeTodos;
	}

}

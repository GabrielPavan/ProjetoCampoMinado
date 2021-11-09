package GabrielPavan.com.GitHub.CM;

import GabrielPavan.com.GitHub.CM.modelo.Tabuleiro;
import GabrielPavan.com.GitHub.CM.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
	}	
}

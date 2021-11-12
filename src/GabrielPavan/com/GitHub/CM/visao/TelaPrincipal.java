package GabrielPavan.com.GitHub.CM.visao;

import javax.swing.JFrame;

import GabrielPavan.com.GitHub.CM.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 60);
		add(new PainelTabuleiro(tabuleiro));
		
		setTitle("Campo Minado");
		setSize(500, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}

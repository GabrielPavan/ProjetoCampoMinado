package GabrielPavan.com.GitHub.CM.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<Campo>();

	public Tabuleiro(int inhas, int colunas, int minas) {
		this.linhas = inhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		gerarMinas();
		assosiarVizinhos();
	}
	public void abrir(int linha,int coluna) {
		try {
			campos.parallelStream()
				.filter(c -> c.getLinha() ==  linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
		} catch (Exception e) {
			//FIXME ajustar method abrir!
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
		
	}
	public void alternarMarcacao(int linha,int coluna) {
		campos.parallelStream()
			.filter(c -> c.getLinha() ==  linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.alternarMarcacao());
	}
	private void gerarCampos() {
		for (int Linha = 0; Linha < linhas; Linha++) {
			for (int Colunas = 0; Colunas < colunas; Colunas++) {
				campos.add(new Campo(Linha, Colunas));
			}
		}
	}
	private void gerarMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {	
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	private void assosiarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		gerarMinas();
	}
}

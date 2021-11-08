package GabrielPavan.com.GitHub.CM.modelo;

import java.util.ArrayList;
import java.util.List;

import GabrielPavan.com.GitHub.CM.excecao.ExplosaoException;

public class Campo {
	
	private boolean Minado = false;
	private boolean Aberto = false;
	private boolean Marcado = false;
	
	private List<Campo> Vizinhos = new ArrayList<>();
	private final int Linha;
	private final int Coluna;
	
	
	Campo (int Linha, int Coluna){
		this.Linha = Linha; this.Coluna = Coluna;
	}
	boolean adicionarVizinho(Campo vizinho) {
		boolean LinhaDiferente = vizinho.Linha != this.Linha;
		boolean ColunaDiferente = vizinho.Coluna != this.Coluna;
		boolean Diagonal = LinhaDiferente && ColunaDiferente;
		
		int deltaLinha = Math.abs(this.Linha - vizinho.Linha);
		int deltaColuna= Math.abs(this.Coluna - vizinho.Coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !Diagonal) {
			Vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && Diagonal) {
			Vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	void alternarMarcacao() {
		if(!Aberto) {
			Marcado =  !Marcado;
		} 
	}
	boolean abrir() {
		if (!Aberto && !Marcado) {
			Aberto = true;
			if (Minado) {
				throw new ExplosaoException();
			}
			if (vizinhacaSegura()) {
				Vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}
	}
	boolean vizinhacaSegura() {
		return Vizinhos.stream().noneMatch(v -> v.Minado);
	}
	
	void minar() {
			Minado = true;
	}
	public boolean isMarcado() {
		return Marcado;
	}
	public boolean isAberto() {
		return Aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}
}

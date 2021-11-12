package GabrielPavan.com.GitHub.CM.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	
	private boolean Minado = false;
	private boolean Aberto = false;
	private boolean Marcado = false;
	
	private List<Campo> Vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	private final int Linha;
	private final int Coluna;
	
	
	Campo (int Linha, int Coluna){
		this.Linha = Linha; this.Coluna = Coluna;
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
	public boolean isMinado() {
		return Minado;
	}
	public int getLinha() {
		return Linha;
	}
	public int getColuna() {
		return Coluna;
	}
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
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
	public void alternarMarcacao() {
		if(!Aberto) {
			Marcado =  !Marcado;
			if(Marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		} 
	}
	public boolean abrir() {
		if (!Aberto && !Marcado) {
			if (Minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			setAberto(true);
			if (vizinhacaSegura()) {
				Vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}
	}
	public boolean vizinhacaSegura() {
		return Vizinhos.stream().noneMatch(v -> v.Minado);
	}
	
	void minar() {
			Minado = true;
	}
	void setAberto(boolean aberto) {
		Aberto = aberto;
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !Minado && Aberto;
		boolean protegido = Minado && Marcado;
		return desvendado || protegido;
	}
	public int minasNaVizinhanca() {
		return (int) Vizinhos.stream().filter(v -> v.Minado).count();
	}
	void reiniciar() {
		Aberto = false;
		Minado = false;
		Marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream().forEach(o ->o.eventoOcorreu(this, evento) );
	}
	//public String toString() {   //Versao 1.0 Usando console!!
	//	if(Marcado) {
	//		return "X";
	//	} else if(Aberto && Minado) {
	//		return "*";
	//	} else if(Aberto && minasNaVizinhanca() > 0) {
	//		return Long.toString(minasNaVizinhanca());
	//	} else if(Aberto) {
	//		return " ";
	//	} else {
	//		return "?";
	//	}
}


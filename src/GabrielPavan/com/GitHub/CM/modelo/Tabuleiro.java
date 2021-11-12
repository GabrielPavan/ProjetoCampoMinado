package GabrielPavan.com.GitHub.CM.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {
	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<Campo>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	public Tabuleiro(int inhas, int colunas, int minas) {
		this.linhas = inhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		gerarMinas();
		assosiarVizinhos();
	}
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	public int getLinhas() {
		return linhas;
	}
	public int getColunas() {
		return colunas;
	}
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	private void notificarObservadores(boolean resultado) {
		observadores.stream().forEach(o ->o.accept(new ResultadoEvento(resultado)));
	}
	public void abrir(int linha,int coluna) {
			campos.parallelStream()
				.filter(c -> c.getLinha() ==  linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
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
				Campo campo = new Campo(Linha, Colunas);
				campo.registrarObservador(this);
				campos.add(campo);
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
	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODIR) {
			mostraMinas();
			notificarObservadores(false);
		} else if (objetivoAlcancado()) {
			notificarObservadores(true);
		}	
	}
	private void mostraMinas() {
		campos.stream()
			.filter(c -> c.isMinado())
			.filter(c -> !c.isMarcado())
			.forEach(c -> c.setAberto(true));
	}

}

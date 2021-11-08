package GabrielPavan.com.GitHub.CM.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CampoTeste {
	private Campo campo;
	
	@BeforeEach
	void InicarCampo() {
		campo = new Campo(3, 3);
	}
	@Test
	void testeVizinhoValido() {
		Campo vizinho = new Campo(4, 4);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	@Test
	void testeVizinhoInvalido() {
		Campo vizinho = new Campo(1, 1);
		boolean result = campo.adicionarVizinho(vizinho);
		assertFalse(result);
	}
}

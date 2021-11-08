package GabrielPavan.com.GitHub.CM;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Teste {

	@Test
	void TestarSeIgualADois() {
		int a = 1 + 1;
		assertEquals(2,a);
	}
	@Test
	void TestarSeIgualATres() {
		int x = 2 + 10 - 7;
		assertEquals(3, x);
	}
}

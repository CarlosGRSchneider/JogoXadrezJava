package testesXadrez.testesTabuleiroXadrez;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import tabuleiro.TabuleiroException;
import xadrez.Cor;
import xadrez.pecasXadrez.Cavalo;
import xadrez.pecasXadrez.Torre;

class TesteTabuleiro {

	private static Tabuleiro tabuleiro;

	@BeforeAll
	public static void setUpBeforeClass() {
		tabuleiro = new Tabuleiro(8, 8);
		Cavalo pecaTeste = new Cavalo(tabuleiro, Cor.BRANCO);
		Posicao posicao = new Posicao(4, 4);
		tabuleiro.inserePeca(pecaTeste, posicao);
	}

	@Test
	void testaTabuleiroInvalido() {
		assertThrows(TabuleiroException.class, () -> {new Tabuleiro(-2, 3);});
	}

	@Test
	void testainserePecaPosicaoOcupada() {
		Torre torre = new Torre(tabuleiro, Cor.PRETO);
		Posicao posicao = new Posicao(4, 4);
		assertThrows(TabuleiroException.class, () -> {tabuleiro.inserePeca(torre, posicao);});	}

	@Test
	void testainserePecaPosicaoLivre() {
		Torre torre = new Torre(tabuleiro, Cor.PRETO);
		Posicao posicao = new Posicao(5, 5);
		tabuleiro.inserePeca(torre, posicao);
		assertTrue(tabuleiro.existePeca(posicao));
	}

	@Test
	void testaExistePeca() {
		assertTrue(tabuleiro.existePeca(new Posicao(4, 4)));

	}

	@Test
	void testaExistePosicaoForaDoTabuleiro() {
		assertFalse(tabuleiro.existePosicao(new Posicao(10, 6)));
	}
	
	@Test
	void testaExistePosicaoNoTabuleiro() {
		assertTrue(tabuleiro.existePosicao(new Posicao(1, 6)));
	}
	
	@Test
	void testaRemovePeca() {
		Torre torre = new Torre(tabuleiro, Cor.PRETO);
		Posicao posicao = new Posicao(7, 7);
		tabuleiro.inserePeca(torre, posicao);
		assertTrue(tabuleiro.removePeca(posicao) instanceof Torre);
	}
	@Test
	void testaRemovePecaForaDoTabuleiro() {
		Posicao posicao = new Posicao(10, 10);
		assertThrows(TabuleiroException.class, () -> {tabuleiro.removePeca(posicao);});
	}
	
	@Test
	void testaPosicaoPeca() {
		Posicao posicao = new Posicao(4, 4);
		assertTrue(tabuleiro.posicaoPeca(posicao) instanceof Peca);
	}
	
	@Test
	void testaPosicaoPecaPosicaoVazia() {
		Posicao posicao = new Posicao(5, 4);
		assertFalse(tabuleiro.posicaoPeca(posicao) instanceof Peca);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		tabuleiro = null;
	}
}

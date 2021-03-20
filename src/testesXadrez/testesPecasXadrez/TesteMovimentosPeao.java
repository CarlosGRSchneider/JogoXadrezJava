package testesXadrez.testesPecasXadrez;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.pecasXadrez.Peao;

class TesteMovimentosPeao {

	private static PartidaXadrez partida;
	private static Tabuleiro tabuleiro;
	private static Peao peao;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		partida = new PartidaXadrez();
		tabuleiro = new Tabuleiro(8, 8);
		
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		partida = null;
		tabuleiro = null;
	}
	
	@BeforeEach
	void setUp() throws Exception {
		Posicao posicao = new Posicao(5, 4);
		peao = new Peao(tabuleiro, Cor.BRANCO, partida);
		tabuleiro.inserePeca(peao, posicao);
	
	}

	@AfterEach
	void tearDown() throws Exception {
		Posicao posicao = new Posicao(5, 4);
		peao = null;
		tabuleiro.removePeca(posicao);
		
	}
	
	@Test
	void testaAumentoContadorDeMovimentos() {
		peao.aumentaContagemMovimentos();
		peao.aumentaContagemMovimentos();
		assertTrue(peao.getContagemMovimentos() > 1);
	}

	@Test
	void testaDiminuicaoContadorDeMovimentos() {
		peao.diminuiContagemMovimentos();
		assertTrue(peao.getContagemMovimentos() < 0);
	}
	
	@Test
	void testaMovimentoPossivelPeao() {
		Posicao posicao = new Posicao(4, 4);
		assertEquals( true, peao.movimentoPossivel(posicao));
	}
	
	@Test
	void testaPosicaoNoTabuleiroDeXadrez() {
		PosicaoXadrez posicao = new PosicaoXadrez(3, 'e');
		assertEquals( posicao.toString(), peao.getPosicaoXadrez().toString());
	}
}

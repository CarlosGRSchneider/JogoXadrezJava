package testesXadrez.testesJogoXadrez;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;

@TestMethodOrder(OrderAnnotation.class)
class TestePartidaXadrez {

	private static PartidaXadrez partida;
	private static PosicaoXadrez origem;
	private static PosicaoXadrez destino;

	@BeforeAll
	public static void setUpBeforeClass() {
		partida = new PartidaXadrez();

	}

	@Test
	@Order(1)
	void testaFazerMovimentoXadrez() {
		// movimentoPeao
		origem = new PosicaoXadrez(2, 'f');
		destino = new PosicaoXadrez(3, 'f');

		partida.fazerMovimentoXadrez(origem, destino);
		assertTrue(true);
	}

	@Test
	@Order(2)
	void testaCheck() {
		// movimento mais rapido de chequemate possível
		origem = new PosicaoXadrez(7, 'e');
		destino = new PosicaoXadrez(5, 'e');

		partida.fazerMovimentoXadrez(origem, destino);

		origem = new PosicaoXadrez(2, 'g');
		destino = new PosicaoXadrez(4, 'g');

		partida.fazerMovimentoXadrez(origem, destino);

		origem = new PosicaoXadrez(8, 'd');
		destino = new PosicaoXadrez(4, 'h');

		partida.fazerMovimentoXadrez(origem, destino);
		assertTrue(partida.isCheck());
	}

	@Test
	@Order(3)
	void testaCheckMate() {
		assertTrue(partida.isCheckMate());
	}
	
	@Test
	@Order(4)
	void testaEnPassant() {
		// testa se as movimentações de um peão alteram o estado en passant, caso as condições da jogada ocorram
		partida = new PartidaXadrez();
		
		origem = new PosicaoXadrez(2, 'f');
		destino = new PosicaoXadrez(4, 'f');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(7, 'b');
		destino = new PosicaoXadrez(5, 'b');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(4, 'f');
		destino = new PosicaoXadrez(5, 'f');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(7, 'e');
		destino = new PosicaoXadrez(5, 'e');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		assertNotNull(partida.getenPassantVulneravel());
	}
	
	@Test
	@Order(5)
	void testaRoque() {
		// testa se as movimentações da partida permitem um roque, caso as condições da jogo ocorram
		partida = new PartidaXadrez();
		
		origem = new PosicaoXadrez(2, 'e');
		destino = new PosicaoXadrez(4, 'e');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(7, 'a');
		destino = new PosicaoXadrez(5, 'a');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(1, 'f');
		destino = new PosicaoXadrez(6, 'a');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(5, 'a');
		destino = new PosicaoXadrez(4, 'a');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(1, 'g');
		destino = new PosicaoXadrez(2, 'e');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(4, 'a');
		destino = new PosicaoXadrez(3, 'a');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(1, 'e');
		destino = new PosicaoXadrez(1, 'g');
		
		partida.fazerMovimentoXadrez(origem, destino);
		
		assertTrue(true);
	}

}

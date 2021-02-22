package testesXadrez.testesJogoXadrez;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;

class testeBateriaDeMovimentosPossiveis {

	private static PartidaXadrez partida;
	private static PosicaoXadrez origem;
	private static PosicaoXadrez destino;

	@BeforeAll
	public static void setUpBeforeClass() {
		partida = new PartidaXadrez();

		origem = new PosicaoXadrez(2, 'd');
		destino = new PosicaoXadrez(4, 'd');

		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(7, 'd');
		destino = new PosicaoXadrez(5, 'd');

		partida.fazerMovimentoXadrez(origem, destino);

		origem = new PosicaoXadrez(1, 'd');
		destino = new PosicaoXadrez(3, 'd');

		partida.fazerMovimentoXadrez(origem, destino);
		
		origem = new PosicaoXadrez(8, 'd');
		destino = new PosicaoXadrez(6, 'd');

		partida.fazerMovimentoXadrez(origem, destino);
		
	}

	
	@ParameterizedTest
	@MethodSource("casasDaRainha")
	void testaMovimentosPossiveisRainhaConsegueExecutar( List<Integer> lista) {
		origem = new PosicaoXadrez(3, 'd');

		boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
		assertEquals(true, movimentosPossiveis[(int)lista.get(0)][(int)lista.get(1)]);
	}
	
	@ParameterizedTest
	@MethodSource("locaisInvalidosParaRainha")
	void testaMovimentosPossiveisRainhaNaoConsegueExecutar( List<Integer> lista) {
		origem = new PosicaoXadrez(3, 'd');

		boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
		assertEquals(false, movimentosPossiveis[(int)lista.get(0)][(int)lista.get(1)]);
	}
	
	private static Stream<Arguments> casasDaRainha() {
		return Stream.of(
				Arguments.of(Arrays.asList(7, 3)),
				Arguments.of(Arrays.asList(6, 3)),
				Arguments.of(Arrays.asList(5, 2)),
				Arguments.of(Arrays.asList(5, 1)),
				Arguments.of(Arrays.asList(5, 0)),
				Arguments.of(Arrays.asList(5, 4)),
				Arguments.of(Arrays.asList(5, 5)),
				Arguments.of(Arrays.asList(5, 6)),
				Arguments.of(Arrays.asList(5, 7)),
				Arguments.of(Arrays.asList(4, 2)),
				Arguments.of(Arrays.asList(3, 1)),
				Arguments.of(Arrays.asList(2, 0)),
				Arguments.of(Arrays.asList(4, 4)),
				Arguments.of(Arrays.asList(3, 5)),
				Arguments.of(Arrays.asList(2, 6)),
				Arguments.of(Arrays.asList(1, 7))
				);
	}
	
	private static Stream<Arguments> locaisInvalidosParaRainha() {
		return Stream.of(
				Arguments.of(Arrays.asList(4, 3)),
				Arguments.of(Arrays.asList(5, 3)),
				Arguments.of(Arrays.asList(2, 2)),
				Arguments.of(Arrays.asList(6, 2)),
				Arguments.of(Arrays.asList(6, 4))
				);
	}

}

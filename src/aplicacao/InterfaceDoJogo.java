package aplicacao;

import java.util.Objects;

import xadrez.PecaXadrez;

public class InterfaceDoJogo {

	public static void desenhoDoTabuleiro(PecaXadrez[][] peca) {

		for (int i = 0; i < peca.length; i++) {
			System.out.print(8 - i + " ");

			for (int j = 0; j < peca.length; j++) {
				desenhoDaPeca(peca[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void desenhoDaPeca(PecaXadrez peca) {

		if (Objects.isNull(peca)) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}
}

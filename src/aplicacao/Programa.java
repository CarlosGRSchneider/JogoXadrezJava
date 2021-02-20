package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();

		while (true) {
			try {
				UI.clearScreen();
				UI.printMatch(partida, capturadas);
				InterfaceDoJogo.desenhoDoTabuleiro(partida.pecasDoJogo());
				System.out.println();
				System.out.print("Origem");
				PosicaoXadrez origem = UI.readChessPosition(sc);

				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.clearScreen();
				UI.printBoard(partida.pecasDoJogo(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino");
				PosicaoXadrez destino = UI.readChessPosition(sc);

				PecaXadrez pecaCapturada = partida.fazerMovimentoXadrez(origem, destino);

				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}

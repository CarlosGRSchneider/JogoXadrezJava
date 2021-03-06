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

		while (!partida.isCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.readChessPosition(sc);
//				InterfaceDoJogo.desenhoDoTabuleiro(partida.pecasDoJogo());

				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.clearScreen();
				UI.printBoard(partida.pecasDoJogo(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.readChessPosition(sc);

				PecaXadrez pecaCapturada = partida.fazerMovimentoXadrez(origem, destino);

				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}

				if (partida.getPromovido() != null) {
					System.out.print("Escolha uma pe�a para Promo��o(T/C/B/R): ");
					String tipo = sc.nextLine().toUpperCase();

					while (!tipo.equals("B") && !tipo.equals("T") && !tipo.equals("C") && !tipo.equals("R")) {
						System.out.print("Valor invalido! Escolha novamente(T/C/B/R): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partida.trocarPecaPromovida(tipo);
				}

			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(partida, capturadas);

	}

}

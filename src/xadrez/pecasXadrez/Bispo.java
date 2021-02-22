package xadrez.pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao pos = new Posicao(0, 0);

		// movimento para diagonal superior esquerda
		pos.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
			pos.setValores(pos.getLinha() - 1, pos.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// movimento para diagonal superior direita
		pos.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
			pos.setValores(pos.getLinha() - 1, pos.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// movimento para diagonal inferior esquerda
		pos.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
			pos.setValores(pos.getLinha() + 1, pos.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// movimento para diagonal inferior direita
		pos.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
			pos.setValores(pos.getLinha() + 1, pos.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		return matriz;
	}

	@Override
	public String toString() {
		return "B";
	}

}

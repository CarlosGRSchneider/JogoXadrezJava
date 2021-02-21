package xadrez.pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao pos = new Posicao(0, 0);

		pos.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		pos.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}
		return matriz;
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().posicaoPeca(posicao);

		return peca == null || peca.getCor() != getCor();
	}

	@Override
	public String toString() {
		return "C";
	}

}

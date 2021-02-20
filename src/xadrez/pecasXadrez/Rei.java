package xadrez.pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao pos = new Posicao(0, 0);

		// acima
		pos.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// abaixo
		pos.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// esquerda
		pos.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// direita
		pos.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// acima-esquerda
		pos.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// acima-direita
		pos.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// abaixo-esquerda
		pos.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}

		// abaixo-direita
		pos.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(pos) && podeMover(pos)) {
			matriz[pos.getLinha()][pos.getColuna()] = true;
		}
		return matriz;
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().posicaoPeca(posicao);

		return peca == null || peca.getCor() != getCor();
	}
}

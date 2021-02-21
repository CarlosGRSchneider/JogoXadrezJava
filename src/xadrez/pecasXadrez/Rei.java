package xadrez.pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
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

		// jogada especial roque
		if (getContagemMovimentos() == 0 && !partidaXadrez.isCheck()) {
			// roque pequeno
			Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testarTorreParaRoque(posicaoTorre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);

				if (getTabuleiro().posicaoPeca(p1) == null && getTabuleiro().posicaoPeca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}

			// roque grande
			Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testarTorreParaRoque(posicaoTorre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);

				if (getTabuleiro().posicaoPeca(p1) == null && getTabuleiro().posicaoPeca(p2) == null
						&& getTabuleiro().posicaoPeca(p3) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}

		return matriz;
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().posicaoPeca(posicao);

		return peca == null || peca.getCor() != getCor();
	}

	private boolean testarTorreParaRoque(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().posicaoPeca(posicao);
		return peca != null && (peca instanceof Torre) && peca.getCor() == getCor()
				&& peca.getContagemMovimentos() == 0;
	}
}

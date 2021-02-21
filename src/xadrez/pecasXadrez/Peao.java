package xadrez.pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao pos = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			
			// adiante 1 casa
			pos.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos)) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}
			
			//adiante 2 casas
			pos.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao pos2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());

			boolean acessoPosicao1 = getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos);
			boolean acessoPosicao2 = getTabuleiro().existePosicao(pos2) && !getTabuleiro().existePeca(pos2);

			if (acessoPosicao1 && acessoPosicao2 && getContagemMovimentos() == 0) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}

			// captura peça diagonal esquerda
			pos.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}
			
			// captura peça diagonal direita
			pos.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}
		} else {
			
			// adiante 1 casa
			pos.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos)) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}
			
			//adiante 2 casas
			pos.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao pos2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());

			boolean acessoPosicao1 = getTabuleiro().existePosicao(pos) && !getTabuleiro().existePeca(pos);
			boolean acessoPosicao2 = getTabuleiro().existePosicao(pos2) && !getTabuleiro().existePeca(pos2);

			if (acessoPosicao1 && acessoPosicao2 && getContagemMovimentos() == 0) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}

			// captura peça diagonal esquerda
			pos.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}
			
			// captura peça diagonal direita
			pos.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(pos) && existePecaOponente(pos)) {
				matriz[pos.getLinha()][pos.getColuna()] = true;
			}
		}

		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	

}

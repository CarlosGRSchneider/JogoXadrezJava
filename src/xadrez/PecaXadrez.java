package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	
	private Cor cor;
	private int contagemMovimentos;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimentos() {
		return contagemMovimentos;
	}
	
	public void aumentaContagemMovimentos() {
		contagemMovimentos ++;
	}
	
	public void diminuiContagemMovimentos() {
		contagemMovimentos --;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.montaPosicaoXadrez(posicao);
	}
	
	protected boolean existePecaOponente(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().posicaoPeca(posicao);
		
		return peca != null && peca.getCor() != cor;
	}

}

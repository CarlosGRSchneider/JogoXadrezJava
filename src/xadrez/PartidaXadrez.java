package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecasXadrez.Rei;
import xadrez.pecasXadrez.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassantVulnerable;
	private PecaXadrez promoted;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		arranjaPecasNoTabuleiro();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public PecaXadrez getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public PecaXadrez getPromoted() {
		return promoted;
	}

	public PecaXadrez[][] pecasDoJogo() {
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];

		for (int i = 0; i < tabuleiro.getLinha(); i++) {
			for (int j = 0; j < tabuleiro.getColuna(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.posicaoPeca(i, j);
			}
		}
		return matriz;
	}

	public PecaXadrez fazerMovimentoXadrez(PosicaoXadrez posicaoInicial, PosicaoXadrez posicaoDestino) {

		Posicao origem = posicaoInicial.montaPosicao();
		Posicao destino = posicaoDestino.montaPosicao();
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazMovimento(origem, destino);
		proximaJogada();
		return (PecaXadrez) pecaCapturada;
	}

	private void validaPosicaoOrigem(Posicao origem) {

		if (!tabuleiro.existePeca(origem)) {
			throw new XadrezException("Não existe uma peça na posição de origem");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.posicaoPeca(origem)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}
		if (!tabuleiro.posicaoPeca(origem).existeUmMovimentoPossivel()) {
			throw new XadrezException("Não existem movimentos possíveis para a peça escolhida");
		}
	}

	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.posicaoPeca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void proximaJogada() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Peca fazMovimento(Posicao origem, Posicao destino) {
		Peca peca = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		tabuleiro.inserePeca(peca, destino);
		return pecaCapturada;
	}

	private void colocaNovaPeca(int linha, char coluna, PecaXadrez pecaXadrez) {
		tabuleiro.inserePeca(pecaXadrez, new PosicaoXadrez(linha, coluna).montaPosicao());
		pecasNoTabuleiro.add(pecaXadrez);
	}

	private void arranjaPecasNoTabuleiro() {
		colocaNovaPeca(6, 'b', new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(4, 'e', new Rei(tabuleiro, Cor.PRETO));
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.montaPosicao();
		return tabuleiro.posicaoPeca(posicao).movimentosPossiveis();
	}
	
}

package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecasXadrez.Peao;
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

		if (testaCheck(jogadorAtual)) {
			desfazMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Você não pode se colocar em check");
		}

		check = (testaCheck(oponente(jogadorAtual))) ? true : false;

		if (testaCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}

		proximaJogada();
		return (PecaXadrez) pecaCapturada;
	}

	private void validaPosicaoOrigem(Posicao origem) {

		if (!tabuleiro.existePeca(origem)) {
			throw new XadrezException("Não existe uma peça na posição de origem");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.posicaoPeca(origem)).getCor()) {
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
		PecaXadrez peca = (PecaXadrez) tabuleiro.removePeca(origem);
		peca.aumentaContagemMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		tabuleiro.inserePeca(peca, destino);
		return pecaCapturada;
	}

	private void desfazMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez peca = (PecaXadrez) tabuleiro.removePeca(destino);
		peca.diminuiContagemMovimentos();
		tabuleiro.inserePeca(peca, origem);

		if (pecaCapturada != null) {
			tabuleiro.inserePeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> listaDoRei = pecasNoTabuleiro.stream().filter(peca -> ((PecaXadrez) peca).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca p : listaDoRei) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não há uma peça de rei no tabuleiro");
	}

	private boolean testaCheck(Cor cor) {
		Posicao posicaoDoREi = rei(cor).getPosicaoXadrez().montaPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream()
				.filter(peca -> ((PecaXadrez) peca).getCor() == oponente(cor)).collect(Collectors.toList());

		for (Peca p : pecasDoOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoDoREi.getLinha()][posicaoDoREi.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testaCheckMate(Cor cor) {
		if (!testaCheck(cor)) {
			return false;
		}

		List<Peca> lista = pecasNoTabuleiro.stream().filter(peca -> ((PecaXadrez) peca).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca p : lista) {

			boolean[][] matriz = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinha(); i++) {
				for (int j = 0; j < tabuleiro.getColuna(); j++) {

					if (matriz[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().montaPosicao();
						Posicao destino = new Posicao(i, j);

						Peca pecaCapturada = fazMovimento(origem, destino);
						boolean testaCheck = testaCheck(cor);
						desfazMovimento(origem, destino, pecaCapturada);

						if (!testaCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private void colocaNovaPeca(int linha, char coluna, PecaXadrez pecaXadrez) {
		tabuleiro.inserePeca(pecaXadrez, new PosicaoXadrez(linha, coluna).montaPosicao());
		pecasNoTabuleiro.add(pecaXadrez);
	}

	private void arranjaPecasNoTabuleiro() {
		colocaNovaPeca(1, 'a', new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'e', new Rei(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'h', new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'a', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'b', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'c', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'd', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'e', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'f', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'g', new Peao(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'h', new Peao(tabuleiro, Cor.BRANCO));

		colocaNovaPeca(8, 'a', new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'e', new Rei(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'h', new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'a', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'b', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'c', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'd', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'e', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'f', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'g', new Peao(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'h', new Peao(tabuleiro, Cor.PRETO));
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.montaPosicao();
		return tabuleiro.posicaoPeca(posicao).movimentosPossiveis();
	}
}

package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecasXadrez.Bispo;
import xadrez.pecasXadrez.Cavalo;
import xadrez.pecasXadrez.Peao;
import xadrez.pecasXadrez.Rainha;
import xadrez.pecasXadrez.Rei;
import xadrez.pecasXadrez.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassantVulneravel;
	private PecaXadrez promovido;

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

	public PecaXadrez enPassantVulneravel() {
		return enPassantVulneravel;
	}

	public PecaXadrez getPromovido() {
		return promovido;
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

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.posicaoPeca(destino);

		// movimento de promoção
		promovido = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promovido = (PecaXadrez)tabuleiro.posicaoPeca(destino);
				promovido = trocarPecaPromovida("R");
			}
		}
		
		check = (testaCheck(oponente(jogadorAtual))) ? true : false;

		if (testaCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}

		proximaJogada();

		if (pecaMovida instanceof Peao
				&& (destino.getColuna() == origem.getColuna() - 2 || destino.getColuna() == origem.getColuna() + 2)) {
			enPassantVulneravel = pecaMovida;
		} else {
			enPassantVulneravel = null;
		}

		return (PecaXadrez) pecaCapturada;
	}

	public PecaXadrez trocarPecaPromovida(String tipoPecaPromovida) {
		if(promovido == null) {
			throw new IllegalStateException("Não há peça para ser promovida");
		}
		if(!tipoPecaPromovida.equals("B") && !tipoPecaPromovida.equals("T") && !tipoPecaPromovida.equals("C") && !tipoPecaPromovida.equals("R") ) {
			throw new InvalidParameterException("Tipo invalido para promoção");
		}
		
		Posicao posicao = promovido.getPosicaoXadrez().montaPosicao();
		Peca peca = tabuleiro.removePeca(posicao);
		pecasNoTabuleiro.remove(peca);
		
		PecaXadrez novaPeca = novaPeca(tipoPecaPromovida, promovido.getCor());
		tabuleiro.inserePeca(novaPeca, posicao);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		switch (tipo) {
		case "B":
			return new Bispo(tabuleiro, cor);
		case "T":
			return new Torre(tabuleiro, cor);
		case "C":
			return new Cavalo(tabuleiro, cor);
		default:
			return new Rainha(tabuleiro, cor);
		}
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
		tabuleiro.inserePeca(peca, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		trataRoquePequeno(peca, origem, destino);
		trataRoqueGrande(peca, origem, destino);

		// enPassant
		if (peca instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoDoPeao;
				if (peca.getCor() == Cor.BRANCO) {
					posicaoDoPeao = new Posicao(destino.getLinha(), destino.getColuna() + 1);
				} else {
					posicaoDoPeao = new Posicao(destino.getLinha(), destino.getColuna() - 1);
				}
				pecaCapturada = tabuleiro.removePeca(posicaoDoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void trataRoquePequeno(PecaXadrez peca, Posicao origem, Posicao destino) {
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.inserePeca(torre, destinoTorre);
			torre.aumentaContagemMovimentos();
		}
	}

	private void trataRoqueGrande(PecaXadrez peca, Posicao origem, Posicao destino) {
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.inserePeca(torre, destinoTorre);
			torre.aumentaContagemMovimentos();
		}
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

		desfazRoquePequeno(peca, origem, destino);
		desfazRoqueGrande(peca, origem, destino);

		// enPassant
		if (peca instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removePeca(destino);
				Posicao posicaoDoPeao;
				if (peca.getCor() == Cor.BRANCO) {
					posicaoDoPeao = new Posicao(3, destino.getColuna() + 1);
				} else {
					posicaoDoPeao = new Posicao(4, destino.getColuna() - 1);
				}
				tabuleiro.inserePeca(peao, posicaoDoPeao);
			}
		}
	}

	private void desfazRoquePequeno(PecaXadrez peca, Posicao origem, Posicao destino) {
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.inserePeca(torre, origemTorre);
			torre.diminuiContagemMovimentos();
		}
	}

	private void desfazRoqueGrande(PecaXadrez peca, Posicao origem, Posicao destino) {
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.inserePeca(torre, origemTorre);
			torre.diminuiContagemMovimentos();
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
		colocaNovaPeca(1, 'b', new Cavalo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'c', new Bispo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'd', new Rainha(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'e', new Rei(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(1, 'f', new Bispo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'g', new Cavalo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(1, 'h', new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca(2, 'a', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'b', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'c', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'd', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'e', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'f', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'g', new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca(2, 'h', new Peao(tabuleiro, Cor.BRANCO, this));

		colocaNovaPeca(8, 'a', new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'b', new Cavalo(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'c', new Bispo(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'd', new Rainha(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'e', new Rei(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(8, 'f', new Bispo(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'g', new Cavalo(tabuleiro, Cor.PRETO));
		colocaNovaPeca(8, 'h', new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca(7, 'a', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'b', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'c', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'd', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'e', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'f', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'g', new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca(7, 'h', new Peao(tabuleiro, Cor.PRETO, this));
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.montaPosicao();
		return tabuleiro.posicaoPeca(posicao).movimentosPossiveis();
	}
}

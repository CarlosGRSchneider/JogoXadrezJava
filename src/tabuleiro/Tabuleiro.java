package tabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int linha, int coluna) {
		
		if( linha < 1 || coluna < 1) {
			throw new TabuleiroException("Erro na criação do tabuleiro; para ele existir, é necessário ao menos 1 linha e 1 coluna.");
		}
		
		this.linhas = linha;
		this.colunas = coluna;
		pecas = new Peca[linha][coluna];
	}

	public int getLinha() {
		return linhas;
	}


	public int getColuna() {
		return colunas;
	}

	public Peca posicaoPeca(int linha, int coluna) {
		if(!existePosicao(linha, coluna)) {
			throw new TabuleiroException("Posição inválida");
		}
		
		return pecas[linha][coluna];
	}
	
	public Peca posicaoPeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuleiroException("Posição inválida");
		}
		
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void inserePeca(Peca peca, Posicao posicao) {
		if(existePosicao(posicao)) {
			throw new TabuleiroException("Há uma peça nesta posição");
		}
		
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removePeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuleiroException("Esta posição não existe");
		}
		
		if(posicaoPeca(posicao) == null) {
			return null;
		}
		
		Peca aux = posicaoPeca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getLinha(), posicao.getColuna()); 
	}
	
	public boolean existePosicao(int estaLinha, int estaColuna) {
		
		
		return estaLinha >= 0 && estaLinha < linhas && estaColuna >= 0 && estaColuna < colunas; 
	}

	public boolean existePeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuleiroException("Posição inválida");
		}
		
		return posicaoPeca(posicao) != null;
	}
}

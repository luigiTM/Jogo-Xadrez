package tabuleiro;

public class Tabuleiro {

	private Integer linhas;
	private Integer colunas;
	private Peca[][] pecas;

	public Tabuleiro(Integer linhas, Integer colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro criando tabuleiro");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public Peca peca(Integer linha, Integer coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new TabuleiroException("Posicao não existe");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posicao não existe");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void colocarPeca(Peca peca, Posicao posicao) {
		if (temPeca(posicao)) {
			throw new TabuleiroException("Já tem uma peça");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	private boolean posicaoExiste(Integer linha, Integer coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}

	public boolean temPeca(Posicao posicao) {
		posicaoExiste(posicao);
		return peca(posicao) != null;
	}

	public Integer getLinhas() {
		return linhas;
	}

	public Integer getColunas() {
		return colunas;
	}

}

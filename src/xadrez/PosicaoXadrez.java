package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {

	public Character coluna;
	public Integer linha;

	public PosicaoXadrez(Character coluna, Integer linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrexException("Erro instanciando " + this.getClass().getSimpleName());
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public Character getColuna() {
		return coluna;
	}

	public Integer getLinha() {
		return linha;
	}

	protected Posicao paraPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	protected static PosicaoXadrez dePosicao(Posicao posicao) {
		return new PosicaoXadrez((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha;
	}

}

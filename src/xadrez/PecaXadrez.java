package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	private Integer contagemDeMovimentos;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
		contagemDeMovimentos = 0;
	}

	public Cor getCor() {
		return cor;
	}
	
	public Integer getContagemDeMovimento() {
		return contagemDeMovimentos;
	}
	
	public void incrementarContagemDeMovimento() {
		contagemDeMovimentos++;
	}
	
	public void decrementarContagemDeMovimento() {
		contagemDeMovimentos--;
	}

	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.dePosicao(posicao);
	}

	protected boolean temPecaAdversaria(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);
		return peca != null && peca.getCor() != cor;
	}

}

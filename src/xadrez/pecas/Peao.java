package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] movimentosPossiveis = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao posicaoVerificacao = new Posicao(0, 0);
		if (getCor() == Cor.BRANCO) {
			posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			posicaoVerificacao.definirValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao posicaoVerificacao2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao) && getContagemDeMovimento() == 0 && getTabuleiro().posicaoExiste(posicaoVerificacao2) && !getTabuleiro().temPeca(posicaoVerificacao2)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.vulneravelEnPassant()) {
					movimentosPossiveis[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temPecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.vulneravelEnPassant()) {
					movimentosPossiveis[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		} else {
			posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			posicaoVerificacao.definirValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao posicaoVerificacao2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao) && getContagemDeMovimento() == 0 && getTabuleiro().posicaoExiste(posicaoVerificacao2) && !getTabuleiro().temPeca(posicaoVerificacao2)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.vulneravelEnPassant()) {
					movimentosPossiveis[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temPecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.vulneravelEnPassant()) {
					movimentosPossiveis[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		return movimentosPossiveis;
	}

	@Override
	public String toString() {
		return "P";
	}

}

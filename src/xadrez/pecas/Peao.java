package xadrez.pecas;

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
		}
		return movimentosPossiveis;
	}

	@Override
	public String toString() {
		return "P";
	}

}

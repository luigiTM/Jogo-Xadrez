package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] movimentosPossiveis = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao posicaoVerificacao = new Posicao(0, 0);

		posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.setLinha(posicaoVerificacao.getLinha() - 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}

		posicaoVerificacao.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.setColuna(posicaoVerificacao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}

		posicaoVerificacao.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.setColuna(posicaoVerificacao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}

		posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.setLinha(posicaoVerificacao.getLinha() + 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}
		posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.definirValores(posicaoVerificacao.getLinha() - 1, posicaoVerificacao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}

		posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.definirValores(posicaoVerificacao.getLinha() - 1, posicaoVerificacao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}

		posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.definirValores(posicaoVerificacao.getLinha() + 1, posicaoVerificacao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}

		posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(posicaoVerificacao) && !getTabuleiro().temPeca(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			posicaoVerificacao.definirValores(posicaoVerificacao.getLinha() + 1, posicaoVerificacao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(posicaoVerificacao) && temPecaAdversaria(posicaoVerificacao)) {
				movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
			}
		}
		return movimentosPossiveis;
	}
}

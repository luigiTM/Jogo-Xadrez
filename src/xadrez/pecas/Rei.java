package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);
		return peca == null || peca.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] movimentosPossiveis = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao posicaoVerificacao = new Posicao(0, 0);

		posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		posicaoVerificacao.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(posicaoVerificacao) && podeMover(posicaoVerificacao)) {
			movimentosPossiveis[posicaoVerificacao.getLinha()][posicaoVerificacao.getColuna()] = true;
		}

		return movimentosPossiveis;

	}
}

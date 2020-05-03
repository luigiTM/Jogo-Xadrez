package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);
		return peca == null || peca.getCor() != getCor();
	}

	private boolean testarMovimentoRoque(Posicao posicao) {
		PecaXadrez torre = (PecaXadrez) getTabuleiro().peca(posicao);
		return torre != null && torre instanceof Torre && torre.getCor() == getCor() && torre.getContagemDeMovimento() == 0;
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

		if (getContagemDeMovimento() == 0 && !partidaXadrez.getCheque()) {
			Posicao posicaoT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testarMovimentoRoque(posicaoT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					movimentosPossiveis[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			Posicao posicaoT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testarMovimentoRoque(posicaoT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					movimentosPossiveis[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}

		return movimentosPossiveis;

	}
}

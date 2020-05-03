package tabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] movimentosPossiveis();

	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean temMovimentoPossivel() {
		boolean[][] matrizMovimentos = movimentosPossiveis();
		for (int i = 0; i < matrizMovimentos.length; i++) {
			for (int j = 0; j < matrizMovimentos.length; j++) {
				if (matrizMovimentos[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}

package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private Cor jogadorAtual;
	private Integer turno;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public Integer getTurno() {
		return turno;
	}

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		setupInicial();
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez origem) {
		Posicao posicaoOrigem = origem.paraPosicao();
		validarPosicaoOrigem(posicaoOrigem);
		return tabuleiro.peca(posicaoOrigem).movimentosPossiveis();
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrexException("Não existe peça nessa posição.");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrexException("Essa peça não é sua.");
		}
		if (!tabuleiro.peca(posicao).temMovimentoPossivel()) {
			throw new XadrexException("Não existe movimento possível para essa peça.");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrexException("Esse movimento não é válido.");
		}
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca peca = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(peca, destino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}

	public PecaXadrez moverPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		proximoTurno();
		return (PecaXadrez) pecaCapturada;

	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private void colocarNovaPeca(Character coluna, Integer linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void setupInicial() {
		colocarNovaPeca('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 7, new Rei(tabuleiro, Cor.PRETO));
	}

}

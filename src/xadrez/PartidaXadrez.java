package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private Cor jogadorAtual;
	private Integer turno;
	private boolean cheque;
	private boolean chequeMate;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public Integer getTurno() {
		return turno;
	}

	public boolean getCheque() {
		return cheque;
	}

	public boolean getChequeMate() {
		return chequeMate;
	}

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		cheque = false;
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

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca peca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(peca, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}

	public PecaXadrez moverPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		if (verificarCheque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrexException("Você não pode se colocar em cheque");
		}
		cheque = verificarCheque(oponente(jogadorAtual));
		if (verificarChequeMate(oponente(jogadorAtual))) {
			chequeMate = true;
		} else {
			proximoTurno();
		}
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

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> pecasDaCor = pecasNoTabuleiro.stream().filter(peca -> ((PecaXadrez) peca).getCor() == cor).collect(Collectors.toList());
		for (Peca peca : pecasDaCor) {
			if (peca instanceof Rei) {
				return (PecaXadrez) peca;
			}
		}
		throw new IllegalStateException("Não existe rei da cor " + cor + " no tabuleiro!");
	}

	private boolean verificarCheque(Cor cor) {
		Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(peca -> ((PecaXadrez) peca).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca peca : pecasDoOponente) {
			boolean[][] movimentosPossiveis = peca.movimentosPossiveis();
			if (movimentosPossiveis[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean verificarChequeMate(Cor cor) {
		if (!verificarCheque(cor)) {
			return false;
		}
		List<Peca> pecasDaCor = pecasNoTabuleiro.stream().filter(peca -> ((PecaXadrez) peca).getCor() == cor).collect(Collectors.toList());
		for (Peca peca : pecasDaCor) {
			boolean[][] movimentosPossiveis = peca.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (movimentosPossiveis[i][j]) {
						Posicao origem = ((PecaXadrez) peca).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean cheque = verificarCheque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!cheque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Rei(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 1, new Rei(tabuleiro, Cor.BRANCO));
	}

}

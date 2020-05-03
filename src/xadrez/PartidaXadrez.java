package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private Cor jogadorAtual;
	private Integer turno;
	private boolean cheque;
	private boolean chequeMate;
	private PecaXadrez vulneravelEnPassant;
	private PecaXadrez promovido;

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

	public PecaXadrez vulneravelEnPassant() {
		return vulneravelEnPassant;
	}

	public PecaXadrez getPromovido() {
		return promovido;
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
		PecaXadrez peca = (PecaXadrez) tabuleiro.removerPeca(origem);
		peca.incrementarContagemDeMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(peca, destino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrementarContagemDeMovimento();
		}
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrementarContagemDeMovimento();
		}
		if (peca instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (peca.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha(), destino.getColuna() + 1);
				} else {
					posicaoPeao = new Posicao(destino.getLinha(), destino.getColuna() - 1);
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez peca = (PecaXadrez) tabuleiro.removerPeca(destino);
		peca.decrementarContagemDeMovimento();
		tabuleiro.colocarPeca(peca, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.decrementarContagemDeMovimento();
		}
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.decrementarContagemDeMovimento();
		}
		if (peca instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulneravelEnPassant) {
				PecaXadrez peaoRecolocado = (PecaXadrez) tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (peca.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna() + 1);
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna() - 1);
				}
				tabuleiro.colocarPeca(peaoRecolocado, posicaoPeao);
			}
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
		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);
		promovido = null;
		if (cheque) {
			if (pecaMovida instanceof Peao) {
				if ((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
					promovido = (PecaXadrez) tabuleiro.peca(destino);
					promovido = trocarPecaPromovida("Q");
				}
			}
		}

		cheque = verificarCheque(oponente(jogadorAtual));
		if (verificarChequeMate(oponente(jogadorAtual))) {
			chequeMate = true;
		} else {
			proximoTurno();
		}
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			vulneravelEnPassant = pecaMovida;
		} else {
			vulneravelEnPassant = null;
		}
		return (PecaXadrez) pecaCapturada;

	}

	public PecaXadrez trocarPecaPromovida(String tipo) {
		if (promovido == null) {
			throw new IllegalStateException("Peça inválida");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo de peca inválida");
		}
		Posicao posicaoPromovida = promovido.getPosicaoXadrez().paraPosicao();
		Peca peca = tabuleiro.removerPeca(posicaoPromovida);
		pecasNoTabuleiro.remove(peca);
		PecaXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.colocarPeca(novaPeca, posicaoPromovida);
		pecasNoTabuleiro.add(novaPeca);
		return novaPeca;
	}

	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if (tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		if (tipo.equals("T"))
			return new Torre(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
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
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}

}

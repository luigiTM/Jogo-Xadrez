package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrexException;

public class Programa {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();

		while (!partidaXadrez.getChequeMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(scanner);

				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(scanner);

				PecaXadrez pecaCapturada = partidaXadrez.moverPeca(origem, destino);
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				if (partidaXadrez.getPromovido() != null) {
					System.out.println("Para qual peça deseja promover?(B/C/T/Q)");
				}
			} catch (XadrexException erroXadrez) {
				System.out.println(erroXadrez.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException erroEntrada) {
				System.out.println(erroEntrada.getMessage());
				scanner.nextLine();
			}
		}
		UI.limparTela();
		UI.imprimirPartida(partidaXadrez, capturadas);
	}

}

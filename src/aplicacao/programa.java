package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrexException;

public class Programa {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();

		while (true) {
			try {
				UI.limparTela();
				UI.imprimirTabuleiro(partidaXadrez.getPecas());
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(scanner);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(scanner);

				PecaXadrez pecaCapturada = partidaXadrez.moverPeca(origem, destino);
			} catch (XadrexException erroXadrez) {
				System.out.println(erroXadrez.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException erroEntrada) {
				System.out.println(erroEntrada.getMessage());
				scanner.nextLine();
			}
		}
	}

}

package view;
import controller.ThreadEscuderia;
import java.util.concurrent.Semaphore;

public class Principal {
	public static void main (String [] args) {
		int numCarro = 1;
		int acessoPista = 5;
		Semaphore entradaCorrida = new Semaphore (acessoPista);
		
			for (int numEscuderia = 1; numEscuderia <= 7; numEscuderia++) {
				Thread f1 = new ThreadEscuderia(numCarro, numEscuderia, entradaCorrida);
				f1.start();
			}
	}
}

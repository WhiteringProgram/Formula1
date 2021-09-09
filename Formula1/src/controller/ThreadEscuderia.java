package controller;
import java.util.concurrent.Semaphore;

public class ThreadEscuderia extends Thread {
	private int numCarro;
	private int numEscuderia;
	private static double [][] grid = new double [14][3]; //Total de carros e de voltas respectivamente
	private static int classificados = 0;
	Semaphore entradaCorrida;
	
	//Construtor
	public ThreadEscuderia (int numCarro, int numEscuderia, Semaphore entradaCorrida) {
		this.numCarro = numCarro;
		this.numEscuderia = numEscuderia;
		this.entradaCorrida = entradaCorrida;
	}
	
	public void run () { //Execução da Thread propriamente dita
		do {
			try {
				entradaCorrida.acquire();
				carroCorrida();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				entradaCorrida.release();
				deixaPista();
				numCarro++;
					if (classificados == 14) { //Número de participantes
						imprimeGrid();
					}
				}
			} while (numCarro <= 2);
		
	}

	public void carroCorrida () {
		double lapTime;
		double melhorTempo = 0;
		System.out.println("O carro #" +numCarro + " da Escuderia #"+ numEscuderia+ " adentrou à pista para obter sua classificação" );
			for (int idLap = 1; idLap <3; idLap++) {
				lapTime = corridaTempo();
					if (melhorTempo == 0) {
						melhorTempo = lapTime;
							} else if (lapTime < melhorTempo) {
							melhorTempo = lapTime;
			}
		System.out.println("O carro #" +numCarro + " da Escuderia #" +numEscuderia + " completou sua " +idLap  + "ª volta" + " em"+ " " + lapTime + " minutos");
		}
		System.out.println("O carro #" +numCarro + " da Escuderia #" +numEscuderia + " fez sua melhor volta em "  +melhorTempo + " minutos");
		saveGrid(melhorTempo);
	}
	
	private double corridaTempo () {
		int distancia = 4500; //Valor aleatório baseado na distância média dos autódromos
		int aux = 0;
		double velocidade = 0;
		double velocidadeMedia = 0;
		int deslocamento = 0;
		double lapTime;
		int tempo = 1; //Tempo alocado como 1 para não demorar muito
			while (deslocamento < distancia) {
				velocidade = (((double)Math.random() *18)+ 55);
					if (velocidade > (distancia - deslocamento)) {
						deslocamento = 4500;
						} else {
							deslocamento += velocidade;
						} 
					System.out.println("O carro #" +numCarro+ " da Escuderia #" +numEscuderia+ " percorreu" + " " +deslocamento+ " metros");
					try {
						sleep(tempo);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					aux++;
					velocidadeMedia += velocidade;
			}
			velocidadeMedia = (velocidade/aux);
			lapTime = (distancia/velocidadeMedia)/((double)(Math.random()*500)+1000); 
			return lapTime;
	}
	
	public void saveGrid(double melhorTempo) {
		int i = 0; //Linha
		int j = 0; //Coluna
			if (numCarro == 1) {
				i = (numEscuderia - 1); //Significa que um espaço já foi ocupado
			} else if (numCarro == 2) {
				i = (numEscuderia + 6);
			}
			//Separação dos elementos de uma matriz com ênfase nos registros compostos por linhas e colunas
		grid [i][j] = numCarro;
		grid [i][j + 1] = numEscuderia;
		grid [i][j + 2] = melhorTempo;
	}
	
	public void deixaPista () {
		System.out.println("O carro #" +numCarro + " da Escuderia #" +numEscuderia + " saiu da pista");
		classificados++;
	}
	
	public static void imprimeGrid() {
		arrumaGrid(grid);
		System.out.println("\n\n");
		System.out.println("#########################################################################");
		System.out.println("################################G R I D##################################");
		System.out.println("#########################################################################");
		System.out.println("\n\n");
		for (int i = 0; i <= 13; i++) {
			System.out.println("O carro #" +grid [i][0] +" da Escuderia "+grid[i][1] + " ficou em "  + (i + 1) + "º lugar" + " com término em " + grid[i][2]);
			}
	}	
		
	private static double [][] arrumaGrid (double [][] grid) {
		double aux [] = new double [3]; //Número de voltas (Será utilizado para percorrer o vetor)
		
			for (int i = 0; i <= 12; i++) {
				for (int line = (i +1); line <= 13; line ++) {
					if (grid[line][2] < grid [i][2]) {
						for (int j = 0; j <= 2 ; j++) {
							aux [j] = grid [line][j];
							grid[line][j] = grid[i][j];
							grid [i][j] = aux[j];
						}
					}
				}
			}
			return grid;
	}
}

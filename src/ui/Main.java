package ui;

import java.util.Scanner;

import model.Game;

public class Main {
	private static Scanner in;
	private static Game game;
	
	public static void main(String[] args) {
		/**in = new Scanner(System.in);
		game = new Game();
		start();**/
		
		Game g = new Game();
		g.createBoard(4, 4);
		g.showBoard();
		
	}
	
	public static void showMainMenu() {
		System.out.println("1. Jugar\n2. Ver tablero de posiciones\n3. Salir\n");
	}
	
	public static int readOption() {
		int option = in.nextInt();
		return option;
	}
	
	public static void start() {
		showMainMenu();
		switch(readOption()) {
		case 1:
			startGame();
			start();
		break;
		
		case 2:
			showScores();
			start();
		break;
			
		case 3:
			
		break;
		default:
		}
		
	}
	
	public static void startGame() {
		String info = in.nextLine();
		String[] information = info.split(" ");
		game.createBoard(Integer.parseInt(information[0]), Integer.parseInt(information[1]));
		game.createLadders(Integer.parseInt(information[2]));
		game.createSnakes(Integer.parseInt(information[3]));
		game.setPlayers(information[4]);
		
	}
	
	public static void showScores() {
		
	}
}

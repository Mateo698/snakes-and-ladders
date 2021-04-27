package model;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Menu {
	private Score scoresTree;
	private Board board;
	private Scanner in;
	
	public Menu() {
		in = new Scanner(System.in);
	}
	
	public void MainMenu() {
		System.out.println("Welcome to snakes and ladders\nPlease type the number of a valid option\n");
		System.out.println("1.Play\n2.Scores\n3.Exit");
		switch (readOption()) {
		case 1:
			if(readInitialData()) {
				showEnum();
				startGame();
				MainMenu();
			}else {
				System.out.println("Please type the right data");
				MainMenu();
			}
			
			break;
			
		case 2:
			seeScores();
			break;
			
		case 3:
			break;

		default:
			break;
		}
	}
	
	private void seeScores() {
		if(scoresTree == null) {
			System.out.println("There are not any scores yet");
		}else {
			System.out.println("Scores:\n" + scoresTree.getList());
		}
	}

	public void startGame() {
		String option = in.nextLine();
		if(option == "" || option.isEmpty()) {
			Random r = new Random();
			int movements = r.nextInt(7);
			if(movements == 0) {
				movements++;
			}
			String playerString = board.getPlayerString();
			System.out.println("The player " + playerString + " got "+movements);
			Player won = board.startMovement(movements);
			if(won != null) {
				System.out.println("The player " + won.getSymbol() + " won with" + won.getMovements());
				System.out.println("Please type the winner's nickname\n");
				String nick = in.nextLine();
				int score = won.getMovements()*board.getRows()*board.getCols();
				if(scoresTree == null) {
					scoresTree = new Score(nick, won.getSymbol(), score);
				}else {
					scoresTree.add(new Score(nick, won.getSymbol(), score));
				}
			}else {
				board.showActual(0);
				startGame();
			}
			
		}else if(option.contains("num")) {
			board.recursiveRows(0);
			in.next();
			board.showActual(0);
			startGame();
		}else if(option.contains("simul")) {
			Timer time = new Timer();
			TimerTask tt = new TimerTask() {
				@Override
				public void run() {
					Random r = new Random();
					int movements = r.nextInt(7);
					if(movements == 0) {
						movements++;
					}
					String playerString = board.getPlayerString();
					System.out.println("The player " + playerString + " got "+movements);
					Player won = board.startMovement(movements);
					if(won != null) {
						
						System.out.println(won.getSymbol() + " a ganao con" + won.getMovements());
						MainMenu();
						time.cancel();
						return;
					}else {
						board.showActual(0);
					}
				}
			};
			time.schedule(tt,0, 1000);

		}else if(option.contains("menu")){
			
		}else {
			System.out.println("Please type a correct option");
		}
	}
	
	public boolean readInitialData() {
		System.out.println("Type the number of rows, columns, snakes, ladders and players symbols\n");
		String data = in.nextLine();
		String[] allData = data.split(data);
		board = new Board(Integer.parseInt(allData[0]), Integer.parseInt(allData[1]));
		if(setSnakesAndLadders(Integer.parseInt(allData[2]), Integer.parseInt(allData[3]))) {
			setPlayer(allData[4]);
			board.setPlayersOnBoard();
			return true;
		}else {
			return false;
		}
		
	}
	
	public int readOption() {
		int option = Integer.parseInt(in.nextLine());
		return option;
	}
	
	public void createLadders(int ladders) {
		board.createLadders(ladders);
	}
	
	public void createSnakes(int snakes) {
		board.createSnakes(snakes);
	}
	
	public void setPlayers(String pl) {
		board.setPlayers(pl);
	}
	
	public void createBoard(int x,int y) {
		board = new Board(x,y);
	}
	
	public void showEnum() {
		board.recursiveRows(0);
	}
	
	public void showActual() {
		board.showActual(0);
	}

	public Score getScoresTree() {
		return scoresTree;
	}

	public void setScoresTree(Score scoresTree) {
		this.scoresTree = scoresTree;
	}
	
	public void setPlayer(String players) {
		board.setPlayers(players);
		board.setPlayersOnBoard();
	}
	
	public boolean setSnakesAndLadders(int snakes,int ladders) {
		if(snakes*2+ladders*2 > board.getCols()*board.getRows()) {
			return false;
		}
		else {
			board.createLadders(ladders);
			board.createSnakes(snakes);
			return true;
		}
	}
	
	public void show() {
		board.show();
	}

}

package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;

public class Menu {
	private Score scoresTree;
	private Board board;
	private Scanner in;
	private boolean leftAuto;
	private String SAVE_PATH_FILE = "data/scores.arroz";
	private int ladders;
	private int snakes;
	private String allPlayers;
	
	public Menu() {
		in = new Scanner(System.in);
		leftAuto = false;
		snakes = 0;
		ladders = 0;
		allPlayers = "";
	}
	
	public void MainMenu() {
		if(leftAuto) {
			Player won = board.getLastWonPlayer();
			System.out.println("The player " + won.getSymbol() + " won with " + won.getMovements() );
			System.out.println("Please type the winner's nickname\n");
			String nick = in.nextLine();
			int score = won.getMovements()*board.getRows()*board.getCols();
			if(scoresTree == null) {
				scoresTree = new Score(nick, won.getSymbol(), score);
			}else {
				scoresTree.add(new Score(nick, won.getSymbol(), score));
			}
			leftAuto = false;
			MainMenu();
		}else {
			System.out.println("Welcome to snakes and ladders\nPlease type the number of a valid option\n");
			System.out.println("1.Play\n2.Scores\n3.Exit");
			switch (readOption()) {
			case "1":
				if(readInitialData()) {
					showEnum();
					startGame();
					MainMenu();
				}else {
					System.out.println("Please type the right data");
					MainMenu();
				}
				
				break;
				
			case "2":
				seeScores();
				MainMenu();
				break;
				
			case "3":
				break;

			default:
				MainMenu();
				break;
			}
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
				try {
					saveData();
				} catch (IOException e) {
					System.out.println("F");
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
			try {
				simulation();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(option.contains("menu")){

		}else {
			System.out.println("Please type a correct option");
		}
	}
	
	/**
	* Read the inital data to start the game (n m s l p) 
	*
	* @return If it was successful or not (if the information was right)
	*/
	public boolean readInitialData() {
		System.out.println("Type the number of rows, columns, snakes, ladders and players symbols\n");
		String data = in.nextLine();
		String[] allData = data.split(" ");
		int rows = Integer.parseInt(allData[0]);
		int cols = Integer.parseInt(allData[1]);
		board = new Board(rows,cols);
		if(setSnakesAndLadders(Integer.parseInt(allData[2]), Integer.parseInt(allData[3]))) {
			setPlayer(allData[4]);
			allPlayers = allData[4];
			snakes = Integer.parseInt(allData[2]);
			ladders = Integer.parseInt(allData[3]);
			board.setPlayersOnBoard();
			return true;
		}else {
			return false;
		}
		
	}
	
	/**
	* Starts the simulation of the game 
	*/
	public void simulation() throws InterruptedException {
		Thread.sleep(5000);
		Random r = new Random();
		int movements = r.nextInt(7);
		if(movements == 0) {
			movements++;
		}
		String playerString = board.getPlayerString();
		System.out.println("The player " + playerString + " got "+movements);
		Player won = board.startMovement(movements);
		if(won != null) {
			won.setRows(board.getRows());
			won.setCols(board.getCols());
			won.setLadders(ladders);
			won.setSnakes(snakes);
			won.setPlayers(allPlayers);
			System.out.println("The player " + won.getSymbol() + " won with" + won.getMovements());
			System.out.println("Please type the winner's nickname\n");
			String nick = in.nextLine();
			int score = won.getMovements()*board.getRows()*board.getCols();
			if(scoresTree == null) {
				scoresTree = new Score(nick, won.getSymbol(), score);
			}else {
				scoresTree.add(new Score(nick, won.getSymbol(), score));
			}
			try {
				saveData();
			} catch (IOException e) {
				System.out.println("F");
			}
		}else {
			board.showActual(0);
			simulation();
		}
	}
	
	public String readOption() {
		String option = in.nextLine();
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
	
	/**
	* Checks if the amount of ladders and snakes can fit in the board 
	*
	* @param snakes Amount of snakes
	* @param ladders Amount of ladders
	* @return If its possible to fit those ladders and snakes in the board
	*/
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
	
	public void saveData() throws IOException{
	    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
	    oos.writeObject(scoresTree);
	    oos.close();
	  }

	  public boolean loadData() throws IOException, ClassNotFoundException{
	    File f = new File(SAVE_PATH_FILE);
	    boolean loaded = false;
	    if(f.exists()){
	      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	      scoresTree = (Score) ois.readObject();
	      ois.close();
	      loaded = true;
	    }
	    return loaded;
	  }

	public void load() {
		try {
			if(loadData()) {
				System.out.println("Data loaded succesfully\n");	
			}else {
				System.out.println("There is no data to load\n");
			}
			
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("FFF\n" + e.getMessage());
		}
		
	}

}

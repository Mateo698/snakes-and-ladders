package model;

import java.util.Random;

public class Board {
	private Square first;
	private int rows;
	private int cols;
	private boolean direction;
	private Player playersSymbol;
	private int ladderCounter;
	private String snakesCounter;
	private int indexPlayerToMove;
	private Player lastWonPlayer; //aux for auto
	
	public Board(int x,int y) {
		playersSymbol = null;
		setRows(x);
		setCols(y);
		direction = true;
		createBoard();
		ladderCounter = 1;
		snakesCounter = "A";
		indexPlayerToMove = 0;
		setLastWonPlayer(null);
	}

	public void show() {
		Square aux = first;
		while(aux != null) {
			System.out.println(aux.getSquareString());
			aux = aux.getNext();
		}
	}
	
	/**
	* Start the movement of the players through the board in order (moves the first player typed in the menu)
	* then it adds 1 to the counter to move to let the method know what player moves next 
	*
	* @param moves The amount of moves of the player
	* @return The player that won if its the case, otherwise returns null
	*/
	public Player startMovement(int moves) {

		if(indexPlayerToMove == 0) {
			Player winPlayer = first.movePlayer(playersSymbol.clone(), moves);	
			playersSymbol.addMovement(moves);
			if(winPlayer != null) {
				indexPlayerToMove = 0;
				if(winPlayer.getSymbol() == playersSymbol.getSymbol()) {
					setLastWonPlayer(playersSymbol.clone());
					return playersSymbol.clone();
				}else {
					setLastWonPlayer(playersSymbol.search(winPlayer.getSymbol()));
					return playersSymbol.search(winPlayer.getSymbol());
				}
				
			}else {
				indexPlayerToMove++;
				return null;
			}
		}else {
			playersSymbol.get(indexPlayerToMove).addMovement(moves);
			
			Player aux = playersSymbol.get(indexPlayerToMove).clone();

			Player winPlayer = first.movePlayer(aux, moves);
			if(winPlayer != null) {
				indexPlayerToMove = 0;
				if(winPlayer.getSymbol() == playersSymbol.getSymbol()) {
					setLastWonPlayer(playersSymbol.clone());
					return playersSymbol.clone();
				}else {
					setLastWonPlayer(playersSymbol.search(winPlayer.getSymbol()));
					return playersSymbol.search(winPlayer.getSymbol());
				}
			}else {
				if(indexPlayerToMove+1 == playersSymbol.size()) {
					indexPlayerToMove = 0;
				}else {
					indexPlayerToMove++;
				}
				return null;
			}
		}
	}
	
	/**
	* Creates the first square and calls the method to create the whole board
	*/
	public void createBoard() {
		first = new Square(1,rows-1,0);
		createNext(first);
	}
	
	/**
	* Generates randomly the ladders with amount typed by the user 
	*
	* @param ladders The amount of ladders it needs to create
	*/
	public void createLadders(int ladders) {
		if(ladders == 0) {
			return;
		}else {
			Random r = new Random();
			int initRow = r.nextInt(rows);
			int endRow = r.nextInt(rows);
			if(initRow < endRow) {
				createLadders(ladders);
			}else {
				int initCol = r.nextInt(cols);
				int endCol = r.nextInt(cols);
				if(checkLadder(initRow,initCol,endRow,endCol)) {
					createLadders(ladders);
				}
				else {
					Square reference = first.search(initRow, initCol);
					if(reference.getInitLadder() == null && reference.getEndLadder() == null && reference.getInitSnake() == null && reference.getEndSnake() == null) {
						Square endRef = first.search(endRow, endCol);
						if(endRef.getEndSnake() == null && endRef.getInitSnake() == null && endRef.getInitLadder() == null && endRef.getEndLadder() == null) {
							reference.setLadderLetter(ladderCounter);
							reference.setInitLadder(endRef);
							endRef.setLadderLetter(ladderCounter);
							endRef.setEndLadder(reference);
							ladderCounter++;
							ladders--;
							createLadders(ladders);
						}
						else {
							createLadders(ladders);
						}
					}
					else {
						createLadders(ladders);
					}
				}
			}
		}
		
	}
	
	
	/**
	* Generates randomly the amount of snakes typed by the user 
	*@param snakes Its the amount of snakes to be created
	*/
	public void createSnakes(int snakes) {
		if(snakes == 0) {
			return;
		}else {
			Random r = new Random();
			int initRow = r.nextInt(rows);
			int endRow = r.nextInt(rows);
			if(initRow > endRow) {
				createSnakes(snakes);
			}else {
				int initCol = r.nextInt(cols);
				int endCol = r.nextInt(cols);
				if(checkSnakes(initRow,initCol,endRow,endCol)) {
					createSnakes(snakes);
				}
				else {
					Square reference = first.search(initRow, initCol);
					if(reference.getInitLadder() == null && reference.getEndLadder() == null && reference.getInitSnake() == null && reference.getEndSnake() == null) {
						Square endRef = first.search(endRow, endCol);
						if(endRef.getEndSnake() == null && endRef.getInitSnake() == null && endRef.getInitLadder() == null && endRef.getEndLadder() == null) {
							reference.setInitSnake(endRef);
							reference.setSnakeLetter(snakesCounter);
							endRef.setEndSnake(reference);
							endRef.setSnakeLetter(snakesCounter);
							plusLetter();
							snakes--;
							createSnakes(snakes);
						}
						else {
							createSnakes(snakes);
						}
					}
					else {
						createSnakes(snakes);
					}
				}
			}
		}
	}
	
	/**
	* Puts the next letter of the alphabet to the counter for snakes 
	*/
	public void plusLetter() {
		char value = snakesCounter.charAt(0);
		value++;
		String letter = String.valueOf(value);
		snakesCounter = letter;
	}
	
	public boolean checkLadder(int initRow, int initCol, int endRow, int endCol) {
		if(initRow < endRow || initRow == endRow) {
			return true;
		}else {
			if(initRow > rows || endRow > rows || initCol > cols || endCol > cols) {
				return true;
			}else {
				if((initRow == rows-1 && initCol == 0) || (initRow == first.getLast().getRow() && initCol == first.getLast().getCol()) || (endRow == rows-1 && endCol == 0) || (endRow == first.getLast().getRow() && endCol == first.getLast().getCol())) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	
	/**
	* Checks if the numbers given are proper for the creation of a Snake. Also checks that the number its not out of 
	* bounds
	*
	* @param initRow the number of the row for the start of the snake
	* @param initCol the number of the column for the start of the snake
	* @param endRow the number of the row for the end of the snake
	* @param endCol the number of the column for the end of the snake
	* @return If the numbers are ok with all the conditions
	*/
	public boolean checkSnakes(int initRow, int initCol, int endRow, int endCol) {
		if(initRow > endRow || initRow == endRow) {
			return true;
		}else {
			if(initRow > rows || endRow > rows || initCol > cols || endCol > cols) {
				return true;
			}else {
				if((initRow == rows-1 && initCol == 0) || (initRow == first.getLast().getRow() && initCol == first.getLast().getCol()) || (endRow == rows-1 && endCol == 0) || (endRow == first.getLast().getRow() && endCol == first.getLast().getCol())) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	
	/**
	* Splits up the String with the players's symbols and adds these symbols to the Player linked list
	*
	* @param playersString Its the String with all the symbols of the players
	*/
	public void setPlayers(String playersString) {
		if(playersString.length() == 1) {
			if(playersSymbol == null) {
				playersSymbol = new Player(playersString);
			}else {
				playersSymbol.add(new Player(playersString));
			}
		}
		else {
			String aux = String.valueOf(playersString.charAt(0));
			setPlayers(aux);
			setPlayers(playersString.substring(1, playersString.length()));
		}
	}
	
	/**
	*Adds all the players to the first Square for the begining of the game
	*/
	public void setPlayersOnBoard(){
		first.addInitialPlayers(playersSymbol);
	}
	
	/**
	* Prints the an specified row of the board with the number of the squares, ladders and snakes (without the players)
	* 
	* @param requestedRow Its the number of the row that its going to be shown
	*/
	public void showBoard(int requestedRow) {
		Square singleRow = null;
		if(first.getRow()==requestedRow) {
			singleRow = first.cloneO();
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			singleRow = sortList(singleRow);
			System.out.println(printRow(singleRow));
		}
		else {
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			singleRow = sortList(singleRow);
			System.out.println(printRow(singleRow));
		}
	}
	
	/**
	* Prints an specified row with the players placed on them, the snakes and the ladders
	*
	* @param requestedRow Its the number of the row that its going to be shown
	*/
	public void showActualBoard(int requestedRow) {
		Square singleRow = null;
		if(first.getRow()==requestedRow) {
			singleRow = first.cloneO();
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			singleRow = sortList(singleRow);
			System.out.println(printActual(singleRow));
		}
		else {
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			singleRow = sortList(singleRow);
			System.out.println(printActual(singleRow));
		}
	}
	
	/**
	* It calls the method to a row with every row, shows the state with the numbers of the squares, snakes and ladders
	*
	* @param rows Its the number of the row to show, it starts ALWAYS with 0
	*/
	public void recursiveRows(int rows) {
		if(rows==this.rows-1) {
			showBoard(rows);
		}
		else {
			showBoard(rows);
			recursiveRows(rows+1);
		}
	}
	
	
	/**
	* It calls the method to a row with every row, shows the state with the Players of the squares, snakes and ladders
	*
	* @param rows Its the number of the row to show, it starts ALWAYS with 0
	*/
	public void showActual(int rows) {
		if(rows==this.rows-1) {
			showActualBoard(rows);
		}
		else {
			showActualBoard(rows);
			showActual(rows+1);
		}
	}
	
	/**
	* It generates a String of the linked list of the numbers and data of the squares
	*
	* @param rows Its the number of the row to show, it starts ALWAYS with 0
	* @return The generated String
	*/
	public String printRow(Square a) {
		if(a.size()==1) {
			return "[" + a.getSquareString() + "]"; 
		}
		else {
			String x = "[" + a.getLast().getSquareString() + "]";
			a.removeLast();
			return printRow(a) + x;
		}
	}
	
	/**
	* It generates the String with info of the squares (without the numbers)
	*
	* @param rows Its the number of the row to show, it starts ALWAYS with 0
	* @return The generated String
	*/
	public String printActual(Square a) {
		if(a.size()==1) {
			return "[" + a.getSquareCurrent() + "]"; 
		}
		else {
			String x = "[" + a.getLast().getSquareCurrent() + "]";
			a.removeLast();
			return printActual(a) + x;
		}
	}
	
	/**
	* Creates the whole board, making the next either the one on the right side, or above or on the left, the
	* first one its the bottom row first column, where the players begin to play normally
	*
	* @param prev The square that will get the next square
	*/
	public void createNext(Square prev) {
		if(direction) {
			if(prev.getCol()+1<cols) {
				Square next = new Square(prev.getSquareNum()+1,prev.getRow(),prev.getCol()+1);
				prev.setNext(next);
				createNext(next);
			}
			else if((prev.getRow()==0 && prev.getCol()==0 && !direction) || (prev.getRow()==0 && prev.getCol()==cols-1 && direction)) {
				//termina
			}
			else {
				Square next = new Square(prev.getSquareNum()+1,prev.getRow()-1,prev.getCol());
				prev.setNext(next);
				direction = false;
				createNext(next);
			}
		}
		else {
			if(prev.getCol()-1>=0) {
				Square next = new Square(prev.getSquareNum()+1,prev.getRow(),prev.getCol()-1);
				prev.setNext(next);
				createNext(next);
			}
			else if((prev.getRow()==0 && prev.getCol()==0 && !direction) || (prev.getRow()==0 && prev.getCol()==cols-1 && direction)) {
				//termina
			}
			else {
				Square next = new Square(prev.getSquareNum()+1,prev.getRow()-1,prev.getCol());
				prev.setNext(next);
				direction = true;
				createNext(next);
			}
		}
	}
	

	public Square getFirst() {
		return first;
	}

	public void setFirst(Square first) {
		this.first = first;
	}



	public int getRows() {
		return rows;
	}



	public void setRows(int rows) {
		this.rows = rows;
	}



	public int getCols() {
		return cols;
	}



	public void setCols(int cols) {
		this.cols = cols;
	}
	
	/**
	* Check if the linked list with an specified row has its squares in order(based on their columns) 
	*
	* @param a Its the linked list with squares of an specified row
	* @return The linked list itself or calls another method to sort it
	*/
	public Square sortList(Square a) {
		if(a.getCol()>a.getNext().getCol()) {
			Square newList = null;
			return exchange(a,newList);
		}
		else {
			return a;
		}
	}
	
	/**
	* We need to know that the method that gets all the squares in an specified row adds them in the
	* order that it founds them, so it will be unsorted sometimes, to sort and to avoid making a pretty
	* complicated method it just inverts the order of the list, making the last one the first, the one before the
	* last one the second one, etc. 
	*
	* @param old Its the old linked list with the unsorted list
	* @param newList the new Linked List of Squares with the new Order
	*/
	public Square exchange(Square old,Square newList) {
		if(newList == null) {
			newList = old.getLast();
			old.removeLast();
			return exchange(old,newList);
		}else {
			if(old.getNext() == null) {
				newList.add(old);
				return newList;
			}else {
				Square temp = old.getLast();
				newList.add(temp);
				old.removeLast();
				return exchange(old,newList);
			}
		}
		
	}
	
	public int getIndex() {
		return indexPlayerToMove;
	}
	
	public String getPlayerString() {
		if(indexPlayerToMove == 0) {
			return playersSymbol.getSymbol();
		}else {
			return playersSymbol.get(indexPlayerToMove).getSymbol();
		}
	}

	public Player getLastWonPlayer() {
		return lastWonPlayer;
	}

	public void setLastWonPlayer(Player lastWonPlayer) {
		this.lastWonPlayer = lastWonPlayer;
	}
}

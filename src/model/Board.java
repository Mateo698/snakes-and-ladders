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
	
	public Board(int x,int y) {
		playersSymbol = null;
		setRows(x);
		setCols(y);
		direction = true;
		createBoard();
		ladderCounter = 1;
		snakesCounter = "A";
		indexPlayerToMove = 0;
	}

	public void show() {
		Square aux = first;
		while(aux != null) {
			System.out.println(aux.getSquareString());
			aux = aux.getNext();
		}
	}
	
	public Player startMovement(int moves) {
		
		if(indexPlayerToMove == 0) {
			Player winPlayer = first.movePlayer(playersSymbol.clone(), moves);			
			if(winPlayer != null) {
				indexPlayerToMove = 0;
				return winPlayer;
			}else {
				indexPlayerToMove++;
				return null;
			}
		}else {
			Player aux = playersSymbol.get(indexPlayerToMove).clone();
			System.out.println("a mover" + aux.getSymbol());
			Player winPlayer = first.movePlayer(aux, moves);
			if(winPlayer != null) {
				indexPlayerToMove = 0;
				return winPlayer;
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
	
	public void createBoard() {
		first = new Square(1,rows-1,0);
		createNext(first);
	}

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
	
	public void setPlayersOnBoard(){
		first.addInitialPlayers(playersSymbol);
	}
	
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
	
	public void recursiveRows(int rows) {
		if(rows==this.rows-1) {
			showBoard(rows);
		}
		else {
			showBoard(rows);
			recursiveRows(rows+1);
		}
	}
	
	public void showActual(int rows) {
		if(rows==this.rows-1) {
			showActualBoard(rows);
		}
		else {
			showActualBoard(rows);
			showActual(rows+1);
		}
	}
	
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
	
	public Square sortList(Square a) {
		if(a.getCol()>a.getNext().getCol()) {
			Square newList = null;
			return exchange(a,newList);
		}
		else {
			return a;
		}
	}
	
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

}

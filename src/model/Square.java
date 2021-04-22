package model;

import java.util.ArrayList;

public class Square implements Comparable<Square>{
	private int row;
	private int col;
	private Square next;
	private Square initSnake;
	private Square endSnake;
	private Square initLadder;
	private Square endLadder;
	private char snakeLetter;
	private int ladderNum;
	private int squareNum;
	private Player players;
	
	public Square(int num,int row, int col) {
		this.row = row;
		this.col = col;
		this.setSquareNum(num);
		next = null;
		setInitSnake(null);
		setEndSnake(null);
		setInitLadder(null);
		setEndLadder(null);
		snakeLetter = 0;
		ladderNum = 0;
		players = null;
	}
	
	//ADD return it can be Boolean or the player that won / null
	public void movePlayer(Player movingP,int amount) {
		if(amount == 0) {
			
		}
		else {
			if(isPlayerOn(players,movingP.getSymbol(),0) >= 0) {
				int index = isPlayerOn(players,movingP.getSymbol(),0);
				Player playerToMove = null;
				if(index == 0) {
					if(players.getNextPlayer() == null) {
						playerToMove = players;
						playerToMove.setNextPlayer(null);
						players = null;
						next.addPlayer(playerToMove);
					}
					else {
						Player aux = players.getNextPlayer();
						playerToMove = players;
						playerToMove.setNextPlayer(null);
						players = aux;
					}
				}
				else {
					playerToMove = players.get(index);
					players.remove(index);
				}
				
				
			}
			else {
				if(next == null) {
					System.out.println("No se encontro al jugador");
					
				}
				else {
					next.movePlayer(movingP, amount);
				}
			}
		}
	}
	
	public int isPlayerOn(Player current,String symbol,int index) {
		if(current == null) {//caso vacio
			return -1;
		}
		else {
			if(players.getSymbol().equals(symbol)) {
				return index;
			}
			else {
				return isPlayerOn(current.getNextPlayer(),symbol,index++);
			}
		}
	}
	
	public Square getSquaresInARow(Square a,int selectedRow){
		if(next != null) {	
			if(next.getRow() == selectedRow) {
				if(a==null) {
					a = new Square(next.getSquareNum(),next.getRow(),next.getCol());
				}else {
					Square aux = new Square(next.getSquareNum(),next.getRow(),next.getCol());
					a.add(aux);	
				}
				return next.getSquaresInARow(a, selectedRow);
			}
			else {
				return next.getSquaresInARow(a, selectedRow);
			}	
		}
		else {
			return a;
		}
	}
	

	public Square getInitSnake() {
		return initSnake;
	}
	
	public String getCords() {
		return row + " " + col; 
	}

	public void setInitSnake(Square initSnake) {
		this.initSnake = initSnake;
	}

	public Square getEndSnake() {
		return endSnake;
	}

	public void setEndSnake(Square endSnake) {
		this.endSnake = endSnake;
	}

	public Square getEndLadder() {
		return endLadder;
	}

	public void setEndLadder(Square endLadder) {
		this.endLadder = endLadder;
	}

	public Square getInitLadder() {
		return initLadder;
	}

	public void setInitLadder(Square initLadder) {
		this.initLadder = initLadder;
	}

	public char getSnakeLetter() {
		return snakeLetter;
	}

	public void setSnakeLetter(char snakeLetter) {
		this.snakeLetter = snakeLetter;
	}

	public int getLadderLetter() {
		return ladderNum;
	}

	public void setLadderLetter(int ladderLetter) {
		this.ladderNum = ladderLetter;
	}



	public int getRow() {
		return row;
	}



	public void setRow(int row) {
		this.row = row;
	}



	public int getCol() {
		return col;
	}



	public void setCol(int col) {
		this.col = col;
	}



	public Square getNext() {
		return next;
	}



	public void setNext(Square next) {
		this.next = next;
	}

	@Override
	public int compareTo(Square o) {
		return row-o.getRow();
	}

	public int getSquareNum() {
		return squareNum;
	}

	public void setSquareNum(int squareNum) {
		this.squareNum = squareNum;
	}
	
	public void addPlayer(Player newPlayer) {
		if(players == null) {
			players = newPlayer;
		}else {
			players.add(newPlayer);
		}
	}
	
	public int size() {
		int size = 1;
		if(next != null) {
			return next.size(size);
		}
		else {
			return size;
		}
	}
	
	private int size(int size) {
		size++;
		if(next != null) {
			return next.size(size);
		}
		else {
			return size;
		}
	}
	
	public Square get(int index) {
		if(index == 1) {
			return next;
		}
		else {
			if(next != null) {
				return next.get(index--);
			}
			else {
				return null;
			}
		}
	}
	
	public Square getLast() {
		if(next.getNext() == null) {
			return next;
		}else {
			return next.getLast();
		}
	}
	
	public void removeLast() {
		if(next.getNext() == null) {
			next = null;
		}else {
			next.removeLast();
		}
	}
	
	public void add(Square a) {
		if(next == null) {
			next = a;
		}
		else {
			next.add(a);
		}
	}
	
}

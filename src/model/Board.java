package model;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
	private Square first;
	private int rows;
	private int cols;
	private boolean direction;
	private Player playersSymbol;
	
	public Board(int x,int y) {
		playersSymbol = null;
		setRows(x);
		setCols(y);
		direction = true;
		createBoard();
	}
	
	public void createBoard() {
		first = new Square(1,rows-1,0);
		createNext(first);
	}
	
	public void createLadders(int ladders) {
		
	}
	
	public void createSnakes(int snakes) {
		
	}
	
	public void show() {
		Square b = null;
		Square a = first.getSquaresInARow(b, 1);
		b = a;
		while(b!= null) {
			System.out.println(b.getSquareNum());
			b = b.getNext();
		}
		System.out.println("xd");
	}
	
	public void setPlayers(String playersString) {		
		if(playersString.length() == 1) {
			if(playersSymbol == null) {
				playersSymbol = new Player(playersString);
			}else {
				playersSymbol.setNextPlayer(new Player(playersString));
			}
		}
		else {
			String aux = String.valueOf(playersString.charAt(0));
			setPlayers(aux);
			setPlayers(playersString.substring(1, playersString.length()));
		}
	}
	
	
	public void showBoard(int requestedRow) {
		//ArrayList<Square> singleRow = new ArrayList<Square>();
		Square singleRow = null;
		if(first.getRow()==requestedRow) {
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			
			//System.out.println(printRow(singleRow));
			//show(singleRow);
			
		}
		else {
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			
			//System.out.println(printRow(singleRow));
			//show(singleRow);
			
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
	
	public String printRow(Square a) {
		if(a.size()==1) {
			return "[ " + a.getSquareNum() + " ]"; 
		}
		else {
			String x = "[ " + a.getLast().getSquareNum() + " ]";
			a.removeLast();
			return printRow(a) + x;
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
	
	public void sortRow(ArrayList<Square> a) {
		Collections.sort(a);
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
	
}

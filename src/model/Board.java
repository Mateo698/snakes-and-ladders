package model;

import java.util.ArrayList;

public class Board {
	private Square first;
	private int rows;
	private int cols;
	private boolean direction;
	
	public Board(int x,int y) {
		setRows(x);
		setCols(y);
		direction = true;
	}
	
	public void createBoard() {
		first = new Square(rows,0);
		createNext(first);
	}
	
	public void showBoard(int requestedRow) {
		ArrayList<Square> singleRow = new ArrayList<Square>();
		if(first.getRow()==requestedRow) {
			singleRow.add(first);
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
		}
		else {
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
		}
	}
	
	public void createNext(Square prev) {
		if(direction) {
			if(prev.getCol()+1<cols) {
				Square next = new Square(prev.getRow(),prev.getCol()+1);
				prev.setNext(next);
				createNext(next);
			}
			else if(prev.getRow()-1<0 && prev.getCol()+1>cols) {
				//termina
			}
			else {
				Square next = new Square(prev.getRow()-1,prev.getCol());
				prev.setNext(next);
				direction = false;
				createNext(next);
			}
		}
		else {
			if(prev.getCol()-1>0) {
				Square next = new Square(prev.getRow(),prev.getCol()-1);
				prev.setNext(next);
				createNext(next);
			}
			else if(prev.getRow()-1<0 && prev.getCol()-1<0) {
				//termina
			}
			else {
				Square next = new Square(prev.getRow()-1,prev.getCol());
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
	
}

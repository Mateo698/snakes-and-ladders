package model;

import java.util.ArrayList;
import java.util.Collections;

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
		first = new Square(1,rows-1,0);
		createNext(first);
	}
	
	public void showBoards() {
		Square aux = first;
		while(aux != null) {
			System.out.println(aux.getSquareNum());
			aux = aux.getNext();
		}
	}
	
	public void showBoard(int requestedRow) {
		ArrayList<Square> singleRow = new ArrayList<Square>();
		if(first.getRow()==requestedRow) {
			singleRow.add(first);
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			Collections.sort(singleRow,new Compare());
			System.out.println(printRow(singleRow));
			
		}
		else {
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			Collections.sort(singleRow,new Compare());
			System.out.println(printRow(singleRow));
			
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
	
	public String printRow(ArrayList<Square> a) {
		if(a.size()==1) {
			return "[ " + a.get(0).getSquareNum() + " ]"; 
		}
		else {
			String x = "[ " + a.get(a.size()-1).getSquareNum() + " ]";
			a.remove(a.size()-1);
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

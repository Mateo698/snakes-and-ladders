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
		first = new Square(rows-1,0);
		createNext(first);
	}
	
	public void showBoards() {
		Square aux = first;
		while(aux != null) {
			System.out.println(aux.getCords());
			aux = aux.getNext();
		}
	}
	
	public void showBoard(int requestedRow) {
		ArrayList<Square> singleRow = new ArrayList<Square>();
		if(first.getRow()==requestedRow) {
			singleRow.add(first);
			singleRow = first.getSquaresInARow(singleRow, requestedRow);
			Collections.sort(singleRow);
			for(int i=0;i<singleRow.size();i++) {
				System.out.println(singleRow.get(i).getCords());
			}
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
				System.out.println(next.getCords());
				System.out.println(direction);
				createNext(next);
			}
			else if((prev.getRow()==0 && prev.getCol()==0 && !direction) || (prev.getRow()==0 && prev.getCol()==cols-1 && direction)) {
				//termina
			}
			else {
				Square next = new Square(prev.getRow()-1,prev.getCol());
				prev.setNext(next);
				System.out.println(next.getCords());
				
				direction = false;
				System.out.println(direction);
				
				createNext(next);
			}
		}
		else {
			if(prev.getCol()-1>=0) {
				Square next = new Square(prev.getRow(),prev.getCol()-1);
				prev.setNext(next);
				System.out.println(next.getCords());
				System.out.println(direction);
				createNext(next);
			}
			else if((prev.getRow()==0 && prev.getCol()==0 && !direction) || (prev.getRow()==0 && prev.getCol()==cols-1 && direction)) {
				//termina
			}
			else {
				Square next = new Square(prev.getRow()-1,prev.getCol());
				prev.setNext(next);
				System.out.println(next.getCords());
				
				direction = true;
				System.out.println(direction);
				
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

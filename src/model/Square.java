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
	
	public Square(int row, int col) {
		this.row = row;
		this.col = col;
		next = null;
		setInitSnake(null);
		setEndSnake(null);
		setInitLadder(null);
		setEndLadder(null);
		snakeLetter = 0;
		ladderNum = 0;
	}
	
	public ArrayList<Square> getSquaresInARow(ArrayList<Square> a,int selectedRow){
		if(next != null) {
			if(next.getRow() == selectedRow) {
				a.add(next);
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
	
	
}

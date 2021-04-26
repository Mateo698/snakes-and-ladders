package model;

import java.util.TimerTask;

public class Auto extends TimerTask{
	
	private Board board;
	
	public Auto(Board b) {
		board = b;
	}

	@Override
	public void run() {
		board.showActual(0);
	}
	
}

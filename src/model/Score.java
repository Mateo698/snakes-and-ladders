package model;

public class Score {
	private String nickname;
	private String symbol;
	private int score;
	
	public Score(String n,String s,int sc) {
		nickname = n;
		symbol = s;
		score = sc;
	}
	
	public String getScore() {
		return nickname + " " + symbol + " " + score;
	}
}

package model;

public class Score {
	private String nickname;
	private String symbol;
	private int score;
	private Score lowerScore;
	private Score higherScore;
	
	public Score(String n,String s,int sc) {
		setLowerScore(null);
		setHigherScore(null);
		setNickname(n);
		setSymbol(s);
		setScore(sc);
	}
	
	public String getString() {
		return nickname + " " + symbol + " " + score;
	}

	public Score getLowerScore() {
		return lowerScore;
	}

	public void setLowerScore(Score lowerScore) {
		this.lowerScore = lowerScore;
	}

	public Score getHigherScore() {
		return higherScore;
	}

	public void setHigherScore(Score higherScore) {
		this.higherScore = higherScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}

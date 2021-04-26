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
	
	public String getList() {
		if(lowerScore != null && higherScore != null) {
			String left = lowerScore.getList();
			String right = higherScore.getList();
			return right + "\n" + getString() + "\n" + left;
		}else if(lowerScore != null && higherScore == null) {
			String left = lowerScore.getList() ;
			return getString() + "\n" + left;
		}else if(lowerScore == null && higherScore != null) {
			String right = higherScore.getList();
			return right + "\n" + getString();
		}else {
			return getString();
		}
	}
	
	public void add(Score newScore) {
		if(newScore.getScore()>score) {
			if(higherScore == null) {
				higherScore = newScore;
			}else {
				higherScore.add(newScore);
			}
		}else {
			if(lowerScore == null) {
				lowerScore = newScore;
			}else {
				lowerScore.add(newScore);
			}
		}
	}
	
	public boolean hasSons() {
		if(lowerScore != null || higherScore != null) {
			return true;
		}else {
			return false;
		}
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

package model;

public class Player {
	private String symbol;
	private Player nextPlayer;
	
	public Player(String s) {
		setSymbol(s);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	
	public int size() {
		int amount = 1;
		if(nextPlayer == null) {
			return amount;
		}else {
			return nextPlayer.size(amount);
		} 
	}
	
	public int size(int amount) {
		amount++;
		if(nextPlayer == null) {
			return amount;
		}
		else {
			return nextPlayer.size(amount);
		}
	}
	
	public void remove(int index) {
		if(index == 1) {
			if(nextPlayer.getNextPlayer() == null) {
				nextPlayer = null;
			}
			else {
				Player aux = nextPlayer.getNextPlayer();
				nextPlayer = aux;
			}
		}else {
			index--;
			nextPlayer.remove(index);
		}
	}
	
	public Player get(int index) {
		if(index == 1) {
			return nextPlayer;
		}else {
			if(nextPlayer == null) {
				return null;
			}else {
				index--;
				return nextPlayer.get(index);
			}
		}
	}
	
	public void add(Player newPlayer) {
		if(nextPlayer == null) {
			nextPlayer = newPlayer;
		}
		else {
			nextPlayer.add(newPlayer);
		}
	}
	
	public String getString() {
		String text = symbol;
		if(nextPlayer != null) {
			return nextPlayer.getString(text);
		}else {
			return symbol;
		}
	}
	
	public String getString(String prev) {
		prev += symbol;
		if(nextPlayer != null) {
			return nextPlayer.getString(prev);
		}else {
			return prev;
		}
	}
	
	public Player clone() {
		Player o = new Player(symbol);
		return o;
	}
}

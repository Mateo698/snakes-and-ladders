package model;

public class Player {
	private String symbol;
	private Player nextPlayer;
	private int rows;
	private int cols;
	private int snakes;
	private int ladders;
	private String players;
	private int movements;
	
	public Player(String s) {
		setSymbol(s);
		setMovements(0);
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
		o.setMovements(movements);
		return o;
	}

	public int getMovements() {
		return movements;
	}

	public void setMovements(int movements) {
		this.movements = movements;
	}
	
	public void addMovement(int mov) {
		movements += mov;
	}
	
	public Player search(String symbol) {
		if(nextPlayer == null) {
			return null;
		}else {
			if(nextPlayer.getSymbol() == symbol) {
				return nextPlayer;
			}else {
				return nextPlayer.search(symbol);
			}
		}
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

	/**
	 * @return the ladders
	 */
	public int getLadders() {
		return ladders;
	}

	/**
	 * @param ladders the ladders to set
	 */
	public void setLadders(int ladders) {
		this.ladders = ladders;
	}

	/**
	 * @return the snakes
	 */
	public int getSnakes() {
		return snakes;
	}

	/**
	 * @param snakes the snakes to set
	 */
	public void setSnakes(int snakes) {
		this.snakes = snakes;
	}

	/**
	 * @return the players
	 */
	public String getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(String players) {
		this.players = players;
	}
}

package model;

public class Square implements Comparable<Square>{
	private int row;
	private int col;
	private Square next;
	private Square initSnake; //El square a donde lleva la serpiente
	private Square endSnake; //El square que tiene una serpiente hacia este square
	private Square initLadder; //El square a donde lleva la escalera
	private Square endLadder; //El square que tiene una escalera hacia este square
	private String snakeLetter;
	private int ladderNum;
	private int squareNum;
	private Player players;
	
	public Square(int num,int row, int col) {
		this.row = row;
		this.col = col;
		this.setSquareNum(num);
		next = null;
		setInitSnake(null);
		setEndSnake(null);
		setInitLadder(null);
		setEndLadder(null);
		snakeLetter = "";
		ladderNum = 0;
		players = null;
	}
	
	/**
	* Moves the selected player square by square until the number of moves its equal to 0, if the next square
	* its null and it still has movements then the method returns the player that won 
	*
	* @param movingP it's the player that its going to move
	* @param amount the number of movements in the board
	* @return The player that won if its the case, otherwise returns null
	*/
	public Player movePlayer(Player movingP,int amount) {
		if(amount == 0) {
			
			if(initLadder != null) {
				isPlayerOn(players, movingP.getSymbol(), 0);
				
				if(players.getNextPlayer() == null) {
					
					initLadder.addPlayer(movingP);
					players = null;
				}
				return null;
			}else if(initSnake != null) {
				isPlayerOn(players, movingP.getSymbol(), 0);
				
				players = null;
				initSnake.addPlayer(movingP);
				return null;
			}else {
				return null;
			}
		}
		else {
			if(isPlayerOn(players,movingP.getSymbol(),0) >= 0) {
				int index = isPlayerOn(players,movingP.getSymbol(),0);
				
				Player playerToMove = null;
				if(index == 0) {
					if(players.getNextPlayer() == null) {
						playerToMove = players;
						players = null;
					}
					else {
						playerToMove = players.clone();
						players = players.getNextPlayer();
					}
				}
				else {
					playerToMove = players.get(index);
					players.remove(index);
				}
				
				
				if(next == null) {
					return playerToMove;
				}
				else {
					
					next.addPlayer(playerToMove);
					amount--;
					return next.movePlayer(movingP, amount);
				}
				
			}
			else {	
					if(next == null) {
						return null;
					}else {
						return next.movePlayer(movingP, amount);
					}
			}
		}
	}
	
	/**
	* Check if the given player its located in this square 
	*
	* @param current	The linked list of players located in this square
	* @param symbol		The symbol of player that its goin to search
	* @param index 		Its the counter to return the index where the player is located
	* @return			The index where the player was found if its the case, otherwise returns -1
	*/
	public int isPlayerOn(Player current,String symbol,int index) {
		if(current == null) {
			return -1;
		}
		else {
			if(current.getSymbol().equals(symbol)) {
				//System.out.println("Returns this index " + index);
				return index;
			}
			else {
				index++;
				return isPlayerOn(current.getNextPlayer(),symbol,index);
			}
		}
	}
	
	
	/**
	* Get all the Squares in the linked list that are located in the same row (given by Board) 
	*
	* @param a 				Its the linked list of Squares where the method will add the requested Squares
	* @param selectedRow	Its the number of the row that it has to search
	* @return 				The linked list of Squares with all the squares in the specified row
	*/
	public Square getSquaresInARow(Square a,int selectedRow){
		if(next != null) {	
			if(next.getRow() == selectedRow) {
				if(a==null) {
					a = next.cloneO();
				}else {
					Square aux = next.cloneO();
					a.add(aux);	
				}
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

	public String getSnakeLetter() {
		return snakeLetter;
	}

	public void setSnakeLetter(String snakeLetter) {
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
	
	/**
	* Generates a String with the the number of the square and its attributes (snakes or ladders) 
	*
	* @return The generated String
	*/
	public String getSquareString() {
		String x = "";
		if(ladderNum != 0) {
			if(squareNum >= 10) {
				x = squareNum + "" + ladderNum;	
			}else {
				x = " " + squareNum + "" + ladderNum;	
			}
			
		}else if(snakeLetter != "") {
			if(squareNum>=10) {
				x = squareNum + snakeLetter;
			}else {
				x = " " + squareNum + snakeLetter;
			}
			
		}else {
			if(squareNum>=10) {
				x = "" + squareNum + " ";
			}else {
				x = " " + squareNum + " ";
			}
			
		}
		
		return x;
	}
	
	/**
	* Generates a String with all the players in the square, if its empty returns a String with 3 spaces
	* to keep the even of the graphic
	* 
	* @return The generated String
	*/
	public String getSquareCurrent() {
		String x = "";
		if(ladderNum != 0) {
			if(players != null) {
				x = players.getString();
				if(x.length() == 1) {
					x = ladderNum + x + " ";
				}else if(x.length() == 2) {
					x = ladderNum + x ;
				}
			}else {
				x = ladderNum + "  ";
			}
		}else if(snakeLetter != ""){
			if(players != null) {
				x = players.getString();
				if(x.length() == 1) {
					x = snakeLetter + x + " ";
				}else if(x.length() == 2) {
					x = snakeLetter + x ;
				}
			}else {
				x = snakeLetter + "  ";
			}
		}else {
			if(players != null) {
				x = players.getString();
				if(x.length() == 1) {
					x = x + "  ";
				}else if(x.length() == 2) {
					x = x + " " ;
				}
			}else {
				x = "   ";
			}
		}
		return x;
	}
	
	public int getSquareNum() {
		return squareNum;
	}

	public void setSquareNum(int squareNum) {
		this.squareNum = squareNum;
	}
	
	public void addPlayer(Player newPlayer) {
		if(players == null) {
			players = newPlayer;
		}else {
			players.add(newPlayer);
		}
	}
	
	public int size() {
		int size = 1;
		if(next != null) {
			return next.size(size);
		}
		else {
			return size;
		}
	}
	
	private int size(int size) {
		size++;
		if(next != null) {
			return next.size(size);
		}
		else {
			return size;
		}
	}
	
	public Square get(int index) {
		if(index == 1) {
			return next;
		}
		else {
			if(next != null) {
				return next.get(index--);
			}
			else {
				return null;
			}
		}
	}
	
	public Square getLast() {
		if(next.getNext() == null) {
			return next;
		}
		else {
			return next.getLast();
		}
	}
	
	public void removeLast() {
		if(next.getNext() == null) {
			next = null;
		}else {
			next.removeLast();
		}
	}
	
	public void add(Square a) {
		if(next == null) {
			next = a;
		}
		else {
			next.add(a);
		}
	}
	
	public void addInitialPlayers(Player initPlayers) {
		players = initPlayers;
	}
	
	public Square search(int row,int col) {
		if(next.getRow() == row && next.getCol() == col) {
			return next;
		}else {
			return next.search(row, col);
		}
	}
	
	public Square cloneO() {
		Square a = new Square(squareNum,row,col);
		a.setEndLadder(endLadder);
		a.setEndSnake(endSnake);
		a.setInitLadder(initLadder);
		a.setInitSnake(initSnake);
		a.setLadderLetter(ladderNum);
		a.setSnakeLetter(snakeLetter);
		a.setPlayers(players);
		return a;
	}
	
	private void setPlayers(Player p) {
		players = p;
		
	}

	public String getSL(){
		String x = "";
		if(initLadder != null) {
			x += initLadder.getSquareString();
		}
		if(endLadder != null) {
			x += endLadder.getSquareString();
		}
		return x;
	}

	public Player getPlayers() {
		return players;
		
	}
}

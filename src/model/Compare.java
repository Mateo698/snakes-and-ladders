package model;

import java.util.Comparator;

public class Compare implements Comparator<Square>{

	@Override
	public int compare(Square o1, Square o2) {
		return o1.getCol()-o2.getCol();	
	}

}

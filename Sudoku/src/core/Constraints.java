package core;

import java.util.ArrayList;

public class Constraints {
	private Field field = null;
	
	public Constraints(Field field){
		this.field = field;
	}
	
	/*
	 * CONSTRAINTS
	 */
	public boolean isConflictingWithSquare(int number, int row, int col){
		Field square = this.field.getSquareAsField(row, col);
		for(int rowIndex = 0; rowIndex < square.getRowCount(); rowIndex++){
			for (ArrayList<Integer> list : square.getRow(rowIndex)){
				if(list.size() == 1 && list.get(0) == number){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isConflictingWithRow(int number, int row, int col){
		for (ArrayList<Integer> list : this.field.getRow(row)){
			if(list.size() == 1 && list.get(0) == number){
				return true;
			}
		}
		return false;
	}
	
	public boolean isConflictingWithColumn(int number, int row, int col){
		for (ArrayList<Integer> list : this.field.getColumn(col)){
			if(list.size() == 1 && list.get(0) == number){
				return true;
			}
		}
		return false;
	}
	
	public boolean isConflicting(int number, int row, int col){
		if(this.isConflictingWithRow(number, row, col)){
			return true;
		}else if(this.isConflictingWithColumn(number, row, col)){
			return true;
		}else if(this.isConflictingWithSquare(number, row, col)){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * Finishing criteria
	 */
	
	public boolean isSolved(){
		for(int i = 0; i <= this.field.getRowCount(); i++){
			for(ArrayList<Integer> list : this.field.getRow(i)){
				if(list.size() > 1){
					return false;
				}
			}
		}
		return true;
	}

}

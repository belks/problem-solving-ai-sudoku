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
	public boolean isConflictingWithSquare(String number, int row, int col){
		Field square = this.field.getSquareAsField(row, col);
		
		for(int rowIndex = 0; rowIndex < square.getRowCount(); rowIndex++){
			for (ArrayList<String> list : square.getRow(rowIndex)){
				if(list.size() == 1 && list.get(0).equals(number)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isConflictingWithRow(String number, int row, int col){
		for (ArrayList<String> list : this.field.getRow(row)){
			if(list.size() == 1 && list.get(0).equals(number)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isConflictingWithColumn(String number, int row, int col){
		for (ArrayList<String> list : this.field.getColumn(col)){
			if(list.size() == 1 && list.get(0).equals(number)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isConflicting(String number, int row, int col){
		if(this.isConflictingWithRow(number, row, col)){
			//System.out.println("a "+row+","+col+","+number);
			return true;
		}else if(this.isConflictingWithColumn(number, row, col)){
			//System.out.println("b "+row+","+col+","+number);
			return true;
		}else if(this.isConflictingWithSquare(number, row, col)){
			//System.out.println("c "+row+","+col+","+number);
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean isLeftOver(String number, int row, int col){
		if(this.isLeftOverInRow(number, row, col) && !this.isConflicting(number, row, col)){
			return true;
		}else if(this.isLeftOverInColumn(number, row, col) && !this.isConflicting(number, row, col)){
			return true;
		}else if(this.isLeftOverInSquare(number, row, col) && !this.isConflicting(number, row, col)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isLeftOverInRow(String number, int row, int col){
		int colIndex = 0;
		for(ArrayList<String> list : this.field.getRow(row)){
			if(colIndex != col){
				for(String val : list){
					if(val.equals(number)){
						return false;
					}
				}
			}
			colIndex++;
		}
		return true;
	}
	
	public boolean isLeftOverInColumn(String number, int row, int col){
		int rowIndex = 0;
		for(ArrayList<String> list : this.field.getColumn(col)){
			if(rowIndex != row){
				for(String val : list){
					if(val.equals(number)){
						return false;
					}
				}
			}
			rowIndex++;
		}
		return true;
	}
	
	public boolean isLeftOverInSquare(String number, int row, int col){
		Field square = this.field.getSquareAsField(row, col);
		for(int rowIndex = 0; rowIndex < square.getRowCount(); rowIndex++){
			int colIndex = 0;
			for (ArrayList<String> list : square.getRow(rowIndex)){
				if( !(rowIndex == row && colIndex == col)){
					for(String val : list){
						if(val.endsWith(number)){
							return false;
						}
					}
				}
				colIndex++;
			}
		}
		return true;
	}
	
	
	public boolean isTheRightCoice(String number, int row, int col){	
		if(this.isTheRightCoiceForRow(number, row, col) && !this.isConflicting(number, row, col)){
			return true;
		}else if(this.isTheRightCoiceForColumn(number, row, col) && !this.isConflicting(number, row, col)){
			return true;
		}else if(this.isTheRightCoiceForSquare(number, row, col) && !this.isConflicting(number, row, col)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isTheRightCoiceForRow(String number, int row, int col){
		ArrayList<String>[] lists = this.field.getRow(row);
		int numOfListWith2Elems = 0;
		for(ArrayList<String> l : lists){
			if(l.size() > 2){
				return false;
			}else if(l.size() == 2){
				numOfListWith2Elems++;
			}
		}
		if(numOfListWith2Elems != 2){
			return false;
		}
		return true;
	}	
	
	public boolean isTheRightCoiceForColumn(String number, int row, int col){
		ArrayList<String>[] lists = this.field.getColumn(col);
		int numOfListWith2Elems = 0;
		for(ArrayList<String> l : lists){
			if(l.size() > 2){
				return false;
			}else if(l.size() == 2){
				numOfListWith2Elems++;
			}
		}
		if(numOfListWith2Elems != 2){
			return false;
		}
		return true;
	}
	
	public boolean isTheRightCoiceForSquare(String number, int row, int col){
		Field square = this.field.getSquareAsField(row, col);
		int numOfListWith2Elems = 0;

		 
		for(int rowIndex = 0; rowIndex < square.getRowCount(); rowIndex++){
			ArrayList<String>[] lists = this.field.getRow(rowIndex);
			for(ArrayList<String> l : lists){
				if(l.size() > 2){
					return false;
				}else if(l.size() == 2){
					numOfListWith2Elems++;
				}
			}
		}	
		
		if(numOfListWith2Elems != 2){
			return false;
		}
		return true;
	}
	
	
	/*
	 * Finishing criteria
	 */
	
	public boolean isSolved(){
		for(int i = 0; i < this.field.getRowCount(); i++){
			for(ArrayList<String> list : this.field.getRow(i)){
				if(list.size() > 1){
					return false;
				}
			}
		}
		return true;
	}

	
	public boolean isSolvedWithoutConflicts(){	
		if(!this.isSolved()){
			return false;
		}
		
		for(int row = 0; row < this.field.getRowCount(); row++){
			int col = 0;
			for(ArrayList<String> list : this.field.getRow(row)){
				list.add("xxxxxxxxxxxxxxxxxxxxxx");
				if(this.isConflicting(list.get(0), row, col)){
					System.out.println("Conflict at: "+ row +","+ col + ":" +list.get(0));
					return false;
				}
				list.remove(1);
				col++;
			}
		}
		return true;
	}
	
}

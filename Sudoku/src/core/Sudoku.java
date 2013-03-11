package core;


import java.io.IOException;

public class Sudoku {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Sudoku(args);
	}
	
	
	
	public Sudoku(String[] args){
		SudokuReader in = this.readInput(args);
		Field field = in.getSudokuField();	
		Constraints constraints = new Constraints(field);
		System.out.println("Input:");
		System.out.println(field);
		
		while( constraints.isSolved() == false){
			this.eliminateConflicts(field, constraints);
			System.out.println("Field after conflict elimination:");
			System.out.println(field);	
			
			if(constraints.isSolved()){
				break;
			}
			
			if(this.findLeftOvers(field, constraints)){
				System.out.println("Field after left overs:");
				System.out.println(field);
				continue;
			}else{
				this.findChoices(field, constraints);
				System.out.println("Field after choices done:");
				System.out.println(field);
			}					
		}
				
		System.out.println("Result:");
		System.out.println(field);
		System.out.println("Solved = "+constraints.isSolvedWithoutConflicts());
	}
	
	
	
	private boolean findLeftOvers(Field field, Constraints constraints) {
		boolean found = false;
		for(int row = 0; row < field.getRowCount(); row++){
			for (int col = 0; col < field.getColumCount(); col++){
				for (String value : field.getValues(row, col)){
					if(field.getValues(row, col).size() > 1 && constraints.isLeftOver(value, row, col)){
						field.setValue(row, col, value);
						found = true;
					}
				}					
			}
		}
		return found;
	}



	private void eliminateConflicts(Field field, Constraints constraints){
		boolean repeat = true;
		while(repeat){
			repeat = false;
			for(int row = 0; row < field.getRowCount(); row++){
				for (int col = 0; col < field.getColumCount(); col++){
					for (String value : field.getValues(row, col)){
						if(field.getValues(row, col).size() > 1 && constraints.isConflicting(value, row, col)){
							field.removeValue(row, col, value);
							repeat = true;
						}
					}					
				}
			}
		}
	}
	
	
	private boolean findChoices(Field field, Constraints constraints){
		boolean found = false;
		for(int row = 0; row < field.getRowCount(); row++){
			for (int col = 0; col < field.getColumCount(); col++){
				for (String value : field.getValues(row, col)){
					if(field.getValues(row, col).size() > 1 && constraints.isTheRightCoice(value, row, col)){
						field.setValue(row, col, value);
						found = true;
					}
				}					
			}
		}
		return found;
	}
	
	
	private SudokuReader readInput(String[] args){
		try {
			if(args.length > 0){
				return new SudokuReader(args[0]);
			}else{
				return new SudokuReader(this.getClass().getResourceAsStream("example1.sudoku"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}		
	}

}

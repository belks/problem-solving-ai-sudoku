package core;


import java.io.IOException;

public class Sudoku {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Sudoku sudoku = new Sudoku();
		if(args.length > 0){
			sudoku.solve(new SudokuReader(args[0]));
		}else{
			sudoku.solve(new SudokuReader(Sudoku.class.getResourceAsStream("example1.sudoku")));
		}
	}
	
	
	
	
	
	public Sudoku(){
			
	}
	
	
	public void solve(SudokuReader in){
		Field field = in.getSudokuField();	
		Constraints constraints = new Constraints(field);
		System.out.println("Input:");
		System.out.println(field);
		
		while( constraints.isSolved() == false){
			this.eliminateConflicts(field, constraints);
			System.out.println("Field after conflict elimination:");
			System.out.println(field);	
						
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
	
	
	private boolean findLeftOvers(Field field, Constraints constraints) {
		boolean found = false;
		for(int row = 0; row < field.getRowCount(); row++){
			for (int col = 0; col < field.getColumCount(); col++){
				for (String value : field.getValues(row, col)){
					if(field.getValues(row, col).size() > 1 && constraints.isLeftOver(value, row, col)){
						field.setValue(row, col, value);
						found = true;
						break;
					}
				}					
			}
		}
		return found;
	}
	
	
	private boolean findChoices(Field field, Constraints constraints){
		boolean found = false;
		for(int row = 0; row < field.getRowCount(); row++){
			for (int col = 0; col < field.getColumCount(); col++){
				for (String value : field.getValues(row, col)){
					if(field.getValues(row, col).size() > 1 && constraints.isTheRightCoice(value, row, col)){
						field.setValue(row, col, value);
						found = true;
						break;
					}
				}					
			}
		}
		return found;
	}
	
	
	

}

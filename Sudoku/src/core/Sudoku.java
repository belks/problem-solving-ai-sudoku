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
		System.out.println(field);
		//System.out.println(field.getSquareAsField(5,5));
		//System.out.println(constraints.isConflictingWithSquare(4, 7, 7));
		//System.out.println(constraints.isConflictingWithRow(4, 7, 7));
		//System.out.println(constraints.isConflictingWithColumn(4, 7, 7));
		//System.out.println(constraints.isSolved());
		
		while( constraints.isSolved() == false){
			for(int row = 0; row < field.getRowCount(); row++){
				for (int col = 0; col < field.getColumCount(); col++){
					if (field.getValues(row, col).size() > 1){
						for (int value : field.getValues(row, col)){
							if(constraints.isConflicting(value, row, col)){
								field.removeValue(row, col, value);
							}
						}
					}					
				}
			}
			
			System.out.println(field);
		}
		
		assert constraints.isSolved();
		System.out.println(field);
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

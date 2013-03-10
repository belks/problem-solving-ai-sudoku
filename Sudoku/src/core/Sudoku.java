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
		System.out.println(field);
		field.getSquare(0, 1);
		field.getSquare(1, 3);
		field.getSquare(5, 5);
		field.getSquare(8, 7);
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

package core;

import java.util.ArrayList;

public class Field {
	private ArrayList<Integer>[][] field = null;
	private int subsquareWidth = 0;
	private int subsquareHeight = 0; 
	
	public Field(ArrayList<Integer>[][] field, int subsquareWidth, int subsquareHeight){
		this.field = field;
		this.subsquareWidth = subsquareWidth;
		this.subsquareHeight = subsquareHeight;
	}
	
	/**
	 * Returns the number of columns.
	 * @return
	 */
	public int getColumCount(){
		return field.length;
	}
	
	/**
	 * Returns the number of rows.
	 * @return
	 */
	public int getRowCount(){
		return field[0].length;
	}
	
	/**
	 * Returns the row with the given index starting with 0;
	 * @param rowIndex
	 * @return
	 */
	public ArrayList<Integer>[] getRow(int rowIndex){
		return field[rowIndex];
	}
	
	/**
	 * Returns the column with the given index starting with 0;
	 * @param colIndex
	 * @return
	 */
	public ArrayList<Integer>[] getColumn(int colIndex){
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] col = new ArrayList[this.getRowCount()]; 
		for(int row = 0; row < this.getRowCount(); row++){
			col[row] = field[row][colIndex];
		}
		return col;
	}
	
	
	/**
	 * Returns the square in which the specified element lies in. 
	 */
	public ArrayList<Integer>[][] getSquare(int row, int col){
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[][] square = new ArrayList[this.subsquareWidth][this.subsquareHeight];
		int[] rowLimits = this.getLowHigh(row, 0, this.subsquareWidth-1, this.subsquareWidth);
		int[] colLimits = this.getLowHigh(col, 0, this.subsquareHeight-1, this.subsquareHeight);
		return square;
	}
	
	
	public Field getSquareAsField(int row, int col){
		return new Field(this.getSquare(row, col), 0,0);
	}
	
	private int[] getLowHigh(int index, int low, int high, int increment){
		if(index >= low && index <= high){
			int[] lh = {low, high};
			return lh;
		}else{
			return this.getLowHigh(index, low+increment, high+increment, increment);
		}
	}
	
	
	
	
	/**
	 * Removes a value from one of the fields.
	 * @param col
	 * @param row
	 * @param val
	 */
	public void removeValue(int row, int col, int val){
		ArrayList<Integer> list = this.field[row][col];
		assert list.size() > 1;
		list.remove(val);
		this.field[row][col] = list;
	}
	
	
	
	
	public String toString(){
		String output = "";
		
		for(ArrayList<Integer>[] row : field){
			output = output + this.rowToString(row);
		}		
		
		return output;
	}
	
	
	private String rowToString(ArrayList<Integer>[] row){
		String line = "";
		for (ArrayList<Integer> list : row){
			if(list.size() > 1){
				line = line + 0 + " ";
			}else{
				line = line + list.get(0) + " ";
			}
		}
		return line.trim()+"\n";
	}
	
	
	/*
	public String join(Collection<Object> words, String padding) {
	    StringBuilder wordList = new StringBuilder();
	    for (Object word : words) {
	        wordList.append(word.toString() + padding);
	    }
	    return new String(wordList.delete(wordList.length() - padding.length(), wordList.length()));
	}*/

}

package core;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private List<String>[][] field = null;
	private int subsquareWidth = 0;
	private int subsquareHeight = 0; 
	
	public Field(List<String>[][] field, int subsquareWidth, int subsquareHeight){
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
	public List<String>[] getRow(int rowIndex){
		return field[rowIndex];
	}
	
	/**
	 * Returns the column with the given index starting with 0;
	 * @param colIndex
	 * @return
	 */
	public List<String>[] getColumn(int colIndex){
		@SuppressWarnings("unchecked")
		List<String>[] col = new ArrayList[this.getRowCount()]; 
		for(int row = 0; row < this.getRowCount(); row++){
			col[row] = field[row][colIndex];
		}
		return col;
	}
	
	
	/**
	 * Returns the square in which the specified element lies in. 
	 */
	public Field getSquareAsField(int row, int col){
		@SuppressWarnings("unchecked")
		List<String>[][] square = new ArrayList[this.subsquareWidth][this.subsquareHeight];
		int[] rowLimits = this.getLowHigh(row, 0, this.subsquareWidth-1, this.subsquareWidth);
		int[] colLimits = this.getLowHigh(col, 0, this.subsquareHeight-1, this.subsquareHeight);
		
		int rowIndex = 0;
		int colIndex = 0;
		for(int i = rowLimits[0]; i <= rowLimits[1]; i++){
			List<String>[] rowArray = this.getRow(i);
			for(int k = colLimits[0]; k <= colLimits[1]; k++){
				square[rowIndex][colIndex] = rowArray[k];
				colIndex++;
			}
			rowIndex++;
			colIndex = 0;
		}
		
		return new Field(square, 0,0);
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
	 */
	public void removeValue(int row, int col, String val){
		ArrayList<String> list = new ArrayList<>();
		assert this.field[row][col].size() > 1;
		for(String oldVal : this.field[row][col]){
			if(!oldVal.equals(val)){
				list.add(oldVal);
			}
		}
		this.field[row][col] = list;
	}
	
	public void setValue(int row, int col, String val){
		ArrayList<String> list = new ArrayList<>();
		list.add(val);
		this.field[row][col] = list;
	}
	
	
	public ArrayList<String> getValues(int row, int col){
		return new ArrayList<String>(this.field[row][col]);
	}
	
	
	public String toString(){
		String output = "";
		
		for(List<String>[] row : field){
			output = output + this.rowToString(row);
		}		
		
		if(output.contains("?")){
			System.out.println(output);
			System.exit(2);
		}
		
		return output;
	}
	
	
	public String rowToString(List<String>[] row){
		String line = "";
		for (List<String> list : row){
			if(list.size() > 1){
				line = line + "["+ list.size() + "] ";
			}else if(list.size() == 1){
				line = line + " " + list.get(0) + "  ";
			}else{
				line = line + " ?  ";
			}
		}
		return line+"\n";
	}

}

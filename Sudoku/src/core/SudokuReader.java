package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Reads the input file. 
 * @author Stefan
 *
 */
public class SudokuReader {
	private ArrayList<String>[][] field = null;
	private int squareSize = 0;
	private int fieldSize = 0;
	private String[] possibleValues = null;
	private String emptyCellValue = null;
	
	public SudokuReader(File f) throws IOException{
		this(new FileInputStream(f));
	}
	
	public SudokuReader(String path) throws IOException{
		this(new File(path));
	}
	
	public SudokuReader(InputStream in) throws IOException{
		this.read(in);
	}
	
	
	/**
	 * Reads the input file line by line.
	 * Comments are starting with #.
	 * #The first line has to be fieldwidth,subsquarewidth
	 * 9,3
	 * #Then follows the data..
	 * 0 1 0 2 ...
	 * @param in
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void read(InputStream in) throws IOException{
		BufferedReader buffIn = new BufferedReader(new InputStreamReader(in));
		
		String line = this.readNextLine(buffIn);
		this.fieldSize = new Integer(line.split(",")[0]);
		this.field = new ArrayList[new Integer(fieldSize)][new Integer(fieldSize)];	
		this.squareSize = new Integer(line.split(",")[1]);
		this.possibleValues = this.readNextLine(buffIn).split(",");
		this.emptyCellValue = this.readNextLine(buffIn);	
		
		int row = 0;
		while( (line = buffIn.readLine()) != null){
			if(!line.startsWith("#") && !line.trim().isEmpty()){
				this.parseLine(line.trim(), row);
				row++;
			}			
		}	
		
		
		if(row!=this.fieldSize){
			throw new IOException("Corrupt input file!!!");
		}
	}
	
	
	private String readNextLine(BufferedReader buffIn) throws IOException{
		String line = null;
		while( (line = buffIn.readLine()) != null){
			line = line.trim();
			if(!line.startsWith("#") && !line.isEmpty()){
				return line;
			}
		}
		return line;
	}
	
	/**
	 * Transforms a line of input into a line in the field object.
	 * @param line
	 * @param row
	 * @throws IOException 
	 */
	private void parseLine(String line, int row) throws IOException{
		if(!line.isEmpty()){
			String[] vals = line.split(" ");
			int col = 0;
			for(String num : vals){
				if(num.equals(this.emptyCellValue)){
					this.field[row][col] = this.getFullList();
				}else{
					this.field[row][col] = this.getSingleList(num);
				}
				col++;
			}
			
			if(col!=this.fieldSize){
				throw new IOException("Corrupt input file!!!");
			}
		}
	}
	
	/**
	 * Creates a list filled with all values from 1 to 9.
	 * @return
	 */
	private ArrayList<String> getFullList(){
		ArrayList<String> l = new ArrayList<>();
		for(String s : this.possibleValues){
			l.add(s);
		}
		return l;
	}
	
	/**
	 * Creates a list with a single element for known numbers.
	 * @param val
	 * @return
	 */
	private ArrayList<String> getSingleList(String val){
		ArrayList<String> l = new ArrayList<>();
		l.add(val);
		return l;
	}
	
	
	/**
	 * Returns the constructed field after the input file has been read.
	 * @return
	 */
	public Field getSudokuField(){
		return new Field(this.field, new Integer(this.squareSize), new Integer(this.squareSize));
	}
	

}

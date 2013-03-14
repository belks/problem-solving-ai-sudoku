package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the input file. 
 * @author Stefan
 *
 */
public class SudokuReader {
	private List<String>[][] field = null;
	private int squareWidth = 0;
	private int squareHeight = 0;
	private int fieldWidth = 0;
	private int fieldHeight = 0;
	private List<String> possibleValues = null;
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
		this.fieldWidth = new Integer(line.split(",")[0]);
		this.fieldHeight = new Integer(line.split(",")[0]);
		this.field = new ArrayList[new Integer(fieldWidth)][new Integer(fieldHeight)];	
		
		line = this.readNextLine(buffIn);
		this.squareWidth = new Integer(line.split(",")[0]);
		this.squareHeight = new Integer(line.split(",")[1]);
		
		//Create a list filled with all values
		possibleValues = new ArrayList<>();
		for(String s : this.readNextLine(buffIn).split(",")){
			this.possibleValues.add(s);
		}
		
		this.emptyCellValue = this.readNextLine(buffIn);	
		
		int row = 0;
		while( (line = buffIn.readLine()) != null){
			if(!line.startsWith("#") && !line.trim().isEmpty()){
				this.parseLine(line.trim(), row);
				row++;
			}			
		}	
				
		if(row != this.fieldHeight){
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
					this.field[row][col] = new ArrayList<String>(this.possibleValues);
				}else{
					List<String> l = new ArrayList<>();
					l.add(num);
					this.field[row][col] = l;
				}
				col++;
			}
			
			if(col != this.fieldWidth){
				throw new IOException("Corrupt input file!!!");
			}
		}
	}
	
		
	
	
	/**
	 * Returns the constructed field after the input file has been read.
	 * @return
	 */
	public Field getSudokuField(){
		return new Field(this.field.clone(), new Integer(this.squareWidth), new Integer(this.squareHeight));
	}
	

}

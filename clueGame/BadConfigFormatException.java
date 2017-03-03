package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException(){
		super("Error Bad Config Format");
		
	}
	
	public BadConfigFormatException(String message) throws FileNotFoundException{
		super(message);
		PrintWriter p = new PrintWriter ("ExceptionLog.txt");
		p.println(super.getMessage());
		p.close();
	}
	

}

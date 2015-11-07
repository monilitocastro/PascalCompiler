import java.util.*;
import java.io.*;

public class PredictTable{
	private Scanner scanner;
	private File file;
	private HashTable<String, String[]> table;


	PredictTable(){
		table = new HashTable<String, String[]>();
	}
	public void initPredictTable(){
		openFile("Pascal.ebnf");

		//get the LHS of the production (1 item)
		//get element from predict set
		//get the RHS of the production (the remaining)
		String str[] = getLineSplit();
		String lhsProd = str[0];
		String elemSigma = str[1];
		String rhsProd[] = new String[str.length-2];
		System.arraycopy((Object)str, 2, (Object)rhsProd, str.length);
	}
	
	
	
	private void openFile(String fileName){
		try {
			file = new File(fileName);
   			scanner = new Scanner(file);
  		} catch (FileNotFoundException e) {
   			e.printStackTrace();
  		} 
 	}
 	
 	private String [] getLineSplit(){
 		if(scanner.hasNext() ){
 			return scanner.next().split(",");
 
 		}
		return null;
 	}
 	
 	private void closeFile(){
 		scanner.close();
 	}
}

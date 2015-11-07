import java.util.*;
import java.io.*;

public class PredictTable{
	private Scanner scanner;
	private File file;


	public void initPredictTable(){
		openFile("Pascal.ebnf");

		//For each line in ebnf file get the cardinality of the predict set
		//get the LHS of the production (1 item)
		//get the RHS of the production (the remaining)
		String str[] = prTable.getLineSplit();
		int cardPrd = 0
		for(int i=0; i < str.length; i++){
			if(i==0){
				cardPrd = Integer.parseInt(str[0]);
			}
		}
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

import java.util.HashTable;

public class PredictTable{
	private Scanner scanner;
	private File file;
	
	public void openFile(String fileName) {
		try {
			file = new File(fileName);
   			scanner = new Scanner(file);
  		} catch (FileNotFoundException e) {
   			e.printStackTrace();
  		} 
 	}
 	
 	public String [] getLineSplit(){
 		if(scanner.hasNext() ){
 			return scanner.next().split();
 
 		}
		return null;
 	}
 	
 	public void closeFile(){
 		scanner.close();
 	}
}

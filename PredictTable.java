import java.util.Hashtable;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Enumeration;
import java.lang.StringBuilder;
import java.io.File;

public class PredictTable{
	private Scanner scanner;
	private File file;
	private Hashtable<String, String[]> table;


	PredictTable(){
		table = new Hashtable<String, String[]>();
	}
	protected void finalize(){
		closeFile();
	}

	public boolean equals(String X, String token, String[] strArr){
		//where X is the lhs of production, token is member of sigma*
		//and arrayRepresentationOfProd is RHS of production (a list)
		StringBuilder keyBuild = new StringBuilder();
		keyBuild.append(X.trim()+","+token.trim());
		String key = keyBuild.toString();
		if(!table.containsKey(key)){
			System.out.println("ERROR: key is not in predict table");
			return false;
		}
		if(!table.contains(key)){
			System.out.println("ERROR: value is not in predict table");
			return false;
		}
		return Arrays.equals(table.get(key),strArr);
	}

	public void initPredictTable(){
		openFile("Pascal.ebnf");

		//get the LHS of the production (1 item)
		//get element from predict set
		//get the RHS of the production (the remaining)
		String str[] = getLineSplit();
		while(str!=null){
			String lhsProd = str[0];
			String elemSigma = str[1];
			String rhsProd[];
			rhsProd = rhsProductions(str);
			StringBuilder build = new StringBuilder();
			build.append(lhsProd.trim() + ","+elemSigma.trim() );
			table.put(build.toString(), rhsProd );
			str = getLineSplit();
		}
		

	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		Enumeration e = table.keys();
	    while (e.hasMoreElements()) {
	      String key = (String) e.nextElement();
	      StringBuilder build = new StringBuilder();
	      build.append("[ ");
	      for(String s : table.get(key)){
	      	build.append(s + " ");
	      }
	      build.append("]");
	      result.append(key + " : " + build.toString()+"\n");
	    }
		return result.toString();
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
 		//				WARNING: guard against this return value
		return null;
 	}
 	
 	private void closeFile(){
 		scanner.close();
 	}

 	private String[] rhsProductions(String strArr[]){
 		int i = strArr.length-2;
 		String[] result = new String[ i ];
 		for(int j = 0; j < i; j++){
 			result[j] = strArr[j+2];
 		}
 		return result;
 	}
}

/**
 * CSCI 465
 * @author Monilito Castro
 * email: monilito.castro@my.und.edu
 * Pascal module
 */
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Tokenizer2 {
	private LinkedList<TokenListElement> tokenList;
	private LinkedList<PatternListElement> patterns;
	private LinkedList<SymbolAttributes> symbolStack;
	private Hashtable<String, LinkedList<SymbolAttributes> > symbolTable;
	private String source;
	private int tokenIndex = 0;
	private boolean isInsideVarDecl = false;
	private boolean isImmAfterFuncTkn = false;
	private boolean isImmAfterProgram = false;
	Tokenizer2(String src){
		source = src.trim();
		tokenList = new LinkedList<TokenListElement>();
		patterns = new LinkedList<PatternListElement>();
		symbolStack = new LinkedList<SymbolAttributes>();
		symbolTable = new Hashtable<    String, LinkedList<SymbolAttributes>     >();
	}
	public void tokenize(){
		if(source==null){
			System.out.println("Source is empty. Exiting.");
			System.exit(0);
		}
		if(patterns.isEmpty()){
			System.out.println("Patterns list is empty. Exiting.");
			System.exit(0);
		}
		while(!source.equals("")){
			/**
			* Finds the biggest token for the input stream
			*/
			int tokenSize = 0;
			PatternListElement currBiggestPattern = null;   //null values, are they possible?
			boolean biggestFound = false;
			for(PatternListElement patElem : patterns){
				Pattern pat = patElem.pattern;
				Matcher mat = pat.matcher(source);
				if(mat.find()){
					int currSize = mat.end() - mat.start();
					if (currSize>tokenSize){
						//System.out.println("currSize="+currSize);
						currBiggestPattern = patElem;
						tokenSize = currSize;
						biggestFound = true;
					}
				}
			}
			if(biggestFound){
				/**
				*  Does:
				*     1) trim input string to reflect the chosen regex consuming the string.
				*     2) add token to list
				*/
				String image = "";
				Pattern pat = currBiggestPattern.pattern;
				Matcher mat = pat.matcher(source);
				if(mat.find()){
					image = mat.group().trim();
					source = mat.replaceFirst("").trim();
					//System.out.println("tokenSize="+tokenSize);

				}else{
					System.out.println("Warning: found string at first but lost it. Line 49 to 62 Tokenizer2 class");
					System.exit(-1);
				}
				TokenListElement elemToAdd = new TokenListElement(image, currBiggestPattern.token);
				tokenList.add( elemToAdd );
				tokenIndex++;
				//Symbol table
				addToSymbolTable(image, currBiggestPattern.token, tokenIndex);


				//flag to determine if inside var declaration
				if(image.equals("Var") || image.equals("var")){
					isInsideVarDecl = true;
					//System.out.println("is inside the flagging op.");
				}
				if((image.equals("Integer") || image.equals("integer")) && isInsideVarDecl  ){
					//do everything here before turning off isInsideVarDecl
					assignAllSymbolStack("INTEGER_ID");
					isInsideVarDecl = false;
				}
				if((image.equals("String") || image.equals("string")) && isInsideVarDecl ){
					assignAllSymbolStack("STRING_ID");
					isInsideVarDecl = false;
				}
				//flag to determine if immediately after a function token
				if(isImmAfterFuncTkn){
					isImmAfterFuncTkn = false;
				}
				if(image.equals("Function") | image.equals("function")){
					isImmAfterFuncTkn = true;
				}

				if(isImmAfterProgram){
					isImmAfterProgram = false;
				}
				if(image.equals("Program") || image.equals("program") ){
					isImmAfterProgram = true;
				}



			}
		}
	}
	public void addToSymbolTable(String name, String token, int index){
		LinkedList<SymbolAttributes> listSymAttr;
		SymbolAttributes symAttr = new SymbolAttributes();
		if(!token.equals("<ID>") ){
			return;
		}

		if(isInsideVarDecl){
			symAttr.tokenType = "VARIABLE_ID";
			symAttr.optionalImage = name;
			symAttr.tokenIndex = index;
			symbolStack.push(symAttr);
		}
		if(isImmAfterFuncTkn){
			symAttr.tokenType = "PROCEDURE_ID";
			symAttr.tokenIndex = index;
			symAttr.optionalImage = name;
		}
		if(isImmAfterProgram){
			symAttr.tokenType = "PROGRAM_ID";
			symAttr.tokenIndex = index;
			symAttr.optionalImage = name;
		}

		if(symbolTable.containsKey(name)){
			listSymAttr = symbolTable.get(name);
			//enter code here to update the symbol with new scope information
		}else{
			listSymAttr = new LinkedList<SymbolAttributes>();

			symbolTable.put(name, listSymAttr);
		}

		System.out.println("ID "+ name + " has "+listSymAttr.size());
		listSymAttr.add(symAttr);

	}
	public void assignAllSymbolStack(String tknType){
		while(symbolStack.size()>0){
			//if the symbolStack will be used for things other than re assigning token types to VARIABLES then
			//make sure that an if condition is used on the stack element before assigning token type.
			SymbolAttributes symAttr = symbolStack.pop();
			symAttr.tokenType=tknType;
		}
	}
	public void addPattern(String regex, String tkn){
		patterns.add( new PatternListElement(Pattern.compile( "^("+regex+")") , tkn ) );
	}
	public LinkedList<TokenListElement> getTokens(){
		return tokenList;
	}
	public void printSymbolTable(){
		int numberOfSymbols = symbolTable.size();
		Set<String> setKeys = symbolTable.keySet();
		LinkedList<String> listKeys = new LinkedList<String>();
		for(String key : setKeys){
			listKeys.add(key);
		}
		System.out.println("There are " + numberOfSymbols + " symbols in the symbol table.");
		System.out.print("These keys are");
		for( int i = 0; i < listKeys.size(); i++){
			LinkedList<SymbolAttributes> listSymAttr = symbolTable.get(listKeys.get(i));
			System.out.println(" " + listKeys.get(i) + " which has " + listSymAttr.size() + " identifier types. The identifiers are of the type: ");
			
			for( SymbolAttributes symAttr : listSymAttr){
				System.out.println(" < " + symAttr.optionalImage + ",<" + symAttr.tokenType +"> >");
			}
		}
	}
	public void allSymbols(){
		System.out.println("*********************Symbol table contents.*********************");
		System.out.println("<:> denotes usage of the identifier.\n"+symbolTable.toString());
	}
}

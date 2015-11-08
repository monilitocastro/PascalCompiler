/**
 * CSCI 465
 * @author Monilito Castro
 * email: monilito.castro@my.und.edu
 * Pascal module
 */
import java.lang.StringBuilder;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Tokenizer2 {
	private LinkedList<TokenListElement> tokenList;
	private LinkedList<PatternListElement> patterns;
	private LinkedList<SymbolAttributes> symbolStack;
	private LinkedList<String> allRegex;
	private Hashtable<String, LinkedList<SymbolAttributes> > symbolTable;
	private String source;
	private int tokenIndex = 0;
	private Pattern  keywordPatterns;
	private boolean isInsideVarDecl = false;
	private boolean isImmAfterFuncTkn = false;
	private boolean isImmAfterProgram = false;
	private boolean isAssigningValue = false;
	private String lastID = "";
	private StringBuilder postLexBuilder;

	private int tokenSize = 0;
	private PatternListElement currBiggestPattern = null; 
	private boolean biggestFound = false;
	private Pattern pat;
	private Matcher mat;
	private String image;
	private TokenListElement elemToAdd;

	Tokenizer2(String src){
		source = src.trim();
		tokenList = new LinkedList<TokenListElement>();
		patterns = new LinkedList<PatternListElement>();
		symbolStack = new LinkedList<SymbolAttributes>();
		symbolTable = new Hashtable<    String, LinkedList<SymbolAttributes>     >();
		allRegex = new LinkedList<String>();
		postLexBuilder = new StringBuilder();
	}

	public boolean hasNext(){
		return !source.equals("");
	}
	public String nextToken(){

		
			/**
			* Finds the biggest token for the input stream
			*/
			//int tokenSize = 0;
			//PatternListElement currBiggestPattern = null;   //null values, are they possible?
			//boolean biggestFound = false;
			for(PatternListElement patElem : patterns){
				pat = patElem.pattern;
				mat = pat.matcher(source);
				if(mat.find()){
					int currSize = mat.end() - mat.start();
					if (currSize>tokenSize){
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
				image = "";
				pat = currBiggestPattern.pattern;
				mat = pat.matcher(source);
				if(mat.find()){
					image = mat.group().trim();
					source = mat.replaceFirst("").trim();

				}else{
					System.out.println("Warning: found string at first but lost it. Line 49 to 60 Tokenizer2 class");
					System.exit(-1);
				}
				elemToAdd = new TokenListElement(image, currBiggestPattern.token);

				if(currBiggestPattern.token.equals("<ILLEGAL>")){postLexBuilder.append("ERROR: Illegal characters detected.\n");}

				tokenIndex++;
				//Symbol table
				addToSymbolTable(image, currBiggestPattern.token, tokenIndex);


				//flag to determine if inside var declaration
				if(image.equals("Var") || image.equals("var")){
					isInsideVarDecl = true;
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
				if((image.equals("Char") || image.equals("char")) && isInsideVarDecl ){
					assignAllSymbolStack("CHAR_ID");
					isInsideVarDecl = false;
				}
				if(image.equals(":=")){
					isAssigningValue = true;
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

				if(!currBiggestPattern.token.equals("<COMMENT>")){
					return currBiggestPattern.token;
				}


			}
			System.out.println("ERROR: Tokenizer2 doesn't have anything to return.");
			return null;
		
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
			tokenSize = 0;
			currBiggestPattern = null;   //null values, are they possible?
			biggestFound = false;
			for(PatternListElement patElem : patterns){
				pat = patElem.pattern;
				mat = pat.matcher(source);
				if(mat.find()){
					int currSize = mat.end() - mat.start();
					if (currSize>tokenSize){
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
				image = "";
				pat = currBiggestPattern.pattern;
				mat = pat.matcher(source);
				if(mat.find()){
					image = mat.group().trim();
					source = mat.replaceFirst("").trim();

				}else{
					System.out.println("Warning: found string at first but lost it. Line 49 to 62 Tokenizer2 class");
					System.exit(-1);
				}
				elemToAdd = new TokenListElement(image, currBiggestPattern.token);
				if(!currBiggestPattern.token.equals("<COMMENT>")){tokenList.add( elemToAdd );}
				if(currBiggestPattern.token.equals("<ILLEGAL>")){postLexBuilder.append("ERROR: Illegal characters detected.\n");}

				tokenIndex++;
				//Symbol table
				addToSymbolTable(image, currBiggestPattern.token, tokenIndex);


				//flag to determine if inside var declaration
				if(image.equals("Var") || image.equals("var")){
					isInsideVarDecl = true;
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
				if((image.equals("Char") || image.equals("char")) && isInsideVarDecl ){
					assignAllSymbolStack("CHAR_ID");
					isInsideVarDecl = false;
				}
				if(image.equals(":=")){
					isAssigningValue = true;
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
		//if(identifierIsAKeyword(name)){
		//	postLexBuilder.append("ERROR: "+name +" is a keyword and isn't a valid identifier.");
		//}
		if(isAssigningValue){
			if(!isSameType(name, lastID)){
				postLexBuilder.append("ERROR: wrong data type on token number "+ index+"\n");
				postLexBuilder.append("\t\t" + lastID + " := " + name + " are two different types.");
			}
			isAssigningValue = false;
		}
		lastID = name;
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

		if(!isInsideVarDecl && !isImmAfterProgram && !isImmAfterFuncTkn){
			return;
		}

		if(symbolTable.containsKey(name)){
			listSymAttr = symbolTable.get(name);
			//enter code here to update the symbol with new scope information
		}else{
			listSymAttr = new LinkedList<SymbolAttributes>();

			symbolTable.put(name, listSymAttr);
		}
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
		if(!tkn.equals("<COMMENT>") && !tkn.equals("<ILLEGAL>") ){allRegex.add(regex);}
	}
	public Pattern unionCompileAllRegex(){
		StringBuilder build = new StringBuilder();
		build.append("^(");
		for(int i = 0 ; i < allRegex.size(); i++){
			build.append(allRegex.get(i));
			if(i < allRegex.size()-1){
				build.append("|");
			}else{
				build.append(")");
			}
		}
		//System.out.println(build.toString());
		return Pattern.compile(build.toString() );
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
		System.out.println("*********************Symbol table contents*********************");
		System.out.println("format: image_of_token = [ list, of, identifiers ]");
		System.out.println(symbolTable.toString());
		System.out.println(postLexBuilder.toString());
	}
	public boolean isSameType(String name1, String name2){
		if(!symbolTable.containsKey(name1)){
			postLexBuilder.append("ERROR: Undeclared identifier "+name1+"\n");
			return false;
		}
		if(!symbolTable.containsKey(name2)){
			postLexBuilder.append("ERROR: Undeclared identifier "+name2+"\n");
			return false;
		}
		LinkedList<SymbolAttributes> listSymAttr1 = symbolTable.get(name1);
		LinkedList<SymbolAttributes> listSymAttr2 = symbolTable.get(name2);
		
		for( SymbolAttributes symAttr1 : listSymAttr1){
			for( SymbolAttributes symAttr2 : listSymAttr2){
				if(symAttr1.tokenType.equals(symAttr2.tokenType)){

					return true;
				}
			}		
		}
		return false;
	}
	public void prepareKeywordPatterns(){
		keywordPatterns = unionCompileAllRegex();
	}
	public boolean identifierIsAKeyword(String name){
		Matcher mat = keywordPatterns.matcher(name);
		return mat.find();
	}
}

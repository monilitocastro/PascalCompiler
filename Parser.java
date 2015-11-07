import java.util.Stack;
import java.util.ArrayList;

public class Parser{
	private Stack stack;
	private PredictTable prTable;
	private ArrayList<String> arrTerminals;
	Parser(){
		stack = new Stack<String>();
		arrTerminals = new ArrayList<String>();
		addAllTerminals();
		stack.push("$");
		stack.push("<program>");
		//define predictTable with proper size.
	}
	public void parse(){
		String token = nextToken();		//the string should be "<program>"
		String X;
		while(true){
			X = stack.peek();
			if( terminal(X) | dollar(X)){
				if(token.equals(X)){
					stack.pop();
					token = nextToken();
				}else{
					printError();
				}
			}else{
				if( prTable.equals(X, token, arrayRepresentingSomeProd) ){
					stack.pop();
					for(String str : arrRepr){
						stack.push(str);       //push each elem in array onto stack
					}
				}else{
					printError();
				}
			}
			if(dollar(X) )break;
		}
	}
	
	private void printError(){
		System.out.println("ERROR.");
	}
	private boolean dollar(String X){
		return X.equals("$");
	}
	private boolean terminal(String X){
		arrTerms = arrTerminals.toArray();
		for(String str: arrTerms){
			if(str.equals(X)) return true;
		}
		return false;
	}
	private void addAllTerminals(){
		arrTerminals("<AND>");
		arrTerminals("<Array>");
		arrTerminals("<BEGIN>");
		arrTerminals("<CHAR>");
		arrTerminals("<CHR>");
		arrTerminals("<DIV>");
		arrTerminals("<DO>");
		arrTerminals("<ELSE>");
		arrTerminals("<END>");
		arrTerminals("<IF>");
		arrTerminals("<INTEGER>");
		arrTerminals("<STRING>");
		arrTerminals("<MOD>");
		arrTerminals("<NOT_OP>");
		arrTerminals("<OF>");
		arrTerminals("<OR>");
		arrTerminals("<ORD>");
		arrTerminals("<PROCEDURE>");
		arrTerminals("<PROGRAM>");
		arrTerminals("<READLN>");
		arrTerminals("<READ>");
		arrTerminals("<THEN>");
		arrTerminals("<VAR>");
		arrTerminals("<WHILE>");
		arrTerminals("<WRITELN>");
		arrTerminals("<WRITE>");
		arrTerminals("<ADD_OP>");
		arrTerminals("<SUBTRACT_OP>");
		arrTerminals("<MULT_OP>");
		arrTerminals("<DIV_OP>");
		arrTerminals("<LESSEQUAL>");
		arrTerminals("<LESS>");
		arrTerminals("<NOTEQUAL>");
		arrTerminals("<GREATEREQUAL>");
		arrTerminals("<GREATER>");
		arrTerminals("<EQUAL>");
		arrTerminals("<ASSIGN>");
		arrTerminals("<COLON>");
		arrTerminals("<SEMICOLON>");
		arrTerminals("<COMMA>");
		arrTerminals("<LPAREN>");
		arrTerminals("<LBRACK>");
		arrTerminals("<RPAREN>");
		arrTerminals("<RBRACK>");
		arrTerminals("<PERIOD>");
		arrTerminals("<ID>");
		arrTerminals("<NUMBER>");
		arrTerminals("<QUOTESTR>");
		arrTerminals("<LITCHAR>");
	}
	
	
}



import java.util.Stack;

public class Parser{
	private Stack stack;
	private PredictTable prTable;
	Parser(){
		stack = new Stack<String>();
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
					stack.push(Y3);//then Y2 Y1
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
	
	
}



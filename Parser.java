import java.util.Stack;

public class Parser{
	private Stack<String> stack;
	private PredictTable prTable;
	Parser(){
		stack = new Stack<String>();
		//define predictTable with proper size.
	}
	public void parse(){
		String token = "<program>";
		String X;
		while(true){
			X = stack.peek();
			if( terminal(X) | dollar(X)){
				if(token.equals(X)){
					stack.pop();
					a = nextToken();
				}else{
					printError();
				}
			}else{
				if( prTable.equals(X, token, "X->Y1 Y2 Y3") ){
					stack.pop();
					stack.push(Y3);//then Y2 Y1
				}else{
					printError();
				}
			}
			if(X.equals("$"))break;
		}
	}
	
	
	
}



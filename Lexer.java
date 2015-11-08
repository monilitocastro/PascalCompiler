/**
 * CSCI 465
 * @author Monilito Castro
 * email: monilito.castro@my.und.edu
 * Pascal: Lexer module
 * First Delivery 
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;



public class Lexer {
	private String _source;
	private Tokenizer2 _tokenizer;
	public void init(String s){
		try{
			_source = loadStringFromFile(s);
			InitTokenizing(source);
		}catch ( IOException e){
			System.out.println("Error with file " + s + "\n"+e.getMessage());
		}
	}

	public void InitTokenizing(String s2){
		_tokenizer = new Tokenizer2(s2);
		_tokenizer.addPattern("(A|a)nd", "<AND>");
		_tokenizer.addPattern("(A|a)rray", "<Array>");
		_tokenizer.addPattern("(B|b)egin", "<BEGIN>");
		_tokenizer.addPattern("(C|c)har", "<CHAR>");
		_tokenizer.addPattern("(C|c)hr", "<CHR>");
		_tokenizer.addPattern("(D|d)iv", "<DIV>");
		_tokenizer.addPattern("(D|d)o", "<DO>");
		_tokenizer.addPattern("(E|e)lse", "<ELSE>");
		_tokenizer.addPattern("(E|e)nd", "<END>");
		_tokenizer.addPattern("(I|i)f", "<IF>");
		_tokenizer.addPattern("(I|i)nteger", "<INTEGER>");
		_tokenizer.addPattern("(S|s)tring", "<STRING>");
		_tokenizer.addPattern("(M|m)od", "<MOD>");
		_tokenizer.addPattern("!", "<NOT_OP>");
		_tokenizer.addPattern("(O|o)f", "<OF>");
		_tokenizer.addPattern("(O|o)r", "<OR>");
		_tokenizer.addPattern("(O|o)rd", "<ORD>");
		_tokenizer.addPattern("(P|p)rocedure", "<PROCEDURE>");
		_tokenizer.addPattern("(P|p)rogram", "<PROGRAM>");
		_tokenizer.addPattern("(R|r)ead(L|l)n", "<READLN>");
		_tokenizer.addPattern("(R|r)ead", "<READ>");
		_tokenizer.addPattern("(T|t)hen", "<THEN>");
		_tokenizer.addPattern("(V|v)ar", "<VAR>");
		_tokenizer.addPattern("(W|w)hile", "<WHILE>");
		_tokenizer.addPattern("(W|w)rite(L|l)n", "<WRITELN>");
		_tokenizer.addPattern("(W|w)rite", "<WRITE>");
		_tokenizer.addPattern("\\+", "<ADD_OP>");
		_tokenizer.addPattern("-", "<SUBTRACT_OP>");
		_tokenizer.addPattern("\\*", "<MULT_OP>");
		_tokenizer.addPattern("/", "<DIV_OP>");
		_tokenizer.addPattern("<=", "<LESSEQUAL>");
		_tokenizer.addPattern("<", "<LESS>");
		_tokenizer.addPattern("!=", "<NOTEQUAL>");
		_tokenizer.addPattern(">=", "<GREATEREQUAL>");
		_tokenizer.addPattern(">", "<GREATER>");
		_tokenizer.addPattern("=", "<EQUAL>");
		_tokenizer.addPattern(":=", "<ASSIGN>");
		_tokenizer.addPattern(":", "<COLON>");
		_tokenizer.addPattern(";", "<SEMICOLON>");
		_tokenizer.addPattern(",", "<COMMA>");
		_tokenizer.addPattern("\\(", "<LPAREN>");
		_tokenizer.addPattern("\\[", "<LBRACK>");
		_tokenizer.addPattern("\\)", "<RPAREN>");
		_tokenizer.addPattern("\\]", "<RBRACK>");
		_tokenizer.addPattern("\\.", "<PERIOD>");
		_tokenizer.addPattern("[a-zA-Z]([a-zA-Z0-9]|_)*", "<ID>");
		_tokenizer.addPattern("[0-9]+(.[0-9]+)?", "<NUMBER>");
		_tokenizer.addPattern("\\\"[^\\\"]*\\\"", "<QUOTESTR>");
		_tokenizer.addPattern("'[^']{1}'", "<LITCHAR>");
		_tokenizer.addPattern("/0", "<EOF>");
		_tokenizer.addPattern("[\u0000-\u001F]|\u007F", "<ILLEGAL>");
		_tokenizer.addPattern("&", "<ILLEGAL>");	  //insert the rest of the illegal chars here
		_tokenizer.addPattern("\\{[^\\}]*\\}", "<COMMENT>");
	}

	/**
	 * Testing program for Tokenizer class. This holds the only main method in the project.
	 * @param 
	 */
	public void FirstDelivery(String[] args) {
		for(String s : args){
			System.out.println("\n***************************************************************");
			System.out.println(  "*                         First Delivery                      *");
			System.out.println(  "***************************************************************\n");
			String source;
			try{
				source = loadStringFromFile(s);
				TokenizeAndPrint(source);
			}catch ( IOException e){
				System.out.println("Error with file " + s + "\n"+e.getMessage());
				break;
			}
		}
		
	}
	public String loadStringFromFile(String filename) throws IOException{
		FileInputStream fileInput = new FileInputStream(filename);
		StringBuilder build = new StringBuilder();
		int r;
		while ((r = fileInput.read()) != -1) {
		   char c = (char)r;
		   build.append(c);
		}
		fileInput.close();
		return build.toString();
	}
	public void TokenizeAndPrint(String source){
		Tokenizer2 tokenizer = new Tokenizer2(source);
		tokenizer.addPattern("(A|a)nd", "<AND>");
		tokenizer.addPattern("(A|a)rray", "<Array>");
		tokenizer.addPattern("(B|b)egin", "<BEGIN>");
		tokenizer.addPattern("(C|c)har", "<CHAR>");
		tokenizer.addPattern("(C|c)hr", "<CHR>");
		tokenizer.addPattern("(D|d)iv", "<DIV>");
		tokenizer.addPattern("(D|d)o", "<DO>");
		tokenizer.addPattern("(E|e)lse", "<ELSE>");
		tokenizer.addPattern("(E|e)nd", "<END>");
		tokenizer.addPattern("(I|i)f", "<IF>");
		tokenizer.addPattern("(I|i)nteger", "<INTEGER>");
		tokenizer.addPattern("(S|s)tring", "<STRING>");
		tokenizer.addPattern("(M|m)od", "<MOD>");
		tokenizer.addPattern("!", "<NOT_OP>");
		tokenizer.addPattern("(O|o)f", "<OF>");
		tokenizer.addPattern("(O|o)r", "<OR>");
		tokenizer.addPattern("(O|o)rd", "<ORD>");
		tokenizer.addPattern("(P|p)rocedure", "<PROCEDURE>");
		tokenizer.addPattern("(P|p)rogram", "<PROGRAM>");
		tokenizer.addPattern("(R|r)ead(L|l)n", "<READLN>");
		tokenizer.addPattern("(R|r)ead", "<READ>");
		tokenizer.addPattern("(T|t)hen", "<THEN>");
		tokenizer.addPattern("(V|v)ar", "<VAR>");
		tokenizer.addPattern("(W|w)hile", "<WHILE>");
		tokenizer.addPattern("(W|w)rite(L|l)n", "<WRITELN>");
		tokenizer.addPattern("(W|w)rite", "<WRITE>");
		tokenizer.addPattern("\\+", "<ADD_OP>");
		tokenizer.addPattern("-", "<SUBTRACT_OP>");
		tokenizer.addPattern("\\*", "<MULT_OP>");
		tokenizer.addPattern("/", "<DIV_OP>");
		tokenizer.addPattern("<=", "<LESSEQUAL>");
		tokenizer.addPattern("<", "<LESS>");
		tokenizer.addPattern("!=", "<NOTEQUAL>");
		tokenizer.addPattern(">=", "<GREATEREQUAL>");
		tokenizer.addPattern(">", "<GREATER>");
		tokenizer.addPattern("=", "<EQUAL>");
		tokenizer.addPattern(":=", "<ASSIGN>");
		tokenizer.addPattern(":", "<COLON>");
		tokenizer.addPattern(";", "<SEMICOLON>");
		tokenizer.addPattern(",", "<COMMA>");
		tokenizer.addPattern("\\(", "<LPAREN>");
		tokenizer.addPattern("\\[", "<LBRACK>");
		tokenizer.addPattern("\\)", "<RPAREN>");
		tokenizer.addPattern("\\]", "<RBRACK>");
		tokenizer.addPattern("\\.", "<PERIOD>");
		tokenizer.addPattern("[a-zA-Z]([a-zA-Z0-9]|_)*", "<ID>");
		tokenizer.addPattern("[0-9]+(.[0-9]+)?", "<NUMBER>");
		tokenizer.addPattern("\\\"[^\\\"]*\\\"", "<QUOTESTR>");
		tokenizer.addPattern("'[^']{1}'", "<LITCHAR>");
		tokenizer.addPattern("/0", "<EOF>");
		tokenizer.addPattern("[\u0000-\u001F]|\u007F", "<ILLEGAL>");
		tokenizer.addPattern("&", "<ILLEGAL>");	  //insert the rest of the illegal chars here
		tokenizer.addPattern("\\{[^\\}]*\\}", "<COMMENT>");

		tokenizer.tokenize();
		System.out.printf("%29s  \t%s\n","TOKENS", "IMAGE" );
		System.out.printf("%29s  \t%s\n","------", "-----" );

		LinkedList<TokenListElement> list = tokenizer.getTokens();
		for( TokenListElement e : list){
			System.out.printf("%29s  \t%s\n",e.token,e.regex );
		}
		//tokenizer.printSymbolTable();
		tokenizer.allSymbols();
		tokenizer.prepareKeywordPatterns();
	}

}


//previous illegal character ranges.
//tokenizer.addPattern("[\u0000-\u0027]|\u002C|[\u003A-\u0040]", "<ILLEGAL>");
//tokenizer.addPattern("[\\[-\\`]", "<ILLEGAL>");
//tokenizer.addPattern("[\\u007B-\\u007F]", "<ILLEGAL>");
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
	Tokenizer2 tokenizer;

	/**
	 * Testing program for Tokenizer class. This holds the only main method in the project.
	 * @param 
	 */
	public static void main(String[] args) {
		for(String s : args){
			System.out.println("\n***************************************************************");
			System.out.println(  "*                         First Delivery                      *");
			System.out.println(  "***************************************************************\n");
			String source;
			try{
				source = loadStringFromFile(s);
				System.out.printf("%29s  \t%s\n","TOKENS", "IMAGE" );
				System.out.printf("%29s  \t%s\n","------", "-----" );

				LinkedList<TokenListElement> list = tokenizer.getTokens();
				for( TokenListElement e : list){
					System.out.printf("%29s  \t%s\n",e.token,e.regex );
				}
				//tokenizer.printSymbolTable();
				//tokenizer.allSymbols();
				//tokenizer.prepareKeywordPatterns();

				String str = tokenizer
			}catch ( IOException e){
				System.out.println("Error with file " + s + "\n"+e.getMessage());
				break;
			}
		}
		
	}
	public static String loadStringFromFile(String filename) throws IOException{
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
	public static void InitTokenizer(String source){
		tokenizer = new Tokenizer2(source);
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


	}

}


//previous illegal character ranges.
//tokenizer.addPattern("[\u0000-\u0027]|\u002C|[\u003A-\u0040]", "<ILLEGAL>");
//tokenizer.addPattern("[\\[-\\`]", "<ILLEGAL>");
//tokenizer.addPattern("[\\u007B-\\u007F]", "<ILLEGAL>");
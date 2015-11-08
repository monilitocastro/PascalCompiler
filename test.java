import java.util.Arrays;
public class test{
	public static void main(String[] args){
		Lexer lex = new Lexer();
		lex.init("test.pas");

		while(lex.hasNext()){
			System.out.println(lex.nextToken());
		}
	}
}

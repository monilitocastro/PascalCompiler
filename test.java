public class test{
 public static void main(String[] args){
  Lexer lex = new Lexer();
  lex.init(args[0]);
  while(lex.hasNext()){
   TokenListElement tokenElement = lex.nextToken();
   System.out.println( tokenElement.token);
  }
  System.out.println(lex.toString());
 }
}

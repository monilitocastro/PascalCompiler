/**
* This class is meant to unite all the modules of the Pascal compiler
* into a coherent whole. It generates new objects to its private attributes.
* The modules of the system will be created from this class and can be used
* for argument to other objects.
*
* @author: Monilito Castro
* @version 2.0 Build 2 November 10, 2015
*/ 
public class PascalCompiler{
 private static Lexer lex;
 private static Parser parser;
 /**
 * This method initializes Lexer().
 * @param none
 */
 private static void init(){
  lex = new Lexer();

 }

 /**
 * This is the only main method in the whole project. This method
 * calls on Lexer to initialize. An ICGenerator object is created
 * and along with Lexer is passed to Parser. The ICGenerator's
 * compile method returns a toString() of the assembly language
 * representation of the Pascal code.
 * @param PascalFile, assembly file output
 */
 public static void main (String[] args){
  if(args.length!=2){
   System.out.println("This program needs two arguments:");
   System.out.println("\tjava PascalCompiler pascalInFile assemblyOutFile");
   System.exit(-1);
  }
  init();
  lex.init(args[0]);
  ICGenerator icg = new ICGenerator(lex.getSymbolTable() );
  parser = new Parser(lex, icg);
  if(parser==null){
   System.out.println("Parser is NULL");
   System.exit(-1);
  }
  parser.parse();
  System.out.println("\n"+icg.compile());
 }
}
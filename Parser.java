/**
* This class is sandwiched between the Lexer and ICGenerator.
* It is a LL(1) Parser and so keeps a PredictTable for the
* predict set. It has references to the Lexer, which has the
* symbol table, and the intermediate code generator.
* 
*@author: Monilito Castro
*@version 2.0 Build 2 November 10, 2015
*/

import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Hashtable;

public class Parser{
 private Stack<String> stack;
 private PredictTable prTable;
 private ArrayList<String> listOfProc = new ArrayList<String>();
 private ArrayList<String> arrTerminals;
 private Lexer lex;
 private ICGenerator icg;
 private Hashtable<String, LinkedList<SymbolAttributes> > symbolTable;
 Parser(Lexer l, ICGenerator i){
  lex = l;
  icg = i;
  stack = new Stack<String>();
  arrTerminals = new ArrayList<String>();
  prTable = new PredictTable();
  addAllTerminals();
  stack.push("$");
  stack.push("<program>");
  symbolTable = lex.getSymbolTable();
  //if(lex.hasNext())stack.push(lex.nextToken().token);
  //define predictTable with proper size.
 }
 /**
 * This method makes use of the predict set to determine
 * what productions to expand given some nonterminal
 * represented by X and terminal represented by 'token'.
 * There are three main cases that control important parts of
 * the code generation process. They are initial stage, terminal,
 * and nonterminal cases
 * terminal case has the emit functions. Emits in this program
 * are stored alongside with the predict set. Those emits are
 * determined by the prefix @.
 */
 public void parse(){
  ArrayList<String> listOfVar = new ArrayList<String>();
  boolean dotTextAlready = false;
  boolean blInsideProcedure = false;
  String latestPopRegex = "";
  String latestID = "";
  String latestLHSVar = "";
  String emitterCommand;
  TokenListElement tokenElement = lex.nextToken();
  String token = "NOT INITIALIZED";
  if(tokenElement!=null)token = tokenElement.token;  //the string should be "<program>"
  String X;
  while(true){
   X = stack.peek();
   if(X.charAt(0)=='@' ){
    emitterCommand = X;
    //This if case will emit on pop
    System.out.println("**************"+emitterCommand+"************");
    stack.pop();
    if(emitterCommand.equals("@DOTDATA")){
     icg.dotData();
     listOfProc = new ArrayList<String>();
    }else if(emitterCommand.equals("@VARDECLARE")){
     listOfVar.add(latestPopRegex);
    }else if(emitterCommand.equals("@PROCEDURE_ID_DECLARED")){
     latestPopRegex = latestID;
     listOfProc.add(latestPopRegex);
     blInsideProcedure = true;
    }else if(emitterCommand.equals("@INTDECLARE")){
     Object[] list = listOfVar.toArray();
     for(Object item: list){
      //System.out.println("item in listOfVar: "+(String)item);
      icg.dataInteger((String)item);
     }
     listOfVar = new ArrayList<String>();
    }else if(emitterCommand.equals("@DOTTEXT")){
      if(!dotTextAlready){
        dotTextAlready=true;
        icg.dotText();
      }
    }else if(emitterCommand.equals("@VARIABLE")){
     latestPopRegex = latestID;
     icg.loadVariable(latestPopRegex);
     //icg.pushByte();
    }else if(emitterCommand.equals("@LHSVARIABLE")){
     latestLHSVar = latestPopRegex;
     checkIfInProc(latestLHSVar);
    }else if(emitterCommand.equals("@CONSTANTNUMBER")){
     icg.loadImm(latestPopRegex);
    }else if(emitterCommand.equals("@ADD")){
     icg.add();
    }else if(emitterCommand.equals("@MULT")){
     icg.multiply();
    }else if(emitterCommand.equals("@DIV")){
     icg.divide();
    }else if(emitterCommand.equals("@SUBTRACT")){
     icg.subtract();
    }else if(emitterCommand.equals("@STORE")){
     icg.storeByte(latestLHSVar);
    }else if(emitterCommand.equals("@LPAREN")){
     icg.pushByte();
    }else if(emitterCommand.equals("@RPAREN")){
     icg.popByte();
    }else if(emitterCommand.equals("@RESTORE")){
     //icg.popByte();
    }else if(emitterCommand.equals("@READ")){
     if(symbolTable.containsKey(latestPopRegex) ){      
      LinkedList<SymbolAttributes> attr = symbolTable.get(latestPopRegex);
      Object list[] = attr.toArray();
      String typeVar = "";
      for(Object item : list){
       if(((SymbolAttributes)item).tokenType.equals("INTEGER_ID")){
        typeVar = "INTEGER_ID";
        break;
       }else if(((SymbolAttributes)item).tokenType.equals("CHAR_ID")){
        typeVar = "CHAR_ID";
        break;
       }else if(((SymbolAttributes)item).tokenType.equals("STRING_ID")){
        typeVar = "STRING_ID";
        break;
       }
      }
      if(typeVar.equals("")){
       System.out.println("ERROR: the variable '"+latestPopRegex+"' doesn't have an integer, char, or string id.");
       System.exit(-1);
      }
      if(typeVar.equals("INTEGER_ID")){
       icg.readInteger(latestPopRegex);
      }else{
       System.out.println("ERROR: Need to implement type '"+typeVar+"' in emitter before proceeding.");
      }
     }
    }else if(emitterCommand.equals("@WRITE")){
     icg.writeString();
    }else if(emitterCommand.equals("@WRITEINT")){
     icg.writeInteger(latestPopRegex);
    }else if(emitterCommand.equals("@DATAQUOTE")){
     icg.dataString(latestPopRegex);
    }else if(emitterCommand.equals("@EXIT")){
     icg.exit();
     //TODO: maybe put test strings in this branch?
     System.out.println("listOfProc content: "+listOfProc.toString() );
    }
    continue;
   }
   if(dollar(X) && lex.hasNext() ){
    System.out.println("Stack pop: " + stack.pop() );
   }else if( terminal(X) | dollar(X)){
    //System.out.println("X="+X + " token="+token);
    if(token.equals(X)){
     
     System.out.println(String.format("stack pop: %35s%15s",stack.pop() ,tokenElement.token ) );
     if(tokenElement.token.equals("<ID>"))latestID=tokenElement.regex;
     latestPopRegex = tokenElement.regex;
     tokenElement = lex.nextToken();
     if(tokenElement!=null)token = tokenElement.token;
    }else{
     System.out.println("Fatal error: Expecting '"+ X + "' but encountered '"+token+"' instead.");
     System.exit(-1);
    }
   }else{
    if(!empty(X)){
     //NONTERMINALS
     //System.out.println("X = "+X + " token = "+token);
     StringBuilder build = new StringBuilder();
     build.append(X+","+token);
     if(!prTable.containsKey(build.toString() ) ){
      System.out.println("Fatal error: PredictTable is undefined for "+ build.toString() );
      System.exit(-1);
     }
     String productions[] = prTable.get(build.toString() );
     System.out.println(String.format("stack pop: %35s%15s",stack.pop() ,tokenElement.token ) );
     for(int i = productions.length-1; i>=0; i--){
      if(productions[i].equals("<error>")){
       System.out.println("*** "+productions[i]+" *** with key "+build.toString() );
       System.exit(-1);
      }
      System.out.format ("stack push: %35s%15s\n",productions[i], tokenElement.regex);
      
      stack.push(productions[i]);
     }
    }else{
     //POPPING EMPTY
     System.out.format("Stack pop:%35s<empty>\n","");
     stack.pop();
    }

   }
   if(dollar(X) && !lex.hasNext() )break;
  }
 }
 
 /**
 * helper function for the class
 * @param string
 */
 private boolean empty(String X){
  return X.equals("<empty>");
 }
 /**
 * Deprecated method
 */
 private void printError(){
  System.out.println("ERROR.");
 }
 private boolean dollar(String X){
  return X.equals("$");
 }
 private boolean terminal(String X){
  Object arrTerms[] = arrTerminals.toArray();
  for(Object str1: arrTerms){
   String str = (String)str1;
   if(str.equals(X)) return true;
  }
  return false;
 }
 
 /**
  * This method determines whether or not a LHS variable is a procedure.
  * If so then we ask icg to jump to procedure code
  */
 
 private void checkIfInProc(String latestLHSVar){
   if(!listOfProc.contains(latestLHSVar) ){
     return;
   }
   //implement code that executes procedure.
 }

 /**
 * inserts strings into arrTerminals which help define
 * terminals from non terminals.
 */
 private void addAllTerminals(){
  arrTerminals.add("<AND>");
  arrTerminals.add("<Array>");
  arrTerminals.add("<BEGIN>");
  arrTerminals.add("<CHAR>");
  arrTerminals.add("<CHR>");
  arrTerminals.add("<DIV>");
  arrTerminals.add("<DO>");
  arrTerminals.add("<ELSE>");
  arrTerminals.add("<END>");
  arrTerminals.add("<IF>");
  arrTerminals.add("<INTEGER>");
  arrTerminals.add("<STRING>");
  arrTerminals.add("<MOD>");
  arrTerminals.add("<NOT_OP>");
  arrTerminals.add("<OF>");
  arrTerminals.add("<OR>");
  arrTerminals.add("<ORD>");
  arrTerminals.add("<PROCEDURE>");
  arrTerminals.add("<PROGRAM>");
  arrTerminals.add("<READLN>");
  arrTerminals.add("<READ>");
  arrTerminals.add("<THEN>");
  arrTerminals.add("<VAR>");
  arrTerminals.add("<WHILE>");
  arrTerminals.add("<WRITELN>");
  arrTerminals.add("<WRITE>");
  arrTerminals.add("<ADD_OP>");
  arrTerminals.add("<SUBTRACT_OP>");
  arrTerminals.add("<MULT_OP>");
  arrTerminals.add("<DIV_OP>");
  arrTerminals.add("<LESSEQUAL>");
  arrTerminals.add("<LESS>");
  arrTerminals.add("<NOTEQUAL>");
  arrTerminals.add("<GREATEREQUAL>");
  arrTerminals.add("<GREATER>");
  arrTerminals.add("<EQUAL>");
  arrTerminals.add("<ASSIGN>");
  arrTerminals.add("<COLON>");
  arrTerminals.add("<SEMICOLON>");
  arrTerminals.add("<COMMA>");
  arrTerminals.add("<LPAREN>");
  arrTerminals.add("<LBRACK>");
  arrTerminals.add("<RPAREN>");
  arrTerminals.add("<RBRACK>");
  arrTerminals.add("<PERIOD>");
  arrTerminals.add("<ID>");
  arrTerminals.add("<NUMBER>");
  arrTerminals.add("<QUOTESTR>");
  arrTerminals.add("<LITCHAR>");
 }
 
 
}



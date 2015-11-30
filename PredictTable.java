/**
* This is a helper class for the Parser to hold the 
* prediction set. This class defined by the CSV files
* pascal.ebnf and pascal.EmptyProductions.ebnf. 
* The pascal.ebnf file is the predict set. The first
* argument is the nonterminal and the second is a
* terminal.
*@author: Monilito Castro
*@version 2.0 Build 2 November 10, 2015
*/
import java.util.*;
import java.lang.StringBuilder;
import java.io.File;
import java.io.FileNotFoundException;

public class PredictTable{
 private Scanner scanner;
 private File file;
 private Hashtable<String, String[]> table;
 private ArrayList<String> arrTerminals;
 private ArrayList<String> arrProds;

 /**
 * This constructor will create a new predict table using
 * Hashtable. To access the LinkedList of SymbolAttributes
 * that it holds you must append the nonterminal and terminal
 * keys to each other, respectively. Then use that to access
 * the linked list.
 */
 PredictTable(){
  table = new Hashtable<String, String[]>();
  arrTerminals = new ArrayList<String>();
  arrProds = new ArrayList<String>();
  initProdsAndTerms();
  initTable();
  initEmptyProductions();
  initRemainingProductions();
 }
 protected void finalize(){
 }
  /**
  * This method is no longer needed.
  * @Deprecated.
  */
 public String[] get(String key){
  return table.get(key);
 }
 public boolean containsKey(String key){
  return table.containsKey(key);
 }

 public boolean equals(String X, String token, String[] strArr){
  //where X is the lhs of production, token is member of sigma*
  //and arrayRepresentationOfProd is RHS of production (a list)
  StringBuilder keyBuild = new StringBuilder();
  keyBuild.append(X.trim()+","+token.trim());
  String key = keyBuild.toString();
  if(!table.containsKey(key)){
   System.out.println("ERROR: key is not in predict table");
   return false;
  }
  /*
  if(!table.contains(key) ){
   System.out.println("ERROR: value is not in predict table");
   return false;
  }*/
  return Arrays.equals(table.get(key),strArr);
 }

 /**
 * This method inserts the production elements onto the stack.
 */
 private void initRemainingProductions(){
  openFile("pascal.ebnf");

  //get the LHS of the production (1 item)
  //get element from predict set
  //get the RHS of the production (the remaining)
  String str[] = getLineSplit();
  while(str!=null){
   String lhsProd = str[0];
   String elemSigma = str[1];
   String rhsProd[];
   rhsProd = rhsProductions(str);
   StringBuilder build = new StringBuilder();
   build.append(lhsProd.trim() + ","+elemSigma.trim() );
   table.remove(build.toString() );
   table.put(build.toString(), rhsProd );
   str = getLineSplit();
  }
  closeFile();
  

 }
 
 public String toString(){
  StringBuilder result = new StringBuilder();
  Enumeration e = table.keys();
     while (e.hasMoreElements()) {
       String key = (String) e.nextElement();
       StringBuilder build = new StringBuilder();
       build.append("[ ");
       for(String s : table.get(key)){
        build.append(s + " ");
       }
       build.append("]");
       result.append(key + " : " + build.toString()+"\n");
     }
  return result.toString();
 }
 
 private void openFile(String fileName){
  try {
   file = new File(fileName);
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
  }
  /**
  * works with CSV files. Source strings from
  * pascal.ebnf and the EmptyProductions use this to easily
  * parse data needed for making transitions
  */
  private String [] getLineSplit(){
   if(scanner.hasNext() ){
    return scanner.next().split(",");
 
   }
   //    WARNING: guard against this return value
  return null;
  }
  
  private void closeFile(){
   scanner.close();
  }

  /**
  * productions start on the second index. At the places before this
  * method is used, the following have happened:
  * the predict set has been associated ertirely of <error>.
  * tags.
  */
  private String[] rhsProductions(String strArr[]){
   int i = strArr.length-2;
   String[] result = new String[ i ];
   for(int j = 0; j < i; j++){
    result[j] = strArr[j+2];
   }
   return result;
  }

  /**
  * This method defines the empty transitios in the grammer.
  */

  private void initEmptyProductions(){
   openFile("Pascal.EmptyProductions.ebnf");
   //System.out.println("Opening empty production ebnf file.");

  //get the LHS of the production (1 item)
  //get element from predict set
  String str[] = getLineSplit();
  while(str!=null){
   String lhsProd = str[0];
   String elemSigma = str[1];
   StringBuilder build = new StringBuilder();
   build.append(lhsProd.trim() + ","+elemSigma.trim() );
   table.remove(build.toString() );
   String empty [] = {"<empty>"};
   table.put(build.toString(), empty );
   str = getLineSplit();
  }
  closeFile();
  
  }
  /**
  * This table makes a Cartesian relationship with tokens.
  */
  private void initTable(){
   Object terms [] = arrTerminals.toArray();
   Object prods [] = arrProds.toArray();
   int size_t = terms.length;
   int size_p = prods.length;
   for(int i = 0; i <size_t; i++){
    for(int j = 0; j <size_p; j++){
     StringBuilder build = new StringBuilder();
     //if(terms[i].equals("<CALL>") ){System.out.println("CALLLLLLLLLLLLLLL!!!!") };
     build.append( ((String)prods[j]).trim()+","+((String)terms[i]).trim() );
     String e[] = {"<error>"};
     table.put(build.toString(), e);
    }
   }
  }

  /**
  * There are two different arraylist for this method: arrProds
  * and arrTerms.
  */
  private void initProdsAndTerms(){
   arrProds.add("<program>");
  arrProds.add("<block>");
  arrProds.add("<variable_declaration_part>");
  arrProds.add("<var_decl_atom>");
  arrProds.add("<variable_declaration>");
  arrProds.add("<id_comma_atom>");
  arrProds.add("<type>");
  arrProds.add("<array_type>");
  arrProds.add("<index_range>");
  arrProds.add("<simple_type>");
  arrProds.add("<type_identifier>");
  arrProds.add("<procedure_declaration_part>");
  arrProds.add("<procedure_declaration>");
  arrProds.add("<statement_part>");
  arrProds.add("<compound_statement>");
  arrProds.add("<semicolon_statement_atom>");
  arrProds.add("<statement>");
  arrProds.add("<simple_statement>");
  arrProds.add("<assignment_statement>");
  arrProds.add("<procedure_statement>");
  arrProds.add("<procedure_identifier>");
  arrProds.add("<read_statement>");
  arrProds.add("<comma_inputvariable_atom>");
  arrProds.add("<input_variable>");
  arrProds.add("<write_statement>");
  arrProds.add("<comma_outputvariable_atom>");
  arrProds.add("<output_value>");
  arrProds.add("<structured_statement>");
  arrProds.add("<if_statement>");
  arrProds.add("<while_statement>");
  arrProds.add("<expression>");
  arrProds.add("<simple_expression>");
  arrProds.add("<adding_operator_atom>");
  arrProds.add("<term>");
  arrProds.add("<mult_operator_atom>");
  arrProds.add("<factor>");
  arrProds.add("<relational_operator>");
  arrProds.add("<sign>");
  arrProds.add("<adding_operator>");
  arrProds.add("<multiplying_operator>");
  arrProds.add("<variable>");
  arrProds.add("<indexed_variable>");
  arrProds.add("<array_variable>");
  arrProds.add("<entire_variable>");
  arrProds.add("<variable_identifier>");
  arrProds.add("<constant>");

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
  arrTerminals.add("<CALL>");

  }
}

/**
* This ICGenerator stand for Intermediate code generator.
* The compiler outputs MIPS code. MIPS has a small granularity
* so the language can be used both for implementation and
* intermediation. 
*@author: Monilito Castro
*@version 2.0 Build 2 November 10, 2015
*/
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Hashtable;
import java.lang.StringBuilder;
import java.lang.String;

public class ICGenerator{
 private boolean routine = false;
 private Hashtable<String, LinkedList<SymbolAttributes> > symbolTable;
 private StringBuilder build;
 private StringBuilder dataBuild;
 private String endingLabel = "";
 private int postfix=0; private String lastStringCreated;
 ICGenerator(Hashtable<String, LinkedList<SymbolAttributes> > s){
  symbolTable=s;
  build = new StringBuilder();
  dataBuild = new StringBuilder();
 }
 /**
  * this method jumps to a labeled procedure
  * 
  */
 public void callProcedure(String name){
   build.append("jal label"+name+"\n");
 }
 /*
  * This method inserts a named label so that mips could jump to section. 
  * @param string label take from procedure id or program name
  */
 public void addAddressLabel(String labelName){
   if(endingLabel.equals(labelName)){
     routine = false;
     build.append("start:\t\t#START\n");
     return;
   }
   endingLabel = labelName;
   build.append("label"+labelName+":\t\t#start of PROCEDURE\n");
   //push $31 into stackframe and increment stackoffset
   build.append("la $t3, stackframe\n");
   build.append("lw $t4, stackoffset\n");
   build.append("add $t3, $t3, $t4\n");
   build.append("sw $31, 0($t3)\n");
   build.append("add $t4, $t4, 4\n");
   build.append("sw $t4, stackoffset\n");
 }
 /**
 * This method will return the assembly code representation of the
 * Pascal code you provide.
 * @param none
 */

 public String compile(){
  dataBuild.append("stackframe:  .word    0:400\nstackoffset: .word 0:1\n");   //bytesize elements
  dataBuild.append(build.toString());
  return dataBuild.toString();
 }

 /**
 * This method ensures that a unique memory label is given to
 * the callee. MIPS allows for labels rather than just using
 * mnemonics.
 * @param to create new key for 
 */

 private String genRAMaddr(String key){
  StringBuilder result = new StringBuilder();
  result.append(key + postfix++);
  return result.toString();
 }

 /**
 * This method will insert the memory address for the given
 * name into the data block of the assembly file.
 * @param name of variable
 */
 public void newVariable(String name){
  if(symbolTable.contains(name)){
   SymbolAttributes attr = getVariableAttribute(name);
   attr.memAddress = genRAMaddr(name);
  }
 }

 /**
 * This method returns the Symbol Attributes of the table.
 * @param name of variable to get
 */
 public SymbolAttributes getVariableAttribute(String name){
  //System.out.println("Trying to find "+name +" in Symbol Table");
  //System.out.println(symbolTable.toString());
  if(symbolTable.containsKey(name)){
   LinkedList<SymbolAttributes> llAttr = symbolTable.get(name);
   Iterator it = llAttr.iterator();
   
   while(it.hasNext()){
    SymbolAttributes item = (SymbolAttributes)it.next();
    if(item.tokenType.equals("INTEGER_ID")){
     return item;
    }else if(item.tokenType.equals("INTEGER_ARRAY_ID")){
     return item;
    }else if(item.tokenType.equals("INTEGER_CHAR_ID")){
     return item;
    }
   }
  }
  System.out.println("ERROR: ICGenerator doesn't have " + name + " token.");
  return null;
 }

 public String compare(String compCommand){
  popSelectByte(1);
  popSelectByte(0);
  build.append("\t\t\t#COMPARISON\n");
  String name = genRAMaddr("NOT_IF");
  build.append(String.format(compCommand+" $t0, $t1, "+name+"\n") );
  
  //pushSelectByte(0);
  
  return name;
 }
 
 public void not_if(String name){
   build.append(name+":\t\t#NOT IF\n");
 }
 public void writeString(){
  build.append(String.format("li $v0, 4\nla $a0, %s\nsyscall\n", lastStringCreated));
 }

 public void writeInteger(String name){
  String memAddr = getVariableAttribute(name).memAddress;
  build.append(String.format("li $v0, 1\nlw $a0, %s\nsyscall\n", memAddr));
 }

 public void exit(){
  build.append("li $v0, 10\nsyscall\n");
 }

 public void readInteger(String name){
  String memAddr = getVariableAttribute(name).memAddress;
  build.append(String.format("li $v0,5\nsyscall\nsw $v0, %s\n", memAddr));
 }
 
 public void dataArray(SymbolAttributes sa){
 	String id = genRAMaddr("array_"+sa.optionalImage);
 	sa.memAddress = id;
 	//Since the range op a..b is inclusive there must be 1 more
 	dataBuild.append(String.format("%s:\t.word\t\t0:%d\n", id, ((sa.rbound-sa.lbound)+1) ) );
 }
 
 public void dataString(String str){
  String ascii = "ascii";
  String var = lastStringCreated = genRAMaddr(ascii);
  //quote symbols are not needed since regex carries it.
  dataBuild.append(String.format("%s:\t.asciiz\t\t%s\n", var, str ) );
 }

 public void dataInteger(String name){
  SymbolAttributes attr = getVariableAttribute(name);
  String id = genRAMaddr(name);
  attr.memAddress = id;
  dataBuild.append(String.format("%s:\t.word\t\t0\n",id) );
 }

 public void dotData(){
  dataBuild.append(String.format(".data\n"));
 } 
 public void dotText(){
  build.append(String.format(".text\nj start\n"));
 }
 public void notInSubRoutine(){  
   if(routine){
     build.append("lw $t4, stackoffset\t\t#end of PROCEDURE\n");
     build.append("sub $t4, $t4, 4\n");
     build.append("sw $t4, stackoffset\n");
     build.append("la $t3, stackframe\n");
     build.append("add $t3, $t3, $t4\n");
     build.append("lw $31, 0($t3)\n");
     
     build.append("jr $31\n");
   }
 }
 public void changeRoutineState(boolean t){
   routine = t;
 }
 public void loadByte(String name){
  String memAddr = getVariableAttribute(name).memAddress;
  build.append(String.format("lw $t0, %s\n", memAddr));
  //pushByte();
 }
 
 public void loadVariable(String name){
  String memAddr = getVariableAttribute(name).memAddress;
  build.append(String.format("lw $t0, %s\n", memAddr));
  pushByte();
 }
 
 public void loadVariableArray(String name, String memAddr, String strIndex){
  //String memAddr = getVariableAttribute(name).memAddress;
  String adjIndex = getAdjustedArrayIndex(name, strIndex);
  build.append(String.format("lw $t4, %s\n", memAddr) );
  build.append(String.format("lw $t0, %s($t4)\t\t#loadVariableArray\n", adjIndex ) );
  pushByte();
 }

 public void storeByte(String name){
  String ram_dest = getVariableAttribute(name).memAddress;
  build.append(String.format("sw $t0, %s\n", ram_dest));
 }

 public void storeArray(String name, String strIndex){
  String ram_dest = getVariableAttribute(name).memAddress;
  String adjIndex = getAdjustedArrayIndex(name, strIndex);
  build.append(String.format("lw $t4, %s\n", ram_dest) );
  build.append(String.format("sw $t0, %s($t4)\t\t#storeArray\n", adjIndex));
 }

 public void loadImm(String value){
  build.append(String.format("li $t0, %s\n ", value));
  pushByte();
 }

 public void add(){
  popSelectByte(1);
  popSelectByte(0);
  build.append("\t\t\t#add\n");
  build.append(String.format("add $t0, $t0, $t1\n") );
  pushSelectByte(0);
 }

 public void subtract(){

  
  popSelectByte(1);
  popSelectByte(0);
  build.append("\t\t\t#subtract\n");
  build.append(String.format("sub $t0, $t0, $t1\n") );
  pushSelectByte(0);
 }

 public void multiply(){
  
  popSelectByte(1);
  popSelectByte(0);
  build.append("\t\t\t#multiply\n");
  build.append(String.format("mult $t0, $t1\nmflo $t0\n"));
  pushSelectByte(0);
 }

 public void divide(){
  
  popSelectByte(1);
  popSelectByte(0);
  build.append("\t\t\t#divide\n");
  build.append(String.format("div $t0, $t1\nmflo $t0\n"));
  pushSelectByte(0);
 }

 public void pushByte(){
  build.append("\t\t\t#pushByte\n");
  build.append(String.format("addi $sp, $sp, -4\nsw $t0, 0($sp)\n"));

 }

 public void popByte(){
  build.append("\t\t\t#popByte\n");
  build.append(String.format("lw $t0, 0($sp)\naddi $sp, $sp, 4\n"));

 }

 public void pushSelectByte(int i){
  build.append("\t\t\t#pushSelectByte("+i+")\n");
  build.append(String.format("addi $sp, $sp, -4\nsw $t%d, 0($sp)\n",i));

 }

 public void popSelectByte(int i){
  build.append("\t\t\t#popSelectByte("+i+")\n");
  build.append(String.format("lw $t%d, 0($sp)\naddi $sp, $sp, 4\n",i));

 }
 
 private String getAdjustedArrayIndex(String name, String index1){
   int index = Integer.parseInt(index1);
   SymbolAttributes item = null;
   if(symbolTable.containsKey(name)){
   LinkedList<SymbolAttributes> llAttr = symbolTable.get(name);
   Iterator it = llAttr.iterator();
   
   while(it.hasNext()){
    item = (SymbolAttributes)it.next();
    if(item.tokenType.equals("INTEGER_ARRAY_ID")  | item.tokenType.equals("INTEGER_ARRAY_ID") ){
     break;
    }
   }
   }
   int lb = item.lbound;
   int rb = item.rbound;
   if((lb <= index) & (index <= rb)){
      StringBuilder b = new StringBuilder();
      b.append(""+(index - lb));
      return b.toString();
   }else{
      System.out.println("Fatal Error: array '"+name+"' index is out of bounds.");
      System.exit(0);
   }
   
   return null; 
}
 
 
 /*
   if(symbolTable.containsKey(name)){
   LinkedList<SymbolAttributes> llAttr = symbolTable.get(name);
   Iterator it = llAttr.iterator();
   
   while(it.hasNext()){
    SymbolAttributes item = (SymbolAttributes)it.next();
    if(item.tokenType.equals("INTEGER_ID")){
     return item;
    }else if(item.tokenType.equals("INTEGER_ARRAY_ID")){
     return item;
    }else if(item.tokenType.equals("INTEGER_CHAR_ID")){
     return item;
    }
   }
  }
  System.out.println("ERROR: ICGenerator doesn't have " + name + " token.");
  return null;
 */


}

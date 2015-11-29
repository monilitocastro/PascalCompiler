/**
*
*@author: Monilito Castro
*@version 2.0 Build 2 November 10, 2015
*/
import java.lang.StringBuilder;
public class SymbolAttributes{
   public String tokenType = "";     //variable, function, ...
   public String optionalImage = "";
   public int tokenIndex = -1;
   public int tokenListStart= -1;
   public int tokenListEnd= -1;
   public String memAddress;
   public String toString(){
    StringBuilder build = new StringBuilder();
    build.append("<"+ tokenType + ":" + optionalImage+">");
    return build.toString();
   }
}
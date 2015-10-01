import java.lang.StringBuilder;
public class SymbolAttributes{
	  public String tokenType = "";     //variable, function, ...
	  public String optionalImage = "";
	  public int tokenIndex = -1;
	  public int tokenListStart= -1;
	  public int tokenListEnd= -1;
	  public String toString(){
	  	StringBuilder build = new StringBuilder();
	  	build.append("<"+ tokenType + ":" + optionalImage+"> ");
	  	return build.toString();
	  }
}
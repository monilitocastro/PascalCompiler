/**
 * CSCI 465
 * @author Monilito Castro
 * email: monilito.castro@my.und.edu
 * Pascal module
 */
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Tokenizer {
	private LinkedList<TokenListElement> tokenList;
	private LinkedList<PatternListElement> patterns;
	private String source;
	Tokenizer(String src){
		source = src.trim();
		tokenList = new LinkedList<TokenListElement>();
		patterns = new LinkedList<PatternListElement>();
	}
	public void tokenize(){
		if(source==null){
			System.out.println("Source is empty. Exiting.");
			System.exit(0);
		}
		if(patterns.isEmpty()){
			System.out.println("Patterns list is empty. Exiting.");
			System.exit(0);
		}
		boolean hasError = false;
		while(!source.equals("")){
			boolean matchFound = false;
			/*  if integer of Token should not correspond with order within LinkedList of patterns
			*   then replace Pattern pat with class that holds Pattern object with integer object.
			*/
			int biggestSize = 0;
			int bigIndex = 0;
			String tag = "";
			PatternListElement patternListElem;
			for(int i = 0; i < patterns.size(); i++){
			        patternListElem = patterns.get(i);
				Pattern pat = patternListElem.pattern;
				tag = patternListElem.token;
				Matcher m = pat.matcher(source);
				if(m.find()){
					if(tag.equals("<ILLEGAL>")){
						hasError = true;
					}else{
						if (hasError){
							matchFound =false;
							break;
						}
						hasError = false;
					}
					if(biggestSize <= m.end() ){
						bigIndex = i;
						matchFound = true;
						System.out.println("index = "+bigIndex);
					}
					tag.replaceAll("\\t","");
					String tok = m.group().trim();
					source = m.replaceFirst("").trim();
					tokenList.add(new TokenListElement( tok, tag ));
					matchFound = true;
					break;
				}
			}
			if(!matchFound){
				System.out.println("ERROR: Symbol not defined.");
				return;
			}
		}
	}
	public void addPattern(String regex, String tkn){
		patterns.add( new PatternListElement(Pattern.compile( "^("+regex+")") , tkn ) );
	}
	public LinkedList<TokenListElement> getTokens(){
		return tokenList;
	}
}

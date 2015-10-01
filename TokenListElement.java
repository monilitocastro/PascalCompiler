/**
 * CSCI 465
 * @author Monilito Castro
 * email: monilito.castro@my.und.edu
 * Pascal: Lexer module
 */
public class TokenListElement {
	public String regex;
	public String token;
	TokenListElement(String r, String t){
		regex = r;
		token = t;
	}
}

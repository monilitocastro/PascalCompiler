/**
 * Helper class for the Lexer and Parser. This is used so that the
 * image (given by regex) and token t can be accessed easily.
 * It is a recursive class for a recursive grammar.
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

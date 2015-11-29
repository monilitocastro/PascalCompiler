/**
 * CSCI 465
 * @author Monilito Castro
 * email: monilito.castro@my.und.edu
 * Pascal: Lexer module
 */
import java.util.regex.Pattern;


public class PatternListElement {
 Pattern pattern;
 String token;
 PatternListElement(Pattern p, String tkn){
  pattern = p;
  token = tkn;
 }
 public String toString(){
  return token;
 }
}

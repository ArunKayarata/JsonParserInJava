package lexer;



import java.util.ArrayList;
import java.util.List;


public class Tokenizer {
    public final String  input;
    private  int index ;
    private  int  startPostion=0;

    public Tokenizer(String a){
        this.input=a;
        this.index=0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (!isEnd()) {
            char c = input.charAt(index);
            if(Character.isWhitespace(c)){
                index++;
                continue;
            }

            if(c=='{') {
                tokens.add(new Token(TokenType.LBRACE, "{", index));
                index++;
                continue;
            }
            if(c=='}'){
                tokens.add(new Token(TokenType.RBRACE,"}",index));
                index++;
                continue;
            }
            if(c=='"'){
                String str =readString();
                tokens.add(new Token(TokenType.STRING, str,startPostion));
                index++;
                continue;
            }
            if(c==':'){
                tokens.add(new Token(TokenType.COLON,":",index));
                index++;
                continue;
            }
            if(c=='['){
                tokens.add(new Token(TokenType.LBRACKET,"[",index));
                index++;
                continue;
            }
            if(c==']'){
                tokens.add(new Token(TokenType.RBRACKET,"]", index));
                index++;
                continue;
            }
            if(c==','){
                tokens.add(new Token(TokenType.COMMA,",",index));
                index++;
                continue;
            }

            if(c=='t' && startsWith("true",index)){
                tokens.add(new Token(TokenType.TRUE,"true",index));
                index+=4;
                continue;
            }
            if(c=='f'  && startsWith("false",index)){
                tokens.add(new Token(TokenType.FALSE,"false",index));
                index+=5;
                continue;
            }
            if(c=='n'  && startsWith("null",index)){
                tokens.add(new Token(TokenType.NULL,"null",index));
                index+=4;
                continue;
            }
            if(c == '-' || (c >='0'&& c<='9')){
                String num = readNumber();
                    tokens.add(new Token(TokenType.NUMBER,num,startPostion));
                    continue;
            }

            //Anything else is invalid for step1
            throw new RuntimeException("Unexpected Character at position " + index + " is found.");
        }
         tokens.add(new Token(TokenType.EOF,"End of file",index));
        return tokens;
    }


    private boolean startsWith(String match , int pos){
        return input.regionMatches(pos,match,0,match.length());
    }
    private String readNumber() {
        startPostion=index;
        int i = index;
        int n = input.length();
        if(i<n && input.charAt(i)=='-') i++;
        if(i<n && input.charAt(i)=='0'){
            i++;
            if (i < n && Character.isDigit(input.charAt(i))) {
                throw new RuntimeException("Leading zeros not allowed at position " + startPostion);
            }
        }else{
            if(i>=n || !Character.isDigit(input.charAt(i))){
                throw  new RuntimeException("Number format is invalid at position: "+ index);
            }
            while(i<n && Character.isDigit(input.charAt(i))){
                i++;
            }
        }

        // fractional part
        if (i < n && input.charAt(i) == '.') {
            i++;
            if (i >= n || !Character.isDigit(input.charAt(i))) {
                throw new RuntimeException("Invalid fractional part in number at position " + index);
            }
            while (i < n && Character.isDigit(input.charAt(i))) i++;
        }

        String lexeme = input.substring(startPostion, i);
        // advance index to i (do not skip next char)
        index = i;
        return lexeme;



    }
//
//    private boolean checkisInteger(String str) {
//        try{
//            Integer num = Integer.parseInt(str);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    private boolean checkisdouble(String str) {
//        try{
//            Double num = Double.parseDouble(str);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }

    private String  readString() {

        index++;
        startPostion = index;
        StringBuilder sb = new StringBuilder();

        while(!isEnd()){
            if(input.charAt(index)=='"'){
                return sb.toString();
            }
            char ch = input.charAt(index);
            if(ch=='\\'){
                index++;
                if (isEnd()) throw new RuntimeException("Unterminated escape at " + index);
                char esc = input.charAt(index);
                switch (esc) {
                    case '"': sb.append('"'); index++; break;
                    case '\\': sb.append('\\'); index++; break;
                    case '/': sb.append('/'); index++; break;
                    case 'b': sb.append('\b'); index++; break;
                    case 'f': sb.append('\f'); index++; break;
                    case 'n': sb.append('\n'); index++; break;
                    case 'r': sb.append('\r'); index++; break;
                    case 't': sb.append('\t'); index++; break;
                    case 'u':
                        // read 4 hex digits; validate length
                        if (index + 4 >= input.length()) throw new RuntimeException("Invalid unicode escape at " + index);
                        String hex = input.substring(index + 1, index + 5);
                        int code;
                        try { code = Integer.parseInt(hex, 16); }
                        catch (NumberFormatException e) { throw new RuntimeException("Invalid unicode escape at " + index); }
                        sb.append((char) code);
                        index += 5; // consumed 'u' and 4 hex digits
                        break;
                    default:
                        throw new RuntimeException("Invalid escape \\" + esc + " at position " + index);
                }
                continue;
            }

            sb.append(ch);
            index++;

        }
        throw new RuntimeException("Found Invalid format with no string ending quotes  from startPosition: " + startPostion);
    }


    private boolean isEnd () {
        if (index < input.length()) {
            return false;
        }
        return true;
    }
}

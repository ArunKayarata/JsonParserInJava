package lexer;

public class Token {
    public final TokenType type;
    public final String lexeme;
    public final int index;

    public Token(TokenType type, String lexeme, int index) {
        this.type = type;
        this.lexeme = lexeme;
        this.index = index;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                ", index=" + index +
                '}';
    }
}

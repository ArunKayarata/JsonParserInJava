package lexer;

public enum TokenType {
    LBRACE,    // {
    RBRACE,    // }
    LBRACKET,  // [
    RBRACKET,  // ]
    COMMA,     // ,
    COLON,     // :
    STRING,    // "..."
    NUMBER,    // 0-9+
    TRUE,      // true
    FALSE,     // false
    NULL,      // null
    EOF        // End of file/input
}

package parser;

import lexer.Token;
import lexer.TokenType;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public List<Token> tokens;
    public int index=0;
    public Parser(List<Token> token){
        this.tokens=token;
    }
    public JsonValue parse(){
        JsonValue v = parseValue();
        expect(TokenType.EOF);
        return v;


    }

    private JsonValue parseValue() {
        Token t = peek();
        TokenType type = t.getType();

        switch (type) {
            case STRING: {
                Token s = advance();
                return new JsonString(s.getLexeme());
            }
            case NUMBER: {
                Token n = advance();
                // prefer to parse here: new JsonNumber(new BigDecimal(n.getLexeme()))
                return new JsonNumber(new BigDecimal(n.getLexeme()));
            }
            case TRUE: {
                advance();
                return new JsonBoolean(true);
            }
            case FALSE: {
                advance();
                return new JsonBoolean(false);
            }
            case NULL: {
                advance(); // CRITICAL: consume null token
                return new JsonNullable(); // or JsonNull.INSTANCE
            }
            case LBRACE: {
                return parseObject(); // parseObject consumes '{' itself
            }
            case LBRACKET:{
                return  parseArray();
            }
            default:
                throw new RuntimeException("Parse error at index " + t.getIndex()
                        + ": unexpected token " + type + " (lexeme='" + t.getLexeme() + "')");
        }
    }

    private JsonArray parseArray() {
        expect(TokenType.LBRACKET);
        List<JsonValue> items = new ArrayList<>();
        if(peek().getType()==TokenType.RBRACKET){
            expect(TokenType.RBRACKET);
            return new JsonArray(items);
        }
        while(true){
            JsonValue item = parseValue();
            items.add(item);
            Token next = peek();
            if(next.getType()==TokenType.COMMA){
                advance();
                if(peek().getType()==TokenType.RBRACKET){
                    throw  new RuntimeException("Trailing comma not allowed in array at index " + peek().getIndex());
                }
            }else if (peek().getType()==TokenType.RBRACKET){
                advance();
                break;
            }else{
                throw new RuntimeException("Found Incorrect format at position: "+peek().getIndex());
            }
        }
        return new JsonArray(items);


    }


    private JsonValue parseObject() {
        expect(TokenType.LBRACE);
        Map<String,JsonValue> members = new LinkedHashMap<>(); // why LinkedHashmap we have to preserve the order of the insertion

        //Empty object
        if(peek().getType() == TokenType.RBRACE){
            expect(TokenType.RBRACE);
            return new JsonObject(members);
        }
        while(true) {
            Token keyTok = peek();
            if (keyTok.getType() != TokenType.STRING) {
                throw new RuntimeException("Invalid format At : "+ keyTok.getIndex());
            }
            keyTok = advance();            // now we have the key token and index advanced
            String key = keyTok.getLexeme();
            expect(TokenType.COLON);
            JsonValue value = parseValue();
            members.put(key, value);
            Token next = peek();
            if (next.getType() == TokenType.COMMA) {
                advance(); // consume comma
                // ensure not trailing comma: next must be STRING
                if (peek().getType() == TokenType.RBRACE) {
                    throw new RuntimeException("Trailing comma not allowed at position " + peek().getIndex());
                }
                continue; // parse next pair
            } else if (next.getType() == TokenType.RBRACE) {
                advance(); // consume '}' and finish
                break;
            } else {
                throw new RuntimeException("Expected ',' or '}' at position " + next.getIndex());
            }
        }
        return new JsonObject(members);
    }

    private Token peek() {
        if (index >= tokens.size()) {
            throw new RuntimeException("Unexpected end of token stream at index " + index);
        }
        return tokens.get(index);
    }

    private Token advance() {
        if (index >= tokens.size()) {
            throw new RuntimeException("Unexpected end of token stream while advancing at index " + index);
        }
        return tokens.get(index++);
    }

    private void expect(TokenType type){
        TokenType a = peek().getType();

        if(a!= type){
            throw  new RuntimeException("Found Invalid JSON structure at position "+ index + " with string as  "+ tokens.get(index).getLexeme());
        }
        advance();


    }


}

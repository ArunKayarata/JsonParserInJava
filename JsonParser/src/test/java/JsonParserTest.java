import lexer.Token;
import lexer.Tokenizer;
import org.junit.Test;
import parser.JsonValue;
import parser.Parser;

import java.util.List;



public class JsonParserTest {

    @Test
    public void testSimpleStringObject1() {
        String input = "{\n" +
                "  \"key\": \"value\",\n" +
                "  \"key-n\": 101,\n" +
                "  \"key-o\": {},\n" +
                "  \"key-l\": []\n" +
                "}";

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens);
        JsonValue value = parser.parse();
        System.out.println("Valid JSON\n");


    }

    @Test(expected = RuntimeException.class)
    public void testSimpleStringObject() {
        String input = "{\n" +
                "  \"key1\": true,\n" +
                "  \"key2\": False,\n" +
                "  \"key3\": null,\n" +
                "  \"key4\": \"value\",\n" +
                "  \"key5\": 101\n" +
                "}\n";

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        // (2) parse
        Parser parser = new Parser(tokens);
        JsonValue value = parser.parse();
        System.out.println("Valid JSON\n");

    }


    @Test(expected = RuntimeException.class)
    public void testSimpleStringObject3() {
        String input = "{\n" +
                "  \"key\": \"value\",\n" +
                "  \"key-n\": 101,\n" +
                "  \"key-o\": {\n" +
                "    \"inner key\": \"inner value\"\n" +
                "  },\n" +
                "  \"key-l\": ['list value']\n" +
                "}\n";

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens);
        JsonValue value = parser.parse();
        System.out.println("Valid JSON\n");

    }


    @Test(expected = RuntimeException.class)
    public void testSimpleStringObject4() {
        String input = "{\n" +
                "  \"key\": \"value\",\n" +
                "  \"key-n\": 101,\n" +
                "  \"key-o\": {\n" +
                "    \"inner key\": \"inner value\"\n" +
                "  },\n" +
                "  \"key-l\": ['list value']\n" +
                "}\n";

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens);
        JsonValue value = parser.parse();
        System.out.println("Valid JSON\n");

    }
}

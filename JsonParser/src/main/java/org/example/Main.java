

package org.example;

import lexer.Token;
import lexer.Tokenizer;
import parser.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("{\"name\":{\"first name\" :\"arun\",\"last name\":\"kumar\"},\"age\":25,\"hobbies\":[\"reading\",\"playing\"]}");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens);
        JsonValue root = parser.parse();

        System.out.println("Valid JSON\n");
        printJson(root, null, 0);
    }

    // Generic recursive printer
    private static void printJson(JsonValue value, String name, int indent) {
        String prefix = (name == null) ? "" : name + " -> ";

        if (value instanceof JsonString) {
            System.out.println(getIndent(indent) + prefix + "\"" + ((JsonString) value).getValue() + "\"");
            return;
        }

        if (value instanceof JsonNumber) {
            System.out.println(getIndent(indent) + prefix + ((JsonNumber) value).getValue().toPlainString());
            return;
        }

        if (value instanceof JsonBoolean) {
            System.out.println(getIndent(indent) + prefix + ((JsonBoolean) value).getValue());
            return;
        }

        if (value instanceof JsonNullable) {
            System.out.println(getIndent(indent) + prefix + "null");
            return;
        }

        if (value instanceof JsonObject) {
            JsonObject obj = (JsonObject) value;
            if (name != null) {
                System.out.println(getIndent(indent) + name + " -> {");
            } else {
                System.out.println(getIndent(indent) + "{");
            }
            for (Map.Entry<String, JsonValue> e : obj.getMembers().entrySet()) {
                printJson(e.getValue(), e.getKey(), indent + 2);
            }
            System.out.println(getIndent(indent) + "}");
            return;
        }

        if (value instanceof JsonArray) {
            JsonArray array = (JsonArray) value;
            if (name != null) {
                System.out.println(getIndent(indent) + name + " -> [");
            } else {
                System.out.println(getIndent(indent) + "[");
            }
            List<JsonValue> items = array.getValues(); // assumes JsonArray has getItems()
            for (int i = 0; i < items.size(); i++) {
                // use index as name for elements
                printJson(items.get(i), "[" + i + "]", indent + 2);
            }
            System.out.println(getIndent(indent) + "]");
            return;
        }

        // Fallback for unknown types
        System.out.println(getIndent(indent) + prefix + "(unknown type: " + value.getClass().getSimpleName() + ")");
    }

    private static String getIndent(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
}

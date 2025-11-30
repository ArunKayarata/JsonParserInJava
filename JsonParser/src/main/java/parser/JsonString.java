package parser;

public class JsonString implements JsonValue{
    public String value;
    public JsonString(String value){
        this.value=value;
    }
    public String  getValue(){
        return value;
    }

}

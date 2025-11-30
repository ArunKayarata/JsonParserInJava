package parser;

public class JsonBoolean implements JsonValue{
    public final boolean value;
    public JsonBoolean(boolean val){
        this.value=val;
    }
    public boolean getValue(){
        return value;
    }
}

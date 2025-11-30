package parser;

import java.util.List;

public class JsonArray implements JsonValue{
   public final List<JsonValue> values;
   public JsonArray(List<JsonValue> val){
       this.values=val;
   }
   public List<JsonValue> getValues(){
       return values;
   }
}

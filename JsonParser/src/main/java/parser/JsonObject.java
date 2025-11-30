package parser;

import java.util.Map;
public final class JsonObject implements JsonValue {
    private final Map<String, JsonValue> members;
    public JsonObject(Map<String, JsonValue> members) { this.members = members; }
    public Map<String, JsonValue> getMembers() {
        return members; }
    public JsonValue getValue(String key){
        return members.get(key);
    }
}
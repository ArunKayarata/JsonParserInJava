package parser;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumber implements JsonValue{
    public final BigDecimal num;
    public JsonNumber(BigDecimal num){
        this.num= num;
    }
    public BigDecimal getValue(){
        return num;
    }
}

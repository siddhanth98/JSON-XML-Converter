import java.util.Map;
import java.util.HashMap;

public class JSONVal {
    Map<String, Object> map;

    public JSONVal() {
        this.map = new HashMap<>();
    }

    public void put(String key, Object value) {
        this.map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (String key : this.map.keySet())
            result.append("\n").append(key).append(":").append(this.map.get(key));
        result.append("\n}");
        return result.toString();
    }
}

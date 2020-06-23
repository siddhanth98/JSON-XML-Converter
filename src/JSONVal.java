import java.util.Map;
import java.util.HashMap;

public class JSONVal {
    Map<String, Object> map;

    public JSONVal() {
        this.map = new HashMap<>();
    }

    public void add(String key, Object value) {
        this.map.put("\"" + key + "\"", value.equals("null") ? null : ("\"" + value + "\""));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\t{");
        for (String key : this.map.keySet())
            result.append("\n\t").append(key).append(":").append(this.map.get(key)).append(",");
        result.deleteCharAt(result.length()-1);
        result.append("\n\t}");
        return result.toString();
    }
}

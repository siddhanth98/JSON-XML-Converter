import java.util.List;
import java.util.ArrayList;

public class JSONArr {
    List<JSONVal> jsonVals;

    public JSONArr() {
        this.jsonVals = new ArrayList<>();
    }

    public void add(JSONVal jsonVal) {
        this.jsonVals.add(jsonVal);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (JSONVal val : this.jsonVals)
            result.append("\n").append(val.toString());
        result.append("\n]");
        return result.toString();
    }
}

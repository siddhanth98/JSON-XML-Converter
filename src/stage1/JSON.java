package stage1;

public class JSON {
    String name;
    JSONVal value = null;
    JSONArr arrValue = null;

    public JSON(String name, JSONVal value) {
        this.name = "\"" + name + "\"";
        this.value = value;
    }

    public JSON(String name, JSONArr values) {
        this.name = "\"" + name + "\"";
        this.arrValue = values;
    }

    @Override
    public String toString() {
        return (
                "{\n\t" + this.name + ": " +
                        (this.arrValue == null ? this.value.toString() : this.arrValue.toString())
                + "\n}"
                );
    }
}

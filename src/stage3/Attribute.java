package stage3;

public class Attribute {
    String name;
    String value;

    Attribute(String n, String v) {
        this.name = n;
        this.value = v;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return (
                this.name + " = " + "\"" + this.value + "\""
        );
    }
}

public class Attribute {
    String name;
    String value;

    Attribute(String n, String v) {
        this.name = n;
        this.value = v;
    }

    @Override
    public String toString() {
        return (
                this.name + " = " + this.value
        );
    }


}

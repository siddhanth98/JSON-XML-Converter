public class XML {
    String tagName;
    Object value;
    Attribute attr;

    XML(String name, Object value, Attribute attr) {
        this.tagName = name;
        this.value = value;
        this.attr = attr;
    }

    @Override
    public String toString() {
        return (
                "<" + this.tagName + (this.attr != null ? " " + this.attr.toString() : "")
                        + (this.value == null ? "/>" : ">"
                        + this.value.toString() + "</" + this.tagName + ">")
        );
    }
}

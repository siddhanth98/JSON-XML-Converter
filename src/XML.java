import java.util.ArrayList;
import java.util.List;

public class XML {
    String tagName;
    Object value;
    List<Attribute> attributeList = null;

    XML(String name, Object value) {
        this.tagName = name;
        this.value = value;
    }

    public void addAttribute(Attribute attr) {
        if (this.attributeList == null)
            this.attributeList = new ArrayList<>();
        this.attributeList.add(attr);
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        result.append(this.tagName);

        if (this.attributeList != null) {
            for (Attribute attr : this.attributeList) {
                result.append(" ");
                result.append(attr.toString());
            }
        }

        if (this.value != null) {
            this.setValue(this.value.toString().replaceAll("\"", ""));
            result.append(">");
            result.append(this.value);
            result.append("</");
            result.append(this.tagName);
            result.append(">");
        }

        else
            result.append(" />");
        return result.toString();
    }
}

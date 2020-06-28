package stage2;

import java.util.ArrayList;
import java.util.List;

public class XML {
    List<XML> parents = null;
    String tagName;
    Object value;
    List<Attribute> attributes = null;
    boolean isParent = true;
    
    XML(String name, Object value) {
        this.tagName = name;
        this.value = value;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        this.isParent = parent;
    }

    public List<XML> getParents() {
        return parents;
    }

    public String getTagName() {
        return tagName;
    }

    public Object getValue() {
        this.setParent(false);
        return value;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void addParent(XML xml) {
        if (this.parents == null)
            this.parents = new ArrayList<>();
        this.parents.add(xml);
    }

    public void addAttribute(Attribute attr) {
        if (this.attributes == null)
            this.attributes = new ArrayList<>();
        this.attributes.add(attr);
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        result.append(this.tagName);

        if (this.attributes != null) {
            for (Attribute attr : this.attributes) {
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

    public String display() {
        StringBuilder result = new StringBuilder("");
        result.append("Element:\n");
        result.append("path =");
        if (this.getParents() != null) {
            for (XML xml : this.getParents())
                result.append(" ").append(xml.getTagName()).append(",");
            result.deleteCharAt(result.length()-1);
        }
        result.append(" ").append(this.getTagName()).append("\n");
        if (!this.isParent()) {
            result.append("value = ");
            if (this.value == null)
                result.append("null");
            else result.append("\"").append(this.value).append("\"");
        }
        if (this.getAttributes() != null) {
            result.append("attributes:\n");
            for (Attribute attribute : this.getAttributes())
                result.append(attribute.getName()).append(" = ").append(attribute.getValue()).append("\n");
        }
        return result.toString();
    }
}

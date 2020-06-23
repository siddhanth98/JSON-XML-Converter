import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;

public class Stage2 {
    public static void main(String[] args) {
        StringBuilder input = new StringBuilder("");
        File file = new File("./test.txt");
        String result;

        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext())
                input.append(scanner.nextLine());
        }
        catch(FileNotFoundException fio) {}
        if (input.charAt(0) == '{')
            result = convert2Xml(input.toString());
        else result = convert2Json(input.toString());
        System.out.println(result);
    }

    public static String convert2Xml(String json) {
        String xmlTagName = getJsonElementName(json); // Get the tag name
        Object xmlTagValue = getTagValue(json); // Get the tag value
        List<Attribute> attributes = getAttributes(json);

        XML xml = new XML(xmlTagName, xmlTagValue);
        for (Attribute attribute : attributes)
            xml.addAttribute(attribute);
        return xml.toString();
    }

    public static String convert2Json(String xml) {
        String xmlTagName = getXmlTagName(xml);
        String xmlValue = getXmlValue(xml);
        Map<String, String> xmlAttrs = getXmlAttributes(xml);
        JSONVal jsonVal = new JSONVal();

        if (xmlAttrs.keySet().isEmpty()) {
            jsonVal.add(xmlTagName, xmlValue);
            return jsonVal.toString();
        }
        else {
            for (String key : xmlAttrs.keySet())
                jsonVal.add("@" + key, xmlAttrs.get(key));
            jsonVal.add("#" + xmlTagName, xmlValue);
            return new JSON(xmlTagName, jsonVal).toString();
        }
    }

    public static String getJsonElementName(String json) {
        json = json.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("\"[\\w_.]+\":");
        Matcher matcher = pattern.matcher(json);
        String elementName;

        matcher.find();
        elementName = json.substring(matcher.start()+1, matcher.end()-2);
        return elementName;
    }

    public static List<Attribute> getAttributes(String json) {
        json = json.replaceAll("\n| {2,}", "");
        Pattern pattern = Pattern.compile("\"@[\\w_.]+\"\\s*:\\s*\"?[\\w_. ]+\"?");
        Matcher matcher = pattern.matcher(json);
        List<Attribute> attributes = new ArrayList<>();
        String attr, attrName, attrValue;
        String[] attrs;

        while(matcher.find()) {
            attr = json.substring(matcher.start(), matcher.end());
            attrs = attr.split(":");
            attrName = attrs[0].strip().replaceAll("[@\"]", "");
            attrValue = attrs[1].strip().replaceAll("\"", "");
            attributes.add(new Attribute(attrName, attrValue));
        }
        return attributes;
    }

    public static Object getTagValue(String json) {
        json = json.replaceAll("\n| {2,}", "");
        Pattern pattern = Pattern.compile("\"#?[\\w_.]+\"\\s*:\\s*\"?[\\w_. ]+\"?");
        Matcher matcher = pattern.matcher(json);
        String valueMapping;
        Object tagValue = null;

        while(matcher.find()) {
            valueMapping = json.substring(matcher.start(), matcher.end());
            tagValue = valueMapping.split(":")[1].strip();
        }

        tagValue = tagValue.equals("null") ? null : tagValue;
        return tagValue;
    }

    public static String getXmlTagName(String xml) {
        Pattern pattern = Pattern.compile("<[\\w_]+");
        Matcher matcher = pattern.matcher(xml);
        matcher.find();
        return xml.substring(matcher.start()+1, matcher.end());
    }

    public static String getXmlValue(String xml) {
        Pattern pattern = Pattern.compile(">.+</");
        Matcher matcher = pattern.matcher(xml);
        if (matcher.find())
            return xml.substring(matcher.start()+1, matcher.end()-2).strip();
        else return "null";
    }

    public static Map<String, String> getXmlAttributes(String xml) {
        Pattern attrPattern = Pattern.compile("[\\w_]+\\s*=\\s*\"[\\w_. -]+\"");
        Matcher attrMatcher = attrPattern.matcher(xml);
        Map<String, String> attributes = new HashMap<>();

        while(attrMatcher.find()) {
            String attr = xml.substring(attrMatcher.start(), attrMatcher.end());
            String[] attrs = attr.split("=");
            attrs[0] = attrs[0].strip();
            attrs[1] = attrs[1].strip();

            attributes.put(attrs[0], attrs[1].substring(1, attrs[1].length()-1));
        }
        return attributes;
    }
}

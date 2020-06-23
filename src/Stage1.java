import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Stage1 {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String jsonInput = "{ \"jdk\" : \"1.8.9\" }";
        String xmlInput = "<host>127.0.0.1</host>";

        System.out.println(convert2XML(jsonInput));
        System.out.println(convert2Json(xmlInput));
    }

    public static String convert2XML(String json) {
        json = json.substring(1, json.length()-1); // Strip out start and end curly braces
        String[] xmlElementVal = json.split(":"); // Split the input using ":"
        xmlElementVal[0] = xmlElementVal[0].strip();
        xmlElementVal[1] = xmlElementVal[1].strip();

        XML xml = new XML(xmlElementVal[0].substring(1, xmlElementVal[0].length()-1), // Strip out the double quotes from the tag name
                        xmlElementVal[1].equals("null") ? null : (xmlElementVal[1].substring(1, xmlElementVal[1].length()-1)));  // Strip out the double quotes from the tag value
        return xml.toString();
    }

    public static String convert2Json(String xml) {
        Pattern pattern = Pattern.compile("</?\\s*\\w+\\s*/?>");
        Matcher matcher = pattern.matcher(xml);
        String tagName, value;
        int endMatchIndex;

        matcher.find(); // Match the starting pair of tags
        endMatchIndex = matcher.end();

        if (endMatchIndex >= xml.length()) {
            tagName = xml.substring(matcher.start()+1, endMatchIndex-2);
            tagName = tagName.strip();

            // Self closing tag
            JSONVal json = new JSONVal();
            json.add(tagName, null);
            return json.toString();
        }

        else {
            // Open and closing tags are separate
            tagName = xml.substring(matcher.start()+1, endMatchIndex-1);
            matcher.find(endMatchIndex);
            value = xml.substring(endMatchIndex, matcher.start());
            JSONVal json = new JSONVal();
            json.add(tagName, value);
            return json.toString();
        }
    }
}

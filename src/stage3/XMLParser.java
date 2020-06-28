package stage3;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.util.Scanner;

public class XMLParser {
    public static void main(String[] args) {
        File file = new File("D:\\Intellij Projects\\HyperSkill Projects\\JSON-XML-Converter\\src\\stage3\\test.txt");
        StringBuilder xml = new StringBuilder();

        try(Scanner sc = new Scanner(file)) {
            while(sc.hasNext())
                xml.append(sc.nextLine());
        }
        catch(FileNotFoundException fio) {}
        parseXMLWrapper(xml.toString());
    }

    public static void parseXMLWrapper(String xmlInput) {
        xmlInput = xmlInput.replaceAll("\n\\s*", "");
        Pattern anyTagPattern = Pattern.compile("<[^<>]+>");

        Pattern tagNamePattern = Pattern.compile("<[^<>\\s]+");

        Pattern attrPattern = Pattern.compile("\\S+\\s*=\\s*\"[^\"]+\"");

        Pattern valuePattern = Pattern.compile(">([.]?|[^<>\n]+)<");

        Stack<XML> elements = new Stack<>();
        parseXML2(xmlInput, elements, anyTagPattern, tagNamePattern, attrPattern, valuePattern);
    }

    public static void parseXML2(String xmlInput, Stack<XML> elements, Pattern anyTagPattern, Pattern tagNamePattern,
                                 Pattern attrPattern, Pattern valuePattern) {
        // Get the next tag if it exists
        Matcher anyTagMatcher = anyTagPattern.matcher(xmlInput);
        if (!anyTagMatcher.find())
            return;
        String tag = anyTagMatcher.group();
        int tagEndIndex = anyTagMatcher.end()-1;

        // Get the tag name
        Matcher tagNameMatcher = tagNamePattern.matcher(tag);
        tagNameMatcher.find();
        String name = tagNameMatcher.group().replaceAll("<", "").replaceAll("/", "");
        if (!elements.empty() && elements.peek().getTagName().equals(name))
            // Closing tag
            elements.pop();
        else {
            XML xml = null;
            if (tag.endsWith("/>")) {
                // Self closing tag
                xml = new XML(name, null);
            }
            else {
                // Opening tag, check whether it is a parent tag or not
                if (anyTagMatcher.find(tagEndIndex) && anyTagMatcher.group().startsWith("</")) {
                    // Not a parent tag, get its value
                    Matcher valueMatcher = valuePattern.matcher(xmlInput);
                    String value;
                    valueMatcher.find(tagEndIndex);
                    value = valueMatcher.group().replaceAll("<", "").replaceAll(">", "");
                    System.out.println("Tag " +name+ " has value " +value);
                    xml = new XML(name, value);
                } else {
                    // Parent tag
                    xml = new XML(name, null);
                    xml.setParent(true);
                }
            }

                // Get parent nodes
                // Create a temporary stack to hold parent tags as they are popped out and added to the xml's parent list
                Stack<XML> tempStack = new Stack<>();
                while (!elements.empty()) {
                    tempStack.push(elements.peek());
                    xml.addParent(elements.pop());
                }
                // Re-populate the elements stack
                while(!tempStack.empty())
                    elements.push(tempStack.pop());

                if (xml.isParent() || xml.getValue() != null)
                    elements.push(xml);

                // Get the attributes
                Matcher attrMatcher = attrPattern.matcher(tag);
                while(attrMatcher.find()) {
                    String[] attrProps = attrMatcher.group().split("=");
                    // attrProps[0] has the attribute name and attrProps[1] has the attribute value
                    Attribute attribute = new Attribute(attrProps[0].strip(), attrProps[1].strip());
                    xml.addAttribute(attribute);
                }
            System.out.println(xml.display());
        }
        parseXML2(xmlInput.substring(tagEndIndex), elements, anyTagPattern, tagNamePattern, attrPattern, valuePattern);
    }
}

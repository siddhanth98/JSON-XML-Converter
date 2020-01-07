import java.util.HashMap;
import java.util.Scanner;

public class Stage1SimpleConverter {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String input = sc.nextLine();

        if(input.charAt(0) == '{') {
            HashMap<String, String> json = json2Xml(input);
            for(String key : json.keySet())
                if(json.get(key).equals("null"))
                    System.out.println("<" + key + "/>");
                else
                    System.out.println("<" + key + ">" + json.get(key) + "</" + key + ">");
        }

        else {
            HashMap<String, Object> json = xml2Json(input);
            for(String key : json.keySet()) {
                if(json.get(key) == null)
                    System.out.println("{\"" + key + "\":" + json.get(key) + "}");
                else
                    System.out.println("{\"" + key + "\":" + "\"" + json.get(key) + "\"}");
            }
        }
    }

    private static HashMap<String, String> json2Xml(String input) {
        HashMap<String, String> json = new HashMap<>();
        int i = 0;
        boolean key = true;
        String elementName = "", elementValue = "";

        while(i < input.length()) {
            if(key && input.charAt(i) == ':') {
                key = false;
                i++;
            }

            else if(key && elementName.length() > 0 && input.charAt(i) == '"')
            i++;

            else if(elementValue.length() > 0 && input.charAt(i) == '}') {
                elementName = elementName.strip();
                elementValue = elementValue.strip();
                json.put(elementName, elementValue);
                return json;
            }

            else if(input.charAt(i) == '{' || input.charAt(i) == '}' || input.charAt(i) == ' ' || input.charAt(i) == '"')
            i++;

            else if(key) {
                elementName += input.charAt(i);
                i++;
            }


            else {
                elementValue += input.charAt(i);
                i++;
            }
        }
        return null;
    }

    private static HashMap<String, Object> xml2Json(String input) {
        HashMap<String, Object> json = new HashMap<>();
        int i = 0;
        boolean openTag = false;
        String key = "", value = "";

        while(i < input.length()) {
            if(openTag && value.length() == 0 && input.charAt(i) == '>') {
                key = key.strip();
                json.put(key, null);
                openTag = false;
            }

            else if(openTag && value.length() == 0 && input.charAt(i) == '/') {
                key = key.strip();
                json.put(key, null);
                return json;
            }

            else if(input.charAt(i) == '<' && value.length() == 0 && !openTag)
                openTag = true;

            else if(openTag && value.length() == 0)
                key += input.charAt(i);

            else if(value.length() == 0)
                value += input.charAt(i);

            else if(input.charAt(i) == '<') {
                value = value.strip();
                json.put(key, value);
                return json;
            }

            else
                value += input.charAt(i);
            i++;
        }
        return null;
    }
}

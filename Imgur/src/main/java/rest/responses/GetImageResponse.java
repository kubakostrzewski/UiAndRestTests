package rest.responses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class GetImageResponse {

    private static JSONObject jsonResponse;
    private static JSONObject jsonData;
    private static JSONObject jsonObject;
    private static JSONArray jsonTagArray;
    private static JSONParser parser = new JSONParser();
    private static List<String> tagList;

    public static String getPropertyFromData(String response, String propertyName) throws ParseException {
        jsonResponse = (JSONObject)parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get(propertyName).toString();
    }
    public static Object getNullValuePropertyFromData(String response, String propertyName) throws ParseException {
        jsonResponse = (JSONObject)parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get(propertyName);
    }
    public static List<String> getTagList(String response) throws ParseException {
        jsonResponse = (JSONObject)parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        jsonTagArray = (JSONArray)jsonData.get("tags");
        tagList = new ArrayList<>();
        for (Object object:jsonTagArray){
            jsonObject = (JSONObject)object;
            tagList.add(jsonObject.get("name").toString());
        }
        return tagList;
    }
}

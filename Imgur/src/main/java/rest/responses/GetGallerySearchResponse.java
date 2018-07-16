package rest.responses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class GetGallerySearchResponse {

    private static JSONObject jsonResponse;
    private static JSONObject jsonObject;
    private static JSONParser parser = new JSONParser();
    private static JSONArray jsonArray;
    private static List list;

    public static List getStringPropertyListFromData(String response, String propertyName) throws ParseException {
        jsonResponse = (JSONObject)parser.parse(response);
        jsonArray = (JSONArray)jsonResponse.get("data");
        list = new ArrayList<>();
        for (Object object:jsonArray){
            jsonObject = (JSONObject)object;
            list.add(jsonObject.get(propertyName));
        }
        return list;
    }
}

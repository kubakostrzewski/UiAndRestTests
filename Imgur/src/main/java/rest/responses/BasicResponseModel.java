package rest.responses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BasicResponseModel {

    private static JSONObject jsonResponse;
    private static JSONParser parser = new JSONParser();
    private static JSONArray jsonArray;
    private static JSONObject jsonObject;

    public static String getProperty(String response, String propertyName) throws ParseException {
        jsonResponse = (JSONObject)parser.parse(response);
        return jsonResponse.get(propertyName).toString();
    }

    public static String getPropertyFromDataArray(String response, String propertyName) throws ParseException {

        jsonResponse = (JSONObject) parser.parse(response);
        jsonArray = (JSONArray) jsonResponse.get("data");
        jsonObject = (JSONObject) jsonArray.get(0);
        return jsonObject.get(propertyName).toString();
    }

    public static int getDataArraySize(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonArray = (JSONArray) jsonResponse.get("data");
        return jsonArray.size();
    }
}

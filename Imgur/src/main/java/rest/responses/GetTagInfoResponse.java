package rest.responses;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetTagInfoResponse {

    private static JSONObject jsonResponse, jsonData;
    private static JSONParser parser = new JSONParser();

    public static String getName(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("name").toString();
    }

    public static String getDisplayName(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("display_name").toString();
    }

    public static String getBackgroundHash(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("background_hash").toString();
    }

    public static String getAccent(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("accent").toString();
    }

    public static String getFollowersCount(String respone) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(respone);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("followers").toString();
    }
}

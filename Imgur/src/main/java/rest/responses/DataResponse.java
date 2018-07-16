package rest.responses;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataResponse {

    private static JSONObject jsonResponse;
    private static JSONObject jsonData;
    private static JSONParser parser = new JSONParser();

    public static String getPropertyFromData(String response, String propertyName) throws ParseException {
        jsonResponse = (JSONObject)parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get(propertyName).toString();
    }
}

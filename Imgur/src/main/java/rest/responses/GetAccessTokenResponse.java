package rest.responses;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetAccessTokenResponse {

    private static JSONObject jsonResponse;
    private static JSONObject jsonData;
    private static JSONParser parser = new JSONParser();

    public static String getTokenType(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        return jsonResponse.get("token_type").toString();
    }

    public static long getAccountId(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        return Long.parseLong(jsonResponse.get("account_id").toString());
    }

    public static String getAccountUsername(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        return jsonResponse.get("account_username").toString();
    }

    public static String getErrorMessage(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("error").toString();
    }
}

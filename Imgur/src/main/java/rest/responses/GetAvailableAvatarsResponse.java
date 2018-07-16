package rest.responses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class GetAvailableAvatarsResponse {

    private static JSONObject jsonObject;
    private static JSONObject jsonAvatarName;
    private static JSONObject jsonResponse;
    private static JSONObject jsonData;
    private static JSONArray jsonAvatarArray;
    private static JSONParser parser = new JSONParser();
    private static List<String> list;

    public static List<String> getAvatarNameList(String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject) jsonResponse.get("data");
        jsonAvatarArray = (JSONArray)jsonData.get("available_avatars");
        list = new ArrayList<>();
        for (Object object:jsonAvatarArray){
            jsonObject = (JSONObject)object;
            list.add(jsonObject.get("name").toString());
        }
        return list;
    }
}

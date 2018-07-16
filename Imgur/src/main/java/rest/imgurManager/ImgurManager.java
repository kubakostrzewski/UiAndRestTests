package rest.imgurManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImgurManager {

    private JSONParser parser = new JSONParser();
    private JSONObject jsonObject;
    private JSONObject jsonClientData;
    private JSONObject jsonAccountSettings;

    public JSONObject getTestCaseData(String testCase) throws ParseException {
        try (FileReader reader = new FileReader(this.getClass().getClassLoader().
                getResource("restTestData.json").getFile())) {
            jsonObject = (JSONObject) parser.parse(reader);
            return (JSONObject) jsonObject.get(testCase);
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
            return null;
        }
    }

    public JSONObject getAccountSettings(String testCase) throws ParseException {
        try (FileReader reader = new FileReader(this.getClass().getClassLoader().
                getResource("restTestData.json").getFile())) {
            jsonObject = (JSONObject) parser.parse(reader);
            jsonAccountSettings = (JSONObject) jsonObject.get("us03TestData");
            return (JSONObject) jsonAccountSettings.get(testCase);
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
            return null;
        }
    }

    public List<String> getJsonArray(String testCase, String jsonArray) throws ParseException {
        jsonObject = getTestCaseData(testCase);
        ArrayList<String> list = new ArrayList<>();
        JSONArray newJsonArray = (JSONArray) jsonObject.get(jsonArray);
        if (newJsonArray != null) {
            int len = newJsonArray.size();
            for (int i = 0; i < len; i++) {
                list.add(newJsonArray.get(i).toString());
            }
        }
        return list;
    }

    public String getToken(String value) throws ParseException, IOException {
        try (FileReader reader = new FileReader("src/main/resources/token.json")) {
            jsonObject = (JSONObject) parser.parse(reader);
            return jsonObject.get(value).toString();
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
            return null;
        }
    }

    public String getClientData(String value) throws FileNotFoundException, ParseException {
        try (FileReader reader = new FileReader(this.getClass().getClassLoader().
                getResource("restTestData.json").getFile())) {
            jsonObject = (JSONObject) parser.parse(reader);
            jsonClientData = (JSONObject) jsonObject.get("clientData");
            return jsonClientData.get(value).toString();
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
            return null;
        }
    }
}
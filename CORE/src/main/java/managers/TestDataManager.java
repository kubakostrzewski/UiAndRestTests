package managers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class TestDataManager {

    private JSONParser parser = new JSONParser();
    private FileReader reader;
    private JSONObject jsonObject;

    public String getProperty(String name) throws IOException, ParseException {

        reader = new FileReader(this.getClass().
                getClassLoader().
                getResource("uiTestData.json").
                getFile());
        jsonObject = (JSONObject)parser.parse(reader);
        return jsonObject.get(name).toString();
    }
}
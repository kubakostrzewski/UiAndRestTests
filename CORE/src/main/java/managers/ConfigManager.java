package managers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {

    private JSONParser parser = new JSONParser();
    private FileReader reader;
    private JSONObject jsonObject;

    public String getProperty(String name) throws IOException, ParseException {
        //buowanie ścieżki do pobrania pliku
        reader = new FileReader(this.getClass().
                getClassLoader().
                getResource("config.json").
                getFile());
        jsonObject = (JSONObject)parser.parse(reader);
        return jsonObject.get(name).toString();
    }
}
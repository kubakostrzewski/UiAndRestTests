package rest.responses;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetChangeAccountSettingsResponse {

    private static JSONObject jsonResponse, jsonData;
    private static JSONParser parser = new JSONParser();

    public static String getUsername (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("account_url").toString();
    }

    public static String getPublicImages (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("public_images").toString();
    }

    public static String getMessagingEnabled (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("messaging_enabled").toString();
    }

    public static String getAlbumPrivacy (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("album_privacy").toString();
    }

    public static String getAcceptedGalleryTerms (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("accepted_gallery_terms").toString();
    }

    public static String getShowMature (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("show_mature").toString();
    }

    public static String getNewsletterSubscribed (String response) throws ParseException {
        jsonResponse = (JSONObject) parser.parse(response);
        jsonData = (JSONObject)jsonResponse.get("data");
        return jsonData.get("newsletter_subscribed").toString();
    }
}

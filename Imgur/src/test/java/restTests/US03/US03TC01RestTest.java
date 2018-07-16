package restTests.US03;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;
import java.io.IOException;

import static rest.responses.GetAccessTokenResponse.getErrorMessage;
import static rest.responses.GetChangeAccountSettingsResponse.*;


public class US03TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    public String accessToken, name, username, publicImages, messaging, albumPrivacy, terms, mature, newsletter;
    public JSONObject testSettingsData, testCaseData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        // SEND FIRST PUT
        prepareTestDataFirst();
        httpResponse = putChangeAccountSettings(username, publicImages, messaging, albumPrivacy, terms,
                mature, newsletter);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(),200,
                "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        Reporter.log("<h3>Put Account Settings Test PASSED</h3>");

        // GET ACCOUNT SETTINGS WITH RECEIVING STATUS 404
        name = testCaseData.get("account_url_second").toString();
        httpResponse = getAccountSettings(name);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(),404,
                "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        Reporter.log("<h3>Get Account Settings Fail Test PASSED</h3>");

        // CHECK ERROR
        action.assertEqualsAndReport(getErrorMessage(response), testCaseData.get("fail_username_error"),
                "Fail username response");
        Reporter.log("Error Check Test PASSED\n");

        // GET ACCOUNT SETTINGS WITH RECEIVING STATUS 200
        name = testCaseData.get("account_url_first").toString();
        httpResponse = getAccountSettings(name);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(),200,
                "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());

        // CHECKING ACCOUNT SETTINGS
        action.assertEqualsAndReport(getUsername(response), testSettingsData.get("username"),
                "New username - newNameCSH");
        action.assertEqualsAndReport(getPublicImages(response), testSettingsData.get("public_images"),
                "Public images - false");
        action.assertEqualsAndReport(getMessagingEnabled(response), testSettingsData.get("messaging_enabled"),
                "Messaging enabled - false");
        action.assertEqualsAndReport(getAlbumPrivacy(response), testSettingsData.get("album_privacy"),
                "Album privacy - hidden");
        action.assertEqualsAndReport(getAcceptedGalleryTerms(response), testSettingsData.get("accept_gallery_terms"),
                "Accept gallery terms  - false");
        action.assertEqualsAndReport(getShowMature(response), testSettingsData.get("show_mature"),
                "Show mature - false");
        action.assertEqualsAndReport(getNewsletterSubscribed(response), testSettingsData.get("newsletter_subscribed"),
                "Newsletter subscribed - false");
        Reporter.log("<h3>Get Account Settings Test PASSED</h3>");

        // SEND SECOND PUT
        prepareTestDataSecond();
        httpResponse = putChangeAccountSettings(username, publicImages, messaging, albumPrivacy, terms,
                mature, newsletter);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(),200,
                "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        Reporter.log("<h3>Put Account Settings Test PASSED</h3>");

        // GET ACCOUNT SETTINGS WITH RECEIVING STATUS 200
        name = testCaseData.get("account_url_second").toString();
        httpResponse = getAccountSettings(name);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(),200,
                "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());

        // CHECKING ACCOUNT SETTINGS
        action.assertEqualsAndReport(getUsername(response), testSettingsData.get("username"),
                "New username - cshGroupB");
        action.assertEqualsAndReport(getPublicImages(response), testSettingsData.get("public_images"),
                "Public images - true");
        action.assertEqualsAndReport(getMessagingEnabled(response), testSettingsData.get("messaging_enabled"),
                "Messaging enabled - true");
        action.assertEqualsAndReport(getAlbumPrivacy(response), testSettingsData.get("album_privacy"),
                "Album privacy - public");
        action.assertEqualsAndReport(getAcceptedGalleryTerms(response), testSettingsData.get("accept_gallery_terms"),
                "Accept gallery terms - true");
        action.assertEqualsAndReport(getShowMature(response), testSettingsData.get("show_mature"),
                "Show mature - true");
        action.assertEqualsAndReport(getNewsletterSubscribed(response), testSettingsData.get("newsletter_subscribed"),
                "Newsletter subscribed - true");
        Reporter.log("<h3>Get Account Settings Test PASSED</h3>");

    }

    public void prepareTestDataFirst() throws ParseException, IOException {
        accessToken =  manager.getToken("accessToken");
        testCaseData = manager.getTestCaseData("us03TestData");
        testSettingsData = manager.getAccountSettings("first");
        username = testSettingsData.get("username").toString();
        publicImages = testSettingsData.get("public_images").toString();
        messaging = testSettingsData.get("messaging_enabled").toString();
        albumPrivacy = testSettingsData.get("album_privacy").toString();
        terms = testSettingsData.get("accept_gallery_terms").toString();
        mature = testSettingsData.get("show_mature").toString();
        newsletter = testSettingsData.get("newsletter_subscribed").toString();
    }

    public void prepareTestDataSecond() throws ParseException, IOException {
        testSettingsData = manager.getAccountSettings("second");
        username = testSettingsData.get("username").toString();
        publicImages = testSettingsData.get("public_images").toString();
        messaging = testSettingsData.get("messaging_enabled").toString();
        albumPrivacy = testSettingsData.get("album_privacy").toString();
        terms = testSettingsData.get("accept_gallery_terms").toString();
        mature = testSettingsData.get("show_mature").toString();
        newsletter = testSettingsData.get("newsletter_subscribed").toString();
    }
}

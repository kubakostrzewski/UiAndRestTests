package restTests.US13;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.BasicResponseModel.*;


public class US13TC01RestTest extends ImgurClient {

    private JSONObject testData;
    private HttpResponse httpResponse;
    private String response;
    private String accountName;
    private String success;
    private String albumID;
    private String imageLink;
    private String imageID;
    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();

        //get albumID
        httpResponse = getAlbums(accountName);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(success, "true", "Success field");
        albumID = getPropertyFromDataArray(response, "id");


        //get image link from album
        httpResponse = getAlbumImages(albumID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(success, "true", "Success field");
        imageLink = getPropertyFromDataArray(response, "link");
        imageID = getPropertyFromDataArray(response, "id");
        action.assertEqualsAndReport(imageID, imageLink.substring(20, 27), "Link substring(imageID) and imageID equality");

        Reporter.log("<h3>Get image link from album test PASSED");

    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us13TestData");
        accountName = testData.get("account_username").toString();
    }
}

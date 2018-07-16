package restTests.US12;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import static rest.responses.BasicResponseModel.*;

import java.io.IOException;
import java.util.List;


public class US12TC01RestTest extends ImgurClient {

    private JSONObject testData;
    private HttpResponse httpResponse;
    private String response;
    private String success;
    private String albumTitle;
    private List<String> images;
    private String accountName;
    private String newAlbumID;

    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();

        //check albums count
        httpResponse = getAlbumCount(accountName);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        int beforePost = Integer.parseInt(getProperty(response, "data"));

        //create new album
        httpResponse = postAlbumCreation(albumTitle, images);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(success, "true", "Success field");

        //check albums count after creating a new one. Must be +1
        httpResponse = getAlbumCount(accountName);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        int afterPost = Integer.parseInt(getProperty(response, "data"));
        boolean addOne = ((afterPost-beforePost)==1) ? true : false;
        action.assertEqualsAndReport(success, "true", "Success fields");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        Assert.assertTrue(addOne, "Comment size difference is not equal to 1\n");
        Reporter.log("Number of album before creating a new one: " + beforePost + "\n" +
                "<br>Number of albums after creating a new one: " + afterPost + "\n" +
                "<br>Difference: 1<br>");

        //check new album ID
        httpResponse = getAlbums(accountName);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        newAlbumID = getPropertyFromDataArray(response, "id");

        //delete album
        httpResponse = deleteAlbumDeletion(accountName, newAlbumID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(success, "true", "Success fields");

        //check albums count after deleting.
        httpResponse = getAlbumCount(accountName);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        int afterDelete = Integer.parseInt(getProperty(response, "data"));
        action.assertEqualsAndReport(success, "true", "Success fields");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(beforePost, afterDelete, "Account albums equality");

        Reporter.log("<h3>Create new album with images addded and delete it test PASSED");

    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us12TestData");
        albumTitle = testData.get("albumTitle").toString();
        accountName = testData.get("account_username").toString();
        images = manager.getJsonArray("us12TestData", "imagesIds");
    }
}

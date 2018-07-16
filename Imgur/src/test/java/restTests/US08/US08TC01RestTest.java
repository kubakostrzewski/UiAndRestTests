package restTests.US08;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import static rest.responses.BasicResponseModel.*;
import static rest.responses.BasicResponseModel.getPropertyFromDataArray;


import java.io.IOException;

public class US08TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String success;
    private String accountName;
    private String imageHash;
    private String commentID;
    private String text;
    private String commentSort;
    private String newestCommentID;

    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();

        //get account comments
        httpResponse = getAccountComments(accountName, commentSort);
        response = EntityUtils.toString(httpResponse.getEntity());
        int beforePosting = getDataArraySize(response);

        //post new comment reply
        httpResponse = postReplyCreation(imageHash, commentID, text);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(success, "true", "Success field");

        //get last comment id from account newest comments
        httpResponse = getAccountComments(accountName, commentSort);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        int afterPosting = getDataArraySize(response);
        newestCommentID = getPropertyFromDataArray(response, "id");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(success, "true", "Success field");
        boolean addOne = ((afterPosting-beforePosting)==1) ? true : false;
        Assert.assertTrue(addOne, "Comment size difference is not equal to 1\n");
        Reporter.log("Number of comments before posting a reply: " + beforePosting + "\n" +
                "<br>Number of comments after posting a reply: " + afterPosting + "\n" +
                "<br>Difference: 1<br>");


        //delete new comment
        httpResponse = deleteCommentDeletion(accountName, newestCommentID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(success, "true", "Success field");

        //check if new comment was deleted
        httpResponse = getComment(newestCommentID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 404, "Status code");
        action.assertEqualsAndReport(success, "false", "Success field");

        //check if there is the same amount of comments as before posting
        httpResponse = getAccountComments(accountName, commentSort);
        response = EntityUtils.toString(httpResponse.getEntity());
        int afterDeleting = getDataArraySize(response);
        action.assertEqualsAndReport(beforePosting, afterDeleting, "Account comments equality");

        Reporter.log("<h3>Post a comment reply and delete it test PASSED");

    }

    private void prepareTestData() throws ParseException {
        JSONObject testData = manager.getTestCaseData("us08TestData");
        accountName = testData.get("account_username").toString();
        imageHash = testData.get("imageHash").toString();
        commentID = testData.get("commentID").toString();
        text = testData.get("commentText").toString();
        commentSort = testData.get("commentSort").toString();

    }
}

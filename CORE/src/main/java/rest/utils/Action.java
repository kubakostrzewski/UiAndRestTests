package rest.utils;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;

public class Action {

    public void assertEqualsAndReport(Object foundValue, Object expectedValue, String name){
        Assert.assertEquals(foundValue, expectedValue,
                name + " is different then expected");
        Reporter.log(name + " OK<br>EXPECTED: " + expectedValue + "<br>FOUND: "
                + foundValue + "<br><br>");
    }
    public void logResponse(HttpResponse httpResponse, String response){

        System.out.println("RESPONSE");
        Reporter.log("<b>RESPONSE</b><br>");
        if(httpResponse.getAllHeaders().length > 0){
            System.out.println("Headers:");
            Reporter.log("<b>Headers:</b><br>");
            for (int i = 0; i < httpResponse.getAllHeaders().length; i++){
                String header = httpResponse.getAllHeaders()[i].toString();
                System.out.println("\t" + header);
                Reporter.log("<div style=\"text-indent: 1cm\">" + header + "</div>");
            }
            Reporter.log("<br>");
        }
        System.out.println("Response Body:\n" + response + "\n");
        if (response.length()>150){
            RandomStringGenerator randomStringGenerator =
                    new RandomStringGenerator.Builder().withinRange('0', 'z').
                            filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
            String partResp = randomStringGenerator.generate(12);
            String fullResp = randomStringGenerator.generate(12);
            Reporter.log("<b>Response Body:</b><br>" +
                    "<div style=\"cursor: pointer; color:blue;word-break:break-all;\"" +
                        "id=\"" + partResp + "\" style=\"display:block;\"" +
                        "onclick=\"document.getElementById('" + fullResp + "').style.display='block';" +
                        "document.getElementById('" + partResp + "').style.display='none';\">" +
                        response.substring(0,70).replace("<", "&lt;").replace(">", "&gt;") +
                    ".......................CLICK TO SEE FULL RESPONSE" +
                    "</div>"+
                    "<div id=\"" + fullResp + "\" style=\"display:none;\">" +
                        "<div style=\"cursor: pointer;color:blue;word-break:break-all;\"" +
                            "onclick=\"document.getElementById('" + partResp + "').style.display='block';" +
                            "document.getElementById('" + fullResp + "').style.display='none';\">" + response +
                        "</div>" +
                    "</div><br>");
        }else {
            Reporter.log("<b>Response Body:</b><br>" + response + "<br><br>");
        }
    }
    public void logRequest(HttpRequestBase httpRequest, String requestMethod) throws IOException {
        String request = httpRequest.getURI().toString();
        Reporter.log("<b>REQUEST</b><br>");
        System.out.println("REQUEST");
        System.out.println(requestMethod + " " + request);
        Reporter.log(requestMethod + " " + request + "<br>");
        if(httpRequest.getAllHeaders().length > 0){
            System.out.println("Headers:");
            Reporter.log("<b>Headers:</b><br>");
            for (int i = 0; i < httpRequest.getAllHeaders().length; i++){
                String header = httpRequest.getAllHeaders()[i].toString();
                System.out.println("\t" + header);
                Reporter.log("<div style=\"text-indent: 1cm\">" + header + "</div>");
            }
        }
        try {
            if (httpRequest.getClass().getSuperclass().equals(HttpEntityEnclosingRequestBase.class)){
                String requestEntity = EntityUtils.toString(((HttpEntityEnclosingRequestBase) httpRequest).getEntity());
                System.out.println("Entity: " + requestEntity);
                Reporter.log("<b>Entity:</b> " + requestEntity + "<br><br>");
            }
        }catch (IllegalArgumentException iae){}

        System.out.println("");
        Reporter.log("<br>");
    }
}

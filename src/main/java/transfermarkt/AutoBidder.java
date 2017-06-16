package transfermarkt;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Chris on 28.12.2016.
 */
public class AutoBidder {
    final String userID = "brotkatenils";
    final String userPass = "dertollenils";
    private int bid=0;

    public AutoBidder(int playerID)

    {


        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";

        try {

            Connection.Response homePageResponse = Jsoup
                    .connect("http://www.onlinefussballmanager.de")
                    .userAgent(userAgent)
                    .method(Connection.Method.GET)
                    .execute();


            Connection.Response loginResponse = null;

            loginResponse = Jsoup.connect("http://www.onlinefussballmanager.de")
                    .userAgent(userAgent)
                    .data("login", userID)
                    .data("password", userPass)
                    .data("remember_me", "1")
                    .data("LoginButton", "Login")
                    .data("js_activated", "1")
                    .data("legacyLoginForm", "1")
                    .method(Connection.Method.POST)
                    .execute();


            Map<String, String> loginCookies = loginResponse.cookies();
            //System.out.println("Login response code: " + loginResponse.statusCode());
            Document bidInfoPage = Jsoup.connect("http://www.onlinefussballmanager.de/transfer/transfermarkt.php?aktion=mitbieten&spielerid=" + playerID)
                    .userAgent(userAgent)
                    .cookies(loginCookies)
                    .get();

            Elements bidInfoElements=bidInfoPage.select("#transfermarkt > form > table > tbody > tr > td > table > tbody > tr > td > div > table > tbody > tr:nth-child(10) > td:nth-child(2) > b > font");
            for (Element ele : bidInfoElements) {
                bid = Integer.parseInt(ele.html().replace(".", "").replace("€", "").trim());
                System.out.println(bid);
            }


            Document bidPage = Jsoup.connect("http://www.onlinefussballmanager.de/transfer/transfermarkt.php?aktion=mitbieten&spielerid=" + playerID)
                    .userAgent(userAgent)
                    .cookies(loginCookies)
                    .data("meingebot", (bid)+"")
                    .data("submit", "mitbieten")
                    .data("spiederidgeboten", playerID+"")
                    .post();


            Elements test=bidInfoPage.select("#transfermarkt > table:nth-child(5) > tbody");
            for (Element ele : test) {
                System.out.println("this is ele" +ele.html());
            }

//
//#transfermarkt > table:nth-child(5) > tbody > tr > td > table > tbody > tr > td > div

            System.out.println("New Bid = " + bid);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

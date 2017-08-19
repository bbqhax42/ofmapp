package friendlymanager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by Chris on 18.08.2017.
 */
public class FriendlyManager {
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Team> teams = new ArrayList<Team>();
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";
    private Map<String, String> loginCookies;
    private int playday = -1, season = -1;
    final int waitTimeLow = 3500; //Minimum wait time between Server requests
    final int waitTimeHigh = 4000; //Maximum wait time between Server requests

    public FriendlyManager() {

        String[] schedule1 = new String[]{"team1", "team1", "team1", "team1", "team1", "team1", "team2", "team2", "team2", "team2", "team2", "team2",
                "team3", "team3", "team3", "team3", "team3", "team3", "team4", "team4", "team4", "team4", "team4", "team4", "team5", "team5", "team5", "team5",
                "team5", "team5", "team6", "team6", "team6", "team6", "team6"};
        String[] schedule2 = new String[]{"team2", "team2", "team2", "team2", "team2", "team2", "team3", "team3", "team3", "team3", "team3", "team3",
                "team4", "team4", "team4", "team4", "team4", "team4", "team5", "team5", "team5", "team5", "team5", "team5", "team6", "team6", "team6",
                "team6", "team6", "team6", "team7", "team7", "team7", "team7", "team7"};
        String[] schedule3 = new String[]{"team3", "team3", "team3", "team3", "team3", "team3", "team4", "team4", "team4", "team4", "team4", "team4", "team5",
                "team5", "team5", "team5", "team5", "team5", "team6", "team6", "team6", "team6", "team6", "team6", "team7", "team7",
                "team7", "team7", "team7", "team7", "team1", "team1", "team1", "team1", "team1"};
        String[] schedule4 = new String[]{"team4", "team4", "team4", "team4", "team4", "team4", "team5", "team5", "team5", "team5", "team5", "team5",
                "team6", "team6", "team6", "team6", "team6", "team6", "team7", "team7", "team7", "team7", "team7", "team7", "team1", "team1",
                "team1", "team1", "team1", "team1", "team2", "team2", "team2", "team2", "team2"};
        String[] schedule5 = new String[]{"team5", "team5", "team5", "team5", "team5", "team5", "team6", "team6", "team6", "team6",
                "team6", "team6", "team7", "team7", "team7", "team7", "team7", "team7", "team1", "team1", "team1", "team1", "team1", "team1", "team2",
                "team2", "team2", "team2", "team2", "team2", "team3", "team3", "team3", "team3", "team3"};
        String[] schedule6 = new String[]{"team6", "team6", "team6", "team6", "team6", "team6", "team7", "team7", "team7", "team7", "team7", "team7",
                "team1", "team1", "team1", "team1", "team1", "team1", "team2", "team2", "team2", "team2", "team2", "team2", "team3", "team3", "team3", "team3",
                "team3", "team3", "team4", "team4", "team4", "team4", "team4"};
        String[] schedule7 = new String[]{"team7", "team7", "team7", "team7", "team7", "team7", "team1", "team1", "team1", "team1", "team1", "team1", "team2",
                "team2", "team2", "team2", "team2", "team2", "team3", "team3", "team3", "team3", "team3", "team3", "team4", "team4", "team4", "team4", "team4",
                "team4", "team5", "team5", "team5", "team5", "team5"};


        //sample users -> can easily be automated and saved within database



    }

    private void scheduler() throws IOException {
        User tmpUser = users.get(0);
        parsePlaydayAndSeason(tmpUser);
        for (int i = 0; i < users.size(); i++) {
            tmpUser = users.get(i); //need to fiond user from schedule to feed into other function
            // scheduleFriendly();
        }

    }

    private void scheduleFriendly(Team team, User user, int date) throws IOException, InterruptedException {
        Random r = new Random();

        Jsoup.connect("http://www.onlinefussballmanager.de")
                .userAgent(userAgent)
                .method(Connection.Method.GET)
                .execute();


        Thread.sleep(r.nextInt(waitTimeHigh - waitTimeLow) + waitTimeLow);

        Connection.Response loginResponse = null;

        loginResponse = Jsoup.connect("http://www.onlinefussballmanager.de")
                .userAgent(userAgent)
                .data("login", user.getUsername())
                .data("password", user.getPassword())
                .data("remember_me", "1")
                .data("LoginButton", "Login")
                .data("js_activated", "1")
                .data("legacyLoginForm", "1")
                .method(Connection.Method.POST)
                .execute();


        loginCookies = loginResponse.cookies();

        Thread.sleep(r.nextInt(waitTimeHigh - waitTimeLow) + waitTimeLow);

        Jsoup.connect("http://www.onlinefussballmanager.de/friendlies/_frame_freund.php")
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/head-int.php?spannend=0")
                .method(Connection.Method.GET)
                .execute();

        Thread.sleep(r.nextInt(waitTimeHigh - waitTimeLow) + waitTimeLow);

        Jsoup.connect("http://www.onlinefussballmanager.de/010_statistiken/search_team.php?mode=friendly")
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/friendlies/anbieten_fr.php")
                .data("mode", "friendly")
                .method(Connection.Method.GET)
                .execute();

        Thread.sleep(r.nextInt(waitTimeHigh - waitTimeLow) + waitTimeLow);

        Jsoup.connect("http://www.onlinefussballmanager.de/010_statistiken/search_team.php?mode=friendly")
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/010_statistiken/search_team.php?mode=friendly")
                .data("land", "0")
                .data("teamname", "")
                .data("ligalevel", "0")
                .data("managername", team.getManagername())
                .data("submit", "suchen")
                .data("selectName", "")
                .method(Connection.Method.POST)
                .execute();

        Thread.sleep(r.nextInt(waitTimeHigh - waitTimeLow) + waitTimeLow);


        Jsoup.connect("http://www.onlinefussballmanager.de/010_freund/anbieten_fr.php?best_gegner=true&gegnerid=" + team.getTeamID())
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/010_statistiken/search_team.php?mode=friendly")
                .data("best_gegner", "true")
                .data("gegnerid", Integer.toString(team.getTeamID()))
                .method(Connection.Method.GET)
                .execute();

        Thread.sleep(r.nextInt(waitTimeHigh - waitTimeLow) + waitTimeLow);


        Jsoup.connect("http://www.onlinefussballmanager.de/010_freund/anbieten_fr.php")
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/010_freund/anbieten_fr.php?best_gegner=true&gegnerid=" + team.getTeamID())
                .data("frspieltag", Integer.toString(date))
                .data("anpfiffzeit", "0")
                .data("heimgast", "als_heim")
                .data("gegnerid", Integer.toString(team.getTeamID()))
                .data("staerke_von_hm", "0")
                .data("staerke_bis_hm", "330")
                .data("staerke_von_am", "0")
                .data("staerke_bis_am", "6")
                .data("antrittsgeld", "100000")
                .data("kommentar", "Friendlytest")
                .data("fr_anbieten", "true")
                .method(Connection.Method.POST)
                .execute();
    }

    private void parsePlaydayAndSeason(User user) throws IOException {

        Connection.Response homePageResponse = Jsoup
                .connect("http://www.onlinefussballmanager.de")
                .userAgent(userAgent)
                .method(Connection.Method.GET)
                .execute();


        Connection.Response loginResponse = null;

        loginResponse = Jsoup.connect("http://www.onlinefussballmanager.de")
                .userAgent(userAgent)
                .data("login", user.getUsername())
                .data("password", user.getPassword())
                .data("remember_me", "1")
                .data("LoginButton", "Login")
                .data("js_activated", "1")
                .data("legacyLoginForm", "1")
                .method(Connection.Method.POST)
                .execute();


        loginCookies = loginResponse.cookies();

        Document playdaypage;
        //parsing the playday
        playdaypage = Jsoup.connect("http://www.onlinefussballmanager.de/head-int.php?spannend=0")
                .userAgent(userAgent)
                .cookies(loginCookies)
                .ignoreContentType(true)
                .get();

        Elements playdays = playdaypage.select("body > div.headFrame.pos-rel.clearfix > div.float.bgFront.pos-rel > div.pos-abs.yellow.clearfix.infoBlock > p > span:nth-child(1)");
        for (Element ele : playdays) {
            this.playday = Integer.parseInt(ele.html().trim());
        }
        if (this.playday == -1) {//date could not be parsed
            System.exit(-1);
        }


        Elements seasons = playdaypage.select("body > div.headFrame.pos-rel.clearfix > div.float.bgFront.pos-rel > div.pos-abs.yellow.clearfix.infoBlock > p > span:nth-child(2)");
        for (Element ele : seasons) {
            this.season = Integer.parseInt(ele.html().trim());
        }
        if (this.playday == -1) {//date could not be parsed
            System.exit(-1);
        }

        System.out.println("Spieltag und Saison detected: " + season + "/" + playday);
    }


}
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
import java.util.Scanner;

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
    private String[] friendlyComments = {"spiele Diva", "Hi. darf ich dir friendly anbieten ? mfg", "Erfahrung für Dein Team, das Geld für meins!", "deine eps",
            "Hi :-)", "Bitte keine Verlängerung!!!", "Abschussfriendly 3,5 ohne TW & Abwehr", "Danke", "gg", ":-)", "erfahrung gegen kohle", "Go for it", "Immer drauf da!!!",
            "Lust auf ein Spielchen!?", "Geld/Erfahrung", "Kann es wieder losgehen? :)", "bitte stärke beachten!!!!!!!!!!!!!", "Vielen Dank, Thank You, Merci, Cпасибо, Gracias.",
            "*** Abschussfriendlys ***", "minimal", "los geht's", "Würde mich freuen wenn es klappt", "gutes spiel", "Auf ne faire Partie ...", "Abschußfriendly",
            "kanonenfutter", "343;off;div", "danke fürs annehmen", "", ""};

    public FriendlyManager() {
        teams.add(new Team("team1", 24743, "bbqhax43"));
        teams.add(new Team("team2", 26041, "Vieiri"));
        teams.add(new Team("team3", 111017, "KB-7"));
        teams.add(new Team("team4", 162168, "daskuh"));
        teams.add(new Team("team5", 182324, "Futschi"));
        teams.add(new Team("team6", 85129, ".Jul"));
        teams.add(new Team("team7", 235157, "phontonton1909"));
        //teams.add(new Team("buschi", 177547, "Bujo"));
        //teams.add(new Team("halmi", 103638, "Eule79"));
        //teams.add(new Team("henry", 206388, "fius"));
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
        users.add(new User("sleepcycle", "melatonin", schedule1));
        users.add(new User("hansi1337v2", "asdf1234", schedule2));
        users.add(new User("ak47purplehaze", "blueberryhaze", schedule3));


    }

    private void scheduler() throws IOException {
        User tmpUser = users.get(0);
        Team tmpTeam = null;
        parsePlaydayAndSeason(tmpUser);
        for (int i = 0; i < users.size(); i++) {
            tmpUser = users.get(i);
            tmpTeam = getTeam(tmpUser.getSchedule()[playday]);
            int frPlayday = 103 + playday + (151 - season) * 35;
            try {
                getCookies(tmpUser);
                declineAllFriendlies();
                for (int frPlaydayLoop = frPlayday; frPlaydayLoop < frPlayday + 6; frPlaydayLoop++) {
                    try {
                        scheduleFriendly(tmpTeam, tmpUser, frPlaydayLoop);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Issue with " + tmpUser.getUsername());
            }
        }

    }

    private void declineAllFriendlies() throws IOException {

        Document doc = Jsoup.connect("http://www.onlinefussballmanager.de/friendlies/_frame_freund.php")
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/head-int.php?spannend=0")
                .method(Connection.Method.GET)
                .get();

        Scanner readFile = new Scanner(doc.toString());
        while (readFile.hasNextLine()) {
            String readerLine = readFile.nextLine();
            if (readerLine.contains("ablehnen</a>")) {
                String tmpString = readerLine.trim().replace("<a href='?fr_annehmen=1&spielid=", "").replace("' class='blue'>annehmen</a> ", "")
                        .replace(" <a href='?fr_absagen=1&spielid=", "").replace("' class='blue'>ablehnen</a>", "");
                String[] tmpAry = tmpString.split("/");

                System.out.println(readerLine);
                System.out.println(tmpString);
                System.out.println("http://www.onlinefussballmanager.de/friendlies/anbieten_fr.php?fr_absagen=1&spielid=" + tmpAry[0]);

                Jsoup.connect("http://www.onlinefussballmanager.de/friendlies/anbieten_fr.php?fr_absagen=1&spielid=" + tmpAry[0])
                        .cookies(loginCookies)
                        .userAgent(userAgent)
                        .referrer("http://www.onlinefussballmanager.de/010_statistiken/search_team.php?mode=friendly")
                        .data("fr_absagen", "1")
                        .data("spielid", tmpAry[0])
                        .method(Connection.Method.GET)
                        .execute();
            }
        }
    }

    private void getCookies(User user) throws IOException, InterruptedException {
        Jsoup.connect("http://www.onlinefussballmanager.de")
                .userAgent(userAgent)
                .method(Connection.Method.GET)
                .execute();

        Random r = new Random();
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
    }

    private void scheduleFriendly(Team team, User user, int spieltag) throws IOException, InterruptedException {
        Random r = new Random();


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
        int tmpInt = r.nextInt(30);

        Jsoup.connect("http://www.onlinefussballmanager.de/010_freund/anbieten_fr.php")
                .cookies(loginCookies)
                .userAgent(userAgent)
                .referrer("http://www.onlinefussballmanager.de/010_freund/anbieten_fr.php?best_gegner=true&gegnerid=" + team.getTeamID())
                .data("frspieltag", Integer.toString(spieltag + 69))
                .data("anpfiffzeit", "0")
                .data("heimgast", "als_heim")
                .data("gegnerid", Integer.toString(team.getTeamID()))
                .data("staerke_von_hm", "0")
                .data("staerke_bis_hm", "330")
                .data("staerke_von_am", "0")
                .data("staerke_bis_am", "6")
                .data("antrittsgeld", "100000")
                .data("kommentar", friendlyComments[tmpInt])
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

    private Team getTeam(String identifier) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getIdentifier().equals(identifier)) return teams.get(i);
        }
        return null;
    }

}
package transfermarkt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.h2.tools.Server;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.Boolean.TRUE;

/**
 * Created by Chris on 01.11.2016.
 */
public class Database {

    private Connection conn = null;
    private Server server = null;
    private int spieltag = -1, saison = -1;

    public Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        conn = DriverManager.
                getConnection("jdbc:h2:~/ofm", "Chris", "asdf1234");
    }


    public Database(boolean tmp) throws ClassNotFoundException, SQLException {
        parseSeason();
        Class.forName("org.h2.Driver");
        conn = DriverManager.
                getConnection("jdbc:h2:~/ofm", "Chris", "asdf1234");
    }

    private void parseSeason() {

        final String userID = "brotkatenils";
        final String userPass = "dertollenils";


        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";

        try {

            org.jsoup.Connection.Response homePageResponse = Jsoup
                    .connect("http://www.onlinefussballmanager.de")
                    .userAgent(userAgent)
                    .method(org.jsoup.Connection.Method.GET)
                    .execute();


            org.jsoup.Connection.Response loginResponse = null;

            loginResponse = Jsoup.connect("http://www.onlinefussballmanager.de")
                    .userAgent(userAgent)
                    .data("login", userID)
                    .data("password", userPass)
                    .data("remember_me", "1")
                    .data("LoginButton", "Login")
                    .data("js_activated", "1")
                    .data("legacyLoginForm", "1")
                    .method(org.jsoup.Connection.Method.POST)
                    .execute();


            Map<String, String> loginCookies = loginResponse.cookies();
            //System.out.println("Login response code: " + loginResponse.statusCode());

            Document playdaypage;
            //parsing the playday
            playdaypage = Jsoup.connect("http://www.onlinefussballmanager.de/head-int.php?spannend=0")
                    .userAgent(userAgent)
                    .cookies(loginCookies)
                    .ignoreContentType(true)
                    .get();

            Elements playdays = playdaypage.select("body > div.headFrame.pos-rel.clearfix > div.float.bgFront.pos-rel > div.pos-abs.yellow.clearfix.infoBlock > p > span:nth-child(1)");
            for (Element ele : playdays) {
                this.spieltag = Integer.parseInt(ele.html().trim());
            }
            if (this.spieltag == -1) {//date could not be parsed
                System.exit(-1);
            }
//spieltag=29;


            Elements seasons = playdaypage.select("body > div.headFrame.pos-rel.clearfix > div.float.bgFront.pos-rel > div.pos-abs.yellow.clearfix.infoBlock > p > span:nth-child(2)");
            for (Element ele : seasons) {
                this.saison = Integer.parseInt(ele.html().trim());
            }
            if (this.spieltag == -1) {//date could not be parsed
                System.exit(-1);
            }

            System.out.println("Spieltag und Saison detected: " + saison + "/" + spieltag);

        } catch (
                IOException e1) {
            e1.printStackTrace();
        }
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws SQLException {
        server = Server.createWebServer().start();
    }

    public void stopServer() {
        server.stop();
    }

    public void addPlayerList(PlayerList list) throws SQLException {
        Statement statement = conn.createStatement();

        for (Iterator<PlayerTM> iter = list.iterator(); iter.hasNext(); ) {
            PlayerTM tmp = iter.next();
            StringBuilder tmpString = new StringBuilder();
            String command = "INSERT INTO SPIELER VALUES(" + tmp.getId() + ", '" + tmp.getName().replaceAll("[^a-zA-Z]+", "") + "', '" + tmp.getPos() + "', " + (spieltag-1) + ", " + saison + ", " + tmp.getAge() + ", " + tmp.getPower() + " , " + tmp.getEp() + ",  " + tmp.getTp() + ", " + tmp.getAwp() + ", " + tmp.getBid() + ", " + tmp.isHasBidder() + ");";
            statement.executeUpdate(command);
        }

    }

    public PlayerList retrievePlayerList(int age, int power) {
        PlayerList tmp = null;

        return tmp;
    }

    public void createExcelSheet(String command) throws SQLException{
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(command);

        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        // sheet name needed
        Sheet sheet = wb.createSheet("SHEET NAME");
        sheet.setColumnWidth(0, 20*256);
        sheet.setColumnWidth(1, 5*256);
        sheet.setColumnWidth(2, 8*256);
        sheet.setColumnWidth(3, 8*256);
        sheet.setColumnWidth(4, 8*256);
        sheet.setColumnWidth(5, 8*256);
        sheet.setColumnWidth(6, 20*256);
        sheet.setColumnWidth(7, 10*256);
        sheet.setColumnWidth(8, 5*256);
        sheet.setColumnWidth(9, 5*256);
        sheet.setColumnWidth(10, 12*256);

        Row row = sheet.createRow((short) 0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Age");
        row.createCell(2).setCellValue("Power");
        row.createCell(3).setCellValue("Ep");
        row.createCell(4).setCellValue("Tp");
        row.createCell(5).setCellValue("Awp");
        row.createCell(6).setCellValue("Bid");
        row.createCell(7).setCellValue("Hasbidder");
        row.createCell(8).setCellValue("Day");
        row.createCell(9).setCellValue("Season");
        row.createCell(10).setCellValue("ID");
        CellStyle styleMoney = wb.createCellStyle();
        CellStyle styleNumber = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        styleMoney.setDataFormat(format.getFormat("_-* #,## \\€_-;-* #,## \\€_-;_-* \"-\"?? \\€_-;_-@_-"));
        styleNumber.setDataFormat(format.getFormat("#,##0"));

            int i=2;
        while (rs.next()) {
            row = sheet.createRow(i++);


            Cell cell = row.createCell(0);
            cell.setCellValue(rs.getString(2));

            cell = row.createCell(1);
            cell.setCellValue(rs.getInt(6));

            cell = row.createCell(2);
            cell.setCellValue(rs.getInt(7));

            cell = row.createCell(3);
            cell.setCellValue(rs.getInt(8));
            cell.setCellStyle(styleNumber);

            cell = row.createCell(4);
            cell.setCellValue(rs.getInt(9));
            cell.setCellStyle(styleNumber);

            cell = row.createCell(5);
            cell.setCellValue(rs.getInt(10));
            cell.setCellStyle(styleNumber);

            cell = row.createCell(6);
            cell.setCellValue(rs.getInt(11));
           cell.setCellStyle(styleMoney);

            cell=row.createCell(7);
            cell.setCellValue(rs.getBoolean(12));

            cell=row.createCell(8);
            cell.setCellValue(rs.getInt(4));

            cell=row.createCell(9);
            cell.setCellValue(rs.getInt(5));

            cell=row.createCell(10);
            cell.setCellValue(rs.getInt(1));

        }


        for (int j = 0; j < 11; j++) {
            sheet.autoSizeColumn(j, false);
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


import finanzgesamtueberblick.ProfileParser;
import finanzgesamtueberblick.SpielerFUContainer;
import gui.MainWindow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.h2.jdbc.JdbcSQLException;
import transfermarkt.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Boolean.TRUE;

public class main {


    public static void main(String[] args) {

        new MainWindow();

        // spieler aufm tm anzeigen, mit filtern, und ggf historie zur spielerstaerke, alter etc

        //new SpielerWechsel();
        //new Transfermarkt();

        //SocketTimeoutException Attempting to parse 26/13
       // java.net.SocketTimeoutException: Read timed out




      //  ProfileParser scan;
      //  SpielerFUContainer container=null;
     //   container= SpielerFUContainer.instance();
        //scan = new ProfileParser();
        //scan.parseProfileByRange(100001, 150000, container);
       // scan.parseProfileById(207966, container);
       // container.gewinnBerechnenRange(1, 150);
        //System.out.println(container.getSize());
        //container.unverkaufteSpielerEntfernen();
        //System.out.println("Gewinn total: " + container.gewinnBerechnen());
        //System.out.println("Durchschnittsgewinn: " + container.gewinnBerechnen() / container.getSize());




        /* Shell launch options:
         java -server \
     -Djava.awt.headless=true \
     -Xms2g  \
     -Xmx2g \
     -XX:MaxMetaspaceSize=2g \
     -XX:+CMSClassUnloadingEnabled \
     -XX:+UseConcMarkSweepGC \
     -XX:+CMSParallelRemarkEnabled \
     -XX:+UseCMSInitiatingOccupancyOnly \
     -XX:CMSInitiatingOccupancyFraction=70 \
     -XX:+ScavengeBeforeFullGC \
     -XX:+CMSScavengeBeforeRemark \
     -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses \
     -jar myJar.jar
         */


    }
}

	
	
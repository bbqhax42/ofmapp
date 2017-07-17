
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

//select * from spieler where age =18 and power =6 and day between 9 and 10 and pos='TW' order by hasbidder desc, day asc, bid asc


    }
}

	
	

import gui.MainWindow;

public class main {


    public static void main(String[] args) {
        String str=new String("linn3232enkohl666@gmail.com");
        System.out.println(str.hashCode());
        int hash=str.hashCode();
        System.out.println(hash==str.hashCode());

        new MainWindow();

        // spieler aufm tm anzeigen, mit filtern, und ggf historie zur spielerstaerke, alter etc

//select * from spieler where age =18 and power =6 and day between 9 and 10 and pos='TW' order by hasbidder desc, day asc, bid asc


    }
}

	
	
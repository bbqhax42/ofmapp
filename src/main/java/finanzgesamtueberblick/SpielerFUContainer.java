package finanzgesamtueberblick;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class SpielerFUContainer implements Iterable<SpielerFU> {

    private transient static SpielerFUContainer unique = null;
    private ArrayList<SpielerFU> alleSpielerFU;

    private SpielerFUContainer() {
        alleSpielerFU = new ArrayList<SpielerFU>();
    }

    public static SpielerFUContainer instance() {
        if (unique == null)
            unique = new SpielerFUContainer();
        return unique;
    }

    public void linkSpieler(SpielerFU spielerFU) {
        this.alleSpielerFU.add(spielerFU);
    }

    public void printSpieler() {
        System.out.println("Alle SpielerFU:");
        if (alleSpielerFU.size() == 0)
            System.out.println("leer");

        for (SpielerFU a : alleSpielerFU)
            System.out.println(a);

    }

    //outdated
    public Boolean istVorhanden(String a) {
        for (SpielerFU b : alleSpielerFU) {
            if (a.equals(b.getName())) {
                return true;
            }
        }
        return false;
    }


    // new

    public Boolean istVorhandenId(int a) {
        for (SpielerFU b : alleSpielerFU) {
            if (a == (b.getId())) {
                return true;
            }
        }
        return false;
    }

    //outdated
    public SpielerFU findeSpieler(String a) {
        for (SpielerFU b : alleSpielerFU) {
            if (a.equals(b.getName())) {
                return b;
            }
        }
        return null;
    }

    //new
    public SpielerFU findeSpielerId(int a) {
        for (SpielerFU b : alleSpielerFU) {
            if (a == (b.getId())) {
                return b;
            }
        }
        return null;
    }

    public void unverkaufteSpielerEntfernen() {
        for (Iterator<SpielerFU> iterator = alleSpielerFU.iterator(); iterator.hasNext(); ) {
            SpielerFU b = iterator.next();
            if (b.getKaufpreis() == 0 && b.getKaufstaerke() == 0 || b.getKaufpreis() == 0 && b.getVerkaufpreis() == 0) {
                //System.out.println(b.getName() + " entfernt");
                iterator.remove();
            }
        }
    }

    public long gewinnBerechnen() {
        long gewinn = 0;
        for (SpielerFU b : alleSpielerFU) {
            if (!(b.getKaufpreis() == 0 && b.getKaufstaerke() == 0))
                if (b.getDoppeltransfer()) gewinn = gewinn + b.getGewinn2();
                else gewinn = gewinn + b.getGewinn1();
        }

        return gewinn;
    }

    public long gewinnBerechnenRange(int low, int up, int teamid) {
        long gewinn = 0;
        int avg = 0;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("ID " + teamid + " S" + low + "-" + up + ".txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (SpielerFU b : alleSpielerFU) {
            if (!(b.getKaufpreis() == 0 && b.getKaufstaerke() == 0)) {
                if (b.getSaisonkauf() >= low && b.getSaisonverkauf() <= up) {
                    if (b.getDoppeltransfer() && b.getSaisonkauf2() >= low && b.getSaisonverkauf2() <= up) {
                        gewinn = gewinn + b.getGewinnTotal();
                    } else gewinn = gewinn + b.getGewinn1();
                    avg++;
                    //System.out.println(b);
                    writer.println(b);
                }
            }
        }


      //  System.out.println("Gewinn total: " + NumberFormat.getIntegerInstance().format(gewinn));
        writer.println("Gewinn total: " + NumberFormat.getIntegerInstance().format(gewinn));
        if (avg != 0) {
         //   System.out.println("Durchschnittsgewinn: " + NumberFormat.getIntegerInstance().format(gewinn / avg));
            writer.println("Durchschnittsgewinn: " + NumberFormat.getIntegerInstance().format(gewinn / avg));
        }
        writer.close();
        return gewinn;
    }

    public long getSize() {
        return alleSpielerFU.size();
    }

    public void clear() {
        this.alleSpielerFU.clear();
    }

    public Iterator<SpielerFU> iterator() {
        return this.alleSpielerFU.iterator();
    }

}

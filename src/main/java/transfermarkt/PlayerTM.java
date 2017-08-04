package transfermarkt;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Created by Chris on 23.10.2016.
 */
public class PlayerTM implements Serializable {
    private static final long serialVersionUID = 1088637054893063932L;
    private String name = null;
    private String pos = null, seller=null, buyer=null;
    private int id, age, power, ep, tp, awp, bid;
    private boolean hasBidder, exportedIntoDatabase;


    public PlayerTM(String name, String pos, int id, int age, int power, int ep, int tp, int bid, boolean hasBidder) {
        setName(name);
        setPos(pos);
        setId(id);
        setAge(age);
        setPower(power);
        setEp(ep);
        setTp(tp);
        setAwp(ep, tp);
        setBid(bid);
        setHasBidder(hasBidder);
        exportedIntoDatabase = false;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAwp(int ep, int tp) {
        this.awp = (ep * tp * 2) / (ep + tp);
    }

    public void setEp(int ep) {
        this.ep = ep;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExportedIntoDatabase(boolean exportedIntoDatabase) {
        this.exportedIntoDatabase = exportedIntoDatabase;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public void setHasBidder(boolean hasBidder) {
        this.hasBidder = hasBidder;
    }

    public int getAge() {
        return age;
    }

    public int getAwp() {
        return awp;
    }

    public int getEp() {
        return ep;
    }

    public int getBid() {
        return bid;
    }

    public int getId() {
        return id;
    }

    public boolean isHasBidder() {
        return hasBidder;
    }

    public boolean isExportedIntoDatabase() {
        return exportedIntoDatabase;
    }

    public int getPower() {
        return power;
    }

    public int getTp() {
        return tp;
    }


    private String bidString() {
        StringBuilder lul = new StringBuilder();
        lul.append(NumberFormat.getIntegerInstance().format(bid)).setLength(11);
        for (int i = 0; i < 11; i++) {
            int rofl = lul.charAt(i);
            if (rofl == 0) {
                lul.replace(i, i + 1, " ");
            }
        }
        return lul.toString();
    }

    private String eptpawpString() {
        StringBuilder lul = new StringBuilder();
        lul.append(NumberFormat.getIntegerInstance().format(ep)).append("/").append(NumberFormat.getIntegerInstance().format(tp)).append(" ").append(NumberFormat.getIntegerInstance().format(awp)).setLength(20);
        for (int i = 19; i >= 0; i--) {
            int rofl = lul.charAt(i);
            if (rofl == 0) {
                lul.replace(i, i + 1, " ");
            }
        }
        return lul.toString();
    }

    private String nameString() {
        StringBuilder lul = new StringBuilder();
        lul.append(name).setLength(25);
        for (int i = 0; i < 25; i++) {
            int rofl = lul.charAt(i);
            if (rofl == 0) {
                lul.replace(i, i + 1, " ");
            }
        }
        return lul.toString();
    }

    private String posString() {
        StringBuilder lul = new StringBuilder();
        lul.append(pos).setLength(3);
        for (int i = 0; i < 3; i++) {
            int rofl = lul.charAt(i);
            if (rofl == 0) {
                lul.replace(i, i + 1, " ");
            }
        }
        return lul.toString();
    }

    private String agepowerString() {
        StringBuilder lul = new StringBuilder();
        lul.append(age).append("/").append(power).setLength(5);
        int rofl = lul.charAt(4);
        if (rofl == 0) {
            lul.replace(4, 4 + 1, " ");
        }
        return lul.toString();
    }


    @Override
    public String toString() {
        return posString() + " " + nameString() + " "+ agepowerString() + " " + eptpawpString() + " " + bidString();
    }
}

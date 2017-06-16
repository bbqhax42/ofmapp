package finanzgesamtueberblick;

import java.text.NumberFormat;

public class SpielerFU {

    private long alter = -1, kaufsalter, kaufstaerke, verkaufstaerke, kaufpreis, verkaufpreis, saisonkauf, saisonverkauf, spieltagkauf,
            spieltagverkauf;
    private long alter2, kaufsalter2, kaufstaerke2, verkaufstaerke2, kaufpreis2 = 0, verkaufpreis2 = 0, saisonkauf2, saisonverkauf2, spieltagkauf2,
            spieltagverkauf2;
    private int id;
    private String name, position;
    private boolean doppeltransfer=false;

    public SpielerFU(int id, String name, String pos, long alter, long verkaufstaerke, long verkaufpreis, long spieltagverkauf,
                     long saisonverkauf) {
        setId(id);
        setName(name);
        setPos(pos);
        setAlter(alter);
        setVerkaufstaerke(verkaufstaerke);
        setVerkaufpreis(verkaufpreis);
        setSpieltagverkauf(spieltagverkauf);
        setSaisonverkauf(saisonverkauf);
    }


    public void setAlter(long q) {
        this.alter = q;
    }

    public void setKaufsalter(long q) {
        this.kaufsalter = q;
    }

    public void setId(int q) {
        this.id = q;
    }

    public void setKaufstaerke(long q) {
        this.kaufstaerke = q;
    }

    public void setVerkaufstaerke(long q) {
        this.verkaufstaerke = q;
    }

    public void setSaisonkauf(long q) {
        this.saisonkauf = q;
    }

    public void setSaisonverkauf(long q) {
        this.saisonverkauf = q;
    }

    public void setSpieltagkauf(long q) {
        this.spieltagkauf = q;
    }

    public void setSpieltagverkauf(long q) {
        this.spieltagverkauf = q;
    }

    public void setKaufpreis(long q) {
        this.kaufpreis = q;
    }

    public void setVerkaufpreis(long q) {
        this.verkaufpreis = q;
    }

    public void setAlter2(long q) {
        this.alter2 = q;
    }

    public void setKaufsalter2(long q) {
        this.kaufsalter2 = q;
    }

    public void setKaufstaerke2(long q) {
        this.kaufstaerke2 = q;
    }

    public void setVerkaufstaerke2(long q) {
        this.verkaufstaerke2 = q;
    }

    public void setSaisonkauf2(long q) {
        this.saisonkauf2 = q;
    }

    public void setSaisonverkauf2(long q) {
        this.saisonverkauf2 = q;
    }

    public void setSpieltagkauf2(long q) {
        this.spieltagkauf2 = q;
    }

    public void setSpieltagverkauf2(long q) {
        this.spieltagverkauf2 = q;
    }

    public void setKaufpreis2(long q) {
        this.kaufpreis2 = q;
    }

    public void setVerkaufpreis2(long q) {
        this.verkaufpreis2 = q;
    }

    public void setName(String q) {
        StringBuilder lul=new StringBuilder();
        lul.append(q).setLength(20);
        for(int i=0; i<20; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        this.name=lul.toString();
    }

    public void setDoppeltransfer(){
        this.doppeltransfer=true;
    }

    public void setPos(String q) {
        StringBuilder lul=new StringBuilder();
        lul.append(q).setLength(3);
        for(int i=0; i<3; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        this.position=lul.toString();
    }

    public long getKaufsalter2() {
        return this.kaufsalter2;
    }

    public long getAlter() {
        return this.alter;
    }

    public long getKaufsalter() {
        return this.kaufsalter;
    }

    public long getKaufstaerke() {
        return this.kaufstaerke;
    }

    private long getVerkaufstaerke() {
        return this.verkaufstaerke;
    }

    public long getSaisonkauf() {
        return this.saisonkauf;
    }

    public boolean getDoppeltransfer(){
        return this.doppeltransfer;
    }

    public int getId() {
        return this.id;
    }

    public long getSaisonverkauf() {
        return this.saisonverkauf;
    }

    public long getSpieltagkauf() {
        return this.spieltagkauf;
    }

    public long getSpieltagverkauf() {
        return this.spieltagverkauf;
    }

    public long getKaufpreis() {
        return this.kaufpreis;
    }

    public long getVerkaufpreis() {
        return this.verkaufpreis;
    }

    public long getAlter2() {
        return this.alter2;
    }

    public long getKaufstaerke2() {
        return this.kaufstaerke2;
    }

    private long getVerkaufstaerke2() {
        return this.verkaufstaerke2;
    }

    public long getSaisonkauf2() {
        return this.saisonkauf2;
    }


    public long getSaisonverkauf2() {
        return this.saisonverkauf2;
    }

    public long getSpieltagkauf2() {
        return this.spieltagkauf2;
    }

    public long getSpieltagverkauf2() {
        return this.spieltagverkauf2;
    }

    public long getKaufpreis2() {
        return this.kaufpreis2;
    }

    public long getVerkaufpreis2() {
        return this.verkaufpreis2;
    }

    public String getName() {
        return this.name;
    }

    public String getPos() {
        return this.position;
    }

    public long getGewinnTotal() {
        return this.verkaufpreis - this.kaufpreis + (this.verkaufpreis2 - this.kaufpreis2);
    }

    public long getGewinn1() {
        return this.verkaufpreis - this.kaufpreis;
    }

    public long getGewinn2() {
        return (this.verkaufpreis2 - this.kaufpreis2);
    }

    public long getAufenthalt() {
        return 34 - this.spieltagkauf + 34 * (this.saisonverkauf - this.saisonkauf - 1) + this.spieltagverkauf;
    }

    public long getAufenthalt2() {
        return 34 - this.spieltagkauf2 + 34 * (this.saisonverkauf2 - this.saisonkauf2 - 1) + this.spieltagverkauf2;
    }


    /*
    Bringt einen String auf die Form SSS/DD SSS/DD
     */
    private String vereinsAufenthalt(){
        StringBuilder lul=new StringBuilder();
        lul.append(getSaisonkauf()).append("/").append(getSpieltagkauf()).append(" ").append(getSaisonverkauf()).append("/").append(getSpieltagverkauf()).setLength(13);
        while(lul.charAt(6)!=' ') lul.insert(5, " ");
        for(int i=0; i<13; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        return lul.toString();
    }

    /*
    Bringt einen String auf die Form AA/PP AA/PP
     */

    private String staerkenEntwicklung(){
        StringBuilder lul=new StringBuilder();
        lul.append(getKaufsalter()).append("/").append(getKaufstaerke()).append(" ").append(getAlter()).append("/").append(getVerkaufstaerke()).setLength(11);
        while(lul.charAt(5)!=' ') lul.insert(4, " ");
        for(int i=0; i<11; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        return lul.toString();
    }
    /*
       Bringt einen String auf die Form $$$.$$$.$$$
       */
    private String kaufpreisString(){
        StringBuilder lul=new StringBuilder();
        lul.append(NumberFormat.getIntegerInstance().format(getKaufpreis())).setLength(11);
        for(int i=0; i<11; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        return lul.toString();
    }

    /*
          Bringt einen String auf die Form $$$.$$$.$$$
          */
    private String verkaufpreisString(){
        StringBuilder lul=new StringBuilder();
        lul.append(NumberFormat.getIntegerInstance().format(getVerkaufpreis())).setLength(11);
        for(int i=0; i<11; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        return lul.toString();
    }

    private String gewinnString(){
        StringBuilder lul=new StringBuilder();
        lul.append(NumberFormat.getIntegerInstance().format(getGewinn1())).setLength(11);
        for(int i=0; i<11; i++){
            int rofl=lul.charAt(i);
            if(rofl==0){
                lul.replace(i, i+1, " ");
            }
        }
        return lul.toString();
    }

    private String doppelTransferString() {
        if (doppeltransfer) {


                StringBuilder lul=new StringBuilder();
                lul.append(getSaisonkauf2()).append("/").append(getSpieltagkauf2()).append(" ").append(getSaisonverkauf2()).append("/").append(getSpieltagverkauf2()).setLength(13);
                while(lul.charAt(6)!=' ') lul.insert(5, " ");
                for(int i=0; i<13; i++){
                    int rofl=lul.charAt(i);
                    if(rofl==0){
                        lul.replace(i, i+1, " ");
                    }
                }
            String vereinsaufenthalt=lul.toString();


            lul = new StringBuilder();
            lul.append(getKaufsalter2()).append("/").append(getKaufstaerke2()).append(" ").append(getAlter2()).append("/").append(getVerkaufstaerke2()).setLength(11);
            while (lul.charAt(5) != ' ') lul.insert(4, " ");
            for (int i = 0; i < 11; i++) {
                int rofl = lul.charAt(i);
                if (rofl == 0) {
                    lul.replace(i, i + 1, " ");
                }
            }

            String staerkenEntwicklung = lul.toString();


            lul = new StringBuilder();
            lul.append(NumberFormat.getIntegerInstance().format(getKaufpreis2())).setLength(11);
            for (int i = 0; i < 11; i++) {
                int rofl = lul.charAt(i);
                if (rofl == 0) {
                    lul.replace(i, i + 1, " ");
                }
            }
            String kaufpreisString = lul.toString();


            lul = new StringBuilder();
            lul.append(NumberFormat.getIntegerInstance().format(getVerkaufpreis2())).setLength(11);
            for (int i = 0; i < 11; i++) {
                int rofl = lul.charAt(i);
                if (rofl == 0) {
                    lul.replace(i, i + 1, " ");
                }}
                String verkaufpreisString = lul.toString();

                lul = new StringBuilder();
                lul.append(NumberFormat.getIntegerInstance().format(getGewinn2())).setLength(11);
                for (int i = 0; i < 11; i++) {
                    int rofl = lul.charAt(i);
                    if (rofl == 0) {
                        lul.replace(i, i + 1, " ");
                    }}
                    String gewinnString = lul.toString();
                    System.out.println(getPos() + " " + getName()+ " Kauf/Verkauf " + vereinsaufenthalt+ " Kauf/Verkaufstaerke: " + staerkenEntwicklung + " -- Kaufpreis: "
                            + kaufpreisString + " Verkaufpreis: " + verkaufpreisString + " Gewinn: " + gewinnString + " Spieltage im Verein: " + getAufenthalt2() + " Doppeltransfer");

                    //return " Doppeltransfer Kauf/Verkauf " + vereinsaufenthalt+ " Kauf/Verkaufstaerke: " + staerkenEntwicklung + " -- Kaufpreis: "
                    //        + kaufpreisString + " Verkaufpreis: " + verkaufpreisString + " Gewinn: " + gewinnString + " Spieltage im Verein: " + getAufenthalt2();


                }




        return "";
    }

    public String toString() {
        // return getPos() + " " + getName() + " S" + getSaisonkauf() + "/" + getSpieltagkauf() + " Kaufestaerke: " + getKaufsalter() + "/" + getKaufstaerke() + " Kaufpreis: " + NumberFormat.getIntegerInstance().format(getKaufpreis()) + " Verkaufpreis: "
        //         + NumberFormat.getIntegerInstance().format(getVerkaufpreis()) + " S" + getSaisonverkauf() + "/" + getSpieltagverkauf() +" Verkaufstaerke: " + getAlter() + "/" + getVerkaufstaerke() + " Gewinn: " + NumberFormat.getIntegerInstance().format(getGewinn())
        //         + " Spieltage im Verein: " + getAufenthalt();

        return getPos() + " " + getName()  + " Kauf/Verkauf " + vereinsAufenthalt()+ " Kauf/Verkaufstaerke: " + staerkenEntwicklung() + " -- Kaufpreis: "
                + kaufpreisString() + " Verkaufpreis: " + verkaufpreisString() + " Gewinn: " + gewinnString() + " Spieltage im Verein: " + getAufenthalt() + doppelTransferString();
    }


}

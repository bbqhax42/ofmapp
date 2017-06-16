package finanzgesamtueberblick;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Scanner;

public class ProfileParser {

    int excelRow=0;
    int gewinn;


    public ProfileParser() {
    }


    public void traderschmiedeExcel(){

        int upper=-1, lower=-1;
        JFrame frame = new JFrame("InputDialog Example #1");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Textfile", "txt"));
        chooser.showOpenDialog(null);

        String path= chooser.getSelectedFile().getPath();

        File file = new File(path);

        String range = JOptionPane.showInputDialog(frame, "Zeitraum? Untere und obere Grenze inklusive. Format: Saison-Saison");
        if(range==null || !(range.contains("-"))) {
            JOptionPane.showMessageDialog(frame,
                    "Ungueltiges Format", "Inane error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        String[] saison=range.split("-");
        lower=Integer.parseInt(saison[0]);
        upper=Integer.parseInt(saison[1]);

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("SHEET NAME");
        sheet.setColumnWidth(0, 25*256);
        sheet.setColumnWidth(1, 14*256);
        sheet.setColumnWidth(2, 14*256);
        sheet.setColumnWidth(3, 14*256);

        CellStyle styleMoney = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        styleMoney.setDataFormat(format.getFormat("_-* #,## \\€_-;-* #,## \\€_-;_-* \"-\"?? \\€_-;_-@_-"));

        Row row = sheet.createRow((short) 0);
        row.createCell(0).setCellValue("Verein");
        row.createCell(1).setCellValue("S" +range);
        row.createCell(2).setCellValue("S" +(lower-1) + "-" +(upper-1));
        row.createCell(3).setCellValue("Differenz");


        try {
            Scanner scanner=new Scanner(file);
            scanner.useDelimiter("\n");
            int i=2;
            while(scanner.hasNext()){
                String next=scanner.next();
                System.out.println(next);
                int teamID=Integer.parseInt(next.trim());

                StringBuffer tmp = new StringBuffer("http://www.onlinefussballmanager.de/vereinsseite/uebersicht/vereinsseite_uebersicht.php?kaderid=" +
                        teamID + "&welche_wechsel=1/");

                try {
                    Scanner scanner2 = new Scanner(new BufferedReader
                            (new InputStreamReader(new URL(tmp.toString()).openStream())));
                    scanner2.useDelimiter("\n");
                    for (int jj=0; jj<89; jj++){
                       scanner2.next();
                    }
                    String teamName=scanner2.next().replace("ajax.name_alt = '", "").replace("';", "").trim();
                    System.out.println(teamName);




                    SpielerFUContainer container=SpielerFUContainer.instance();
                    container.clear();
                    parseProfileById(teamID, container);
                    long rangeWinUp2Date=container.gewinnBerechnenRange(lower, upper, teamID);
                    long rangeWinOld=container.gewinnBerechnenRange(lower-1, upper-1, teamID);


                    row = sheet.createRow(i++);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(teamName);

                    cell = row.createCell(1);
                    cell.setCellValue(rangeWinUp2Date);
                    cell.setCellStyle(styleMoney);

                    cell = row.createCell(2);
                    cell.setCellValue(rangeWinOld);
                    cell.setCellStyle(styleMoney);

                    cell = row.createCell(3);
                    cell.setCellValue(rangeWinUp2Date-rangeWinOld);
                    cell.setCellStyle(styleMoney);





                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame,
                            "Fehler beim Teamname parsen", "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }



            }
            for (int j = 0; j < 4; j++) {
                sheet.autoSizeColumn(j, false);
            }
            try {
                FileOutputStream fileOut = new FileOutputStream("workbook2.xlsx");
                wb.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame,
                    "File not found", "Inane error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        System.out.println("done");
    }


    public void parseProfileById(int id, SpielerFUContainer container) {

        long startTime = System.currentTimeMillis();
        StringBuffer fileTmp = new StringBuffer("ID " + id + ".txt");
        //PrintWriter writer = null;
        //try {
          //  writer = new PrintWriter(fileTmp.toString(), "UTF-8");
        //} catch (FileNotFoundException e) {
         //   e.printStackTrace();
       // } catch (UnsupportedEncodingException e) {
        //    e.printStackTrace();
        //}


        StringBuffer tmp = new StringBuffer("http://www.onlinefussballmanager.de/vereinsseite/uebersicht/vereinsseite_uebersicht.php?kaderid=" +
                id + "&welche_wechsel=1/");


        try {


            Scanner scanner = new Scanner(new BufferedReader
                    (new InputStreamReader(new URL(tmp.toString()).openStream())));


            StringBuffer tmp2 = new StringBuffer(id + "");
            //System.out.println("Daten fuer Vereinsprofil ID " + id + " werden gelesen");
           // writer.println("Daten fuer Vereinsprofil ID " + id + " werden gelesen");
            int flag = -1;
            while (scanner.hasNext() && flag != 3) {
                String[] spielerIdUndName = null;
                String position = null;
                int alter = 0, staerke = 0, preis = 0, spieltag = 0, saison = 0, spielerId = 0;
                boolean zugangabgang = false;
                SpielerFU newSpielerFU;

                String next = scanner.nextLine().replace('"', '_').trim();
                //System.out.println(next);

                if (next.equals("<span class=_tabelle-small bold_>alter / neuer Verein</span>")) {
                    flag = 1;
                    //System.out.println("Flag = " + flag);
                }


                if (flag == 2) {

                    /*
                    Skippt wiederholbaren Block
                    <tr bgcolor=_#b0dbb0_>
                    <td>
                    <div align=_center_>
                     */
                    for (int i = 0; i < 2; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                    /*
                    Analysiert ob next Form
                    a) <img src=_/bilder/transfer_zugang.gif_ alt=__ height=_13_ width=_13_ align=_absmiddle_>
                    oder
                    b) <img src=_/bilder/transfer_abgang.gif_ alt=__ height=_13_ width=_13_ align=_absmiddle_>
                    hat
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim();

                        if (next.equals("<!-- Counter Old Start -->") && flag == 2) {
                            flag = 3;
                             //System.out.println("Flag = " + flag);
                            break;
                        }


                        if (next.charAt(27) == 'z') {
                            //System.out.println("Zugang");
                            zugangabgang = true;
                        }
                        if (next.charAt(27) == 'a') {
                             //System.out.println("Abgang");
                            zugangabgang = false;
                        }
                    }

                    /*
                    Skippt wiederholbaren Block
                    </div>
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                    for (int i = 0; i < 4; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                    /*
                    Parst Spieltag und Saison aus einem String folgender Form:
                    21.&#160;/&#160;142</div>
                    in dem die Form auf 21.142 im obrigen Beispiel abgeaendert wird
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim();
                        String[] spieltagsaison = next.replace("&#160;/&#160;", "").replace("</div>", "").replace('.', '-').split("-");
                        spieltag = Integer.parseInt(spieltagsaison[0]);
                        saison = Integer.parseInt(spieltagsaison[1]);
                        // System.out.println("Spieltag: " + spieltagsaison[0] + " Saison: " + spieltagsaison[1]);
                    }

                    /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_left_>
                    */
                    for (int i = 0; i < 3; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                    /*
                    Parst SpielerFU ID und Spielername aus einem String folgender Form:
                    <b><a class=_black_ target=__blank_ href=_/player/161385189-Marvan-Dilhani_ onclick=_NewWindow(this.href,'161385189','400','400','yes');return false_>Marvan Dilhani</a></b></div>
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim().replace('"', '_').replace("<b><a class=_black_ target=__blank_ href=_/player/", "").replace("_ onclick=_NewWindow(this.href,", "_").replace(",'400','400','yes');return false_>", "").replace("</a></b></div>", "");
                        String[] tmp3 = next.split("_");
                        spielerIdUndName = tmp3[1].split("'");
                        spielerId = Integer.parseInt(spielerIdUndName[1]);
                        //System.out.println("SpielerFU: " + spielerIdUndName[2] + " SpielerFU ID: " + spielerIdUndName[1]);
                    }





                     /*
                    Skippt wiederholbaren Block
                    <td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                    for (int i = 0; i < 3; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                    /*
                    Parst die Position des Spielers
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim().replace("</div>", "");
                        position = next;
                        //System.out.println("Position: " + position);
                    }

                     /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                    for (int i = 0; i < 3; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                      /*
                    Parst das Alter des Spielers
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim().replace("</div>", "");
                        alter = Integer.parseInt(next);
                        // System.out.println("Alter: " + alter);
                    }

                     /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                    for (int i = 0; i < 3; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                      /*
                    Parst die Staerke des Spielers
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim().replace("</div>", "");
                        staerke = Integer.parseInt(next);
                        // System.out.println("Staerke: " + staerke);
                    }

                    /*
                    Skippt wiederholbaren Block
                    </td>
                    */
                    for (int i = 0; i < 1; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                     /*
                    Parst den Preis des Spielers
                     */
                    if (scanner.hasNextLine()) {
                        next = scanner.nextLine().replace('"', '_').trim().replace("<td align=_center_ nowrap class=_black_>", "").replace(".", "").replace("€", "").trim();
                        if (next.equals("-")) {
                            next = "0";
                        }
                        preis = Integer.parseInt(next);
                        //  System.out.println("Preis: " + preis);
                    }

                     /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                    for (int i = 0; i < 3; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                    /*
                    Ueberprueft ob der SpielerFU gefeuert wurde
                     */
                    if (scanner.hasNext()) {
                        next = scanner.nextLine().replace('"', '_').trim();
                        if (next.contains("...wurde gefeuert!                                    </td>") || next.contains("Amateurmarkt") || next.contains("Vertragsende") || next.contains("unbekannt") || next.contains("A-Jugend")) {
                            /*
                            Skippt wiederholbaren Block
                            <td></td>
                            </tr>
                            */
                            for (int i = 0; i < 2; i++) {
                                if (scanner.hasNext()) {
                                    scanner.nextLine();
                                }
                            }
                        } else {
                            /*
                            Skippt wiederholbaren Block
                            </td>
                            <td></td>
                            </tr>
                            */
                            for (int i = 0; i < 3; i++) {
                                if (scanner.hasNext()) {
                                    scanner.nextLine();
                                }
                            }

                        }
                    }
                    if (!zugangabgang) {
                        if (container.istVorhandenId(spielerId)) {
                            SpielerFU tmpSpielerFU = container.findeSpielerId(spielerId);
                            tmpSpielerFU.setAlter2(alter);
                            tmpSpielerFU.setVerkaufstaerke2(staerke);
                            tmpSpielerFU.setVerkaufpreis2(preis);
                            tmpSpielerFU.setSpieltagverkauf2(spieltag);
                            tmpSpielerFU.setSaisonverkauf2(saison);
                            tmpSpielerFU.setDoppeltransfer();
                            //System.out.println("Doppeltransfer entdeckt, Abgangsdaten updated " + tmpSpielerFU);
                        } else {
                            newSpielerFU = new SpielerFU(spielerId, spielerIdUndName[2], position, alter, staerke, preis, spieltag, saison);
                            container.linkSpieler(newSpielerFU);
                            //System.out.println("Neuer SpielerFU erstellt: " + spielerIdUndName[2]);
                        }

                    }

                    if (zugangabgang) {
                        if (container.istVorhandenId(spielerId)) {
                            SpielerFU tmpSpielerFU = container.findeSpielerId(spielerId);
                            if (tmpSpielerFU.getDoppeltransfer()) {
                                tmpSpielerFU.setSpieltagkauf2(spieltag);
                                tmpSpielerFU.setSaisonkauf2(saison);
                                tmpSpielerFU.setKaufpreis2(preis);
                                tmpSpielerFU.setKaufstaerke2(staerke);
                                tmpSpielerFU.setKaufsalter2(alter);
                                //System.out.println("Doppeltransfer entdeckt, Zugangsdaten updated " + tmpSpielerFU);
                            } else {
                                tmpSpielerFU.setSpieltagkauf(spieltag);
                                tmpSpielerFU.setSaisonkauf(saison);
                                tmpSpielerFU.setKaufpreis(preis);
                                tmpSpielerFU.setKaufstaerke(staerke);
                                tmpSpielerFU.setKaufsalter(alter);
                                //System.out.println("Updated: " + tmpSpielerFU);
                            }
                        }
                    }



                }

                if (flag == 1) {
                    for (int i = 0; i < 4; i++) {
                        if (scanner.hasNext()) {
                            scanner.nextLine();
                        }
                    }

                    flag = 2;
                    // System.out.println("Flag = " + flag);
                }


            }
            container.unverkaufteSpielerEntfernen();
            if (container.getSize() == 0) {
                System.out.println("Kein Team gefunden!");
             //   writer.println("Kein Team gefunden!");

            } else {
                //System.out.println("Gewinn total: " + NumberFormat.getIntegerInstance().format(container.gewinnBerechnen()));
               // System.out.println("Durchschnittsgewinn: " + NumberFormat.getIntegerInstance().format(container.gewinnBerechnen() / container.getSize()));

                //System.out.println("Daten fuer Vereinsprofil ID " + id + " fertig gelesen");
               // writer.println("Gewinn total: " + container.gewinnBerechnen());
                //writer.println("Durchschnittsgewinn: " + container.gewinnBerechnen() / container.getSize());
               // writer.println("Daten fuer Vereinsprofil ID " + id + " fertig gelesen");


            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            container.clear();
            System.out.println("NumberFormatException bei Vereinsprofil " + id);
           // writer.println("NumberFormatException bei Vereinsprofil " + id);

        }
        catch (ArrayIndexOutOfBoundsException e) {
            container.clear();
            System.out.println("ArrayOutOfBoundsException bei Vereinsprofil " + id);
          //  writer.println("ArrayOutOfBoundsException bei Vereinsprofil " + id);

        }

       // writer.close();

    }


    //Befuellt den Container, wertet ihn aus und leert ihn dann wieder und befuellt ihn neu mit der naechsten ID
    public void parseProfileByRange(int lower, int upper, SpielerFUContainer container) {
        StringBuffer fileTmp = new StringBuffer("ID " + lower + " bis " + upper + ".txt");
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileTmp.toString(), "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (long id = lower; id < upper + 1; id++) {




            StringBuffer tmp = new StringBuffer("http://www.onlinefussballmanager.de/vereinsseite/uebersicht/vereinsseite_uebersicht.php?kaderid=" +
                    id + "&welche_wechsel=1/");


            try {


                Scanner scanner = new Scanner(new BufferedReader
                        (new InputStreamReader(new URL(tmp.toString()).openStream())));


                System.out.println("Daten fuer Vereinsprofil ID " + id + " werden gelesen");

                int flag = -1;
                while (scanner.hasNext() && flag != 3) {
                    String[] spielerIdUndName = null;
                    String position = null;
                    int alter = 0, staerke = 0, preis = 0, spieltag = 0, saison = 0, spielerId = 0;
                    boolean zugangabgang = false;
                    SpielerFU newSpielerFU;

                    String next = scanner.nextLine().replace('"', '_').trim();
                    //System.out.println(next);

                    if (next.equals("<span class=_tabelle-small bold_>alter / neuer Verein</span>")) {
                        flag = 1;
                        //System.out.println("Flag = " + flag);
                    }


                    if (flag == 2) {

                    /*
                    Skippt wiederholbaren Block
                    <tr bgcolor=_#b0dbb0_>
                    <td>
                    <div align=_center_>
                     */
                        for (int i = 0; i < 2; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                    /*
                    Analysiert ob next Form
                    a) <img src=_/bilder/transfer_zugang.gif_ alt=__ height=_13_ width=_13_ align=_absmiddle_>
                    oder
                    b) <img src=_/bilder/transfer_abgang.gif_ alt=__ height=_13_ width=_13_ align=_absmiddle_>
                    hat
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim();

                            if (next.equals("<!-- Counter Old Start -->") && flag == 2) {

                                // System.out.println("Flag = " + flag);
                                break;
                            }


                            if (next.charAt(27) == 'z') {
                                //System.out.println("Zugang");
                                zugangabgang = true;
                            }
                            if (next.charAt(27) == 'a') {
                                // System.out.println("Abgang");
                                zugangabgang = false;
                            }
                        }

                    /*
                    Skippt wiederholbaren Block
                    </div>
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                        for (int i = 0; i < 4; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                    /*
                    Parst Spieltag und Saison aus einem String folgender Form:
                    21.&#160;/&#160;142</div>
                    in dem die Form auf 21.142 im obrigen Beispiel abgeaendert wird
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim();
                            String[] spieltagsaison = next.replace("&#160;/&#160;", "").replace("</div>", "").replace('.', '-').split("-");
                            spieltag = Integer.parseInt(spieltagsaison[0]);
                            saison = Integer.parseInt(spieltagsaison[1]);
                            // System.out.println("Spieltag: " + spieltagsaison[0] + " Saison: " + spieltagsaison[1]);
                        }

                    /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_left_>
                    */
                        for (int i = 0; i < 3; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                    /*
                    Parst SpielerFU ID und Spielername aus einem String folgender Form:
                    <b><a class=_black_ target=__blank_ href=_/player/161385189-Marvan-Dilhani_ onclick=_NewWindow(this.href,'161385189','400','400','yes');return false_>Marvan Dilhani</a></b></div>
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim().replace('"', '_').replace("<b><a class=_black_ target=__blank_ href=_/player/", "").replace("_ onclick=_NewWindow(this.href,", "_").replace(",'400','400','yes');return false_>", "").replace("</a></b></div>", "");
                            String[] tmp3 = next.split("_");
                            spielerIdUndName = tmp3[1].split("'");
                            spielerId = Integer.parseInt(spielerIdUndName[1]);
                            //System.out.println("SpielerFU: " + spielerIdUndName[2] + " SpielerFU ID: " + spielerIdUndName[1]);
                        }



                     /*
                    Skippt wiederholbaren Block
                    <td>
                    <div align=_center_ class=_black_>
                    */
                        for (int i = 0; i < 3; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                    /*
                    Parst die Position des Spielers
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim().replace("</div>", "");
                            position = next;
                            //System.out.println("Position: " + position);
                        }

                     /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                        for (int i = 0; i < 3; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                      /*
                    Parst das Alter des Spielers
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim().replace("</div>", "");
                            alter = Integer.parseInt(next);
                            // System.out.println("Alter: " + alter);
                        }

                     /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                        for (int i = 0; i < 3; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                      /*
                    Parst die Staerke des Spielers
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim().replace("</div>", "");
                            staerke = Integer.parseInt(next);
                            // System.out.println("Staerke: " + staerke);
                        }

                    /*
                    Skippt wiederholbaren Block
                    </td>
                    */
                        for (int i = 0; i < 1; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                     /*
                    Parst den Preis des Spielers
                     */
                        if (scanner.hasNextLine()) {
                            next = scanner.nextLine().replace('"', '_').trim().replace("<td align=_center_ nowrap class=_black_>", "").replace(".", "").replace("€", "").trim();
                            if (next.equals("-")) {
                                next = "0";
                            }
                            preis = Integer.parseInt(next);
                            //  System.out.println("Preis: " + preis);
                        }

                     /*
                    Skippt wiederholbaren Block
                    </td>
                    <td>
                    <div align=_center_ class=_black_>
                    */
                        for (int i = 0; i < 3; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                    /*
                    Ueberprueft ob der SpielerFU gefeuert wurde
                     */
                        if (scanner.hasNext()) {
                            next = scanner.nextLine().replace('"', '_').trim();
                            if (next.contains("...wurde gefeuert!                                    </td>") || next.contains("Amateurmarkt") || next.contains("Vertragsende") || next.contains("unbekannt") || next.contains("A-Jugend")) {
                            /*
                            Skippt wiederholbaren Block
                            <td></td>
                            </tr>
                            */
                                for (int i = 0; i < 2; i++) {
                                    if (scanner.hasNext()) {
                                        scanner.nextLine();
                                    }
                                }
                            } else {
                            /*
                            Skippt wiederholbaren Block
                            </td>
                            <td></td>
                            </tr>
                            */
                                for (int i = 0; i < 3; i++) {
                                    if (scanner.hasNext()) {
                                        scanner.nextLine();
                                    }
                                }

                            }
                        }
                        if (!zugangabgang) {
                            if (container.istVorhandenId(spielerId)) {
                                SpielerFU tmpSpielerFU = container.findeSpielerId(spielerId);
                                tmpSpielerFU.setAlter2(alter);
                                tmpSpielerFU.setVerkaufstaerke2(staerke);
                                tmpSpielerFU.setVerkaufpreis2(preis);
                                tmpSpielerFU.setSpieltagverkauf2(spieltag);
                                tmpSpielerFU.setSaisonverkauf2(saison);
                                System.out.println("Doppeltransfer entdeckt, Abgangsdaten updated " + tmpSpielerFU);
                            } else {
                                newSpielerFU = new SpielerFU(spielerId, spielerIdUndName[2], position, alter, staerke, preis, spieltag, saison);
                                container.linkSpieler(newSpielerFU);
                                //System.out.println("Neuer SpielerFU erstellt: " + spielerIdUndName[2]);
                            }

                        }

                        if (zugangabgang) {
                            if (container.istVorhandenId(spielerId)) {
                                SpielerFU tmpSpielerFU = container.findeSpielerId(spielerId);
                                if (tmpSpielerFU.getAlter() < 0) {
                                    tmpSpielerFU.setSpieltagkauf2(spieltag);
                                    tmpSpielerFU.setSaisonkauf2(saison);
                                    tmpSpielerFU.setKaufpreis2(preis);
                                    tmpSpielerFU.setKaufstaerke2(staerke);
                                    System.out.println("Doppeltransfer entdeckt, Zugangsdaten updated" + tmpSpielerFU);
                                } else {
                                    tmpSpielerFU.setSpieltagkauf(spieltag);
                                    tmpSpielerFU.setSaisonkauf(saison);
                                    tmpSpielerFU.setKaufpreis(preis);
                                    tmpSpielerFU.setKaufstaerke(staerke);
                                    tmpSpielerFU.setKaufsalter(alter);
                                    // System.out.println("Updated: " + tmpSpielerFU);
                                }
                            }
                        }

                    }

                    if (flag == 1) {
                        for (int i = 0; i < 4; i++) {
                            if (scanner.hasNext()) {
                                scanner.nextLine();
                            }
                        }

                        flag = 2;
                        // System.out.println("Flag = " + flag);
                    }


                }
                container.unverkaufteSpielerEntfernen();
                if (container.getSize() == 0) {
                    System.out.println("Kein Team gefunden!");


                } else {
                    //System.out.println(container.getSize());
                    container.unverkaufteSpielerEntfernen();
                    //System.out.println(container.getSize());
                    System.out.println("Gewinn total: " + NumberFormat.getIntegerInstance().format(container.gewinnBerechnen()));
                    System.out.println("Durchschnittsgewinn: " + NumberFormat.getIntegerInstance().format(container.gewinnBerechnen() / container.getSize()));
                    writer.println("Daten fuer Vereinsprofil ID " + id + " werden gelesen");
                    writer.println("Gewinn total: " + NumberFormat.getIntegerInstance().format(container.gewinnBerechnen()));
                    writer.println("Durchschnittsgewinn: " + NumberFormat.getIntegerInstance().format(container.gewinnBerechnen() / container.getSize()));

                    container.clear();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (NumberFormatException e) {
                container.clear();
                System.out.println("NumberFormatException bei Vereinsprofil " + id);
                writer.println("NumberFormatException bei Vereinsprofil " + id);

            }
            catch (ArrayIndexOutOfBoundsException e) {
                container.clear();
                System.out.println("ArrayOutOfBoundsException bei Vereinsprofil " + id);
                writer.println("ArrayOutOfBoundsException bei Vereinsprofil " + id);

            }
            catch (StringIndexOutOfBoundsException e) {
                container.clear();
                System.out.println("StringIndexOutOfBoundsException bei Vereinsprofil " + id);
                writer.println("StringIndexOutOfBoundsException bei Vereinsprofil " + id);
            }

        }

        writer.close();

    }




}

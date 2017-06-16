package gui;

import finanzgesamtueberblick.ProfileParser;
import transfermarkt.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Chris on 01.12.2016.
 */
public class MainWindow extends Frame implements ActionListener {

    private PlayerListContainer container;
    private Database databaseConn = null;


    public MainWindow() {
        super("Mainwindow");
        this.setLayout(new FlowLayout());
        Button buttonTransfermarkt = new Button("Transfermarkt");
        Button buttonSpielerwechsel = new Button("Spielerwechsel");
        Button buttonDatenbankbefuellen = new Button("Datenbank befuellen");
        Button buttonDatenbankstarten = new Button("Datenbank starten");
        Button buttonDatenbankbeenden = new Button("Datenbank beenden");
        Button buttonQuery = new Button("SQL Anfrage");
        Button buttonAutoBidder = new Button("AutoBidder");
        Button traderschmiedeButton = new Button("Traderschmiede Excel");
        this.add(buttonTransfermarkt);
        buttonTransfermarkt.addActionListener(this);
        this.add(buttonSpielerwechsel);
        buttonSpielerwechsel.addActionListener(this);
        this.add(buttonDatenbankbefuellen);
        buttonDatenbankbefuellen.addActionListener(this);
        this.add(buttonDatenbankstarten);
        buttonDatenbankstarten.addActionListener(this);
        this.add(buttonDatenbankbeenden);
        buttonDatenbankbeenden.addActionListener(this);
        this.add(buttonQuery);
        buttonQuery.addActionListener(this);
        this.add(buttonAutoBidder);
        buttonAutoBidder.addActionListener(this);
        this.add(traderschmiedeButton);
        traderschmiedeButton.addActionListener(this);
        container = PlayerListContainer.instance();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocation(400, 400);
        pack();
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Transfermarkt")) {
            new Transfermarkt();
            container.clear();

        } else if (e.getActionCommand().equals("Spielerwechsel")) {
            new SpielerWechsel();
            container.clear();




        } else if (e.getActionCommand().equals("Datenbank befuellen")) {

            JFrame frame = new JFrame("InputDialog Example #1");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Objectfile", "ser"));
            chooser.showOpenDialog(this);
            String range = chooser.getSelectedFile().getName().trim();

            if (range == null || range.length() <= 1) {
                JOptionPane.showMessageDialog(frame,
                        "Ungueltiges Format", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
            try {
                databaseConn = new Database(true);
                container = container.load(range);
                container.saveDB(databaseConn);
                databaseConn.startServer();
                System.out.println("Database entries done!");
            } catch (ClassNotFoundException f) {
                f.printStackTrace();
            } catch (IOException f) {
                f.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            container.clear();



        } else if (e.getActionCommand().equals("Datenbank starten")) {

            if (databaseConn == null) {
                try {
                    databaseConn = new Database();
                    System.out.println("Connection initiated!");
                    databaseConn.startServer();
                } catch (ClassNotFoundException f) {
                    f.printStackTrace();
                } catch (SQLException f) {
                    f.printStackTrace();
                }
            } else System.out.println("Connection already initiated!");



        } else if (e.getActionCommand().equals("Datenbank beenden")) {

            if (databaseConn != null) {
                databaseConn.stopServer();
                databaseConn.closeConn();
                System.out.println("Connection stopped!");
            } else System.out.println("There was no connection!");


        } else if (e.getActionCommand().equals("SQL Anfrage")) {

            JFrame frame = new JFrame("InputDialog Example #1");
            String query = JOptionPane.showInputDialog(frame, "Query?");
            if (query == null) {
                JOptionPane.showMessageDialog(frame,
                        "Keine gueltiges Query", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
            try {
                databaseConn.createExcelSheet(query);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else if (e.getActionCommand().equals("AutoBidder")) {

            JFrame frame = new JFrame("InputDialog Example #1");
            String query = JOptionPane.showInputDialog(frame, "SpielerID?");
                System.out.println(query);
                new AutoBidder(Integer.parseInt(query));

            }else if (e.getActionCommand().equals("Traderschmiede Excel")) {

            ProfileParser obj=new ProfileParser();
            obj.traderschmiedeExcel();


        }


        }
    }


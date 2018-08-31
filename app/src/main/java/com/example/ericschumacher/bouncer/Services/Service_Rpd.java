package com.example.ericschumacher.bouncer.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ericschumacher.bouncer.Objects.Object_Model_Color_Shape;
import com.example.ericschumacher.bouncer.Objects.Rpd.Kindartikel;
import com.example.ericschumacher.bouncer.Objects.Rpd.Warenbewegung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Service_Rpd extends IntentService{

    private java.sql.Connection cWawi = null;
    Statement stat = null;
    ResultSet rs = null;
    private final String url = "jdbc:sqlserver://62.108.36.198:49745;databaseName=eazybusiness;user=sa;password=D353§$tgftg";
    private Statement stErp;
    private Connection cErp;
    int verkauft;
    int anzeige = 0;
    ArrayList<Object_Model_Color_Shape> lModelColorShape = new ArrayList<Object_Model_Color_Shape>();
    ArrayList<Warenbewegung> Warenbuchung = new ArrayList<Warenbewegung>();
    ArrayList<Kindartikel> Kinder = new ArrayList<Kindartikel>();
    DecimalFormat dff = new DecimalFormat("####0.00");

    public Service_Rpd() {
        super("Service_Rpd");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

            cWawi = java.sql.DriverManager.getConnection(url);
            if (cWawi != null)
                System.out.println("Connection Successful!!");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cErp = DriverManager.getConnection("jdbc:mysql://217.160.167.191:3306/svp_erp", "eric", "kuerbiskopf1");
                stErp = cErp.createStatement();
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            String bsql = "Update Lagerliste set Relevant=0";
            String sql = "SELECT Model_Color_Shape.id, Model_Color_Shape.id_shape, Model_Color.id_color, Model.name FROM Model_Color_Shape\n" +
                    "    INNER JOIN Model_Color ON Model_Color_Shape.id_model_color = Model_Color.id INNER JOIN Model ON Model_Color.id_model = Model.id";
            stat = cWawi.createStatement();
            //stErp.execute(bsql);
            rs = stErp.executeQuery(sql);

            while (rs.next()) {
                lModelColorShape.add(new Object_Model_Color_Shape(rs.getInt("id"), rs.getInt("id_color"), rs.getInt("id_shape"), rs.getString("name")));
            }

            for (int j = 0; j < lModelColorShape.size(); j++) {
                // System.out.println(skuliste.get(j));
                double Vaterprio = 0;
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

                String sql1 = "Select kArtikel, fvkNetto from dbo.tArtikel where cArtNr like('" + skuliste.get(j)
                        + "-00_') AND cArtNr Not like('" + skuliste.get(j) + "-001')";
                stat = cWawi.createStatement();
                rs = stat.executeQuery(sql1);
                Kinder.clear();
                while (rs.next()) {
                    String key = rs.getString("kArtikel");
                    double preis = rs.getDouble("fvkNetto");
                    int lager = 0;
                    Kinder.add(new Kindartikel(key, preis, lager));
                }

                // Kinderebene
                for (int x = 0; x < Kinder.size(); x++) {
                    // System.out.println(Kinder.size());

                    Warenbuchung.clear();

                    String sql2 = "Select h.kArtikel, fAnzahl, dGebucht, cKommentar, fLagerBestandGesamt, fLagerbestand, fVerfuegbar from  dbo.tArtikelHistory h  Where kArtikel="
                            + Kinder.get(x).getKey();
                    stat = cWawi.createStatement();
                    rs = stat.executeQuery(sql2);

                    while (rs.next()) {
                        int anzahl = rs.getInt("fAnzahl");
                        String d1 = df.format(rs.getDate("dGebucht"));
                        java.util.Date date = df.parse(d1);
                        // System.out.println(date);
                        String kommentar = rs.getString("cKommentar");
                        int lagerbestand = rs.getInt("fLagerbestand");
                        Warenbuchung.add(new Warenbewegung(anzahl, date, kommentar, lagerbestand));
                    }

                    verkauft = 0;
                    double verkaufstage = 1;
                    if (Warenbuchung.size() > 0) {
                        int maxdate = Warenbuchung.size() - 1;

                        verkaufstage = datediff(Warenbuchung.get(0).getDate(), Warenbuchung.get(maxdate).getDate());
                    }

                    // Problem liegt bei 0 Wert für verkauft Variable -----------------

                    // System.out.println();
                    // System.out.println("Gesamter Zeitraum: "+verkaufstage);

                    for (int i = 0; i < Warenbuchung.size(); i++) {

                        if (Warenbuchung.get(i).getfAnzahl() < 0 && (Warenbuchung.get(i).getKommentar().equals("Auslieferung") || Warenbuchung.get(i).getKommentar().equals("Versand mit Packtisch+"))) {
                            verkauft = verkauft - Warenbuchung.get(i).getfAnzahl(); // Hier ist das Problem // Checken ob überall Auslieferung steht - Beispiel SKU 1032 - Check in DB
                        }
                        if (Warenbuchung.get(i).getLagerbestand() == 0 && (Warenbuchung.size() > i + 1)) {
                            long d = datediff(Warenbuchung.get(i).getDate(), Warenbuchung.get(i + 1).getDate());
                            // System.out.println(d);
                            verkaufstage = verkaufstage - d;

                        }
                    }

                    int lager = 0;
                    if (verkaufstage <= 0) {
                        verkaufstage = 1;
                    }

                    double prio = Kinder.get(x).getPreis() * verkauft / verkaufstage;

                    double verkaufpweek = (double) verkauft * 7.0 / verkaufstage;
                    if (Warenbuchung.size() > 0) {
                        lager = Warenbuchung.get(Warenbuchung.size() - 1).getLagerbestand();
                    }


                    Kinder.get(x).setLager(lager);
                    Kinder.get(x).setPrio(prio);

                    /*
                     * System.out.println("Verfügbare Tage: "+verkaufstage);
                     * System.out.println("Verkaufte Geräte: "+verkauft);
                     * System.out.println("Preis: "+dff.format(Kinder.get(x).
                     * getPreis())+" \nPriorität: "+Kinder.get(x).getPrio());
                     * System.out.println("Verkauf p Woche: "+verkaufpweek);
                     * System.out.println("Auf Lager: "+Kinder.get(x).getLager()
                     * );
                     */

                    if (Kinder.get(x).getLager() < verkaufpweek) {
                        Kinder.get(x).setZugelassen(true);
                        // System.out.println("Zugelassen:
                        // "+Kinder.get(x).isZugelassen());

                        String sql3 = "Update Lagerliste set Relevant = 1 where SKU=" + skuliste.get(j);
                        stErp.execute(sql3);
                    }
                    // System.out.println();
                    Vaterprio = Vaterprio + Kinder.get(x).getPrio();

                    if (Vaterprio == 0) {
                        System.out.println(Vaterprio);
                        System.out.println("SKU: "+skuliste.get(j)+", "+"Preis: "+Double.toString(Kinder.get(x).getPreis())+ "Verkauft: "+Integer.toString(verkauft)+ "Verkaufstage: "+ Double.toString(verkaufstage));
                    }

                } // end for kinder

                // System.out.println("Durchschnittspriorität:
                // "+round2(Vaterprio/Kinder.size()));
                if (Kinder.size() > 0) {
                    Vaterprio = round2(Vaterprio / Kinder.size());
                } else {
                    Vaterprio = 1000;
                }


                // System.out.println(skuliste.get(j));
                String sql4 = "Update Lagerliste set Prioritaet =" + Vaterprio + " where SKU='" + skuliste.get(j) + "'";
                stErp.execute(sql4);
                anzeige = anzeige + 1;
                Log.i("Status", ("Datensatz: " + anzeige + " von " + skuliste.size()));
                // System.out.println(anzeige);

            } // end for skuliste
        } // end try

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
        }

        try {
            cWawi.close();
            cErp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    long datediff(java.util.Date from, java.util.Date to) {
        long i = 0;
        i = from.getTime() - to.getTime();
        i = Math.abs(i / (24 * 60 * 60 * 1000));
        return i;
    }

    double round2(double d) {
        d = Math.floor(d * 100) / 100;
        return d;
    }
}

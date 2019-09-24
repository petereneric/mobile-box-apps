package com.example.ericschumacher.bouncer.Zebra;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.zebra.sdk.btleComm.BluetoothLeConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;

public class ManagerPrinter {

    ZebraPrinter Printer;
    private Connection Connection;
    Context Context;
    SharedPreferences SharedPreferences;

    boolean bUsePrinter;

    public ManagerPrinter(Context context) {
        Context = context;
        SharedPreferences = context.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);
        if (usePrinter(Context)) connect();

    }

    public void disconnect() {
        new Thread(new Runnable() {
            public void run() {

                try {
                    if (Connection != null) {
                        Connection.close();
                    }
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void printDevice(Device device) {
        if (usePrinter(Context) && Connection != null) {
            try {
                if (Printer != null && readyToPrint()) {
                    Connection.write(getDeviceLabel(device));
                    Log.i("What", "should work");
                }
            } catch (ConnectionException e1) {
                Log.d("JOOO","Error sending file to printer");
            }
        }
    }

    private byte[] getDeviceLabel(Device device) {
        String idDevice = Integer.toString(device.getIdDevice());
        //String LKU = Integer.toString(device.getLKU());
        String name = device.getoModel().getName();
        String manufacturer = device.getoModel().getoManufacturer().getName();
        String color = device.getoColor().getName();
        String shape = device.getoShape().getName();

        String label = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW448\n" +
                "^LL0406\n" +
                "^LS0\n" +
                "^FO32,64^GFA,00512,00512,00008,:Z64:\n" +
                "eJxjYBheQB6IBUC4AUofYGCYAKIdWCB8BSiteMxiIkiNkorOJBBfRUUETKct0QGLu7C4THBgYBBqUsuZBKTlGQ+bgMSHGwAAbUQPDQ==:EF28\n" +
                "^FO136,146^GB165,49,4^FS\n" +
                "^FO37,30^GB107,34,4^FS\n" +
                "^BY2,3,62^FT157,96^BCN,,N,N\n" +
                "^FD>:"+idDevice+"^FS\n" +
                "^FT94,53^A0N,20,19^FH\\^FD"+"FEHLT"+"^FS\n" +
                "^FT314,125^A0N,23,21^FH\\^FD"+name+"^FS\n" +
                "^FT187,124^A0N,23,19^FH\\^FD"+manufacturer+"^FS\n" +
                "^FT345,183^A0N,23,16^FH\\^FD"+idDevice+"^FS\n" +
                "^FT37,136^A0N,20,19^FH\\^FD"+color+"^FS\n" +
                "^FT37,161^A0N,17,16^FH\\^FD"+shape+"^FS\n" +
                "^FO33,108^GB92,0,2^FS\n" +
                "^FT146,181^A0N,28,28^FH\\^FD"+"LKU Lagerung"+"^FS\n" +
                "^FT43,52^A0N,20,19^FH\\^FDLKU:^FS\n" +
                "^FO312,134^GE93,64,4^FS\n" +
                "^FT354,159^A0N,17,14^FH\\^FDID^FS\n" +
                "^PQ1,0,1,Y^XZ";


        String labelSimple = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW448\n" +
                "^LL0406\n" +
                "^LS0\n" +
                "^BY4,3,142^FT25,152^BCN,,Y,N\n" +
                "^FD>:"+idDevice +"^FS\n" +
                "^PQ1,0,1,Y^XZ";
        Log.i("Device Label", labelSimple);
        return labelSimple.getBytes();
    }
    private boolean readyToPrint () {
        boolean isReady = true;
        try {
            ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(Printer);
            PrinterStatus printerStatus = (linkOsPrinter != null) ? linkOsPrinter.getCurrentStatus() : Printer.getCurrentStatus();
            if (printerStatus.isReadyToPrint) {
                isReady = true;
            } else {
                if (printerStatus.isHeadOpen) {
                    Toast.makeText(Context, Context.getString(R.string.printer_head_open), Toast.LENGTH_LONG).show();
                } else if (printerStatus.isPaused) {
                    Toast.makeText(Context, Context.getString(R.string.printer_paused), Toast.LENGTH_LONG).show();
                } else if (printerStatus.isPaperOut) {
                    Toast.makeText(Context, Context.getString(R.string.printer_paper_out), Toast.LENGTH_LONG).show();
                }
                isReady = false;
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        return isReady;
    }

    public void connect() {
        new Thread(new Runnable() {
            public void run() {
                Log.i("Zebra connects", "jo");
                if (usePrinter(Context)) {
                    if (Printer == null) {
                        Connection = null;
                        Connection = new BluetoothLeConnection(SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, "C4:F3:12:17:D0:2E"), Context);

                        try {
                            Connection.open();
                        } catch (ConnectionException e) {
                            DemoSleeper.sleep(1000);
                            disconnect();
                        }
                        Printer = null;

                        if (Connection.isConnected()) {
                            try {
                                Printer = ZebraPrinterFactory.getInstance(Connection);
                                String pl = SGD.GET("device.languages", Connection);
                            } catch (ConnectionException e) {
                                Printer = null;
                                DemoSleeper.sleep(1000);
                                disconnect();
                            } catch (ZebraPrinterLanguageUnknownException e) {
                                Printer = null;
                                DemoSleeper.sleep(1000);
                                disconnect();
                            }
                        }
                    }
                }
            }
        }).start();


    }

    public static boolean usePrinter(Context context) {
        return context.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).getBoolean(Constants_Intern.USE_PRINTER, false);
    }

    public static void usePrinter(Context context, boolean bUsePrinter) {
        context.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putBoolean(Constants_Intern.USE_PRINTER, bUsePrinter).commit();
    }
}

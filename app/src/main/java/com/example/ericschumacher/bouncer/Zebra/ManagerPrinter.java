package com.example.ericschumacher.bouncer.Zebra;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    public ManagerPrinter(Context context) {
        Context = context;
        connect();

    }

    public void disconnect() {
        try {
            if (Connection != null) {
                Connection.close();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }

    public void printDevice(Device device) {
        try {
            if (Printer != null && readyToPrint()) {
                Connection.write(getDeviceLabel(device));
                Log.i("What", "should work");
            }
        } catch (ConnectionException e1) {
            Log.d("JOOO","Error sending file to printer");
        }
    }

    private byte[] getDeviceLabel(Device device) {
        /*
        String labelTest = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW408\n" +
                "^LL0200\n" +
                "^LS0\n" +
                "^FT392,47^A0I,28,28^FH\\^FD"+device.getIdModel()+"^FS\n" +
                "^FT256,46^A0I,28,28^FH\\^FD-^FS\n" +
                "^FT220,50^A0I,28,16^FH\\^FD"+"FARBE"+"^FS\n" +
                "^FT135,47^A0I,28,19^FH\\^FD-^FS\n" +
                "^FT113,49^A0I,28,24^FH\\^FD"+"ZUSTAND"+"^FS\n" +
                "^FT160,15^A0I,25,26^FH\\^FD"+device.getName()+"^FS\n" +
                "^FT189,11^A0I,28,28^FH\\^FD:^FS\n" +
                "^FT389,16^A0I,21,28^FH\\^FD"+device.getManufacturer().getName()+"^FS\n" +
                "^FT269,163^A0I,28,28^FH\\^FD"+device.getLKU()+"^FS\n" +
                "^BY4,3,40^FT384,114^BCI,,Y,N\n" +
                "^FD>:"+device.getIdDevice()+"^FS\n" +
                "^PQ1,0,1,Y^XZ";

                */

        String idDevice = Integer.toString(device.getIdDevice());
        String LKU = Integer.toString(device.getLKU());
        String name = device.getName();
        String manufacturer = device.getManufacturer().getName();
        String color = device.getVariationColor().getName();
        String shape = device.getVariationShape().getName();

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
                "^FT94,53^A0N,20,19^FH\\^FD"+LKU+"^FS\n" +
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
        Log.i("Device Label", label);
        return label.getBytes();
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
        if (Printer == null) {
            Connection = null;
            Connection = new BluetoothLeConnection("C4:F3:12:17:D0:2E", Context);

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

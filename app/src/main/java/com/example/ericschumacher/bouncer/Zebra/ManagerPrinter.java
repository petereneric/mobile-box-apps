package com.example.ericschumacher.bouncer.Zebra;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Article;
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

import java.util.ArrayList;

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


    public void printDeviceId(Device device) {
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

    public void printDeviceSku(Article oArticle, Device oDevice) {
        if (usePrinter(Context) && Connection != null) {
            try {
                if (Printer != null && readyToPrint()) {
                    Connection.write(getDeviceSku(oArticle, oDevice));
                    Log.i("What", "should work");
                }
            } catch (ConnectionException e1) {
                Log.d("JOOO","Error sending file to printer");
            }
        }
    }

    public void printBattery(Battery battery) {
        if (usePrinter(Context) && Connection != null) {
            try {
                if (Printer != null && readyToPrint()) {
                    Connection.write(getBatteryLabel(battery));
                    Log.i("What", "should work");
                }
            } catch (ConnectionException e1) {
                Log.d("JOOO","Error sending file to printer");
            }
        }
    }

    private byte[] getBatteryLabel(Battery battery) {
        String id = Integer.toString(battery.getId());
        String name = battery.getName();
        String manufacturer = battery.getoManufacturer().getName();
        String lku = battery.getoManufacturer().getcShortcut()+"-"+Integer.toString(battery.getLku());
        String lStorageName = battery.getlStockName();

        String label = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA\n" +
                "~TA000\n" +
                "~JSN\n" +
                "^LT0\n" +
                "^MNW\n" +
                "^MTT\n" +
                "^PON\n" +
                "^PMN\n" +
                "^LH0,0\n" +
                "^JMA\n" +
                "^PR6,6\n" +
                "~SD15\n" +
                "^JUS\n" +
                "^LRN\n" +
                "^CI27\n" +
                "^PA0,1,1,0\n" +
                "^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW408\n" +
                "^LL200\n" +
                "^LS0\n" +
                "^FPH,3^FT212,188^A@B,25,25,TT0003M_^FH\\^CI28^FDBATTERY^FS^CI27\n" +
                "^FPH,3^FT252,186^A@B,17,18,TT0003M_^FH\\^CI28^FD"+name+"^FS^CI27\n" +
                "^FPH,3^FT322,186^A@B,17,18,TT0003M_^FH\\^CI28^FD"+lku+"^FS^CI27\n" +
                "^FPH,3^FT345,186^A@B,14,13,TT0003M_^FH\\^CI28^FD"+lStorageName+"^FS^CI27\n" +
                "^FPH,3^FT275,186^A@B,14,13,TT0003M_^FH\\^CI28^FD"+manufacturer+"^FS^CI27\n" +
                "^BY2,3,20^FT387,186^BCB,,N,N\n" +
                "^FH\\^FD>:411^FS\n" +
                "^PQ1,0,1,Y\n" +
                "^XZ";

        return label.getBytes();
    }

    private byte[] getDeviceSku(Article oArticle, Device oDevice) {
        String cId = Integer.toString(oDevice.getIdDevice());
        String cSku = oArticle.getkSku();
        String cManufacturer = oDevice.getoModel().getoManufacturer().getName();
        String cModel = oDevice.getoModel().getName();

        String cNumber;
        String cTop;
        String cShape;

        if (oArticle.getkSku().substring(0,1).equals("A")) {
            // New system
            String s = "A-5-Space Gray-64 GB-sehr gut";
            String s2 = "A-1-Schwarz-sehr gut";

            int indexPosition = cSku.indexOf("-");
            ArrayList<Integer> indexPositions = new ArrayList<>();
            while (indexPosition >= 0) {
                indexPositions.add(indexPosition);
                indexPosition = cSku.indexOf("-", indexPosition+1);
            }
            cNumber = cSku.substring(0, indexPositions.get(1));
            if (indexPositions.size() > 3) {
                // With GB
                cTop = cSku.substring(indexPositions.get(1)+1, indexPositions.get(3));
                cShape = cSku.substring(indexPositions.get(3)+1);
            } else {
                // Without GB
                cTop = cSku.substring(indexPositions.get(1)+1, indexPositions.get(2));
                cShape = cSku.substring(indexPositions.get(2)+1);
            }
        } else {
            // Old system
            cTop = cSku;
            cNumber = "";
            cShape = "";
        }

        String labelNew = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA\n" +
                "~TA000\n" +
                "~JSN\n" +
                "^LT0\n" +
                "^MNW\n" +
                "^MTT\n" +
                "^PON\n" +
                "^PMN\n" +
                "^LH0,0\n" +
                "^JMA\n" +
                "^PR6,6\n" +
                "~SD15\n" +
                "^JUS\n" +
                "^LRN\n" +
                "^CI27\n" +
                "^PA0,1,1,0\n" +
                "^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW448\n" +
                "^LL607\n" +
                "^LS0\n" +
                "^FO386,20^GB0,584,1^FS\n" +
                "^FO117,18^GFA,57,2344,4,:Z64:eJxjYGBgaGCAABjNgMYfFR8VHxUfFR8VHxUfFR8VH4niAMtoYgE=:D29D\n" +
                "^FO343,18^GFA,57,2344,4,:Z64:eJxjYGBgaGCAABjNgMYfFR8VHxUfFR8VHxUfFR8VH4niAMtoYgE=:D29D\n" +
                "^BY2,3,27^FT432,584^BCB,,N,N\n" +
                "^FH\\^FD>:"+cId+">63^FS\n" +
                "^FT157,248^BQN,2,6\n" +
                "^FH\\^FDLA,"+cSku+"^FS\n" +
                "^FPH,3^FT75,584^A@B,45,45,TT0003M_^FH\\^CI28^FD"+cTop+"^FS^CI27\n" +
                "^FPH,8^FT228,584^A@B,79,79,TT0003M_^FH\\^CI28^FD"+cNumber+"^FS^CI27\n" +
                "^FPH,3^FT295,584^A@B,45,45,TT0003M_^FH\\^CI28^FD"+cShape+"^FS^CI27\n" +
                "^FPH,3^FT372,584^A@B,20,20,TT0003M_^FH\\^CI28^FD"+cManufacturer+" | "+cModel+"^FS^CI27\n" +
                "^PQ1,0,1,Y\n" +
                "^XZ\n";

        String label = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA\n" +
                "~TA000\n" +
                "~JSN\n" +
                "^LT0\n" +
                "^MNW\n" +
                "^MTT\n" +
                "^PON\n" +
                "^PMN\n" +
                "^LH0,0\n" +
                "^JMA\n" +
                "^PR6,6\n" +
                "~SD15\n" +
                "^JUS\n" +
                "^LRN\n" +
                "^CI27\n" +
                "^PA0,1,1,0\n" +
                "^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW448\n" +
                "^LL607\n" +
                "^LS0\n" +
                "^FO386,20^GB0,584,1^FS\n" +
                "^FO117,18^GFA,57,2344,4,:Z64:eJxjYGBgaGCAABjNgMYfFR8VHxUfFR8VHxUfFR8VH4niAMtoYgE=:D29D\n" +
                "^FO343,18^GFA,57,2344,4,:Z64:eJxjYGBgaGCAABjNgMYfFR8VHxUfFR8VHxUfFR8VH4niAMtoYgE=:D29D\n" +
                "^BY2,3,27^FT432,519^BCB,,N,N\n" +
                "^FH\\^FD>:"+cId+">63^FS\n" +
                "^FT164,255^BQN,2,6\n" +
                "^FH\\^FDLA,"+cSku+"^FS\n" +
                "^FPH,3^FT69,519^A@B,39,38,TT0003M_^FH\\^CI28^FD"+cTop+"^FS^CI27\n" +
                "^FPH,8^FT228,519^A@B,79,79,TT0003M_^FH\\^CI28^FD"+cNumber+"^FS^CI27\n" +
                "^FPH,3^FT287,519^A@B,37,36,TT0003M_^FH\\^CI28^FD"+cShape+"^FS^CI27\n" +
                "^FPH,3^FT372,519^A@B,20,20,TT0003M_^FH\\^CI28^FD"+cManufacturer+" | "+cModel+"^FS^CI27\n" +
                "^PQ1,0,1,Y\n" +
                "^XZ\n";

        return label.getBytes();
    }

    private byte[] getDeviceLabel(Device device) {
        String idDevice = Integer.toString(device.getIdDevice());
        String shape = device.getoShape().getName();
        String cState = device.getStateName();
        String lkuBattery;
        if (device.getoModel().getoBattery() != null) {
            Battery battery = device.getoModel().getoBattery();
            lkuBattery = battery.getoManufacturer().getcShortcut()+"-"+Integer.toString(battery.getLku());
        } else {
            lkuBattery = "-";
        }
        String cLoadingStation = device.getoModel().getoCharger().gettLoadingStation();
        String cCharger = device.getoModel().getoCharger().getName();


        String labelNew = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA\n" +
                "~TA000\n" +
                "~JSN\n" +
                "^LT0\n" +
                "^MNW\n" +
                "^MTT\n" +
                "^PON\n" +
                "^PMN\n" +
                "^LH0,0\n" +
                "^JMA\n" +
                "^PR6,6\n" +
                "~SD15\n" +
                "^JUS\n" +
                "^LRN\n" +
                "^CI27\n" +
                "^PA0,1,1,0\n" +
                "^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW408\n" +
                "^LL200\n" +
                "^LS0\n" +
                "^FO17,66^GFA,29,48,48,:Z64:eJxb1KWxiGi0gIEBAA9MFI0=:2CE5\n" +
                "^FO15,109^GFA,29,48,48,:Z64:eJxb1KWxiHjUwAAAEFIVDw==:22CD\n" +
                "^FO264,118^GFA,29,116,4,:Z64:eJxjYICABjSagYbiABiDBQE=:557A\n" +
                "^FO264,71^GFA,29,116,4,:Z64:eJxjYICABjSagYbiABiDBQE=:557A\n" +
                "^FO17,155^GFA,29,48,48,:Z64:eJxb1KWxiHjUwAAAEFIVDw==:22CD\n" +
                "^FO265,164^GFA,29,112,4,:Z64:eJxjYICABjSagUbiAAR/BIE=:8DFE\n" +
                "^FPH,3^FT21,50^A@N,31,31,TT0003M_^FH\\^CI28^FD"+idDevice+"^FS^CI27\n" +
                "^FPH,3^FT21,139^A@N,17,18,TT0003M_^FH\\^CI28^FD"+shape+"^FS^CI27\n" +
                "^FPH,3^FT293,139^A@N,17,18,TT0003M_^FH\\^CI28^FD"+lkuBattery+"^FS^CI27\n" +
                "^FPH,3^FT21,93^A@N,17,18,TT0003M_^FH\\^CI28^FD"+cState+"^FS^CI27\n" +
                "^BY2,3,20^FT168,48^BCN,,N,N\n" +
                "^FH\\^FD>:"+idDevice+">68^FS\n" +
                "^FPH,3^FT21,184^A@N,17,18,TT0003M_^FH\\^CI28^FD"+cCharger+"^FS^CI27\n" +
                "^FPH,3^FT327,93^A@N,17,18,TT0003M_^FH\\^CI28^FD-^FS^CI27\n" +
                "^FPH,3^FT321,188^A@N,23,22,TT0003M_^FH\\^CI28^FD"+cLoadingStation+"^FS^CI27\n" +
                "^PQ1,0,1,Y\n" +
                "^XZ\n";

        return labelNew.getBytes();
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
                Log.i("Das isser", SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, "28:EC:9A:21:30:55"));
                if (usePrinter(Context)) {
                    if (Printer == null) {
                        Connection = null;
                        Connection = new BluetoothLeConnection(SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, "28:EC:9A:21:30:55"), Context);
                        Log.i("Das isser", SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, "28:EC:9A:21:30:55"));

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

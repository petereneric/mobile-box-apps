package com.example.ericschumacher.bouncer.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dymo.label.framework.Framework;
import com.dymo.label.framework.Label;
import com.dymo.label.framework.LabelSet;
import com.dymo.label.framework.LabelSetRecord;
import com.dymo.label.framework.NewPrintersFoundEvent;
import com.dymo.label.framework.Printer;
import com.dymo.label.framework.PrinterLookupFailureEvent;
import com.dymo.label.framework.PrintersListener;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Model;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Condition;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Shape;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_JSON;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Utilities.Utility_Printer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Interface_Selection, View.OnClickListener {

    // Utilities
    Utility_Network uNetwork;
    Framework uPrinterFramework;

    // Objects
    Object_Model oModel;

    // Layout
    EditText etScan;
    ImageView ivClearScan;
    TextView tvCollector;
    TextView tvCounterReuse;
    TextView tvCounterRecycling;
    TextView tvCounterTotal;
    TextView tvName;
    TextView tvManufacturer;
    TextView tvCharger;
    TextView tvBattery;
    FloatingActionButton fab;

    // Fragments
    FragmentManager fManager;

    // Counter
    private int cRecycling = 0;
    private int cReuse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Counter
        if (savedInstanceState != null) {
            cRecycling = savedInstanceState.getInt(Constants_Intern.COUNTER_RECYCLING, 0);
            cReuse = savedInstanceState.getInt(Constants_Intern.COUNTER_REUSE, 0);
        }

        // Utilities
        uNetwork = new Utility_Network(this);
        setupFrameWork();


        // Objects
        oModel = new Object_Model();

        // Fragments
        fManager = getSupportFragmentManager();

        // Layout
        setLayout();
        handleInteraction();
        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants_Intern.COUNTER_RECYCLING, cRecycling);
        outState.putInt(Constants_Intern.COUNTER_REUSE, cReuse);
    }

    // Layout
    private void setLayout() {
        setContentView(R.layout.activity_selection);
        etScan = findViewById(R.id.et_scan);
        ivClearScan = findViewById(R.id.ivClearScan);
        tvCollector = findViewById(R.id.tvCollector);
        tvCounterReuse = findViewById(R.id.tvCounterReuse);
        tvCounterRecycling = findViewById(R.id.tvCounterRecycling);
        tvCounterTotal = findViewById(R.id.tvCounterTotal);
        tvName = findViewById(R.id.tvName);
        tvManufacturer = findViewById(R.id.tvManufacturer);
        tvCharger = findViewById(R.id.tvCharger);
        tvBattery = findViewById(R.id.tvBattery);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(this);
        ivClearScan.setOnClickListener(this);
    }

    private void handleInteraction() {
        etScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != "" && editable.toString().length() == 15) {

                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etScan.getWindowToken(), 0);

                    // Get TAC
                    String tac = editable.toString().substring(0, 8);
                    uNetwork.getModelByTac(tac, new Interface_VolleyCallback_JSON() {
                        @Override
                        public void onSuccess(JSONObject json) {
                            try {
                                oModel.setId(json.getInt(Constants_Extern.ID_MODEL));
                                oModel.setName(json.getString(Constants_Extern.NAME));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            updateUI();
                            checkExploitation();
                        }

                        @Override
                        public void onFailure(JSONObject json) {
                            startFragmentRequest();
                        }
                    });
                }
            }
        });
    }

    // Class Methods

    private void setupFrameWork() {
        uPrinterFramework = Utility_Printer.instance(this).getFramework();
        uPrinterFramework.setPrintersListener(new PrintersListener()
        {
            @Override
            public void printerLookupFailure(PrinterLookupFailureEvent event)
            {
                final String message = String.format("Unable to contact '%s' (%s)", event.getPrinterUri(),
                        event.getLocation());
                Log.e("Print Label", message);

                MainActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void newPrintersFound(final NewPrintersFoundEvent event)
            {
                Log.i("Printer, ", "Founde");
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // output all discovered printers to the log
                        Iterable<Printer> printers = event.getPrinters();
                        for (Printer p : printers) {
                            Log.i("Printer: ", p.getName());
                            printWithLabelSet(p);
                        }
                    }
                });
            }
        });
    }

    /*
    private void refreshPrinters()
    {

        uPrinterFramework.startRefreshPrinters();

        // / stop refreshing in 5 seconds
        labelContentEditText_.postDelayed(new CheckedRunnable()
        {
            @Override
            public void run2()
            {
                Log.i("PrintLabel", "----------- Stopping printers lookup after 5 seconds");
                uPrinterFramework.stopRefreshPrinters();

                // usually this is called from newPrintersFound() handler
                // newPrintersFound will not be called if all printers are gone
                updateCurrentPrinter(framework_.getPrinters());
            }
        }, 5000);
    }
    */

    void printWithLabelSet(Printer printer)
    {
        // open a label
        Label label = null;
        try {
            label = uPrinterFramework.openLabel(this, "Phone.label");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create a label set
        LabelSet labelSet = uPrinterFramework.createLabelSet();

        // label #1
        LabelSetRecord record = labelSet.addRecord();
        record.setText("TEXT", "6x7=42");

        //label #2
        record = labelSet.addRecord();
        record.setTextMarkup("TEXT", "font family='Arial' size='36'>6x7=<b>42</b></font>");

        // print label
        label.print(printer, labelSet);
    }

    /*
    private void printButtonClick()
    {
        try
        {
            //... open label, assemble print data, etc

            // print with default parameters
            final PrintJob job = label.print(currentPrinter);

            final Runnable getStatus = new CheckedRunnable()
            {
                @Override
                public void run2()
                {
                    job.getStatus();
                }
            };

            // an executor service to ask for the status in the background
            final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            // set the listener to be called when the status received
            job.setOnStatusListener(new PrintJobListener()
            {

                @Override
                public void statusReceived(PrintJobStatusReceivedEvent event)
                {
                    try
                    {
                        final PrintJobStatus jobStatus = event.getPrintJobStatus();
                        final JobStatusId statusId = jobStatus.getStatusId();
                        final String statusMessage = jobStatus.getStatusMessage();
                        Log.i("job status", statusMessage);

                        switch (statusId)
                        {
                            case ProcessingError:
                                // bad - unrecoverable printing error, do not ask status again
                                break;

                            case Finished:
                                // done - printing is done, do not ask status again
                                break;

                            default:
                                // OK - printing is in progress, ask the status after one second delay
                                scheduler.schedule(getStatus, 1, TimeUnit.SECONDS);
                                break;
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            // schedule first status request in a second
            scheduler.schedule(getStatus, 1, TimeUnit.SECONDS);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    */


    private void checkExploitation() {
        uNetwork.checkLku(oModel.getId(), new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                Log.i("checkExploitation()", "Found LKU");
                oModel.setExploitation(Constants_Intern.EXPLOITATION_REUSE);
                checkDetails();
            }

            @Override
            public void onFailure() {
                uNetwork.checkExploitation(oModel.getId(), new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oModel.setExploitation(i);
                        if (oModel.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
                            startFragmentResult();
                        } else {
                            checkDetails();
                        }
                    }

                    @Override
                    public void onFailure() {
                        startFragmentExploitation();
                    }
                });
            }
        });
    }

    // Fragments
    @Override
    public void startFragmentResult() {
        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putParcelable(Constants_Intern.OBJECT_MODEL, oModel);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_result").commit();

        if (oModel.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            cRecycling++;
        } else {
            cReuse++;
        }
        updateUI();
    }

    @Override
    public void setShape(int shape) {
        oModel.setShape(shape);
        // Check mindest shape in DB
        checkConditionAndShape();
    }

    @Override
    public void setCondition(int condition) {
        oModel.setCondition(condition);
        if (oModel.getCondition() == Constants_Intern.CONDITION_BROKEN) {
            oModel.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        }
        checkConditionAndShape();
    }

    @Override
    public void setModel(int id, int name) {

    }

    private void startFragmentExploitation() {
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oModel.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_exploitation").commit();
    }

    private void startFragmentRequest() {
        Fragment_Request_Name_Model f = new Fragment_Request_Name_Model();
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_name_model").commit();
    }

    private void startFragmentRequest(Fragment f) {
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oModel.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_name").commit();
    }

    private void startFragmentChoice(Bundle bundle) {
        Fragment_Request_Choice f = new Fragment_Request_Choice();
        f.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_request_choice").commit();
    }

    // Interface - Network
    @Override
    public void exploitReuse(int idModel) {
        oModel.setExploitation(Constants_Intern.EXPLOITATION_REUSE);
        uNetwork.exploitReuse(idModel);
    }

    @Override
    public void exploitRecycling(int idModel) {
        oModel.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        uNetwork.exploitRecycling(idModel);
    }

    @Override
    public void checkName(final String name) {
        uNetwork.getIdModel_Name(name, etScan.getText().toString().substring(0, 8), new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setId(i);
                oModel.setName(name);
                checkExploitation();
                updateUI();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(name, etScan.getText().toString().substring(0, 8), new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oModel.setId(i);
                        oModel.setName(name);
                        updateUI();
                        startFragmentExploitation();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
    }

    @Override
    public void callbackManufacturer(int id, String name) {
        oModel.setIdManufacturer(id);
        oModel.setNameManufacturer(name);
        uNetwork.addManufacturerToModel(oModel.getId(), oModel.getIdManufacturer());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDetails();
            }
        }, 500);
    }

    @Override
    public void callbackCharger(int id, String name) {
        oModel.setIdCharger(id);
        oModel.setNameCharger(name);
        uNetwork.connectChargerWithModel(oModel.getId(), oModel.getIdCharger());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDetails();
            }
        }, 500);
    }

    @Override
    public void callbackColor(int id, String name) {
        oModel.setIdColor(id);
        oModel.setNameColor(name);
        uNetwork.getIdModelColor(oModel.getId(), oModel.getIdColor(), new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setIdModelColor(i);
                startFragmentResult();
            }

            @Override
            public void onFailure() {
                uNetwork.addModelColor(oModel.getId(), oModel.getIdColor(), new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oModel.setIdModelColor(i);
                        startFragmentResult();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    @Override
    public void checkBattery(final String name) {
        uNetwork.getIdBattery(oModel.getId(), name, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setIdBattery(i);
                uNetwork.connectBatteryWithModel(oModel.getId(), oModel.getIdBattery());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        checkDetails();
                    }
                }, 500);
            }

            @Override
            public void onFailure() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.add_battery, name));
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uNetwork.addBattery(name, oModel.getIdManufacturer(), new Interface_VolleyCallback_Int() {
                            @Override
                            public void onSuccess(int i) {
                                oModel.setIdBattery(i);
                                uNetwork.connectBatteryWithModel(oModel.getId(), oModel.getIdBattery());
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        checkDetails();
                                    }
                                }, 500);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startFragmentResult();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void checkDetails() {
        uNetwork.getManufacturer(oModel.getId(), new Interface_VolleyCallback_JSON() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    oModel.setIdManufacturer(json.getInt(Constants_Extern.ID_MANUFACTURER));
                    oModel.setNameManufacturer(json.getString(Constants_Extern.NAME_MANUFACTURER));
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateUI();
                uNetwork.getCharger(oModel.getId(), new Interface_VolleyCallback_JSON() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            oModel.setIdCharger(json.getInt(Constants_Extern.ID_CHARGER));
                            oModel.setNameCharger(json.getString(Constants_Extern.NAME_CHARGER));
                            updateUI();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        uNetwork.getBattery(oModel.getId(), new Interface_VolleyCallback_JSON() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                try {
                                    oModel.setIdBattery(json.getInt(Constants_Extern.ID_BATTERY));
                                    oModel.setNameBattery(json.getString(Constants_Extern.NAME_BATTERY));
                                    updateUI();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                checkConditionAndShape();
                            }

                            @Override
                            public void onFailure(JSONObject json) {
                                startFragmentRequest(new Fragment_Request_Name_Battery());
                            }
                        });
                    }

                    @Override
                    public void onFailure(JSONObject json) {
                        uNetwork.getChargers(oModel.getId(), new Interface_VolleyCallback_ArrayList_Choice() {
                            @Override
                            public void onSuccess(ArrayList<Object_Choice> list) {
                                Bundle b = new Bundle();
                                b.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                                startFragmentChoice(b);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(JSONObject json) {
                uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Choice() {
                    @Override
                    public void onSuccess(ArrayList<Object_Choice> list) {
                        Bundle b = new Bundle();
                        b.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                        startFragmentChoice(b);
                    }
                });
            }
        });
    }

    void checkConditionAndShape() {
        if (oModel.getCondition() == Constants_Intern.CONDITION_NOT_SET && oModel.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
            Fragment_Request_Condition f = new Fragment_Request_Condition();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants_Intern.OBJECT_MODEL, oModel);
            f.setArguments(bundle);
            fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_request_condition").commit();
        } else {
            if (oModel.getShape() == Constants_Intern.SHAPE_NOT_SET && oModel.getExploitation() == Constants_Intern.EXPLOITATION_REUSE && false) {
                Fragment_Request_Shape f = new Fragment_Request_Shape();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants_Intern.OBJECT_MODEL, oModel);
                f.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_request_shape").commit();
            } else {
                //requestColor();
                startFragmentResult();
            }
        }
    }

    void requestColor() {
        Log.i("Hey", "Honey");
        uNetwork.getColors(oModel.getId(), new Interface_VolleyCallback_ArrayList_Choice() {
            @Override
            public void onSuccess(ArrayList<Object_Choice> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                startFragmentChoice(bundle);
            }
        });
    }

    @Override
    public void reset() {
        oModel = new Object_Model();
        etScan.setText("");
        etScan.requestFocus();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        updateUI();
    }

    private void totalReset() {
        cReuse = 0;
        cRecycling = 0;
        reset();
    }

    private void updateUI() {
        tvName.setText(oModel.getName());
        tvManufacturer.setText(oModel.getNameManufacturer());
        tvCharger.setText(oModel.getNameCharger());
        tvBattery.setText(oModel.getNameBattery());

        tvCounterRecycling.setText(Integer.toString(cRecycling));
        tvCounterReuse.setText(Integer.toString(cReuse));
        tvCounterTotal.setText(Integer.toString(cRecycling + cReuse));
    }

    @Override
    public Object_Model getModel() {
        return oModel;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                totalReset();
                break;
            case R.id.ivClearScan:
                reset();
        }
    }
}

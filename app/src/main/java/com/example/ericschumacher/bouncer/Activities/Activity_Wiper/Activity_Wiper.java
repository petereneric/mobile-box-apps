package com.example.ericschumacher.bouncer.Activities.Activity_Wiper;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Activities.Menu.Activity_Menu;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Wiper_Procedure;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Activity_Wiper extends Activity_Device {


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_wiper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSettings:
                startActivityForResult(new Intent(this, Activity_Wiper_Procedure.class).putExtra(Constants_Intern.TOKEN_AUTHENTICATION, getTokenAuthentication()), Constants_Intern.REQUEST_CODE_TOKEN_AUTHENTICATION);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

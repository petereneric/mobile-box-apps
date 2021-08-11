package com.example.ericschumacher.bouncer.Activities.Tools

import android.os.Bundle
import android.support.design.widget.TabLayout.GONE
import android.support.v4.app.FragmentManager
import android.util.Log
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager
import com.example.ericschumacher.bouncer.Fragments.Checker.Fragment_Checker
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result
import com.example.ericschumacher.bouncer.R
import com.example.ericschumacher.bouncer.Volley.Volley_Connection
import javax.inject.Inject

class Activity_Checker : Activity_Device(), Fragment_Result.Interface_Fragment_Result {

    // Fragments
    var fChecker: Fragment_Checker? = Fragment_Checker()

    // Connection
    @Inject
    lateinit var cVolley: Volley_Connection

    // Attributes
    var aChecker: Adapter_Pager = Adapter_Pager(supportFragmentManager)

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Layout

    override fun getIdLayout(): Int {
        return super.getIdLayout()
    }

    override fun setLayout() {
        super.setLayout()

        // Toolbar
        supportActionBar?.title = getString(R.string.checker)
    }

    override fun updateLayout() {
        super.updateLayout()

        if (oDevice != null) {
            if (fChecker == null) {
                fChecker = Fragment_Checker()
            } else {
                //fChecker = Fragment_Checker()
            }
            showFragment(fChecker, null, "FRAGMENT_CHECKER", true);
            if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
                supportFragmentManager.beginTransaction()
                    .show(supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER")).commit()
            }
            fChecker?.update()
        } else {
            Log.i("Hide Fragment", "fChecker");
            if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
                supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER")).commit()
            }
        }

        supportFragmentManager.beginTransaction().hide(fModel).commit()
    }

    // Fragments

    override fun initiateFragments() {
        super.initiateFragments()
        fDevice.lMenu.visibility = GONE;
        supportFragmentManager.beginTransaction().hide(fModel).commit()
    }

    override fun removeFragments() {
        super.removeFragments()
        /*
        if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
            fChecker?.removeFragments()
            fManager.beginTransaction().hide(fChecker);
            removeFragment("FRAGMENT_CHECKER")
        }
         */
        //fChecker?.removeFragments();
    }

    override fun reset() {
        /*
        val fm: FragmentManager = getSupportFragmentManager()
        for (i in 0 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }
         */
        //fChecker == null;
        fChecker?.reset()
        super.reset()
    }

    override fun returnResult(cTag: String?) {
        reset()
    }

}


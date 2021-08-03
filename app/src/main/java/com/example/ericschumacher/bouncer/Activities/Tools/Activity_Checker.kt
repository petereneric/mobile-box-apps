package com.example.ericschumacher.bouncer.Activities.Tools

import android.os.Bundle
import android.support.design.widget.TabLayout.GONE
import android.util.Log
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager
import com.example.ericschumacher.bouncer.Constants.Constants_Intern
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
            showFragment(fChecker, null, "FRAGMENT_CHECKER", true);
            fChecker?.update()
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
        if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
            fManager.beginTransaction().hide(fChecker);
            removeFragment("FRAGMENT_CHECKER")
        }
    }

    override fun reset() {
        super.reset()
    }

    override fun returnResult(cTag: String?) {
        reset()
    }

}


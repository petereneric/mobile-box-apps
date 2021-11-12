package com.example.ericschumacher.bouncer.Activities.Tools

import android.os.Bundle
import android.support.design.widget.TabLayout.GONE
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager
import com.example.ericschumacher.bouncer.Fragments.Checker.Fragment_Checker
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Info_Checker
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager
import com.example.ericschumacher.bouncer.R
import com.example.ericschumacher.bouncer.Volley.Volley_Connection
import javax.inject.Inject

class Activity_Checker : Activity_Device(), Fragment_Result.Interface_Fragment_Result, Interface_Manager {

    // Menu
    lateinit var menu: Menu;
    var menuVisibility = false;

    // Fragments
    var fChecker: Fragment_Checker? = Fragment_Checker()
    var fSelected: String? = null

    // Connection
    @Inject
    lateinit var cVolley: Volley_Connection

    // Attributes
    var aChecker: Adapter_Pager = Adapter_Pager(supportFragmentManager)

    // Interfaces
    val iChecker : Interface_Fragment_Checker = fChecker as Interface_Fragment_Checker;

    // Token
    var tAuthentication : String? = null;

    // Log
    private val logTitle = "ACTIVITY_CHECKER"


    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log
        Log.i(logTitle, "onCreate")
    }

    // Layout

    override fun getIdLayout(): Int {
        return R.layout.activity_checker
    }

    override fun setLayout() {
        super.setLayout()

        // Toolbar
        supportActionBar?.title = getString(R.string.checker)
    }

    override fun updateLayout() {
        super.updateLayout()

        if (oDevice != null) {
            if (menuVisibility) {
                supportFragmentManager.beginTransaction().show(fDevice).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(fDevice).commit()
            }

            showFragment(fChecker!!, null, "FRAGMENT_CHECKER", true);
            if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
                supportFragmentManager.beginTransaction().show(supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER")).commit()
            }
            fChecker?.update()
        } else {
            Log.i("Hide Fragment", "fChecker");
            if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
                supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER")).commit()
            }
        }
    }

    // Fragments

    override fun initiateFragments() {
        super.initiateFragments()
        fDevice.lMenu.visibility = GONE;

        // Visibility

        // Visibility
        fModel.showAll(false)
        fDevice.showAll(false);
    }

    override fun showFragment(fragment: Fragment, bData: Bundle?, cTag: String?, bKeyboard: Boolean?) {
        setKeyboard(bKeyboard)
        fragment.arguments = bData
        val f = supportFragmentManager.findFragmentByTag(cTag)
        if (f == null) {
            fManager.beginTransaction().add(R.id.flInteraction, fragment, cTag).commit()
        }
        if (!cTag.equals("FRAGMENT_CHECKER")) {
            if (supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER") != null) {
                removePossibleFragments()
                supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag("FRAGMENT_CHECKER")).commit()
                fSelected = cTag
            }
        }
    }

    override fun reset() {
        fChecker?.reset()
        super.reset()
        fDevice.showAll(false);
        menuVisibility = false
        updateLayoutMenu()
    }

    override fun returnResult(cTag: String?) {
        reset()
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_activity_checker, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.mVisibility -> {
                menuVisibility = !menuVisibility;
                updateLayoutMenu();
                true
            }
            R.id.mInfo -> {
                val fDialogInfo = Fragment_Dialog_Info_Checker()
                fDialogInfo.show(supportFragmentManager, "FRAGMENT_DIALOG_INFO_CHECKER");
                updateLayoutMenu();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateLayoutMenu() {
        if (menuVisibility) {
            if (this::menu.isInitialized) menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_visibility))
            supportFragmentManager.beginTransaction().show(fDevice).commit()
        } else {
            if (this::menu.isInitialized) menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_visibility_off))
            supportFragmentManager.beginTransaction().hide(fDevice).commit()
        }
    }

    fun removePossibleFragments() {
        if (fSelected != null) {
            if (supportFragmentManager.findFragmentByTag(fSelected) != null) {
                supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentByTag(fSelected)).commit()
            }
        }
    }

    override fun afterTextChanged(editable: Editable) {
        super.afterTextChanged(editable)
        if (editable.toString() != "") {
            fChecker?.reset()
            fModel.showAll(false)
            fDevice.showAll(false)
            menuVisibility = false
            updateLayoutMenu()
        }
    }
}


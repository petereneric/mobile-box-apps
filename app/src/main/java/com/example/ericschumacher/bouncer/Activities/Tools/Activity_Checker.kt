package com.example.ericschumacher.bouncer.Activities.Tools

import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.OnTabSelectedListener
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Checks
import com.example.ericschumacher.bouncer.R
import kotlinx.android.synthetic.main.activity_checker.*

class Activity_Checker : Activity_Device() {

    // Attributes
    var aChecker: Adapter_Pager = Adapter_Pager(supportFragmentManager)


    // Layout

    override fun getIdLayout(): Int {
        return R.layout.activity_checker
    }

    override fun setLayout() {
        super.setLayout()

        // Toolbar
        supportActionBar?.title = getString(R.string.checker)

        // Tablayout
        vTabLayout.setupWithViewPager(ViewPager)
        vTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                ViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        vTabLayout.addTab(vTabLayout.newTab().setText(getString(R.string.diagnose)))
        vTabLayout.addTab(vTabLayout.newTab().setText(getString(R.string.edit_checks)))
        vTabLayout.addTab(vTabLayout.newTab().setText(getString(R.string.handling)))

        // Adapter

        // Set Adapter
        aChecker.add(getString(R.string.diagnose), Fragment_Edit_Model_Checks())
        aChecker.add(getString(R.string.edit_checks), Fragment_Edit_Model_Checks())
        aChecker.add(getString(R.string.handling), Fragment_Edit_Model_Checks())
        ViewPager.adapter = aChecker

    }

    override fun updateLayout() {
        super.updateLayout()
        supportFragmentManager.beginTransaction().hide(fDevice).commit()
    }
}


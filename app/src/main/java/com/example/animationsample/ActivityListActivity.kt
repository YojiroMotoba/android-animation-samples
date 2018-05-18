package com.example.animationsample

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_list_activity.*


class ActivityListActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_activity)
        val packageInfo: PackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        val activityNames = mutableListOf<String>()
        packageInfo.activities.forEach { activityInfo ->
            activityNames.add(activityInfo.name)
        }
        list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, activityNames)
        list.setOnItemClickListener { _, _, position, _ ->
            startActivity(Intent().also {
                it.component = ComponentName(packageName, packageInfo.activities[position].name)
            })
        }
    }
}
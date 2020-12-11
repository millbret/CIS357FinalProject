package edu.gvsu.cis.conversioncalculator

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import androidx.multidex.MultiDexApplication

class ConversionApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}

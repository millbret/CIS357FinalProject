package edu.gvsu.cis.conversioncalculator

import android.app.Application
import androidx.multidex.MultiDexApplication
import net.danlew.android.joda.JodaTimeAndroid

class ConversionApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}

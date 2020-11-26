package edu.gvsu.cis.conversioncalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorDataViewModel : ViewModel() {

    data class UnitSettings(val mode: String, val toUnits: String, val fromUnits:String)

    private var _settings = MutableLiveData<UnitSettings>()

    // Declare properties with getters
    val settings get() = _settings
}
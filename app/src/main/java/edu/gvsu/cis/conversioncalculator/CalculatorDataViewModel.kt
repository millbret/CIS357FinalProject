package edu.gvsu.cis.conversioncalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.gvsu.cis.conversioncalculator.dummy.HistoryContent

class CalculatorDataViewModel : ViewModel() {

    data class UnitSettings(val mode: String, val toUnits: String, val fromUnits:String)

    private var _settings = MutableLiveData<UnitSettings>()
    private var _selected = MutableLiveData<HistoryContent.HistoryItem>()

    // Declare properties with getters
    val settings get() = _settings
    val selected get() = _selected
}
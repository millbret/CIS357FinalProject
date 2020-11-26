package edu.gvsu.cis.conversioncalculator.dummy

import org.joda.time.DateTime
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object HistoryContent {

    val ITEMS: MutableList<HistoryItem> = ArrayList()

    fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }

    data class HistoryItem(val fromVal: Double, val toVal: Double,
                           val mode: String, val fromUnits: String, val toUnits: String,
                           val timestamp: DateTime
    )
    {
        override fun toString(): String {
            return "$fromVal $fromUnits = $toVal $toUnits"
        }
    }
}

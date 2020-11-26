package edu.gvsu.cis.conversioncalculator

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import edu.gvsu.cis.conversioncalculator.dummy.HistoryContent

import edu.gvsu.cis.conversioncalculator.dummy.HistoryContent.HistoryItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<HistoryItem>,
    val listener: ((HistoryItem)  -> Unit)? = null)
 : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(values[position], listener)
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView)
    {
        val mP1: TextView
        val mDateTime: TextView
        var mItem: HistoryItem? = null
        var mImage: ImageView
        val parentView:View


        override fun toString(): String {
            return super.toString() + " '" + mDateTime.text + "'"
        }

        init {
            mP1 = mView.findViewById<View>(R.id.p1) as TextView
            mDateTime = mView.findViewById<View>(R.id.timestamp) as TextView
            mImage = mView.findViewById<ImageView>(R.id.imageView)
            parentView = mView
        }

        public fun bindTo(d: HistoryItem, listener: ((HistoryItem) -> Unit)?) {
            mItem = d
            mP1.text = d.toString()
            mDateTime.text = d.timestamp.toString()
            if (listener != null) {
                parentView.setOnClickListener {
                    listener(d)
                }
            }
            if (d.mode == "Length") {
                mImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        mImage.resources, R.drawable.length_icon, null))
            } else {
                mImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        mImage.resources, R.drawable.volume_icon, null))
            }

        }
    }

}
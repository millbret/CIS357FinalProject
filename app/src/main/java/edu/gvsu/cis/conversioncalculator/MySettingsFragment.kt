package edu.gvsu.cis.conversioncalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_my_settings.*
import kotlinx.android.synthetic.main.content_my_settings.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [MySettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MySettingsFragment : Fragment() {

    lateinit var viewModel: CalculatorDataViewModel

    private var fromUnits = "Yards"
    private var toUnits = "Meters"
    private var mode = "Length"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_settings, container, false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CalculatorDataViewModel::class.java)
        viewModel.settings.observe(this.viewLifecycleOwner, Observer { z ->
            mode = z.mode
            toUnits = z.toUnits
            fromUnits = z.fromUnits
            setupSpinners()
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener(View.OnClickListener {
            viewModel.settings.value = CalculatorDataViewModel.UnitSettings(mode, toUnits, fromUnits)
            findNavController().navigate(R.id.action_setting2main)
        })


    }

    fun setupSpinners() {
        val vals = ArrayList<String>()
        //val selection = 0
        if (mode.intern() === "Length") {
            for (unit in UnitsConverter.LengthUnits.values()) {
                vals.add(unit.toString())
            }

        } else {
            for (unit in UnitsConverter.VolumeUnits.values()) {
                vals.add(unit.toString())
            }
        }
        //val unitAdapter = ArrayAdapter(this.activity?.applicationContext?, android.R.layout.simple_spinner_dropdown_item, vals)
        val unitAdapter = this.activity?.applicationContext?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, vals) }

        from_spinner.adapter = unitAdapter

        from_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                fromUnits = adapterView.getItemAtPosition(i) as String
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        to_spinner.adapter = unitAdapter

        if (mode.intern() === "Length") {

        }

        to_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                toUnits = adapterView.getItemAtPosition(i) as String
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        // set initial val of spinners.
        for (i in vals.indices) {
            if (fromUnits.intern() === vals[i]) {
                from_spinner.setSelection(i)
            }
            if (toUnits.intern() === vals[i]) {
                to_spinner.setSelection(i)
            }
        }
    }
}
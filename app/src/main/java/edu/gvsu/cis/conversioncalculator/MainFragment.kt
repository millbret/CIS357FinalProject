package edu.gvsu.cis.conversioncalculator

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    lateinit var viewModel: CalculatorDataViewModel

    private enum class Mode {
        Length, Volume
    }

    private var mode: Mode = Mode.Length

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CalculatorDataViewModel::class.java)
        viewModel.settings.observe(this.viewLifecycleOwner, Observer { z ->
            this.from_units.text = z.fromUnits
            this.to_units.text = z.toUnits
            this.mode = Mode.valueOf(z.mode)
            calculator_title.text = "$mode Converter"
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set initial units.
        from_units.text = UnitsConverter.LengthUnits.Meters.toString()
        to_units.text = UnitsConverter.LengthUnits.Yards.toString()

        // convert when calc button tapped
        button_calc.setOnClickListener {
            doConversion()
        }

        // clear when clear button tapped
        button_clear.setOnClickListener {
            to_field.setText("")
            from_field.setText("")
            hideKeyboard()
        }

        // toggle conversion mode when mode button tapped.
        button_mode.setOnClickListener {
            to_field.setText("")
            from_field.setText("")
            hideKeyboard()
            when (mode) {
                Mode.Length -> {
                    mode = Mode.Volume
                    from_units.text = UnitsConverter.VolumeUnits.Gallons.toString()
                    to_units.text = UnitsConverter.VolumeUnits.Liters.toString()
                }
                Mode.Volume -> {
                    mode = Mode.Length
                    from_units.text = UnitsConverter.LengthUnits.Yards.toString()
                    to_units.text = UnitsConverter.LengthUnits.Meters.toString()
                }
            }
            calculator_title.text = "$mode Converter"
        }

        to_field.onFocusChangeListener =
            View.OnFocusChangeListener { view, b -> from_field.text.clear() }

        from_field.onFocusChangeListener =
            View.OnFocusChangeListener { view, b ->
                to_field.text.clear()
            }
    }

    private fun doConversion() {
        var dest: EditText? = null
        var `val` = ""
        val fromVal: String = from_field.getText().toString()
        if (fromVal.intern() !== "") {
            `val` = fromVal
            dest = to_field
        }
        val toVal: String = to_field.getText().toString()
        if (toVal.intern() !== "") {
            `val` = toVal
            dest = from_field
        }
        if (dest != null) {
            when (mode) {
                MainFragment.Mode.Length -> {
                    val tUnits: UnitsConverter.LengthUnits
                    val fUnits: UnitsConverter.LengthUnits
                    if (dest === to_field) {
                        fUnits = UnitsConverter.LengthUnits.valueOf(from_units.getText().toString())
                        tUnits = UnitsConverter.LengthUnits.valueOf(to_units.getText().toString())
                    } else {
                        fUnits = UnitsConverter.LengthUnits.valueOf(to_units.getText().toString())
                        tUnits = UnitsConverter.LengthUnits.valueOf(from_units.getText().toString())
                    }
                    val dVal = `val`.toDouble()
                    val cVal: Double = UnitsConverter.convert(dVal, fUnits, tUnits)
                    dest.setText(java.lang.Double.toString(cVal))
                }
                MainFragment.Mode.Volume -> {
                    val vtUnits: UnitsConverter.VolumeUnits
                    val vfUnits: UnitsConverter.VolumeUnits
                    if (dest === to_field) {
                        vfUnits = UnitsConverter.VolumeUnits.valueOf(from_units.getText().toString())
                        vtUnits = UnitsConverter.VolumeUnits.valueOf(to_units.getText().toString())
                    } else {
                        vfUnits = UnitsConverter.VolumeUnits.valueOf(to_units.getText().toString())
                        vtUnits = UnitsConverter.VolumeUnits.valueOf(from_units.getText().toString())
                    }
                    val vdVal = `val`.toDouble()
                    val vcVal: Double = UnitsConverter.convert(vdVal, vfUnits, vtUnits)
                    dest.setText(java.lang.Double.toString(vcVal))
                }
            }
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        // Check if no view has focus:
        val view = this.activity?.currentFocus
        if (view != null) {
            val imm = this.activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            viewModel.settings.value = CalculatorDataViewModel.UnitSettings(mode.toString(), to_units.text.toString(), from_units.text.toString())
            findNavController().navigate(R.id.action_main2settings)
            return true
        }
        return false
    }
}
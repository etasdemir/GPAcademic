package com.elacqua.gpacademic.ui.dreamgpa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elacqua.gpacademic.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dream_gpa.*

private const val TAG = "DreamGpaFragment"

class DreamGpaFragment : Fragment() {

    private val dreamGpaViewModel: DreamGpaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_dream_gpa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDreamGpaCalc.setOnClickListener {
            val totalCredit = txtTotalCredit.text.toString()
            val termCredit = txtTermCredit.text.toString()
            val currentGpa = txtCurrentGpa.text.toString()
            val dreamGpa = txtDreamGpa.text.toString()

            val requiredGpa = dreamGpaViewModel.calculateDreamGpa(totalCredit, termCredit, currentGpa, dreamGpa)
            if (requiredGpa>4.01 || requiredGpa<0.00){
                showInputError()
            } else {
                val calculatedValue = String.format("%.2f", requiredGpa)
                btnDreamGpaCalc.text = "GPA: $calculatedValue"
            }
        }
    }

    private fun showInputError() {
        Snackbar.make(linearLayoutDreamGpa, R.string.dream_gpa_wrong_input, Snackbar.LENGTH_SHORT).show()
    }
}
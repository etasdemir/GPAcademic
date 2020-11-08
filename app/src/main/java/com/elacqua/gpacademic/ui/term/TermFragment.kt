package com.elacqua.gpacademic.ui.term

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.data.db.entities.Term
import com.elacqua.gpacademic.ui.GpaTypeActivity
import com.elacqua.gpacademic.util.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_term.*

@AndroidEntryPoint
class TermFragment : Fragment(){

    private lateinit var recyclerViewAdapter: RecyclerViewTermAdapter
    private val termViewModel: TermViewModel by navGraphViewModels(R.id.mobile_navigation){
        defaultViewModelProviderFactory
    }
    private var gpaType: Int ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGpaType()
        initRecyclerView()
        initSwipeToDelete()

        termViewModel.termsLiveData.observe(viewLifecycleOwner, { termList ->
            recyclerViewAdapter.submitList(termList)
        })

        btnCalculateCgpa.setOnClickListener {
            calculateGpaAndUpdateButton()
        }

        btnSettings.setOnClickListener{
            navigateToGpaTypeActivity()
        }
    }

    private fun getGpaType() {
        val appPreferences = AppPreferences(requireContext())
        gpaType = appPreferences.gpaType
    }

    private fun initRecyclerView() {
        recyclerViewAdapter =
            RecyclerViewTermAdapter(object: OnItemClickListener{
                override fun onClick(term: Term) {
                    val bundle = bundleOf("getTerm" to term)
                    findNavController().navigate(R.id.navigation_home, bundle)
                }
            })
        recyclerViewTerm.run {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val term = recyclerViewAdapter.getItemAt(position)
                termViewModel.deleteTerm(term)
            }
        }).attachToRecyclerView(recyclerViewTerm)
    }

    private fun calculateGpaAndUpdateButton() {
        val termHashedMap = recyclerViewAdapter.getTermHashedMap()
        val termList = recyclerViewAdapter.currentList
        val cgpa = termViewModel.calculateCumulativeGpa(gpaType!!, termHashedMap, termList)
        btnCalculateCgpa.text = cgpa
    }

    private fun navigateToGpaTypeActivity() {
        val intent = Intent(requireContext(), GpaTypeActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        recyclerViewTerm.adapter = null
        super.onDestroyView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_term, container, false)
    }
}
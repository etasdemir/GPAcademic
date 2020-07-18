package com.elacqua.gpacademic.ui.terms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.gpacademic.GpaTypeActivity
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.data.Term
import com.elacqua.gpacademic.utility.AppPreferences
import kotlinx.android.synthetic.main.fragment_term.*

private const val TAG = "TermFragment"

class TermFragment : Fragment(), RecyclerViewTermAdapter.OnItemClickListener {

    private lateinit var termsLiveData: LiveData<List<Term>>
    private lateinit var recyclerViewAdapter: RecyclerViewTermAdapter
    private val termViewModel: TermViewModel by viewModels()
    private var type: Int ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        termsLiveData = termViewModel.getTermLiveData()
        getGpaType()
        return inflater.inflate(R.layout.fragment_term, container, false)
    }

    private fun getGpaType() {
        val appPreferences = AppPreferences(requireContext())
        type = appPreferences.gpaType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initSwipeToDelete()

        termsLiveData.observe(viewLifecycleOwner, Observer {termList ->
            recyclerViewAdapter.submitList(termList)
        })

        btnCalculateCgpa.setOnClickListener {
            calculateGpaAndUpdateButton()
        }

        btnSettings.setOnClickListener{
            navigateToGpaTypeActivity()
        }
    }

    override fun onClick(term: Term) {
        Log.e(TAG, "onClick: $term")
        val bundle = bundleOf("getTerm" to term)
        findNavController().navigate(R.id.navigation_home, bundle)
    }

    private fun initRecyclerView() {
        recyclerViewAdapter =
            RecyclerViewTermAdapter(this)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewTerm.run {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            this.layoutManager = layoutManager
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
        val recyclerViewList = recyclerViewAdapter.currentList
        val cgpa = termViewModel.calculateCgpa(type!!, termHashedMap, recyclerViewList)
        btnCalculateCgpa.text = cgpa
    }

    private fun navigateToGpaTypeActivity() {
        val intent = Intent(requireContext(), GpaTypeActivity::class.java)
        startActivity(intent)
    }
}
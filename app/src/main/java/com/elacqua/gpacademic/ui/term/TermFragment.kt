package com.elacqua.gpacademic.ui.term

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.data.db.entities.Term
import com.elacqua.gpacademic.ui.GpaTypeActivity
import com.elacqua.gpacademic.util.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_term.*
import timber.log.Timber

@AndroidEntryPoint
class TermFragment : Fragment(), RecyclerViewTermAdapter.OnItemClickListener {

    private lateinit var recyclerViewAdapter: RecyclerViewTermAdapter
    private val termViewModel by activityViewModels<TermViewModel>()
    private var type: Int ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

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

        termViewModel.termsLiveData.observe(viewLifecycleOwner, Observer {termList ->
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
        Timber.d( "onClick: $term")
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
        val termList = recyclerViewAdapter.currentList
        val cgpa = termViewModel.calculateCgpa(type!!, termHashedMap, termList)
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
}
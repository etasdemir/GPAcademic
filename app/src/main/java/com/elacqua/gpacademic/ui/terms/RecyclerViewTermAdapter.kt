package com.elacqua.gpacademic.ui.terms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.data.Term
import kotlinx.android.synthetic.main.item_term.view.*

class RecyclerViewTermAdapter(val listener: OnItemClickListener):
    ListAdapter<Term, RecyclerViewTermAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    private val termCheckedMap = HashMap<Term, Boolean>()
    private lateinit var clickListener: OnItemClickListener

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Term>(){
            override fun areItemsTheSame(oldItem: Term, newItem: Term): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Term, newItem: Term): Boolean {
                return oldItem.termName == newItem.termName && oldItem.lessons == newItem.lessons
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_term, parent, false)
        clickListener = listener
        return ViewHolder(view, CustomOnCheckedListener())
    }

    /**
     * Used if/else because when an element deleted can not access last element, indexOutOfBound
     * because position returns size
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTermName.text = currentList[position].termName
        holder.customOnCheckedListener.updatePosition(position)
        holder.txtTermName.setOnClickListener {
            if (position >= currentList.size){
                clickListener.onClick(currentList.last())
            } else {
                clickListener.onClick(getItem(position))
            }
        }
    }

    fun getItemAt(position: Int): Term{
        return getItem(position)
    }

    fun getTermHashedMap(): HashMap<Term, Boolean> {
        return termCheckedMap
    }

    inner class ViewHolder(
        view: View,
        val customOnCheckedListener: CustomOnCheckedListener
    ): RecyclerView.ViewHolder(view){
        val txtTermName: TextView = view.txtTermNameRecycler
        private val checkBoxTerm: CheckBox = view.checkBoxTerm

        init {
            checkBoxTerm.setOnCheckedChangeListener(customOnCheckedListener)
        }
    }

    inner class CustomOnCheckedListener: CompoundButton.OnCheckedChangeListener{
        private var position = -1

        fun updatePosition(position: Int){
            this.position = position
        }

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (position == -1 || position < currentList.size){
                val currentTerm = currentList[position]
                termCheckedMap[currentTerm] = isChecked
            }
        }
    }

    interface OnItemClickListener{
        fun onClick(term: Term)
    }
}
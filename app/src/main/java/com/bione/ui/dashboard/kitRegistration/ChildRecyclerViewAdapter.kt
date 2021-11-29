package com.bione.ui.dashboard.kitRegistration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.bione.R
import com.bione.model.questionnaire.Datum
import com.bione.utils.Log

class ChildRecyclerViewAdapter(context: Context, list: ArrayList<Datum>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1 // text
        const val VIEW_TYPE_TWO = 2 // radio
        const val VIEW_TYPE_THREE = 3 // select
    }

    private val context: Context = context
    var list: ArrayList<Datum> = list

    private inner class View1ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.textView)
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            if (recyclerViewModel.fields == null) {
                message.text = recyclerViewModel.type
            } else {
                message.text = "fields: " + recyclerViewModel.fields[0].type
            }

        }
    }


    private inner class View2ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        //        var message: TextView = itemView.findViewById(R.id.textView)
        fun bind(position: Int) {
            Log.d("View2ViewHolder", "----bind----")
            Log.d("list size", "--------" + list.size)
            val recyclerViewModel = list[position]

            if(recyclerViewModel.options!=null){
                setRadioButton(itemView, recyclerViewModel.options.size)
            }
            else if(recyclerViewModel.fields!= null){
                setRadioButton(itemView, recyclerViewModel.fields[0].options.size)
            }

//            if (recyclerViewModel.fields == null) {
////                message.text = recyclerViewModel.type
//                Log.d("View2ViewHolder", "----fields null----")
//            } else {
//                Log.d("View2ViewHolder", "----not null----")
////                message.text = "fields: " + recyclerViewModel.fields[0].type
//                if (recyclerViewModel.fields[0].options != null) {
//                    Log.d("options", "----not null----")
//                    setRadioButton(itemView, recyclerViewModel.fields[0].options.size)
//                } else {
//                    Log.d("option", "---- null----")
////                    setRadioButton(itemView, list[position].options.size)
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return View1ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_view_1, parent, false)
            )
        }
        Log.d("View2ViewHolder", "------")
        return View2ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_view_radio, parent, false)
        )
    }

    override fun getItemCount(): Int {
        Log.d("getItemCount", "------")
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (list[position].fields == null) {
//            (holder as View1ViewHolder).bind(position)
//        } else {
//            (holder as View2ViewHolder).bind(position)
//        }
        if (list[position].type != null) {
            if (list[position].type == "radio") {
                (holder as View2ViewHolder).bind(position)
            }
        } else if (list[position].fields != null) {
            if (list[position].fields[0].type == "radio") {
                (holder as View2ViewHolder).bind(position)
            }
        } else {
            (holder as View1ViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("getItemViewType", "--------")
//        if (list[position].fields == null) {
//            return VIEW_TYPE_ONE
//        } else {
//            return VIEW_TYPE_TWO
//        }
        if (list[position].type != null) {
            if (list[position].type == "radio") {
                return VIEW_TYPE_TWO
            }
        }
        if (list[position].fields != null) {
            if (list[position].fields[0].type == "radio") {
                return VIEW_TYPE_TWO
            }
        }

        return VIEW_TYPE_ONE
    }

    private fun setRadioButton(view: View, buttons: Int) {
        Log.d("setradio", "------")
        //Defining 5 buttons.
        val rb = arrayOfNulls<AppCompatRadioButton>(buttons)
        val rgp = view.findViewById<View>(R.id.radio_group) as RadioGroup
        rgp.orientation = LinearLayout.HORIZONTAL
        for (i in 1..buttons) {
            val rbn = RadioButton(context)
            rbn.id = View.generateViewId()
            rbn.text = "Radio$i"
            val params = LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            rbn.layoutParams = params
            rgp.addView(rbn)
        }
        Log.d("radio selection ", "----" + rgp.checkedRadioButtonId)
        rgp.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<View>(checkedId) as RadioButton
            Toast.makeText(context, radioButton.text, Toast.LENGTH_LONG).show()
        }
    }
}
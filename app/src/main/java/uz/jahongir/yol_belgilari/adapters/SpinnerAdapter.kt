package uz.jahongir.yol_belgilari.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import uz.jahongir.yol_belgilari.R

class SpinnerAdapter(var list: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var itemView: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item,parent,false)

        itemView.findViewById<TextView>(R.id.txt_view_for_spinner).text = list[position]

        return itemView
    }
}
package com.example.symbolkt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.symbolkt.R
import com.example.symbolkt.model.StockResult

class StockListAdapter: RecyclerView.Adapter<StockListAdapter.StockViewHolder>() {

     var data = listOf<StockResult>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.stock_row_item, parent, false)

        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.description
        holder.type.text = item.type
        holder.symbol.text = item.symbol
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class StockViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_stock_description)
        val type: TextView = view.findViewById(R.id.tv_stock_type)
        val symbol: TextView = view.findViewById(R.id.tv_stock_symbol)
    }
}


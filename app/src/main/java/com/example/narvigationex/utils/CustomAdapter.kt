package com.example.narvigationex.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.narvigationex.R
import com.example.narvigationex.model.NFT
import kotlin.collections.List;

class CustomAdapter<T: NFT>(context: Context, @LayoutRes var resource: Int, var data: List<T>):ArrayAdapter<T>(context, resource , data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var item = data.get(position)
        var itemView = (context as Activity).layoutInflater.inflate(resource, parent, false)
        itemView.findViewById<TextView>(R.id.item_nome).text = item.nome;
        itemView.findViewById<TextView>(R.id.item_valor).text = "${ item.valor }"
        itemView.findViewById<TextView>(R.id.item_id).text = item.id;
        itemView.findViewById<TextView>(R.id.item_descricao).text = item.descricao;

        return itemView
    }

}
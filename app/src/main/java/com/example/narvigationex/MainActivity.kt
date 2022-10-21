package com.example.narvigationex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import com.example.narvigationex.model.NFT
import com.example.narvigationex.utils.CustomAdapter

class MainActivity : AppCompatActivity() {

    companion object{
        var ctrId:Int = 0
    }

    var selected:Int = -1
    lateinit var nfts: ArrayList<NFT>
    lateinit var customAdapter: CustomAdapter<NFT>
    lateinit var itemsView: ListView
    private var receiver:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result -> onResult(result)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nfts = ArrayList<NFT>()
        customAdapter = CustomAdapter<NFT>(this,R.layout.list_item,nfts)
        itemsView = findViewById<ListView>(R.id.nfts_view)
        itemsView.adapter = customAdapter
        itemsView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            selected = if (selected == i) -1 else i
            if (selected < 0){
                view.isSelected = false
                findViewById<Button>(R.id.btn_edite).text = "Editar"
            }else{
                view.isSelected = true
                itemsView.setSelection(i)
                var id = nfts.get(selected).id
                findViewById<Button>(R.id.btn_edite).text = "Editar #${id}"
            }
        }
    }

    fun onNewItemClick(view:View){
        var intent:Intent = Intent(this, FormActivity::class.java)
        intent.putExtra("id", ctrId.toString())
        receiver.launch(intent)
    }

    fun onEdtItemClick(view: View){
        if (selected < 0) return
        var item:NFT =  nfts.get(selected)
        var intent:Intent = Intent(this, FormActivity::class.java)
        intent.putExtra("index",selected)
        intent.putExtra("id", item.id)
        intent.putExtra("nome",item.nome)
        intent.putExtra("descricao",item.descricao)
        intent.putExtra("valor", item.valor)
        receiver.launch(intent)
    }

    private fun onResult(result:ActivityResult) {
        if (result.resultCode == FormActivity.CANCEL_CODE) return

        var index:Int = result.data?.getIntExtra("index", -1)!!
        var id: String? = result.data?.getStringExtra("id")
        var nome:String? = result.data?.getStringExtra("nome")
        var descricao:String? = result.data?.getStringExtra("descricao")
        var valor: Double? = result.data?.getDoubleExtra("valor",0.0)

        if (index < 0){
            var NFT:NFT =  NFT( id,nome, descricao, valor)
            nfts.add(NFT)
            ctrId++
        }else{
            var toUpdateItem = nfts.get(index)
            toUpdateItem.id = id
            toUpdateItem.descricao = descricao
            toUpdateItem.nome = nome
            toUpdateItem.valor = valor
        }
        customAdapter.notifyDataSetChanged()
    }
}
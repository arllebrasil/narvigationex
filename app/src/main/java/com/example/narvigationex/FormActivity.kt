package com.example.narvigationex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FormActivity : AppCompatActivity() {

    companion object{
        val SUCCESS_CODE:Int = 1
        val CANCEL_CODE:Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)



        var id: String? = intent.getStringExtra("id")
        var nome:String? = intent.getStringExtra("nome")
        var descricao:String? = intent.getStringExtra("descricao")
        var valor: Double? = intent.getDoubleExtra("valor",0.0)
        findViewById<TextView>(R.id.nftid).setText(id)

        var index:Int = intent.getIntExtra("index",-1)
        if (index < 0) return

        findViewById<EditText>(R.id.edt_name).setText(nome)
        findViewById<EditText>(R.id.edt_detalies).setText(descricao)
        findViewById<EditText>(R.id.edt_value).setText(valor.toString())
        setResult(CANCEL_CODE)
    }

    fun onCancelClick(view:View){
        setResult(CANCEL_CODE)
        finish()
    }

    fun onSavaClick(view:View){
        var resultIntent = Intent()
        var id = findViewById<TextView>(R.id.nftid).text.toString()
        var nome = findViewById<EditText>(R.id.edt_name).text.toString()
        var descricao = findViewById<EditText>(R.id.edt_detalies).text.toString()
        var valor = findViewById<EditText>(R.id.edt_value).text.toString()

        resultIntent.putExtra("index",intent.getIntExtra("index",-1))
        resultIntent.putExtra("id", id)
        resultIntent.putExtra("nome",nome)
        resultIntent.putExtra("descricao",descricao)
        resultIntent.putExtra("valor", (if (valor.isEmpty()) 0.0 else valor.toDouble()))
        setResult(SUCCESS_CODE,resultIntent)

        finish()
    }
}
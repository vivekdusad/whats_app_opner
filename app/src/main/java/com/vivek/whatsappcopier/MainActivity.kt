package com.vivek.whatsappcopier

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val number: String = if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            intent?.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        } else if (intent.action == Intent.ACTION_DIAL ||
            intent.action == Intent.ACTION_VIEW
        ) {
            intent?.data?.schemeSpecificPart.toString()
        }else{
            "a"
        }

        if(number.isDigitsOnly()){
            startWhatsapp(number)
        }else{
            Toast.makeText(this,"Please check your number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startWhatsapp(number: String) {
        Log.d("Number", "startWhatsapp:"+number.toString())
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        val data :String = if(number[0] == '+'){
            number.substring(1);
        }else if(number.length == 10){
            "91"+number;
        }else{
            number;
        }
        intent.data = Uri.parse("https://wa.me/$data");
        if(packageManager.resolveActivity(intent,0) != null){
            startActivity(intent);
        }else{
            Toast.makeText( this,"Please Intall WhatsApp", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
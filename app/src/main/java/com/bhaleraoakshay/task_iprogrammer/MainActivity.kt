package com.bhaleraoakshay.task_iprogrammer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val autotextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_serachPlace)

        val arrayListPlaces = ArrayList<String>()
        arrayListPlaces.add("Pune")
        arrayListPlaces.add("Mumbai")
        arrayListPlaces.add("Delhi")
        arrayListPlaces.add("Chennai")
        arrayListPlaces.add("Solapur")
        autotextView.setThreshold(1);


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListPlaces)
        autotextView.setAdapter(adapter)



        autotextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", s.toString())
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("onTextChanged", s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", s.toString())
            }
        })
        findViewById<Button>(R.id.button_actionSearch)?.setOnClickListener {
            val enteredText = "Selected " + autotextView.getText().toString()

            Toast.makeText(this, enteredText, Toast.LENGTH_SHORT).show()
        }

    }
}
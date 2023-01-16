package com.biniyam.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    val API_URL = "https://jsonplaceholder.typicode.com/todos/"
    lateinit var textView: TextView
    lateinit var inputField:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView= findViewById(R.id.textView)
        inputField= findViewById(R.id.et_fetchId)
        val button:Button = findViewById(R.id.btn_fetch)
        button.setOnClickListener {
            val s = API_URL + inputField.text.toString()

            fetchDataFromAPI(s)

        }
    }
    fun fetchDataFromAPI(s:String){

        val result = GlobalScope.launch(Dispatchers.Main) {

            // this result is dependant upon getData function therefore it needs to suspend it
            val result = getData(s)
            // five seconds delay like we did with timer sleep() one second per frame like answer time in a quize app
            delay(5000)
           textView.text = result as String
        }

    }
    // suspend is retlated to the context coroutine holds threats while they switch
   suspend fun getData(s: String):String{
        return withContext(Dispatchers.IO){
            val data = URL(s).readText()
            return@withContext data
        }
    }
}
package com.example.everysearch1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.activity_findschool.*
import org.json.JSONObject

class findschoolActivity : AppCompatActivity() {
companion object{
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findschool)

        val schlNmArray: ArrayList<String> = ArrayList()

        val assetManager=resources.assets
        val inputStream=assetManager.open("SchlNm.json")
        val jsonString=inputStream.bufferedReader().use{
            it.readText()
        }
        val jObject= JSONObject(jsonString)
        val jArray=jObject.getJSONArray("2018")

        for(i in 0 until  jArray.length()){
            val name:String=jArray.getJSONObject(i).getString("학교명")
            schlNmArray.add(name)
        }

        var schlKrnNm : String =autoComplete.text.toString()
        val textView: AutoCompleteTextView = findViewById(R.id.autoComplete);
        val adapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_dropdown_item_1line, schlNmArray as List<Any?>
        )
        textView.setAdapter(adapter)//autocreatetextview설정

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent: AdapterView<*>,
                                                                             view: View?,
                                                                             position: Int,
                                                                             id: Long ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val nextIntent = Intent(this, mainsearch()::class.java)
            //nextIntent.putExtra("schoolName", autoComplete.text.toString())
            saveAutoCompleteText(autoComplete.text.toString())
            startActivity(nextIntent)
        }

        button2.setOnClickListener(){
            finish()
        }
        /*button10.setOnClickListener{
            val nextIntent = Intent(this, mainsearch()::class.java)
            startActivity(nextIntent)
        }*/
    }
    fun Context.saveAutoCompleteText(text: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("schoolName", text)
        editor.apply()
    }
}


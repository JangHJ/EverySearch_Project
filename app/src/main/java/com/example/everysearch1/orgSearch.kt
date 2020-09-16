package com.example.everysearch1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mainsearch.*
import kotlinx.android.synthetic.main.activity_org_search.*
import kotlinx.android.synthetic.main.activity_org_search.button
import kotlinx.android.synthetic.main.activity_org_search.button5
import org.json.JSONObject

class orgSearch : AppCompatActivity() {
    companion object{
        var keyword:String="검색키워드"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_org_search)

        val searchIntent = Intent(this, searchResult()::class.java)
        var schlList=ArrayList<String>()
        schlList.add("서울여자대학교")
        var departmentList = ArrayList<String>()
        var teamList = ArrayList<String>()

        val assetManager = resources.assets
        val inputStream = assetManager.open("Entire.json")
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }

        val inputStream_dep = assetManager.open("department.json")
        val jsonString_dep = inputStream_dep.bufferedReader().use {
            it.readText()
        }

        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("Key")

        for (i in 0 until jArray.length()) {
            if (departmentList.contains(jArray.getJSONObject(i).getString("기관명"))) {

            } else {
                departmentList.add(jArray.getJSONObject(i).getString("기관명"))
            }

        }
        val schlAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, schlList)
        schlSpinner.adapter = schlAdapter

        val depAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, departmentList)
        depSpinner.adapter = depAdapter

        val teamAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, teamList)
        teamSpinner.adapter = teamAdapter



        depSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                teamList.clear()
                val vo = depAdapter.getItem(position)
                val jObject_dep = JSONObject(jsonString_dep)
                val jArray_dep = jObject_dep.getJSONArray(vo)
                for (i in 0 until jArray_dep.length()) {
                    if (teamList.contains(jArray_dep.getJSONObject(i).getString("부서"))) {

                    } else {
                        teamList.add(jArray_dep.getJSONObject(i).getString("부서"))
                    }
                }
                teamAdapter.notifyDataSetChanged()
            }
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }

        teamSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                keyword=teamAdapter.getItem(position).toString()
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }


        }

        srchBtn.setOnClickListener{
            Toast.makeText(this,keyword,Toast.LENGTH_SHORT).show()
            startActivity(searchIntent)
        }
        button.setOnClickListener{
            //val nexIntent = Intent(this, mainsearch()::class.java)
           // startActivity(nexIntent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        }
        button5.setOnClickListener{
            val nexIntent3 = Intent(this, navigationreplace()::class.java)
            startActivity(nexIntent3)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_side_none)
        }
    }
}

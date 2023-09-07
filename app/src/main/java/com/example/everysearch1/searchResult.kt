package com.example.everysearch1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.main.Search
import com.example.main.SearchAdapter
import kotlinx.android.synthetic.main.activity_mainsearch.*
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.searchresultitemfix.*
import org.json.JSONObject

class searchResult : AppCompatActivity() {
    private lateinit  var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val list = ArrayList<Search>()
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        adapter = SearchAdapter(list)
        recyclerView.adapter = adapter

        val intent = intent ?: return
        var txtName = "";
        //val txtName = intent.getStringExtra(mainsearch.word)

        // 검색 방법을 확인
        val searchType = intent.getStringExtra("searchType")

        // "searchType" 값이 없는 경우 예외 처리
        if (searchType == null) {
            // "searchType" 값이 없을 때의 처리를 여기에 추가
            return
        }

        // 선택 항목 검색인 경우
        // "searchType" 값에 따라서 txtName을 설정
        if (searchType == "selectedSearch") {
            val selectedSchl = intent.getStringExtra("selectedSchl")
            val selectedDep = intent.getStringExtra("selectedDep")
            val selectedTeam = intent.getStringExtra("selectedTeam")

            // 선택된 항목 정보를 화면에 표시
            editText.text = selectedTeam
            txtName = selectedTeam
        }
        // 텍스트 검색인 경우
        else if (searchType == "textSearch") {
            val searchKeyword = intent.getStringExtra("searchKeyword")

            // 검색 키워드를 화면에 표시
            editText.text = searchKeyword
            txtName = searchKeyword
        }

        //editText.text=autoCompleteTextView2.text
        val assetManager = resources.assets
        val inputStream = assetManager.open("Find.json")
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("Key")



        fun AddArray( editText:String){
            if(list.isNotEmpty()){
                list.clear()
            }
            for (i in 0 until jArray.length()) {

                if (jArray.getJSONObject(i).getString("기관명").contains(editText)) {
                    list.add(
                        Search(
                            jArray.getJSONObject(i).getString("기관명"),
                            jArray.getJSONObject(i).getString("부서"),
                            jArray.getJSONObject(i).getString("직급"),
                            jArray.getJSONObject(i).getString("이름"),
                            jArray.getJSONObject(i).getString("업무안내"),
                            jArray.getJSONObject(i).getString("번호"),
                            jArray.getJSONObject(i).getString("즐겨찾기")
                        )
                    )
                }
                else if(jArray.getJSONObject(i).getString("부서").contains(editText)){
                    list.add(
                        Search(
                            jArray.getJSONObject(i).getString("기관명"),
                            jArray.getJSONObject(i).getString("부서"),
                            jArray.getJSONObject(i).getString("직급"),
                            jArray.getJSONObject(i).getString("이름"),
                            jArray.getJSONObject(i).getString("업무안내"),
                            jArray.getJSONObject(i).getString("번호"),
                            jArray.getJSONObject(i).getString("즐겨찾기")
                        )
                    )
                }
                else if(jArray.getJSONObject(i).getString("직급").contains(editText)){
                    list.add(
                        Search(
                            jArray.getJSONObject(i).getString("기관명"),
                            jArray.getJSONObject(i).getString("부서"),
                            jArray.getJSONObject(i).getString("직급"),
                            jArray.getJSONObject(i).getString("이름"),
                            jArray.getJSONObject(i).getString("업무안내"),
                            jArray.getJSONObject(i).getString("번호"),
                            jArray.getJSONObject(i).getString("즐겨찾기")
                        )
                    )
                }
                else if(jArray.getJSONObject(i).getString("이름").contains(editText)){
                    list.add(
                        Search(
                            jArray.getJSONObject(i).getString("기관명"),
                            jArray.getJSONObject(i).getString("부서"),
                            jArray.getJSONObject(i).getString("직급"),
                            jArray.getJSONObject(i).getString("이름"),
                            jArray.getJSONObject(i).getString("업무안내"),
                            jArray.getJSONObject(i).getString("번호"),
                            jArray.getJSONObject(i).getString("즐겨찾기")
                        )
                    )
                }
                else if(jArray.getJSONObject(i).getString("업무안내").contains(editText)){
                    list.add(
                        Search(
                            jArray.getJSONObject(i).getString("기관명"),
                            jArray.getJSONObject(i).getString("부서"),
                            jArray.getJSONObject(i).getString("직급"),
                            jArray.getJSONObject(i).getString("이름"),
                            jArray.getJSONObject(i).getString("업무안내"),
                            jArray.getJSONObject(i).getString("번호"),
                            jArray.getJSONObject(i).getString("즐겨찾기")
                        )
                    )
                }
                else if(jArray.getJSONObject(i).getString("번호").contains(editText)){
                    list.add(
                        Search(
                            jArray.getJSONObject(i).getString("기관명"),
                            jArray.getJSONObject(i).getString("부서"),
                            jArray.getJSONObject(i).getString("직급"),
                            jArray.getJSONObject(i).getString("이름"),
                            jArray.getJSONObject(i).getString("업무안내"),
                            jArray.getJSONObject(i).getString("번호"),
                            jArray.getJSONObject(i).getString("즐겨찾기")
                        )
                    )
                }
            }
            // 어댑터를 업데이트하여 변경된 데이터를 화면에 표시
            adapter.notifyDataSetChanged()
        }
        AddArray(txtName)
        autoCompleteTextView2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                list.clear()
                adapter.notifyDataSetChanged()
                AddArray(autoCompleteTextView2.text.toString())

                return@OnKeyListener true
            }
            else
                false
        })


        button2.setOnClickListener{
            finish()
        }
    }
}

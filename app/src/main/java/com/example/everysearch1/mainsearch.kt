package com.example.everysearch1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mainsearch.*
import okhttp3.*
import org.json.JSONObject
import org.w3c.dom.Element
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.HashMap

class mainsearch : AppCompatActivity() {
    companion object {
        var word: String = "검색어"
    }

    // 팝업창에 쓰이게 될 데이터
    var nameOfScl: String? = null
    var numOfStd: String? = null

    var itemList: ArrayList<HashMap<String, String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainsearch)
        val nextIntent2 = Intent(this, searchResult()::class.java)
        //nameOfScl = intent.getStringExtra("schoolName")
        nameOfScl = loadAutoCompleteText();
        nameOfScl?.let { Log.d("대학교이름 나오는지 테스트", it) }
        // 날짜와 학사일정 표시
        displayDateAndTask()

        // 버튼 클릭 이벤트 함수
        editword.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                nextIntent2.putExtra("searchKeyword", editword.text.toString())
                nextIntent2.putExtra("searchType", "textSearch")
                startActivity(nextIntent2)

                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_side_out_left)
                return@OnKeyListener true
            } else
                false
        })

        button.setOnClickListener {
            val nexIntent2 = Intent(this, orgSearch()::class.java)
            startActivity(nexIntent2)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_side_out_left)
        }

        button5.setOnClickListener {
            val nexIntent3 = Intent(this, navigationreplace()::class.java)
            startActivity(nexIntent3)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_side_none)
        }

        button9.setOnClickListener {
            val popIntent = Intent(this, onClick_PopUp()::class.java)
            startActivity(popIntent)
        }
    }

    private fun displayDateAndTask() {
        val dateSet = SimpleDateFormat("MMMM dd, yyyy", Locale.US) // 월과 일, 연도까지 출력
        // 현재 날짜를 "MMMM dd, yyyy" 형식의 문자열로 포맷팅
        val currentDateStr = dateSet.format(Date())
        Log.d("CurrentDate111111111", currentDateStr)
        val currentDate = dateSet.parse(currentDateStr) ?: Date() // 현재 날짜
        val dateText = SimpleDateFormat("MM월 dd일", Locale.KOREA).format(currentDate)

        val assetManager = resources.assets
        val inputStream = assetManager.open("ScheduleDate.json")
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("Key")

        // 현재 날짜보다 이전에 있는 마지막 학사일정 초기화
        var lastEventDate: Date? = null
        var lastEventText = ""

        // 학사일정 날짜 확인 및 출력
        for (i in 0 until jArray.length()) {
            val dateStr = jArray.getJSONObject(i).getString("날짜")
            // "MMMM dd, yyyy" 형식의 날짜 문자열을 파싱하여 전체 날짜로 남김
            val eventDate = dateSet.parse("$dateStr, ${currentDate.year + 1900}")

            if (currentDate.after(eventDate)) {
                val eventText = jArray.getJSONObject(i).getString("학사일정")

                // 현재 날짜보다 이전에 있는 학사일정 업데이트
                lastEventDate = eventDate
                lastEventText = eventText
            }
        }

        // 가장 최근 학사일정을 출력
        if (lastEventDate != null) {
            runOnUiThread {
                textView_date.text = dateText
                textView_task.text = lastEventText
                textView_date.visibility = View.VISIBLE
                textView_task.visibility = View.VISIBLE
            }
        } else {
            // 학사일정이 없는 경우 현재 날짜와 "일정없음"을 출력
            runOnUiThread {
                textView_date.text = dateText
                textView_task.text = "일정없음"
                textView_date.visibility = View.VISIBLE
                textView_task.visibility = View.VISIBLE
            }
        }
    }


    /*private fun getNumofStdList(serviceKey: String,
                                pageNo: String,
                                numOfRows: String,
                                schlld: String,
                                svyYr: String) {
        //val request = getRequestUrl(serviceKey, pageNo, numOfRows, schlld, svyYr)
        Log.d("테스트테스트", request.toString())
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                itemList = ArrayList()

                val body = response.body()?.string()?.byteInputStream()
                val buildFactory = DocumentBuilderFactory.newInstance()
                val docBuilder = buildFactory.newDocumentBuilder()
                val doc = docBuilder.parse(body, null)
                val nList = doc.getElementsByTagName("item")

                for (n in 0 until nList.length) {
                    val element = nList.item(n) as Element
                    val dataHashMap = HashMap<String, String>()
                    dataHashMap["schlId"] = getValueFromKey(element, "schlId") //학교아이디
                    dataHashMap["schlKrnNm"] = getValueFromKey(element, "schlKrnNm") //학교한글명
                    dataHashMap["indctVal1"] = getValueFromKey(element, "indctVal1") //재적학생
                    //dataHashMap["svyYr"] = getValueFromKey(element, "svyYr")
                    itemList!!.add(dataHashMap)
                }
                runOnUiThread {
                    temp = itemList.toString()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                val body = e.message
                runOnUiThread {
                    temp = body
                }
            }
        })
    }*/

    /*private fun getRequestUrl(serviceKey: String,
                              pageNo: String,
                              numOfRows: String,
                              schlld: String,
                              svyYr: String) : Request {

        var url = "http://openapi.academyinfo.go.kr/openapi/service/rest/StudentService/getEnrolledStudentCrntSt"
        var httpUrl = HttpUrl.parse(url)
            ?.newBuilder()
            ?.addEncodedQueryParameter("serviceKey", serviceKey)
            ?.addEncodedQueryParameter("pageNo",pageNo)
            ?.addEncodedQueryParameter("numOfRows", numOfRows)
            ?.addEncodedQueryParameter("schlld", schlld)
            ?.addEncodedQueryParameter("svyYr", svyYr)
            ?.build()

        return Request.Builder()
            .url(httpUrl)
            .addHeader(
                "Content-Type",
                "application/x-www-form-urlencoded; text/xml; charset=utf-8")
            .build()
    }*/

    private fun getValueFromKey(element: Element, key: String): String {
        val nodeList = element.getElementsByTagName(key)
        if (nodeList != null && nodeList.length > 0) {
            val item = nodeList.item(0)
            if (item != null && item.hasChildNodes()) {
                return item.firstChild.nodeValue
            }
        }
        return ""
    }

    fun Context.loadAutoCompleteText(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("schoolName", "") ?: ""
    }
}
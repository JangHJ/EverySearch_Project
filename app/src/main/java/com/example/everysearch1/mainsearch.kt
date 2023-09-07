package com.example.everysearch1

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
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
    companion object{
        var word:String="검색어"
    }
    //날짜, 학사일정 출력 관련 변수
    var date_text: String? = null
    var t_date: Date? = null
    var d_date: Date? = null
    var dateSet: SimpleDateFormat = SimpleDateFormat(" MM월 dd일(EE)")
    // 팝업창에 쓰이게 될 데이터
    var code: String? = null
    var numOfStd: String? = null

    var itemList: ArrayList<HashMap<String, String>> ?= null
    var temp : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainsearch)
        val nextIntent2 = Intent(this, searchResult()::class.java)

        val assetManager = resources.assets
        val inputStream = assetManager.open("ScheduleDate.json")
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("Key")

        //학사일정 날짜 확인 및 출력
        t_date = Date()
        for(i in 0 until jArray.length()) {
            d_date = Date(jArray.getJSONObject(i).getString("날짜"))
            //var diffsec : Long =  d_date!!.getTime() - t_date!!.getTime()
            date_text = dateSet.format(d_date)
            if (t_date!!.getTime() <= d_date!!.getTime()) {
                textView_task.setText(jArray.getJSONObject(i).getString("학사일정").toString())
                textView_date.setText(date_text)
                break
            }
        }//


        //버튼 클릭이벤트 함수
        editword.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                nextIntent2.putExtra(word,editword.text.toString())

                nextIntent2.putExtra("searchType", "textSearch")
                startActivity(nextIntent2)

                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_side_out_left)
                return@OnKeyListener true
            }
            else
                false
        })
        /*button7.setOnClickListener(){
            val nextIntent = Intent(this, login()::class.java)
            startActivity(nextIntent)
        }*/
        button.setOnClickListener{
            val nexIntent2 = Intent(this, orgSearch()::class.java)
            startActivity(nexIntent2)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_side_out_left)
        }
        button5.setOnClickListener{
            val nexIntent3 = Intent(this, navigationreplace()::class.java)
            startActivity(nexIntent3)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_side_none)
        }
        button9.setOnClickListener{
            val popIntent = Intent(this, onClick_PopUp()::class.java)

            val serviceKey = "Lb28IpzH6noVYNF%2FKeXpFqSgMOgfI0ClnD7w7atIOLyho8mYTB2wfBcEa%2BDvokxHwbYBBr0lEC0emJQ7ibd%2BUg%3D%3D"
            val pageNo = ""
            val numOfRows = ""
            val schlId = "0000126"
            val svyYr = "2019"

            getNumofStdList(serviceKey, pageNo, numOfRows, schlId,svyYr)

            numOfStd = itemList.toString()
            //code = dataHashMap!!.get("schlKrnNm")

            popIntent.putExtra("code",code)
            popIntent.putExtra("numOfStd",numOfStd)
            popIntent.putExtra("temp", temp)

            Toast.makeText(this, temp, Toast. LENGTH_SHORT).show()
            startActivity(popIntent)
        }
    }
    private fun getNumofStdList(serviceKey: String,
                                pageNo: String,
                                numOfRows: String,
                                schlld: String,
                                svyYr: String) {
        val request = getRequestUrl(serviceKey, pageNo, numOfRows, schlld, svyYr)
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
                    dataHashMap!!.put("indctId", getValueFromKey(element, "indctId"))
                    dataHashMap!!.put("indctVal1", getValueFromKey(element, "indctVal1"))
                    dataHashMap!!.put("indctYr", getValueFromKey(element, "indctYr"))
                    dataHashMap!!.put("schlDivNm", getValueFromKey(element, "schlDivNm"))
                    dataHashMap!!.put("schlEstbNm", getValueFromKey(element, "schlEstbNm"))
                    dataHashMap!!.put("schlId", getValueFromKey(element, "schlId"))
                    dataHashMap!!.put("schlKrnNm", getValueFromKey(element, "schlKrnNm"))
                    dataHashMap!!.put("svyYr", getValueFromKey(element, "svyYr"))
                    itemList!!.add(dataHashMap!!)
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
    }
    private fun getRequestUrl(serviceKey: String,
                              pageNo: String,
                              numOfRows: String,
                              schlld: String,
                              svyYr: String) : Request {

        var url = "http://openapi.academyinfo.go.kr/openapi/service/rest/StudentService/getComparisonEnrolledStudentCrntSt"
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
            .addHeader("Content-Type",
                "application/x-www-form-urlencoded; text/xml; charset=utf-8")
            .build()
    }

    private fun getValueFromKey(element: Element, key: String) : String {
        return element.getElementsByTagName(key).item(0).firstChild.nodeValue
    }
}
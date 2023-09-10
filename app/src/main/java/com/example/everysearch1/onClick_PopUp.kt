package com.example.everysearch1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_pop.*
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.xml.parsers.DocumentBuilderFactory

class onClick_PopUp : AppCompatActivity() {
    var stdnum: String? = null
    var schlname: String? = null
    private val serviceKey = "kZs%2BCJwkh%2BFPLNaDHZ2D5Tp9VminZhDvj1QZIZ4nrZ9BO4zx246LcohHm2doewl%2B99DJKEP331jtV4C7q4vilQ%3D%3D"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pop)

        // 학교명 가져오기
        schlname = loadAutoCompleteText()

        // API를 사용하여 학생 수 가져오기
        stdnum = getStudentCountFromAPI()

        //팝업 메시지 내용
        tv_numOfStd.text = schlname + "에 재적중인 학생은 총 " + stdnum + "명 입니다."

        btn_ok.setOnClickListener {
            finish()
        }
    }

    // 학생 수를 API에서 가져오는 함수
    private fun getStudentCountFromAPI() : String{
        try {
            val urlBuilder =
                StringBuilder("http://openapi.academyinfo.go.kr/openapi/service/rest/StudentService/getComparisonEnrolledStudentCrntSt")
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + serviceKey)
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "pageNo",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("1", "UTF-8")
            )
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "numOfRows",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("999", "UTF-8")
            )
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "schlId",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("0000126", "UTF-8")
            )
            urlBuilder.append(
                "&" + URLEncoder.encode(
                    "svyYr",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("2018", "UTF-8")
            )

            val url = URL(urlBuilder.toString())
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-type", "application/json")

            val rd: BufferedReader = if (conn.responseCode >= 200 && conn.responseCode <= 300) {
                BufferedReader(InputStreamReader(conn.inputStream))
            } else {
                BufferedReader(InputStreamReader(conn.errorStream))
            }

            val sb = StringBuilder()
            var line: String?

            while (rd.readLine().also { line = it } != null) {
                sb.append(line)
            }

            rd.close()
            conn.disconnect()

            val responseXml = sb.toString()

            // XML 파싱
            val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(InputSource(StringReader(responseXml)))
            xmlDoc.documentElement.normalize()

            // 학생 수 파싱
            val indctVal1Node = xmlDoc.getElementsByTagName("indctVal1").item(0)

            return indctVal1Node.textContent
        }  catch (e: Exception) {
            // 오류가 발생한 경우 0으로 반환
            return "0"
        }
    }

    fun Context.loadAutoCompleteText(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("schoolName", "") ?: ""
    }
}
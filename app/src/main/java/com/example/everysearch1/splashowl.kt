package com.example.everysearch1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashowl : AppCompatActivity() {

    private val splashTimeOut: Long = 2000 // 2초 동안 스플래시 화면 표시

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashowl) // 스플래시 화면의 레이아웃 파일을 설정해야 합니다.

        Handler().postDelayed({
            // 일정 시간 후에 MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }, splashTimeOut)
    }
}

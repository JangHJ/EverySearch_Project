package com.example.everysearch1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class splashowl : AppCompatActivity() {

    private val splashTimeOut: Long = 2000 // 2초 동안 스플래시 화면 표시
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashowl) // 스플래시 화면의 레이아웃 파일을 설정해야 합니다.

        val auth = FirebaseAuth.getInstance() // Firebase 인증 객체 초기화

        Handler().postDelayed({
            // 현재 로그인한 사용자 확인
            val currentUser = auth.currentUser
            currentUser?.email?.let { Log.d("로그인 확인 !!!!", it) }

            // 사용자가 로그인한 경우 mainsearch 화면으로 이동
            if (currentUser != null) {
                val intent = Intent(this, mainsearch::class.java)
                startActivity(intent)
            } else {
                // 사용자가 로그인하지 않은 경우 MainActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            finish() // 현재 액티비티 종료
        }, splashTimeOut)
    }
}

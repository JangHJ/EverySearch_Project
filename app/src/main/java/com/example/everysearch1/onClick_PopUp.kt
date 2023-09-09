package com.example.everysearch1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_pop.*

class onClick_PopUp : AppCompatActivity() {
    var pop_txt : String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //타이틀바 없애기
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pop)

        intent = getIntent()

        val nos1 = intent.getStringExtra("nameOfScl")
        val nos2 = ""
        pop_txt = (nos1 + "에 재적 중인 학생은 " + nos2 + "명입니다")

        tv_numOfStd.setText(pop_txt)
        btn_ok.setOnClickListener()
        {
            finish()
        }
    }
}

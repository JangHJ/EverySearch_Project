package com.example.everysearch1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chooseschool_afterlogin.*

class chooseschool_afterlogin : AppCompatActivity() {
    var isLogin2 : Boolean ?= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooseschool_afterlogin)

        intent = getIntent()
        isLogin2 = intent.getBooleanExtra("isLogin", false)

        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.activity_navigationreplace, null)

        val name : TextView = view.findViewById(R.id.textView2)
        val tv_login : Button = view.findViewById(R.id.button4)

        if(isLogin2 == true)
        {
            name.setText("이서연님 환영합니다")
            tv_login.setText("로그아웃")
        }

        button17.setOnClickListener{
            finish()
        }
    }
}

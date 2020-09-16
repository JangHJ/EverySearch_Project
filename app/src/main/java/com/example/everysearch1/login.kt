package com.example.everysearch1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_chooseschool_afterlogin.*
import kotlinx.android.synthetic.main.activity_chooseschool_afterlogin.button17
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    var login_suc : Boolean ?= false
    var isLogin : Boolean ?= false

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button17.setOnClickListener{
            finish()
        }
        button6.setOnClickListener{
            emailLogin()
            //1. 네비게이션 화면에서 로그인을 누른 경우 이 화면으로 오는데, 이 경우는 이 버튼 클릭시 activity_chooseschool_afterlogin으로 가야함
            //      + 네비게이션 화면의 이름을 '이서연님 환영합니다'로 바꾸고 아래 버튼의 텍스트도 로그아웃으로 바꿈.
            //2. search화면에서 즐겨찾기를 누른 경우 이 화면으로 오는데, 이 경우는 이 버튼 클릭시 finish()하여 이 엑티비티를 종료.
            //search화면에서 즐겨찾기를 누르면 이 페이지로 왔다가 로그인을 하면 search화면의 즐겨찾기 이미지가 변경되어야 함.
            //drawable에 있는 ic_bookmark_2파일로 변경(현재 ic_bookmark_1임.)
        }
        button20.setOnClickListener{
            val nextIntent = Intent(this, MainActivity()::class.java)
            startActivity(nextIntent)
            //로그인이 된 상태에서 학교바꾸기 버튼(button20)을 누르면 activity_chooseschool_afterlogin(로그인 후 버전 학교 선택)로 가야하고,
            //로그인이 되지 않은 상태에서 학교바꾸기 버튼(button20)을 누르면 activity_main(=로그인 전 버전 학교 선택)로 가야하고
        }
    }
    private fun emailLogin() {
        if (editText6.text.toString().isNullOrEmpty() || editText5.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        var email = editText6.text.toString()
        var password = editText5.text.toString()
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email 로그인 성공", Toast.LENGTH_LONG).show()
                    moveMainPage(auth?.currentUser)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            val after_Intent = Intent(this, chooseschool_afterlogin()::class.java)
            isLogin = true
            after_Intent.putExtra("isLogin", isLogin!!)
            startActivity(after_Intent)
            finish()
        }
    }
}

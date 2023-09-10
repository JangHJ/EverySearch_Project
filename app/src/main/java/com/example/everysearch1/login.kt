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
import kotlinx.android.synthetic.main.activity_signup.editEmail

class login : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        //뒤로가기 버튼
        btnBack.setOnClickListener{
            finish()
        }
        //로그인 버튼 클릭 시
        btnLogin.setOnClickListener{
            emailLogin()
        }
        //회원가입 버튼 클릭 시
        btnSignup.setOnClickListener{
            val nextIntent = Intent(this, signup()::class.java)
            startActivity(nextIntent)
        }
    }
    private fun emailLogin() {
        if (editEmail.text.toString().isNullOrEmpty() || editPwd.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        var email = editEmail.text.toString()
        var password = editPwd.text.toString()
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email 로그인 성공", Toast.LENGTH_LONG).show()
                    val nextIntent = Intent(this, navigationreplace()::class.java)
                    startActivity(nextIntent)
                    finish()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}

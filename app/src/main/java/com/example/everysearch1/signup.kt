package com.example.everysearch1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        btnBack.setOnClickListener{
            finish()
        }
        //회원가입 버튼 클릭 시
        btnSignup.setOnClickListener{
            emailSignup()
        }
    }
    private fun emailSignup() {
        if (editEmail.text.toString().isNullOrEmpty() || editPwd.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        var email = editEmail.text.toString()
        var password = editPwd.text.toString()

        auth?.createUserWithEmailAndPassword(email, password)
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "회원가입이 실패했습니다", Toast.LENGTH_LONG).show()
                }
            }

    }
}

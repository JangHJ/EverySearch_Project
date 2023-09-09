package com.example.everysearch1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mainsearch.*
import kotlinx.android.synthetic.main.activity_mainsearch.button
import kotlinx.android.synthetic.main.activity_mainsearch.button5
import kotlinx.android.synthetic.main.activity_navigationreplace.*

class navigationreplace : AppCompatActivity() {

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigationreplace)

        val name = findViewById<TextView>(R.id.textView2)
        val shlname = findViewById<TextView>(R.id.textView3)
        val loginbtn = findViewById<Button>(R.id.button4)
        auth = FirebaseAuth.getInstance() // Firebase 인증 객체 초기화
        val currentUser = auth?.currentUser

        shlname.text = loadAutoCompleteText()
        if(currentUser != null){
            name.text = currentUser?.email
            loginbtn.text = "로그아웃"
        }
        else
            Log.d("테스트테스트3333333333","이메일 로그인이 안된것같다?")

        button5.setOnClickListener{
            val nextIntent = Intent(this, mainsearch()::class.java)
            startActivity(nextIntent)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
            finish()
        }
        button.setOnClickListener{
            val nextIntent2 = Intent(this, bookmark()::class.java)
            startActivity(nextIntent2)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
            finish()
        }
        button7.setOnClickListener{
            val nextIntent3 = Intent(this, mainsearch()::class.java)
            startActivity(nextIntent3)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
            finish()
        }
        button8.setOnClickListener{
            val nextIntent4 = Intent(this, orgSearch()::class.java)
            startActivity(nextIntent4)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
            finish()
        }
        button3.setOnClickListener{
            val nextIntent5 = Intent(this, findschoolActivity()::class.java)
            startActivity(nextIntent5)
            finish()
        }
        button4.setOnClickListener{
            if(currentUser != null){
                auth!!.signOut()
                Toast.makeText(this, "로그아웃 되었습니다!", Toast.LENGTH_LONG).show()
                finish()
            }else{
                val nextIntent6 = Intent(this, login()::class.java)
                startActivity(nextIntent6)
            }
        }
    }
    fun Context.loadAutoCompleteText(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("schoolName", "") ?: ""
    }
}

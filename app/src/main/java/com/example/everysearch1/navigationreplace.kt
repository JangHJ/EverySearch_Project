package com.example.everysearch1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mainsearch.*
import kotlinx.android.synthetic.main.activity_mainsearch.button
import kotlinx.android.synthetic.main.activity_mainsearch.button5
import kotlinx.android.synthetic.main.activity_navigationreplace.*

class navigationreplace : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigationreplace)

        button5.setOnClickListener{
            val nextIntent = Intent(this, mainsearch()::class.java)
            startActivity(nextIntent)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
        }
        button.setOnClickListener{
            val nextIntent2 = Intent(this, bookmark()::class.java)
            startActivity(nextIntent2)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
        }
        button7.setOnClickListener{
            val nextIntent3 = Intent(this, mainsearch()::class.java)
            startActivity(nextIntent3)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
        }
        button8.setOnClickListener{
            val nextIntent4 = Intent(this, orgSearch()::class.java)
            startActivity(nextIntent4)

            overridePendingTransition(R.anim.anim_side_none, R.anim.anim_side_out_left)
        }
        button3.setOnClickListener{
            val nextIntent5 = Intent(this, chooseschool_afterlogin()::class.java)
            startActivity(nextIntent5)
        }
        button4.setOnClickListener{
            val nextIntent6 = Intent(this, login()::class.java)
            startActivity(nextIntent6)
        }
    }

}

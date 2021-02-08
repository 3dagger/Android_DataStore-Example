package com.example.datastoreex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var name = ""
    var age = 0
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)

        buttonSave()

        observeData()

    }

    private fun buttonSave() {
        btn_save.setOnClickListener {
                name = et_name.text.toString()
                age = et_age.text.toString().toInt()
                val isMale = switch_gender.isChecked

                GlobalScope.launch {
                    userManager.storeUser(age, name, isMale)
                }

        }
    }

    private fun observeData() {
        userManager.userNameFlow.asLiveData().observe(this) {
            name = it
            tv_name.text = it
        }

        userManager.userAgeFlow.asLiveData().observe(this) {
            age = it
            tv_age.text = it.toString()
        }

        userManager.userGenderFlow.asLiveData().observe(this) {
            gender = if (it) "Male" else "Female"
            tv_gender.text = gender
        }

    }
}
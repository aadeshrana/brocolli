package com.example.broccoli.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.broccoli.R


class PreRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_register)
    }

    // Request button click
    fun requestInvite(view : View){
        val intent = Intent(this, Register::class.java)
          startActivity(intent)
    }





}
package com.example.broccoli

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button

class PostRegisted : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registered)
    }

    // Cancel button click
    fun cancelInvite(view: View){
        showConfirmDialog()
    }

    // Display custom confirmation dialog box
    private fun showConfirmDialog() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_remove_diag)

        val cancelBtn = dialog.findViewById<Button>(R.id.btnCancel)
        val confirmBtn = dialog.findViewById<Button>(R.id.btnConf)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        confirmBtn.setOnClickListener {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("isRegistered","false")
            editor.apply()
            editor.commit()
            dialog.dismiss()
            showSuccessDiag()

        }
        dialog.show()

    }

    // Display the custom success dialog
    private fun showSuccessDiag() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_sucess_diag)

        val confBtn = dialog.findViewById<Button>(R.id.btnOkay)
        confBtn.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this,PreRegister::class.java)
            startActivity(intent)
        }
        dialog.show()

    }
}
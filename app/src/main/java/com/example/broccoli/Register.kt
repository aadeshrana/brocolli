package com.example.broccoli

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class Register : AppCompatActivity() {
    var fullNameET : EditText? = null
    var emailET : EditText? = null
    var confEmailET : EditText? = null

    var fullNameCV : CardView? = null
    var emailCV : CardView? = null
    var confEmailCV : CardView? = null

    var fullNameValue : String? = null
    var emailValue : String? = null
    var confEmailValue : String? = null

    var validName = false
    var validEmail = false
    var validConfEmail = false

    var requestBtn : Button? = null
    var errorMessage : String = ""
    var errorTV : TextView? = null

    var requestPB : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        fullNameET = findViewById(R.id.etFullName)
        emailET = findViewById(R.id.etEmail)
        confEmailET = findViewById(R.id.etConfirmEmail)

        fullNameCV = findViewById(R.id.cvFullName)
        emailCV = findViewById(R.id.cvEmail)
        confEmailCV = findViewById(R.id.cvConfEmail)

        requestBtn = findViewById(R.id.btnRequest)
        requestBtn?.isClickable = false

        errorTV = findViewById(R.id.tvError)

        requestPB = findViewById(R.id.pbRequest)

        fullNameET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count < 3){
                    fullNameCV?.setCardBackgroundColor(Color.parseColor("#FF0000"))
                    validName = false
                    errorMessage = "Full name must be at least 3 characters long"
                }else{
                    fullNameCV?.setCardBackgroundColor(Color.parseColor("#00ff00"))
                    fullNameValue = fullNameET?.text.toString()
                    validName = true
                    errorMessage = ""
                }
                setErrorMessage()
                isValidForm()
            }
        })

        emailET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count == 0){
                    emailCV?.setCardBackgroundColor(Color.parseColor("#FF0000"))
                    validEmail = false
                    errorMessage = "Email cannot be empty"
                }else{
                    if(isValidEmail(s)){
                        emailCV?.setCardBackgroundColor(Color.parseColor("#00ff00"))
                        emailValue = s.toString()
                        validEmail = true
                        errorMessage = ""
                    }else {
                        emailCV?.setCardBackgroundColor(Color.parseColor("#FF0000"))
                        validEmail = false
                        errorMessage = "Please enter a valid email"
                    }
                }
                setErrorMessage()
                isValidForm()
            }
        })

        confEmailET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count == 0){
                    confEmailCV?.setCardBackgroundColor(Color.parseColor("#FF0000"))
                    validConfEmail = false
                    errorMessage = "Confirm Email cannot be empty"
                }else{
                    if(isEmailMatch(emailValue, s.toString())){
                        confEmailCV?.setCardBackgroundColor(Color.parseColor("#00ff00"))
                        confEmailValue = s.toString()
                        validConfEmail = true
                        errorMessage = ""
                    }else {
                        confEmailCV?.setCardBackgroundColor(Color.parseColor("#FF0000"))
                        validConfEmail = false
                        errorMessage = "Emails do not match"
                    }
                }
                setErrorMessage()
                isValidForm()
            }
        })
    }

    fun  sendRequest(view : View){
        requestBtn?.visibility = View.GONE
        requestPB?.visibility = View.VISIBLE
        postRequest()


    }

    fun afterpostRequest(response: String){
        if(response.equals("Registered")) {
            runOnUiThread { requestBtn?.visibility = View.VISIBLE
                requestPB?.visibility = View.GONE }

            val intent = Intent(this,SuccessRegister::class.java)
            startActivity(intent)
        }else{
            errorMessage = response
            runOnUiThread { requestBtn?.visibility = View.VISIBLE
                requestPB?.visibility = View.GONE
                setErrorMessage()}


        }
    }

    private fun setText(text: TextView, value: String) {
        runOnUiThread { text.text = value }
    }

    fun setErrorMessage(){
        errorTV?.setText(errorMessage)
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun isEmailMatch(email: String?, confEmail : String): Boolean{
        return email.equals(confEmail)
    }

    fun isValidForm(){
        Log.e("formValid","validName"+validName)
        Log.e("formValid","validEmail"+validEmail)
        Log.e("formValid","validConfEmail"+validConfEmail)
        if(validName && validEmail &&  validConfEmail){
            Log.e("formValid","validConfEmailssss"+validConfEmail)
            requestBtn?.background?.setTint(Color.parseColor("#292C87"))
            requestBtn?.isClickable = true
        }
       else{

            requestBtn?.background?.setTint(Color.parseColor("#CACACA"))
            requestBtn?.isClickable = false

        }
    }

    fun postRequest(){
        val rootObject= JSONObject()
        rootObject.put("name",fullNameValue)
        rootObject.put("email",emailValue)
        val JSONObjectString = "{ \"name\":"+fullNameValue+", \"email\":"+emailValue+" }"

        val okHttpClient = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = rootObject.toString().toRequestBody(mediaType)

        var returnString = ""
        val request = Request.Builder()
            .method("POST", requestBody)
            .url("https://us-central1-blinkapp-684c1.cloudfunctions.net/fakeAuth\n")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("httpResponse","e"+e)

            }

            override fun onResponse(call: Call, response: Response) {

                val responseString = response.body!!.string()
                Log.e("httpResponse","r"+ responseString)
                if(responseString == "Registered"){
                    returnString =  "Registered"
                }else{
                    val jobject = JSONObject(responseString)
                    returnString = jobject.getString("errorMessage")
                }


                afterpostRequest(returnString)
            }
        })


    }
}
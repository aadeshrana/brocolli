package HelperClass

import Interface.OnRequestCompleteListner
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class OkHttpProvider {

    companion object {
        fun requestInvitation(fullName: String, email: String, onRquestCallBack : OnRequestCompleteListner) {
            val url = "https://us-central1-blinkapp-684c1.cloudfunctions.net/fakeAuth"
            val rootObject = JSONObject()
            rootObject.put("name", fullName)
            rootObject.put("email", email)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = rootObject.toString().toRequestBody(mediaType)
            postRequest(url, requestBody, onRquestCallBack)


        }

        fun postRequest(url :String, requestBody: RequestBody,onRquestCallBack : OnRequestCompleteListner){
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .method("POST", requestBody)
                .url(url)
                .build()
            var returnString = ""
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("httpResponse","error"+e)
                    returnString = "Unexpected error"
                    onRquestCallBack.onSuccess(returnString)
                }
                override fun onResponse(call: Call, response: Response) {

                    val responseString = response.body!!.string()

                    if(responseString == "Registered"){
                        returnString =  "Registered"


                    }else{
                        val jobject = JSONObject(responseString)
                        returnString = jobject.getString("errorMessage")
                    }

                    onRquestCallBack.onSuccess(returnString)
                }
            })
        }

    }


}



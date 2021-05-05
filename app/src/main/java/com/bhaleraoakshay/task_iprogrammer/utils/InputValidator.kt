package com.bhaleraoakshay.task_iprogrammer.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Akshay on 05-05-2021.
 */
class InputValidator ():TextWatcher  {

    private var mIsValid = false
    fun isValid(): Boolean {
        return mIsValid
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        mIsValid = Companion.isValidCity(p0);

    }

    companion object {
        fun isValidCity(email: CharSequence?): Boolean {
            return email != null
        }
    }


}


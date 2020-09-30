package com.ard.neonetzexamapp.ui.strategy

import android.widget.EditText
import com.ard.neonetzexamapp.utils.afterTextChanged

interface EditTextStrategy {
    fun requestFocus(editTextList: List<EditText>)
    fun getEditTextsString(editTextList: List<EditText>): String
    fun cleanEditText(editTextList: List<EditText>)
}

class EditTextFocusChangeStrategy() : EditTextStrategy {
    private var returnFlag = true

    override fun requestFocus(editTextList: List<EditText>) {
        for (i in editTextList.indices) {
            if (i != editTextList.size - 1) {
                editTextList[i].afterTextChanged {
                    if (it.length == 1) editTextList[i + 1].requestFocus()
                }
            }
        }

        for (i in editTextList.indices) {
            if (i > 0) {

                editTextList[i].setOnKeyListener { _, _, keyEvent ->
                    if (keyEvent.keyCode == 67 && returnFlag) {
                        editTextList[i - 1].requestFocus()
                        returnFlag = false
                    } else returnFlag = true
                    false
                }
            }
        }
    }

    override fun getEditTextsString(editTextList: List<EditText>): String {
        var password = ""
        for (editText in editTextList) {
            password += editText.text.toString()
        }
        return password
    }

    override fun cleanEditText(editTextList: List<EditText>) {
        for (editText in editTextList) {
            editText.setText("")
        }
    }
}
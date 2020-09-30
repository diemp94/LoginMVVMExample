package com.ard.neonetzexamapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(requireContext(),message,duration).show()
fun Fragment.toast(resourceId: Int, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(requireContext(),resourceId,duration).show()

fun EditText.afterTextChanged(text: (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            text(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })
}

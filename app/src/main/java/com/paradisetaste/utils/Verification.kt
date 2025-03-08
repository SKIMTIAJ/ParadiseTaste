package com.paradisetaste.utils

fun verifyMobilenumber(mobileNumber: String): Boolean {
    return (mobileNumber.length == 10 || mobileNumber.length == 12) && mobileNumber.all { it.isDigit()}
}

fun nameCheck(name: String):Boolean{
    return name.isEmpty()
}
package com.example.authentication.Core

import com.facebook.CallbackManager

object FacebookUtil {
    val callbackManager by lazy {
        CallbackManager.Factory.create()
    }
}

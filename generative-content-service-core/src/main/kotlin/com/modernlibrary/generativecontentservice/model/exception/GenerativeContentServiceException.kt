package com.modernlibrary.generativecontentservice.model.exception

import java.lang.Exception

data class GenerativeContentServiceException(val reason: String) : Exception()

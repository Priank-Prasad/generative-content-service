package com.modernlibrary.generativecontentservice.model.exception

import java.lang.Exception

data class ApiCallException(val failureResponse: String): Exception()
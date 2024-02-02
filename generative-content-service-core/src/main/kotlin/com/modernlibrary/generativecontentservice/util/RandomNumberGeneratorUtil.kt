package com.modernlibrary.generativecontentservice.util

import java.time.Instant
import java.util.UUID

class RandomNumberGeneratorUtil {

    companion object {
        suspend fun generateRandomNumberWithPrefix(prefix: String) : String {
            return prefix + Instant.now().toEpochMilli() + UUID.randomUUID().toString()
        }
    }
}

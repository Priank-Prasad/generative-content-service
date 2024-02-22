package com.modernlibrary.generativecontentservice.util

import java.time.Instant
import java.util.UUID

class RandomNumberGeneratorUtil {

    companion object {
        suspend fun generateRandomNumberWithPrefix(prefix: String) : String {
            return Instant.now().toEpochMilli().toString().takeLast(8) + prefix + UUID.randomUUID().toString().replace("-", "").takeLast(20)
        }
    }
}

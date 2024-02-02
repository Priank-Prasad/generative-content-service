package com.modernlibrary.generativecontentservice.api.content

import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalRequest
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalResponse
import com.modernlibrary.generativecontentservice.constant.GenerativeContentServiceConstants.Companion.AUTH_HEADER_NAME
import com.modernlibrary.generativecontentservice.handler.CreateContentHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/generate")
class ContentController(
    private val createContentHandler: CreateContentHandler,
) {

    @PostMapping("/content")
    suspend fun createContent(
        @RequestHeader(AUTH_HEADER_NAME) authToken: String,
        @RequestBody request: GenerateComponentInternalRequest
    ): ResponseEntity<GenerateComponentInternalResponse> {
        return ResponseEntity.ok(createContentHandler.createContent(request))
    }
}
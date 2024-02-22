package com.modernlibrary.generativecontentservice.handler

import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalRequest
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalResponse
import com.modernlibrary.generativecontentservice.model.entity.RequestDetailTracker
import com.modernlibrary.generativecontentservice.model.exception.GenerativeContentServiceException
import com.modernlibrary.generativecontentservice.repository.store.RequestDetailTrackerStore
import com.modernlibrary.generativecontentservice.service.ContentServiceMiddleware
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateContentHandler (
    private val requestDetailTrackerStore: RequestDetailTrackerStore,
    private val contentServiceMiddleware: ContentServiceMiddleware,
) {

    private val logger = KotlinLogging.logger {}

    suspend fun createContent(request: GenerateComponentInternalRequest): GenerateComponentInternalResponse {
        var rdt = RequestDetailTracker(request)
        rdt = requestDetailTrackerStore.save(rdt)
        try {
            val response = contentServiceMiddleware.createContent(request)
            requestDetailTrackerStore.updateRdtProcessed(rdt)
            return response
        } catch (ex: Exception) {
            logger.error(ex) { "Caught exception while creating content: ex: ${ex.message}" }
            requestDetailTrackerStore.updateRdtFailed(rdt, ex.message ?: "Caught exception in CreateContentHandler::createContent")
            throw GenerativeContentServiceException(ex.message ?: "Caught exception in CreateContentHandler::createContent")
        }
    }
}
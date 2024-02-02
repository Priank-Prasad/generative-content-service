package com.modernlibrary.generativecontentservice.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.modernlibrary.generativecontentservice.model.exception.ApiCallException
import kotlinx.coroutines.reactive.awaitFirst
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException

@Component
class BaseClient(
    private val objectMapper: ObjectMapper,
) {
    private val logger = KotlinLogging.logger {}

    suspend fun <T, R: Any> post(webclient: WebClient, body: T, responseClass: Class<R>, path: String, headersMap: MultiValueMap<String, String> = LinkedMultiValueMap(), uriVariables: MutableMap<String, Any?> = mutableMapOf()): R {
        val payload = objectMapper.writeValueAsString(body)
        logger.info { "Request payload from Origination Service to uri: $path is $payload" }

        try {
            val response: String = webclient.post()
                .uri(path, uriVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .headers{ headers -> headers.addAll(headersMap)}
                .bodyValue(payload)
                .retrieve()
                .onStatus({status -> status.isError},
                    {response -> logger.warn { "status code caught is: ${response.statusCode()}" }
                        response.bodyToMono(String::class.java).map { faiResponse -> ApiCallException(faiResponse) }
                    })
                .bodyToMono(String::class.java).awaitFirst()

            logger.info {"Response in Origination Service to uri: $path, is $response"}
            return objectMapper.readValue(response, responseClass)
        } catch(ex: ApiCallException) {
            logger.error(ex) { "Caught exception for uri: $path, ex: $ex" }
            throw ex
        } catch (ex: WebClientException) {
            logger.error(ex) { "Caught webclient exception for uri: $path, ex: $ex" }
            throw ex
        }
    }
}
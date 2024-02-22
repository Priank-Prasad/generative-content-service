package com.modernlibrary.generativecontentservice.repository.store

import com.modernlibrary.generativecontentservice.model.entity.RequestDetailTracker
import com.modernlibrary.generativecontentservice.model.enums.RdtStatus
import com.modernlibrary.generativecontentservice.repository.RequestDetailTrackerRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class RequestDetailTrackerStore(
    private val requestDetailTrackerRepository: RequestDetailTrackerRepository,
) {

    suspend fun save(rdtObject: RequestDetailTracker): RequestDetailTracker {
        return requestDetailTrackerRepository.save(rdtObject).awaitFirst()
    }


    suspend fun updateRdtFailed(rdtObject: RequestDetailTracker, failureReason: String): RequestDetailTracker {
        rdtObject.rdtStatus = RdtStatus.FAILURE
        rdtObject.errorReason = failureReason
        return requestDetailTrackerRepository.save(rdtObject).awaitFirst()
    }

    suspend fun updateRdtProcessed(rdtObject: RequestDetailTracker): RequestDetailTracker {
        rdtObject.rdtStatus = RdtStatus.COMPLETED
        return requestDetailTrackerRepository.save(rdtObject).awaitFirst()
    }
}

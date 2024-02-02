package com.modernlibrary.generativecontentservice.repository

import com.modernlibrary.generativecontentservice.model.entity.RequestDetailTracker
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestDetailTrackerRepository: R2dbcRepository<RequestDetailTracker, Long> {
}

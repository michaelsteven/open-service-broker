package com.swisscom.cloud.sb.broker.provisioning.lastoperation

import com.swisscom.cloud.sb.broker.error.ErrorCode
import com.swisscom.cloud.sb.broker.model.LastOperation
import com.swisscom.cloud.sb.broker.model.repository.LastOperationRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
@CompileStatic
class LastOperationStatusService {
    @Autowired
    private LastOperationStatus2CFLastOperationStatusConverter converter
    @Autowired
    private LastOperationRepository lastOperationRepository

    LastOperationResponseDto pollJobStatus(String serviceInstanceGuid) {
        LastOperation job = lastOperationRepository.findByGuid(serviceInstanceGuid)
        if (!job) {
            log.debug "LastOperation with id: ${serviceInstanceGuid} does not exist - returning 410 GONE"
            ErrorCode.LAST_OPERATION_NOT_FOUND.throwNew()
        }
        return new LastOperationResponseDto(status: converter.convert(job.status),
                description: job.description)
    }
}

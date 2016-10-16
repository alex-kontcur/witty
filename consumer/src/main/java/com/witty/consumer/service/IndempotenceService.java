package com.witty.consumer.service;

import com.witty.error.DuplicatedMissionIdException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IndempotenceService - application service for indempotence checking. It holds recent missionId's in memory with
 * predefined ttl. If missionId is duplicated the DuplicatedMissionIdException will thrown. This will lead
 * client get HTTP code 409 (Conflict) (@see DuplicatedMissionIdException)
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Service
public class IndempotenceService {

    @Value("${witty.consumer.mission.ttl.ms:86400000}")
    private Integer missionTtl;

    private Map<Integer, Long> conflictMap = new ConcurrentHashMap<>();

    /**
     * Checks for indempotence of given missionId. Throws DuplicatedMissionIdException in the case of violation.
     *
     * @param missionId Integer - An unique identifier used to ensure indempotence
     */
    public void checkIndempotence(Integer missionId) {
        Long current = conflictMap.putIfAbsent(missionId, System.currentTimeMillis());
        if (current != null) {
            conflictMap.put(missionId, System.currentTimeMillis());
            throw new DuplicatedMissionIdException("missionId = " + missionId + " has been used");
        }
    }

    /**
     * Periodical task for evicting of expired missionId items. Used to get rid of memory leaks.
     */
    @Scheduled(fixedDelayString = "${witty.consumer.mission.clean.period.ms:10000}")
    public void ttlExpired() {
        conflictMap.forEach((k, v) -> {
            if (v + missionTtl <= System.currentTimeMillis()) {
                conflictMap.remove(k);
            }
        });
    }
}

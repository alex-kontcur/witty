/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.service;

import com.witty.error.DuplicatedMissionIdException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IndempotenceService
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Service
public class IndempotenceService {

    @Value("${witty.consumer.mission.ttl.ms:86400000}")
    private Integer missionTtl;

    private Map<Integer, Long> conflictMap = new ConcurrentHashMap<>();

    public void checkIndempotence(Integer missionId) {
        Long current = conflictMap.putIfAbsent(missionId, System.currentTimeMillis());
        if (current != null) {
            conflictMap.put(missionId, System.currentTimeMillis());
            throw new DuplicatedMissionIdException("missionId = " + missionId + " has been used");
        }
    }

    @Scheduled(fixedDelayString = "${witty.consumer.mission.clean.period.ms:10000}")
    public void monitorExpired() {
        conflictMap.forEach((k, v) -> {
            if (v + missionTtl <= System.currentTimeMillis()) {
                conflictMap.remove(k);
            }
        });
    }
}

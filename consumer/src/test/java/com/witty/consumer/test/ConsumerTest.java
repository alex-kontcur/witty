/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.test;

import com.witty.api.v1.AmicableRequestDto;
import com.witty.api.v1.AmicableSumDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

/**
 * ConsumerTest - tests common scenarios of getting amicable sums
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class ConsumerTest extends AbstractMvcTest {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

    @Value("${witty.producer.seed.low.bound:1000}")
    private Integer seedLowBound;

    @Value("${witty.producer.seed.high.bound:20000}")
    private Integer seedHighBound;

    @Inject
    private RestTemplate restTemplate;

    private Random random = new SecureRandom();

    @Test
    public void amicableSumGettingErrorOnDuplicateMissionId() throws Exception {
        AmicableRequestDto amicableRequestDto = prepareAmicableRequest(1);
        AmicableSumDto sumDto = doPost("/messages", amicableRequestDto, AmicableSumDto.class);
        assertNotNull(sumDto);

        Exception exception = null;
        try {
            amicableRequestDto = prepareAmicableRequest(1);
            doPost("/messages", amicableRequestDto, AmicableSumDto.class);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    public void consumerControllerWorkingCorrectly() throws Exception {
        AmicableRequestDto amicableRequestDto = prepareAmicableRequest(random.nextInt());
        AmicableSumDto sumDto = doPost("/messages", amicableRequestDto, AmicableSumDto.class);
        assertNotNull(sumDto);
    }

    private AmicableRequestDto prepareAmicableRequest(int missionId) {
        AmicableRequestDto amicableRequestDto = new AmicableRequestDto();
        amicableRequestDto.setMissionId(missionId);
        amicableRequestDto.setSeed(random.nextInt(seedHighBound - seedLowBound) + seedLowBound);
        return amicableRequestDto;
    }


}

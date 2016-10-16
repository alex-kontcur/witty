package com.witty.consumer.test;

import com.witty.api.v1.AmicableRequestDto;
import com.witty.api.v1.AmicableSumDto;
import org.junit.Before;
import org.junit.Test;
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

    @Value("${witty.producer.seed.high.bound:20000}")
    private Integer seedHighBound;

    @Value("${witty.producer.seed.low.bound:1000}")
    private Integer seedLowBound;

    @Inject
    private RestTemplate restTemplate;

    private Random random;

    @Before
    public void init() {
        applyCodes(409);
        random = new SecureRandom();
    }

    @Test
    public void validationSupportedProperly() throws Exception {
        AmicableRequestDto amicableRequestDto = prepareAmicableRequest(null);
        Throwable exception = null;
        try {
            doPost("/messages", amicableRequestDto, AmicableSumDto.class);
        } catch (Throwable e) {
            exception = e;
        }
        assertNotNull(exception);
    }

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

    private AmicableRequestDto prepareAmicableRequest(Integer missionId) {
        AmicableRequestDto amicableRequestDto = new AmicableRequestDto();
        amicableRequestDto.setMissionId(missionId);
        amicableRequestDto.setSeed(random.nextInt(seedHighBound - seedLowBound) + seedLowBound);
        return amicableRequestDto;
    }


}

package com.witty.producer.service;

import com.google.gson.Gson;
import com.witty.api.v1.AmicableRequestDto;
import com.witty.api.v1.AmicableSumDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Random;

/**
 * ProducerService - sends messages via HTTP POST requests to "/messages" once every 3 seconds,
 * with a JSON object in the request body
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Inject
    private RestTemplate restTemplate;

    @Value("${witty.consumer.url}")
    private String url;

    @Value("${witty.producer.seed.high.bound:20000}")
    private Integer seedHighBound;

    @Value("${witty.producer.seed.low.bound:1000}")
    private Integer seedLowBound;

    private Random random = new SecureRandom();

    private Gson gson = new Gson();

    /**
     * Periodical task for sending AmicableRequestDto to consumer server
     */
    @Scheduled(fixedDelayString = "${witty.consumer.request.period.ms:3000}")
    public void requestTask() {
        try {
            AmicableRequestDto amicableRequestDto = new AmicableRequestDto();
            amicableRequestDto.setMissionId(random.nextInt());
            amicableRequestDto.setSeed(random.nextInt(seedHighBound - seedLowBound) + seedLowBound);
            AmicableSumDto amicableSumDto = getSum(amicableRequestDto);
            logger.info("Response sum for {} = {}", amicableRequestDto, amicableSumDto);
        } catch (Exception e) {
            logger.error("Error while requestTask ->", e);
        }
    }

    private AmicableSumDto getSum(AmicableRequestDto amicableRequestDto) {
        try {
            logger.info("");
            logger.info("Getting amicable sum for {}", amicableRequestDto);
            String json = gson.toJson(amicableRequestDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
            httpHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(json.length()));
            HttpEntity<AmicableRequestDto> entity = new HttpEntity<>(amicableRequestDto, httpHeaders);

            ResponseEntity<AmicableSumDto> response = restTemplate.exchange(url + "/messages", HttpMethod.POST, entity, AmicableSumDto.class);
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode.equals(HttpStatus.OK)) {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.error("Error while getSum -> ", e);
        }
        return null;
    }

}

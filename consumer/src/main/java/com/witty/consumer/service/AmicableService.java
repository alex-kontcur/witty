/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.service;

import com.witty.api.v1.AmicableRequestDto;
import com.witty.api.v1.AmicableSumDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * AmicableService
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Service
public class AmicableService {

    @Value("${witty.producer.seed.high.bound:20000}")
    private Integer seedHighBound;

    //todo - matrix ranges

    @Cacheable(value = "com.witty.api.v1.AmicableSumDto", unless = "#result == null")
    public AmicableSumDto calculateSum(AmicableRequestDto amicableRequestDto) {
        int sum = sumOfAmicablePairs(amicableRequestDto.getSeed());
        AmicableSumDto amicableSumDto = new AmicableSumDto();
        amicableSumDto.setCorrectAnswer(sum);
        return amicableSumDto;
    }

    private static int sumOfAmicablePairs(int upperLimit) {
        int answer = 0;
        for (int i = 1; i < upperLimit; i++) {
            int a = sumOfProperDivisors(i);
            int b = sumOfProperDivisors(a);
            if (a != b && i == b) {
                answer += a + b;
            }
        }
        return answer / 2;
    }

    public static int sumOfProperDivisors(int n) {
        int sum = 1;
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                sum += i + n / i;
                if (i * i == n) {
                    sum -= i;
                }
            }
        }
        return sum;
    }
}

/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.rest;

import com.witty.api.v1.AmicableRequestDto;
import com.witty.api.v1.AmicableSumDto;
import com.witty.consumer.service.AmicableService;
import com.witty.consumer.service.IndempotenceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * ConsumerController - REST endpoint of consumer with "/message" mapping. Receive http POST requests with the purpose
 * to calculate (or find in cache) amicable numbers sums.
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@RestController
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerController {

    @Inject
    private AmicableService amicableService;

    @Inject
    private IndempotenceService indempotenceService;

    @RequestMapping(method = RequestMethod.POST)
    public AmicableSumDto calculateSum(@Valid @RequestBody AmicableRequestDto amicableRequestDto) {
        indempotenceService.checkIndempotence(amicableRequestDto.getMissionId());
        return amicableService.calculateSum(amicableRequestDto);
    }

}

/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.service;

import com.witty.api.v1.AmicableRequestDto;
import com.witty.api.v1.AmicableSumDto;
import com.witty.consumer.range.SeedRange;
import com.witty.error.SeedRangeNotFoundException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

/**
 * AmicableService - application service for processing AmicableRequestDto. It maps dto's seed value to prepared
 * SeedRange object and pass that SeedRange object to MatrixProcessor
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Service
@DependsOn("matrixProcessor")
public class AmicableService {

    @Inject
    private MatrixProcessor matrixProcessor;

    private Collection<SeedRange> ranges;

    @PostConstruct
    public void init() {
        ranges = matrixProcessor.prepareRanges();
    }

    /**
     * Form AmicableSumDto response
     *
     * @param amicableRequestDto - request dto
     * @return AmicableSumDto
     */
    public AmicableSumDto calculateSum(AmicableRequestDto amicableRequestDto) {
        Integer seed = amicableRequestDto.getSeed();
        SeedRange seedRange = findRange(seed);
        if (seedRange != null) {
            seedRange.setSeed(seed);
            return AmicableSumDto.valueOf(matrixProcessor.calcAmicableSum(seedRange));
        }
        throw new SeedRangeNotFoundException("No amicable range found for seed = " + seed);
    }

    private SeedRange findRange(Integer seed) {
        return ranges.stream().parallel().filter(seedRange -> seedRange.seedMatched(seed)).findFirst().orElse(null);
    }

}

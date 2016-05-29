/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.service;

import com.witty.consumer.CacheNames;
import com.witty.consumer.range.SeedRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * MatrixProcessor - matrix implemetation of calculating amicable numbers under given seed.
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Component
public class MatrixProcessor {

    @Value("${witty.producer.seed.high.bound:20000}")
    private Integer seedHighBound;

    /**
     * Calculate sum of amicable numbers under given upper level seed and puts the result into the cache using range
     * of numbers. This range corresponse to all seeds within range, so we don't need to utilize CPU time
     * to calculate sums again
     *
     * @param seedRange - object contaiting given seed and range which is a key for cache of already calculated sums
     * @return sum
     */
    @Cacheable(CacheNames.SUMS_CACHE)
    public Long calcAmicableSum(SeedRange seedRange) {
        int[][] matrix = getMatrix(seedRange.getSeed());
        long sum = 0;
        for (int[] aMatrix : matrix) {
            sum += aMatrix[0];
            sum += aMatrix[1];
        }
        return sum;
    }

    /**
     * Prepares SeedRange objects using the following algorithm:
     *  - find all amicable number pairs under upper threshold from settings (20000 from requirements)
     *  - use max numbers from those pairs (pMax)
     *  - create ranges 0 .. pMax1, pMax1 + 1 .. pMax2, ... , pMaxN + 1 .. upperThreshold
     *
     * @return set of ranges
     */
    public Set<SeedRange> prepareRanges() {
        Set<SeedRange> set = new TreeSet<>();

        Set<Integer> upperSet = new TreeSet<>();
        int[][] matrix = getMatrix(seedHighBound);
        long sum = 0;
        for (int[] aMatrix : matrix) {
            sum += aMatrix[0];
            sum += aMatrix[1];
            upperSet.add(aMatrix[0]);
        }

        List<Integer> list = new ArrayList<>(upperSet);
        int size = list.size();
        if (size > 0) {
            set.add(new SeedRange(0, list.get(0)));
            if (size > 1) {
                for (int i = 1; i < size; i++) {
                    set.add(new SeedRange(list.get(i - 1) + 1, list.get(i)));
                }
            }
            set.add(new SeedRange(list.get(size - 1) + 1, seedHighBound));
        }
        return set;
    }

    private static int sumFactors(int n) {
        int sum = 0;
        for (int div = 1; div <= n / 2; div++) {
            if (n % div == 0) {
                sum += div;
            }
        }
        return sum;
    }

    private static int[][] getMatrix(int limit) {
        int[] array = new int[limit];
        for (int i = 2; i < limit; i++) {
            array[i] = sumFactors(i);
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 2; i < limit; i++) {
            int j = array[i];
            if (j < i && i == array[j]) {
                map.put(i, j);
            }
            // Check 'j < i' in order to:
            // 1. Avoid an illegal index when 'j >= limit'
            // 2. Avoid the insertion of the equivalent pair [j,i]
            // 3. Avoid the insertion of perfect numbers such as [6,6]
        }

        int[][] matrix = new int[map.size()][2];
        int index = 0;
        for (int key : map.keySet()) {
            matrix[index][0] = key;
            matrix[index][1] = map.get(key);
            index++;
        }
        return matrix;
    }
}

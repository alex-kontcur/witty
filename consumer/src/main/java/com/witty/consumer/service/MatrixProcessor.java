/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * MatrixProcessor
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Component
public class MatrixProcessor {

    public SortedMap<Integer, Long> prepareSumThresholds(int upperBound) {
        SortedMap<Integer, Long> map = new TreeMap<>();

        int[][] matrix = getMatrix(upperBound);
        long sum = 0;
        for (int[] mx : matrix) {
            sum += mx[0];
            sum += mx[1];
            map.put(mx[0], sum);
        }
        return map;
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

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
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

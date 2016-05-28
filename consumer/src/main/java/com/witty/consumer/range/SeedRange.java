/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.range;

import java.util.Objects;

/**
 * SeedRange
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class SeedRange implements Comparable<SeedRange> {

    private Integer lowerBound;
    private Integer upperBound;
    private Integer seed;

    public SeedRange(Integer lowerBound, Integer upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public boolean seedMatched(Integer seed) {
        if (lowerBound == null || upperBound == null) {
            throw new IllegalStateException("SeedRange corrupted");
        }
        if (seed == null) {
            throw new IllegalArgumentException("Seed invalid");
        }
        return seed >= lowerBound && seed <= upperBound;
    }

    @Override
    public int compareTo(SeedRange o) {
        return upperBound.compareTo(o.upperBound);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeedRange seedRange = (SeedRange) o;
        return Objects.equals(lowerBound, seedRange.lowerBound) &&
            Objects.equals(upperBound, seedRange.upperBound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound);
    }
}

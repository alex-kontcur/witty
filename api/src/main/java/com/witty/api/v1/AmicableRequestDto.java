/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.api.v1;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

/**
 * AmicableRequestDto
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class AmicableRequestDto {

    @NotNull
    private Integer missionId;

    @NotNull
    private Integer seed;

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AmicableRequestDto that = (AmicableRequestDto) o;
        return Objects.equals(seed, that.seed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seed);
    }

    @Override
    public String toString() {
        return "AmicableRequestDto" + "{missionId=" + missionId + ", seed=" + seed + '}';
    }
}

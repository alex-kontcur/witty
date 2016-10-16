package com.witty.api.v1;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * AmicableRequestDto - request object used by producer server while getting amicable numbers sums
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class AmicableRequestDto {

    /**
     * An unique identifier used to ensure indempotence. Every time the app server sends a request,
     * a new missionId should be generated and used.
     */
    @NotNull
    private Integer missionId;

    /**
     * A positive integer used to seed the algorithm to be executed. This should be a random
     * integer between 1,000 and 20,000
     */
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

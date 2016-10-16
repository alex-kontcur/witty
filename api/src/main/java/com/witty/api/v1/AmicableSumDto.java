package com.witty.api.v1;

/**
 * AmicableSumDto - response object used by consumer server while responding with amicable numbers sums
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class AmicableSumDto {

    /**
     * Correct answer to the question posed in Project Euler Problem 21
     */
    private Long answer;

    public AmicableSumDto(Long answer) {
        this.answer = answer;
    }

    public Long getAnswer() {
        return answer;
    }

    public static AmicableSumDto valueOf(Long answer) {
        return new AmicableSumDto(answer);
    }

    @Override
    public String toString() {
        return "AmicableSumDto" + "{answer=" + answer + '}';
    }
}

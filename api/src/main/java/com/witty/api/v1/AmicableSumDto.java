/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.api.v1;

/**
 * AmicableSumDto
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class AmicableSumDto {

    private Long answer;

    public Long getAnswer() {
        return answer;
    }

    public void setAnswer(Long answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AmicableSumDto" + "{answer=" + answer + '}';
    }
}

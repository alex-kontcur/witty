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

    private Integer correctAnswer;

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "AmicableSumDto" + "{correctAnswer=" + correctAnswer + '}';
    }
}

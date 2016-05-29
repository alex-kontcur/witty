/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.error;

/**
 * SeedRangeNotFoundException - thrown if no appropriate SeedRange found for given seed
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class SeedRangeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -866763957613632876L;

    public SeedRangeNotFoundException() {
    }

    public SeedRangeNotFoundException(String message) {
        super(message);
    }
}

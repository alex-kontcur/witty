/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer.test;

/**
 * StatusHandler
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public interface StatusHandler {

    boolean statusAccepted(int status);

    void handle(String response);

}

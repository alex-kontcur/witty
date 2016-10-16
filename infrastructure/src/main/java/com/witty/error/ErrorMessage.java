package com.witty.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ErrorMessage - json oriented object for providing human error message to client
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class ErrorMessage {

    private List<String> errors;

    public ErrorMessage(List<String> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public ErrorMessage(String error) {
        this(Collections.singletonList(error));
    }

    public ErrorMessage(String ... errors) {
        this(Arrays.asList(errors));
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
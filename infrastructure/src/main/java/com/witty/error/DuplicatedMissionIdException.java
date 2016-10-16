package com.witty.error;

/**
 * DuplicateMissionIdException - thrown if missionId has already been received
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class DuplicatedMissionIdException extends RuntimeException {

    private static final long serialVersionUID = -4355016662784478218L;

    public DuplicatedMissionIdException() {
    }

    public DuplicatedMissionIdException(String message) {
        super(message);
    }
}

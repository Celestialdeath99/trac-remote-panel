package net.celestialdata.panel;

import java.time.Duration;
import java.time.Instant;

public class States {
    private static boolean playStructureState = false;
    private static boolean lazyRiverState = false;
    private static boolean riverFountainsState = false;
    private static boolean centerJetsState = false;
    private static boolean yellowSlideState = false;
    private static boolean blueSlideState = false;
    private static boolean emergencyStopState = false;
    private static Instant lastPingTime = Instant.now();
    private static boolean oldState;

    public static boolean isPlayStructureOn() {
        return playStructureState;
    }

    public static boolean isLazyRiverOn() {
        return lazyRiverState;
    }

    public static boolean isRiverFountainsOn() {
        return riverFountainsState;
    }

    public static boolean isCenterJetsOn() {
        return centerJetsState;
    }

    public static boolean isYellowSlideOn() {
        return yellowSlideState;
    }

    public static boolean isBlueSlideOn() {
        return blueSlideState;
    }

    public static boolean isEmergencyStopOn() {
        return emergencyStopState;
    }

    public static void setPlayStructure(boolean newState) {
        oldState = playStructureState;
        playStructureState = newState;

        if (oldState != playStructureState) {
            Logger.useLog("\n  - Feature: Play Structure\n  - New State: " + playStructureState + "\n\n");
        }
    }

    public static void setLazyRiver(boolean newState) {
        oldState = lazyRiverState;
        lazyRiverState = newState;

        if (oldState != lazyRiverState) {
            Logger.useLog("\n  - Feature: Lazy River\n  - New State: " + lazyRiverState + "\n\n");
        }
    }

    public static void setRiverFountains(boolean newState) {
        oldState = riverFountainsState;
        riverFountainsState = newState;

        if (oldState != riverFountainsState) {
            Logger.useLog("\n  - Feature: River Fountains\n  - New State: " + riverFountainsState + "\n\n");
        }
    }

    public static void setCenterJets(boolean newState) {
        oldState = centerJetsState;
        centerJetsState = newState;

        if (oldState != centerJetsState) {
            Logger.useLog("\n  - Feature: Center Jets\n  - New State: " + centerJetsState + "\n\n");
        }
    }

    public static void setYellowSlide(boolean newState) {
        oldState = yellowSlideState;
        yellowSlideState = newState;

        if (oldState != yellowSlideState) {
            Logger.useLog("\n  - Feature: Yellow Slide\n  - New State: " + yellowSlideState + "\n\n");
        }
    }

    public static void setBlueSlide(boolean newState) {
        oldState = blueSlideState;
        blueSlideState = newState;

        if (oldState != blueSlideState) {
            Logger.useLog("\n  - Feature: Blue Slide\n  - New State: " + blueSlideState + "\n\n");
        }
    }

    public static void setEmergencyStop(boolean newState) {
        oldState = emergencyStopState;
        emergencyStopState = newState;

        if (oldState != emergencyStopState) {
            Logger.useLog("\n  - Feature: Emergency Stop\n  - New State: " + emergencyStopState + "\n\n");
        }
    }

    public static boolean areAllFeaturesOff() {
        return !playStructureState && !lazyRiverState && !riverFountainsState && !centerJetsState && !yellowSlideState && !blueSlideState;
    }

    static void setLastPingTime() {
        lastPingTime = Instant.now();
    }

    public static long getTimeSinceLastPing() {
        return Duration.between(lastPingTime, Instant.now()).toMillis();
    }
}

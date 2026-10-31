package com.example.devicemanager;

/**
 * Pure-Java validation rules (no Android classes), so they can be unit-tested on the JVM.
 *
 * To change the battery rule (for example the instructor asks for an upper bound
 * of 150), change MAX_BATTERY in this one place; the screen and the tests follow.
 */
public final class DeviceValidator {

    public static final int MIN_BATTERY = 0;
    public static final int MAX_BATTERY = 100;

    private DeviceValidator() {
    }

    /** A device name is valid when it is not null and not blank after trimming. */
    public static boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Converts text to a whole number.
     *
     * @return the number, or null when the text is empty or not a whole number
     */
    public static Integer parseBattery(String text) {
        if (text == null) {
            return null;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /** Range check, kept separate from parsing so each failure has its own message. */
    public static boolean isBatteryInRange(int battery) {
        return battery >= MIN_BATTERY && battery <= MAX_BATTERY;
    }
}

package com.example.devicemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Local JVM tests for the validation rules used by EditDeviceActivity.
 * Command:  .\gradlew.bat testDebugUnitTest
 *
 * If MAX_BATTERY is changed from 100 to 150, batteryAboveMaximumIsRejected fails -
 * that is the "affected test" for the instructor-requested change.
 */
public class DeviceValidatorTest {

    @Test
    public void blankOrMissingNameIsRejected() {
        assertFalse(DeviceValidator.isNameValid(null));
        assertFalse(DeviceValidator.isNameValid(""));
        assertFalse(DeviceValidator.isNameValid("   "));
    }

    @Test
    public void normalNameIsAccepted() {
        assertTrue(DeviceValidator.isNameValid("Sensor-A"));
        assertTrue(DeviceValidator.isNameValid("  Phone B  "));
    }

    @Test
    public void batteryTextThatIsNotAWholeNumberReturnsNull() {
        assertNull(DeviceValidator.parseBattery(null));
        assertNull(DeviceValidator.parseBattery(""));
        assertNull(DeviceValidator.parseBattery("abc"));
        assertNull(DeviceValidator.parseBattery("12.5"));
    }

    @Test
    public void batteryTextIsParsedAndTrimmed() {
        assertEquals(Integer.valueOf(80), DeviceValidator.parseBattery("80"));
        assertEquals(Integer.valueOf(15), DeviceValidator.parseBattery(" 15 "));
    }

    @Test
    public void batteryBoundariesAreAccepted() {
        assertTrue(DeviceValidator.isBatteryInRange(0));
        assertTrue(DeviceValidator.isBatteryInRange(100));
    }

    @Test
    public void batteryBelowMinimumIsRejected() {
        assertFalse(DeviceValidator.isBatteryInRange(-1));
    }

    @Test
    public void batteryAboveMaximumIsRejected() {
        assertFalse(DeviceValidator.isBatteryInRange(101));
        assertFalse(DeviceValidator.isBatteryInRange(120));
    }
}

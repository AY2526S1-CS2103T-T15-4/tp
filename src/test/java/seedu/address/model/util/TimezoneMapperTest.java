package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class TimezoneMapperTest {

    @Test
    void getZoneIdFromCountry_shouldReturnCorrectZone() {
        //valid
        assertEquals(ZoneId.of("Asia/Singapore"), TimezoneMapper.getZoneIdFromCountry("Singapore"));
        assertEquals(ZoneId.of("Europe/London"), TimezoneMapper.getZoneIdFromCountry("United Kingdom"));
        assertEquals(ZoneId.of("America/New_York"), TimezoneMapper.getZoneIdFromCountry("United States"));
        assertEquals(ZoneId.of("Asia/Tokyo"), TimezoneMapper.getZoneIdFromCountry("jApAn"));

        //invalid
        assertNull(TimezoneMapper.getZoneIdFromCountry("UnknownLand"));
        assertNull(TimezoneMapper.getZoneIdFromCountry(null));
        assertNull(TimezoneMapper.getZoneIdFromCountry(""));
        assertNull(TimezoneMapper.getZoneIdFromCountry("   "));
    }

    @Test
    public void getZoneIdFromCountry_whitespaceHandling() {
        assertNotNull(TimezoneMapper.getZoneIdFromCountry("  singapore  "));
        assertNull(TimezoneMapper.getZoneIdFromCountry("\t\n"));
    }

    @Test
    public void getZoneIdFromCountry_mixedCase() {
        assertEquals(ZoneId.of("Asia/Singapore"),
                TimezoneMapper.getZoneIdFromCountry("SiNgApOrE"));
    }

    @Test
    void getSupportedCountries_shouldReturnNonEmptySet() {
        Set<String> supported = TimezoneMapper.getSupportedCountries();
        assertNotNull(supported);
        assertTrue(supported.contains("singapore"));
        assertTrue(supported.contains("united states"));
        assertTrue(supported.contains("france"));
        assertFalse(supported.contains("unknownland"));
    }

    @Test
    void isCountrySupported_shouldReturnTrueForSupportedAndFalseForUnsupported() {
        assertTrue(TimezoneMapper.isCountrySupported("Singapore"));
        assertTrue(TimezoneMapper.isCountrySupported("united states"));
        assertTrue(TimezoneMapper.isCountrySupported("FRANCE"));

        //invalid
        assertFalse(TimezoneMapper.isCountrySupported(null));
        assertFalse(TimezoneMapper.isCountrySupported(""));
        assertFalse(TimezoneMapper.isCountrySupported("   "));
        assertFalse(TimezoneMapper.isCountrySupported("Atlantis"));
    }
}

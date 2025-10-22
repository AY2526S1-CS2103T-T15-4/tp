package seedu.address.model.util;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Maps country names to their timezones.
 */
public class TimezoneMapper {

    private static final Map<String, ZoneId> COUNTRY_TO_ZONE = new HashMap<>();

    static {
        //Asia
        COUNTRY_TO_ZONE.put("singapore", ZoneId.of("Asia/Singapore"));
        COUNTRY_TO_ZONE.put("india", ZoneId.of("Asia/Kolkata"));
        COUNTRY_TO_ZONE.put("japan", ZoneId.of("Asia/Tokyo"));
        COUNTRY_TO_ZONE.put("china", ZoneId.of("Asia/Shanghai"));
        COUNTRY_TO_ZONE.put("south korea", ZoneId.of("Asia/Seoul"));
        COUNTRY_TO_ZONE.put("uae", ZoneId.of("Asia/Dubai"));

        //Europe
        COUNTRY_TO_ZONE.put("france", ZoneId.of("Europe/Paris"));
        COUNTRY_TO_ZONE.put("germany", ZoneId.of("Europe/Berlin"));
        COUNTRY_TO_ZONE.put("united kingdom", ZoneId.of("Europe/London"));
        COUNTRY_TO_ZONE.put("spain", ZoneId.of("Europe/Madrid"));
        COUNTRY_TO_ZONE.put("italy", ZoneId.of("Europe/Rome"));
        COUNTRY_TO_ZONE.put("netherlands", ZoneId.of("Europe/Amsterdam"));
        COUNTRY_TO_ZONE.put("sweden", ZoneId.of("Europe/Stockholm"));
        COUNTRY_TO_ZONE.put("norway", ZoneId.of("Europe/Oslo"));

        //Americas
        COUNTRY_TO_ZONE.put("united states", ZoneId.of("America/New_York"));
        COUNTRY_TO_ZONE.put("canada", ZoneId.of("America/Toronto"));
        COUNTRY_TO_ZONE.put("brazil", ZoneId.of("America/Sao_Paulo"));
        COUNTRY_TO_ZONE.put("mexico", ZoneId.of("America/Mexico_City"));
        COUNTRY_TO_ZONE.put("argentina", ZoneId.of("America/Buenos_Aires"));

        //Oceania
        COUNTRY_TO_ZONE.put("australia", ZoneId.of("Australia/Sydney"));

        //Africa
        COUNTRY_TO_ZONE.put("south africa", ZoneId.of("Africa/Johannesburg"));
        COUNTRY_TO_ZONE.put("egypt", ZoneId.of("Africa/Cairo"));
        COUNTRY_TO_ZONE.put("kenya", ZoneId.of("Africa/Nairobi"));
        COUNTRY_TO_ZONE.put("nigeria", ZoneId.of("Africa/Lagos"));
    }

    /**
     * Gets primary ZoneId for country input.
     * @param country country name
     * @return ZoneId if recognized country, null otherwise
     */
    public static ZoneId getZoneIdFromCountry(String country) {
        assert country != null : "Country cannot be null.";
        if (country.isBlank()) {
            return null;
        }
        return COUNTRY_TO_ZONE.get(country.trim().toLowerCase());
    }

    /**
     * Returns all supported countries
     * @return set of country names
     */
    public static Set<String> getSupportedCountries() {
        return COUNTRY_TO_ZONE.keySet();
    }

    /**
     * Checks for supported country.
     * @param country country name
     * @return true if country is supported, false otherwise.
     */
    public static boolean isCountrySupported(String country) {
        assert country != null : "Country cannot be null.";
        if (country.isBlank()) {
            return false;
        }
        return COUNTRY_TO_ZONE.containsKey(country.trim().toLowerCase());
    }
}

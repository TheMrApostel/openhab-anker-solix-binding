/**
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.ankersolix.internal.mqtt;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Field definitions for A17C5 (Solarbank 3 Pro) MQTT message type 0x0405.
 * Ported from the Python anker-solix-api mqttmap.py.
 *
 * Fields are keyed by their hex tag byte (e.g., 0xA2 = "device_sn").
 * Each entry contains the field name and optional metadata (signed, factor, bitmap masks).
 */
@NonNullByDefault
public final class SolarbankFieldMap {

    /**
     * Represents a field definition in the MQTT binary protocol.
     */
    public static class FieldDef {
        public final String name;
        public final boolean signed;
        public final double factor;
        public final @Nullable Map<String, Integer> bitmasks;

        public FieldDef(String name) {
            this(name, false, 1.0, null);
        }

        public FieldDef(String name, boolean signed) {
            this(name, signed, 1.0, null);
        }

        public FieldDef(String name, boolean signed, double factor) {
            this(name, signed, factor, null);
        }

        public FieldDef(String name, boolean signed, double factor, @Nullable Map<String, Integer> bitmasks) {
            this.name = name;
            this.signed = signed;
            this.factor = factor;
            this.bitmasks = bitmasks;
        }
    }

    /**
     * Field map for A17C5 message type 0x0405 (param_info telemetry).
     * Key = field tag byte (unsigned), Value = field definition.
     */
    private static final Map<Integer, FieldDef> A17C5_0405;
    private static final Map<Integer, FieldDef> A17C1_0405;

    static {
        Map<Integer, FieldDef> map = new LinkedHashMap<>();

        map.put(0xA2, new FieldDef("device_sn"));
        map.put(0xA3, new FieldDef("main_battery_soc"));
        map.put(0xA5, new FieldDef("temperature", true, 0.1));
        map.put(0xA6, new FieldDef("battery_soc"));
        map.put(0xA7, new FieldDef("sw_version"));
        map.put(0xA8, new FieldDef("sw_controller"));
        map.put(0xA9, new FieldDef("sw_expansion"));
        map.put(0xAB, new FieldDef("photovoltaic_power"));
        map.put(0xAC, new FieldDef("battery_power_signed", true));
        map.put(0xAD, new FieldDef("output_power"));
        map.put(0xAE, new FieldDef("ac_output_power_signed", true));
        map.put(0xB0, new FieldDef("pv_yield"));
        map.put(0xB1, new FieldDef("charged_energy"));
        map.put(0xB2, new FieldDef("discharged_energy"));
        map.put(0xB3, new FieldDef("output_energy"));
        map.put(0xB4, new FieldDef("consumed_energy"));
        map.put(0xB5, new FieldDef("min_soc"));
        map.put(0xB6, new FieldDef("min_soc_exp_1"));
        map.put(0xB7, new FieldDef("min_soc_exp_2"));
        map.put(0xB8, new FieldDef("usage_mode"));
        map.put(0xB9, new FieldDef("home_load_preset"));

        // Bitmap field ba: light_mode, light_off_switch, ac_socket_switch, temp_unit_fahrenheit
        Map<String, Integer> baBitmasks = new LinkedHashMap<>();
        baBitmasks.put("light_mode", 0x40);        // bit 6
        baBitmasks.put("light_off_switch", 0x20);   // bit 5
        baBitmasks.put("ac_socket_switch", 0x08);    // bit 3
        baBitmasks.put("temp_unit_fahrenheit", 0x01); // bit 0
        map.put(0xBA, new FieldDef("bitmap_ba", false, 1.0, baBitmasks));

        map.put(0xBB, new FieldDef("heating_power"));
        map.put(0xBC, new FieldDef("grid_to_battery_power"));
        map.put(0xBD, new FieldDef("max_load"));
        map.put(0xBE, new FieldDef("max_load_legal"));
        map.put(0xBF, new FieldDef("timestamp_backup_start"));
        map.put(0xC0, new FieldDef("timestamp_backup_end"));
        map.put(0xC2, new FieldDef("photovoltaic_power_2"));
        map.put(0xC4, new FieldDef("grid_power_signed", true));
        map.put(0xC5, new FieldDef("home_demand"));
        map.put(0xC6, new FieldDef("pv_1_power"));
        map.put(0xC7, new FieldDef("pv_2_power"));
        map.put(0xC8, new FieldDef("pv_3_power"));
        map.put(0xC9, new FieldDef("pv_4_power"));
        map.put(0xCB, new FieldDef("expansion_packs"));
        map.put(0xD4, new FieldDef("device_timeout_minutes", false, 30.0));
        map.put(0xD5, new FieldDef("pv_limit"));
        map.put(0xD6, new FieldDef("ac_input_limit"));

        // Bitmap field fb: grid_export_disabled
        Map<String, Integer> fbBitmasks = new LinkedHashMap<>();
        fbBitmasks.put("grid_export_disabled", 0x01); // bit 0
        map.put(0xFB, new FieldDef("bitmap_fb", false, 1.0, fbBitmasks));

        map.put(0xFE, new FieldDef("msg_timestamp"));

        A17C5_0405 = Collections.unmodifiableMap(map);

        map = new LinkedHashMap<>();
        map.put(0xA2, new FieldDef("device_sn"));
        map.put(0xA3, new FieldDef("main_battery_soc")); // controller battery only
        map.put(0xA5, new FieldDef("error_code"));
        map.put(0xA6, new FieldDef("sw_version"));
        map.put(0xA7, new FieldDef("sw_controller"));
        map.put(0xA8, new FieldDef("sw_expansion"));
        map.put(0xA9, new FieldDef("temp_unit_fahrenheit"));
        map.put(0xAA, new FieldDef("temperature", true));
        map.put(0xAB, new FieldDef("photovoltaic_power", false, 0.1));
        map.put(0xAC, new FieldDef("ac_output_power", false, 0.1));
        map.put(0xAD, new FieldDef("battery_soc")); // controller + expansions avg
        map.put(0xB0, new FieldDef("bat_charge_power", false, 0.01));
        map.put(0xB1, new FieldDef("pv_yield", false, 0.0001));
        map.put(0xB3, new FieldDef("output_energy", false, 0.0001));
        map.put(0xB2, new FieldDef("charged_energy", false, 0.00001));
        map.put(0xB4, new FieldDef("output_cutoff_data"));
        map.put(0xB5, new FieldDef("lowpower_input_data"));
        map.put(0xB6, new FieldDef("input_cutoff_data"));
        map.put(0xB7, new FieldDef("bat_discharge_power", false, 0.01));
        map.put(0xBC, new FieldDef("grid_to_home_power", false, 0.1));
        map.put(0xBD, new FieldDef("pv_to_grid_power", false, 0.1));
        map.put(0xBE, new FieldDef("grid_import_energy", false, 0.0001));
        map.put(0xBF, new FieldDef("grid_export_energy", false, 0.0001));
        map.put(0xC2, new FieldDef("max_load"));
        map.put(0xC4, new FieldDef("home_demand", false, 0.1));
        map.put(0xC6, new FieldDef("usage_mode"));
        map.put(0xC7, new FieldDef("home_load_preset"));
        map.put(0xC8, new FieldDef("ac_socket_power", false, 0.1));
        map.put(0xC9, new FieldDef("consumed_energy", false, 0.0001));
        map.put(0xCA, new FieldDef("pv_1_power", false, 0.1));
        map.put(0xCB, new FieldDef("pv_2_power", false, 0.1));
        map.put(0xCC, new FieldDef("pv_3_power", false, 0.1));
        map.put(0xCD, new FieldDef("pv_4_power", false, 0.1));
        map.put(0xD2, new FieldDef("light_mode")); // Normal mode (0) or Mood mode (1)
        map.put(0xD3, new FieldDef("output_power", false, 0.1));
        map.put(0xE0, new FieldDef("grid_status")); // Grid OK (1), No grid (6), Grid connecting (3)
        map.put(0xE1, new FieldDef("light_off_switch")); // Light on (0), Light off (1)
        map.put(0xE8, new FieldDef("battery_heating")); // Not heating (1), heating (3)

        // Bitmap field fb: grid_export_disabled
        Map<String, Integer> fbBitmasks_a17c1 = new LinkedHashMap<>();
        fbBitmasks_a17c1.put("grid_export_disabled", 0x01); // bit 0
        map.put(0xFB, new FieldDef("bitmap_fb", false, 1.0, fbBitmasks_a17c1));

        map.put(0xFE, new FieldDef("msg_timestamp"));

        A17C1_0405 = Collections.unmodifiableMap(map);
    }

    public static Map<Integer, FieldDef> getFieldMap(String productCode, int messageType) {
        return switch (productCode) {
            case "A17C1" -> {
                if (messageType == 0x0405) {
                    yield A17C1_0405;
                }
                yield Collections.emptyMap();
            }
            case "A17C5" -> {
                if (messageType == 0x0405) {
                    yield A17C5_0405;
                }
                yield Collections.emptyMap();
            }
            default -> Collections.emptyMap();
        };
    }

    private SolarbankFieldMap() {
    }
}

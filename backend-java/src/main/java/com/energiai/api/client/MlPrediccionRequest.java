package com.energiai.api.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MlPrediccionRequest {

    @JsonProperty("household_size")
    private Integer householdSize;

    @JsonProperty("has_ac")
    private Integer hasAc;

    @JsonProperty("home_office")
    private Boolean homeOffice;

    @JsonProperty("housing_type")
    private String housingType;

    @JsonProperty("equipment_count")
    private Integer equipmentCount;

    @JsonProperty("avg_energy_consumption_kwh")
    private Double avgEnergyConsumptionKwh;

    @JsonProperty("peak_usage_level")
    private String peakUsageLevel;
}
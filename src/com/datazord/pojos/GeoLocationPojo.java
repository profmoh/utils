package com.datazord.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeoLocationPojo {

	private String countryCode;
	private String countryName;
	private String cityName;
	private String postalCode;
	private String state;

}

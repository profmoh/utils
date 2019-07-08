package com.datazord;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.datazord.pojos.GeoLocationPojo;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class GeoLiteUtils {

	private static String BASE_PATH = System.getProperty("user.dir") + File.separator;

	public static GeoLocationPojo getLocation(String ipAddress) {

		System.out.println(BASE_PATH);

		try {
			File file = new File(BASE_PATH + "GeoLite2-City.mmdb");

			return getLocation(ipAddress, file);
		} catch (Exception e) {
			e.printStackTrace();

			return new GeoLocationPojo("UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED");
		}
	}

	private static GeoLocationPojo getLocation(String ip, File database) throws IOException, GeoIp2Exception, UnknownHostException {

		DatabaseReader dbReader = new DatabaseReader.Builder(database).build();

		InetAddress ipAddress = InetAddress.getByName(ip);
		CityResponse response = dbReader.city(ipAddress);

		String countryCode = response.getCountry().getIsoCode();
		String countryName = response.getCountry().getName();
		String cityName = response.getCity().getName();
		String postalCode = response.getPostal().getCode();
		String state = response.getLeastSpecificSubdivision().getName();

		return new GeoLocationPojo(countryCode, countryName, cityName, postalCode, state);
	}
}

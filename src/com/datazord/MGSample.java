package com.datazord;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MGSample {

	public static ClientResponse sendSimpleMessage(String baseUrl, String apiKey,
			String domainName, String from, String email, String subject, String bodyType, String body) {

		Client client = Client.create();

		client.addFilter(new HTTPBasicAuthFilter("api", apiKey));

		WebResource webResource = client.resource(baseUrl + domainName + "/messages");

		MultivaluedMapImpl formData = new MultivaluedMapImpl();

		formData.add("from", from);
		formData.add("to", email);
		formData.add("subject", subject);
		formData.add(bodyType, body);

		return webResource
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, formData);
	}
}

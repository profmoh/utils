package com.datazord.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePojo {

	public Long iat;
	public Long exp;

	public String iss;
	public String azp;
	public String aud;
	public String sub;
	public String alg;
	public String kid;
	public String typ;

	public String name;
	public String email;
	public String locale;
	public String at_hash;
	public String picture;
	public String given_name;
	public String family_name;

	public Boolean email_verified;

}

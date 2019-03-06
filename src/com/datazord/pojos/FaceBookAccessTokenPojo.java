package com.datazord.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FaceBookAccessTokenPojo {
	
	public String access_token;
	public String token_type;
	public Long expires_in;

}

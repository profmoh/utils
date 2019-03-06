package com.datazord.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleFirebasePojo {

	private String uid;
	private String name;
	private String email;
	private String picutre;
}

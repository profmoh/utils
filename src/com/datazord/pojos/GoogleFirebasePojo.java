package com.datazord.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoogleFirebasePojo {

	private String uid;
	private String name;
	private String email;
	private String picutre;
}

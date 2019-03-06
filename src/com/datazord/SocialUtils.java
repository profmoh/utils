package com.datazord;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;

import com.datazord.pojos.FaceBookAccessTokenPojo;
import com.datazord.pojos.FaceBookPojo;
import com.datazord.pojos.GoogleFirebasePojo;
import com.datazord.pojos.GooglePojo;
import com.datazord.pojos.ResponseDetails;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

public class SocialUtils {
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
//	public static void main(String[] args) {
////		try {
////			decodeGoogleFirebaseToken(
////				"https://arabee-1540800611721.firebaseio.com",
////				"eyJhbGciOiJSUzI1NiIsImtpZCI6IjY1ZjRhZmFjNjExMjlmMTBjOTk5MTU1ZmE1ODZkZWU2MGE3MTM3MmIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXJhYmVlLTE1NDA4MDA2MTE3MjEiLCJuYW1lIjoiTW9oYW1lZCBNYWdkeSIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLTZpWGhOTnk5NXB3L0FBQUFBQUFBQUFJL0FBQUFBQUFBQ2JJL3Fnb3lUT0ZtVTBNL3Bob3RvLmpwZyIsImF1ZCI6ImFyYWJlZS0xNTQwODAwNjExNzIxIiwiYXV0aF90aW1lIjoxNTQzNDc5NTkwLCJ1c2VyX2lkIjoiYzk2MXFaVzBxblNqbW5NcGFka084T0tLQzg2MiIsInN1YiI6ImM5NjFxWlcwcW5Tam1uTXBhZGtPOE9LS0M4NjIiLCJpYXQiOjE1NDM0Nzk1OTEsImV4cCI6MTU0MzQ4MzE5MSwiZW1haWwiOiJwcm9mLm1vaGFtZWRtYWdkeUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjEwODMxOTc5Mzg3MDc3ODQxMjMzMiJdLCJlbWFpbCI6WyJwcm9mLm1vaGFtZWRtYWdkeUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.UvWWxcDcrknmuX6EZmOPuaEyWlcCWefe11_lOVRQbz_vIIXbjtVLxSmQxhuRXD53ZrhO-Ot-bQSJHkOC_RimoVX_J2rplXILzl_wNHOPJudxAylLxF7f1RTKZTgxcLDetctNvL67VYEy7B6FWW33hW0WK7S9YNsIQNdyif7GXVCKLADq-ZLZ68xy31VdfCrVlVDaPuKZj4o0zmcYqGSXvfNNQL1ODBqas9lYFXjwSIBBwMDvW9fkkyJ8LB0SzvXxp3Z9UCwkhk9LkkRLY_LDBZL_XE802AfyExO8RAKSirD1Tk14GXJSnm6nkeiVIVjbVOXeR8aI_lKHDFIV9nziVQ",
////				GoogleCredentials.fromStream(new FileInputStream(new File("D:\\googleCredentials.json"))));
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		try {
////			decodeGoogleToken("4/pAAyAfWkACI1WX6xSzH6RWR2Xwts9ylG9umAZ4Z0fMzirvpJgp7cv_kVtn9ca642dstsSNllfTTYyE0QqWMBStA",
////					"https://www.googleapis.com/oauth2/v4/token", "858709699984-knuaosrobj7n96vm5lnmtrrb5eqggjdg.apps.googleusercontent.com", "K141rpYlFbz12Sr_T6j-e1ao", "https://arabeeworld.com/oauth");
////		} catch (GeneralSecurityException | IOException e) {
////			e.printStackTrace();
////		}
////		decodeFacebookToken("AQAPqlLR9TdirzE0it8Qrk9Fd8DiODWY0H9ADJVflvXGmzXivnfe5wwm14fd6Y5uFKZqrMyBeo-Yd9WEIkY0UsqHk2SSMkuJ-Jr_ppxqku-pAA3ActfHiriUj8ziBP9DmtzeU4HRpMrawoXdMcr6DKgd-ECw0dxr07DshPbdFa6EaxxFoJg07SWU1YGPasUCxtrmRL0EJHNSu8YWw0dM6vb2BaegBpI0iEYwTlJt3tMPNfly3pT2TWflnzjBKHEQK-2Fi4MCFWyMmdiVXWV642gm62d1_zaQ3-6rOIvaATIyCWSswRGl6oB0TFYbJ_NAyTNvceu6P7OC7UtyD8nVNF1n");
////
////		getUserInfoFromFaceBook("EAADfXE8ZBqUABALLKZCe9QKzL2aLzjB9ZCZChj3o9n6UAdoFZCrVE7wYzLdjyexZC0BCoSnncZBKzy7HAEXbYO9LF9vkPHedcTqscjQZCjZA7uSZAPjPFsmSgni8HUhmNR1DNWbMl2FZCmBkN7ew7nrKw5VGzKqI7OcyDWcLTXmiPzZAVa6buGqGC2eoKbJHkiljZBc3t1fgNki4yMANL2a53TZA7WuKRimaXSTe8ZD", null);
//	}

	public static GooglePojo decodeGoogleToken(String googleAuthCode, 
			String googleTokenServerEncodeUrl, String googleClientId, String googleClientSecret, String redirectUri) throws GeneralSecurityException, IOException {
		GoogleTokenResponse tokenResponse =
				new GoogleAuthorizationCodeTokenRequest(
						HTTP_TRANSPORT,
						JSON_FACTORY,
						googleTokenServerEncodeUrl,
						googleClientId,
						googleClientSecret,
						googleAuthCode,
						redirectUri)
						.execute();

		System.out.println("Token\n" + tokenResponse.getIdToken());

		GoogleIdTokenVerifier verifier =
				new GoogleIdTokenVerifier
					.Builder(HTTP_TRANSPORT, JSON_FACTORY)
					.setAudience(Collections.singletonList(googleClientId)) // .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
					.build();

		GoogleIdToken idToken = verifier.verify(tokenResponse.getIdToken());

		if (idToken == null)
			throw new GeneralSecurityException("Invalid Google tokenId");

		Payload payload = idToken.getPayload();

		GooglePojo googlePojo = new GooglePojo();

		Class<GooglePojo> pojoClass = GooglePojo.class;

		for(Field field : pojoClass.getDeclaredFields()) {
			try {
				field.set(googlePojo, payload.get(field.getName()));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return googlePojo;
	}

	public static FaceBookPojo decodeFacebookToken(String faceBookAuthCode, 
			String faceBookClientId, String faceBookClientSecret, String redirectUri) throws RestClientException{

		String getAccessTokenEndPoint =
				"https://graph.facebook.com/v3.2/oauth/access_token?"
				+ "client_id=" + faceBookClientId
				+ "&redirect_uri=" + redirectUri
				+ "&client_secret=" + faceBookClientSecret
				+ "&code=" + faceBookAuthCode;

		ResponseDetails<FaceBookAccessTokenPojo> faceBookAccessTokenPojo =
				ApiUtils.doRequest(null, null, null, null, getAccessTokenEndPoint, HttpMethod.GET, FaceBookAccessTokenPojo.class, MediaType.APPLICATION_JSON);

		String faceBookUserInfo = "https://graph.facebook.com/v3.2/me?fields=id,name,email&access_token="
				+ faceBookAccessTokenPojo.getBody().getBody().getAccess_token();

		ResponseDetails<FaceBookPojo> faceBookpojo = ApiUtils.doRequest(null, null, null, null, faceBookUserInfo, HttpMethod.GET, FaceBookPojo.class, MediaType.APPLICATION_JSON);

		return faceBookpojo.getBody().getBody();
	}

	public static FaceBookPojo getUserInfoFromFaceBook(String accessToken, String userId) {
		String faceBookUserInfo =
				"https://graph.facebook.com/v3.2/me?fields=id,name,email&"
				+ "access_token=" + accessToken;

		ResponseDetails<FaceBookPojo> faceBookpojo = ApiUtils.doRequest(null, null, null, null, faceBookUserInfo, HttpMethod.GET, FaceBookPojo.class, MediaType.APPLICATION_JSON);

		return faceBookpojo.getBody().getBody();
	}

	static FirebaseApp firebaseApp = null;
	public static GoogleFirebasePojo decodeGoogleFirebaseToken(String firebaseDatabaseUrl, String idToken, GoogleCredentials googleCredentials) throws FirebaseException, IOException { 

		if(firebaseApp == null) {
			FirebaseOptions firebaseOptions =
					new FirebaseOptions
					.Builder()
					.setCredentials(googleCredentials/*GoogleCredentials.fromStream(inputStream)*/)
					.setDatabaseUrl(firebaseDatabaseUrl)
					.build();

			firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
		}

		FirebaseToken decodedToken =
				FirebaseAuth
				.getInstance(firebaseApp)
				.verifyIdToken(idToken);

		String userId = decodedToken.getClaims().get("firebase").toString();

		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(userId);

		if(m.find())
			userId = m.group(1);
		else
			throw new FirebaseException("cannot parse userId");

		return new GoogleFirebasePojo(
				userId,
				decodedToken.getName(),
				decodedToken.getEmail(),
				decodedToken.getPicture());
	}
}

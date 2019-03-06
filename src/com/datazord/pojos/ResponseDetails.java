package com.datazord.pojos;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

public class ResponseDetails<T> extends HttpEntity<Body<T>> {

	private final Object status;

	public ResponseDetails() {
		this(new Body<T>(null, null, true), null, HttpStatus.OK);
	}

	public ResponseDetails(HttpStatus status, String error, boolean success) {
		this(new Body<T>(null, error, success), null, status);
	}

	public ResponseDetails(@Nullable T body, HttpStatus status, String error, boolean success) {
		this(new Body<T>(body, error, success), null, status);
	}

	public ResponseDetails(MultiValueMap<String, String> headers, HttpStatus status, String error, boolean success) {
		this(new Body<T>(null, error, success), headers, status);
	}

	public ResponseDetails(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status, String error, boolean success) {
		super(new Body<T>(body, error, success), headers);

		Assert.notNull(status, "HttpStatus must not be null");
		this.status = status;
	}

	private ResponseDetails(@Nullable Body<T> body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers);

		Assert.notNull(status, "HttpStatus must not be null");
		this.status = status;
	}

	public HttpStatus getHttpStatus() {
		if (this.status instanceof HttpStatus)
			return (HttpStatus) this.status;
		else
			return HttpStatus.valueOf((Integer) this.status);
	}

	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other)
			return true;

		if (!super.equals(other))
			return false;

		ResponseDetails<?> otherEntity = (ResponseDetails<?>) other;

		return ObjectUtils.nullSafeEquals(this.status, otherEntity.status);
	}

	@Override
	public int hashCode() {
		return (super.hashCode() * 29 + ObjectUtils.nullSafeHashCode(this.status));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("<");
		builder.append(this.status.toString());

		if (this.status instanceof HttpStatus) {
			builder.append(' ');
			builder.append(((HttpStatus) this.status).getReasonPhrase());
		}

		builder.append(',');
		Body<T> body = getBody();
		HttpHeaders headers = getHeaders();

		if (body != null && body.getBody() != null) {
			builder.append(body.getBody());
			builder.append(',');
		}

		builder.append(headers);
		builder.append('>');

		return builder.toString();
	}

	public static BodyBuilder status(HttpStatus status) {
		Assert.notNull(status, "HttpStatus must not be null");
		return new DefaultBuilder(status);
	}

	public static BodyBuilder ok() {
		return status(HttpStatus.OK);
	}

	public static <T> ResponseDetails<T> ok(T body) {
		BodyBuilder builder = ok();
		return builder.body(body);
	}

	public static BodyBuilder created(URI location) {
		BodyBuilder builder = status(HttpStatus.CREATED);
		return builder.location(location);
	}

	public static BodyBuilder accepted() {
		return status(HttpStatus.ACCEPTED);
	}

	public static HeadersBuilder<?> noContent() {
		return status(HttpStatus.NO_CONTENT);
	}

	public static BodyBuilder badRequest() {
		return status(HttpStatus.BAD_REQUEST);
	}

	public static HeadersBuilder<?> notFound() {
		return status(HttpStatus.NOT_FOUND);
	}

	public static BodyBuilder unprocessableEntity() {
		return status(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	public static BodyBuilder internalServerError() {
		return status(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public interface HeadersBuilder<B extends HeadersBuilder<B>> {

		B header(String headerName, String... headerValues);

		B headers(@Nullable HttpHeaders headers);

		B allow(HttpMethod... allowedMethods);

		B eTag(String etag);

		B lastModified(long lastModified);

		B location(URI location);

		B cacheControl(CacheControl cacheControl);

		B varyBy(String... requestHeaders);

		<T> ResponseDetails<T> build();

		<T> ResponseDetails<T> build(String error);
	}

	public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

		BodyBuilder contentLength(long contentLength);

		BodyBuilder contentType(MediaType contentType);

		<T> ResponseDetails<T> body(@Nullable T body);

		<T> ResponseDetails<T> body(@Nullable T body, String error);
	}

	private static class DefaultBuilder implements BodyBuilder {

		private final HttpStatus httpStatus;

		private final HttpHeaders headers = new HttpHeaders();

		public DefaultBuilder(HttpStatus httpStatus) {
			this.httpStatus = httpStatus;
		}

		@Override
		public BodyBuilder header(String headerName, String... headerValues) {
			for (String headerValue : headerValues)
				this.headers.add(headerName, headerValue);

			return this;
		}

		@Override
		public BodyBuilder headers(@Nullable HttpHeaders headers) {
			if (headers != null)
				this.headers.putAll(headers);

			return this;
		}

		@Override
		public BodyBuilder allow(HttpMethod... allowedMethods) {
			this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
			return this;
		}

		@Override
		public BodyBuilder contentLength(long contentLength) {
			this.headers.setContentLength(contentLength);
			return this;
		}

		@Override
		public BodyBuilder contentType(MediaType contentType) {
			this.headers.setContentType(contentType);
			return this;
		}

		@Override
		public BodyBuilder eTag(String etag) {
			if (!etag.startsWith("\"") && !etag.startsWith("W/\""))
				etag = "\"" + etag;

			if (!etag.endsWith("\""))
				etag = etag + "\"";

			this.headers.setETag(etag);

			return this;
		}

		@Override
		public BodyBuilder lastModified(long date) {
			this.headers.setLastModified(date);
			return this;
		}

		@Override
		public BodyBuilder location(URI location) {
			this.headers.setLocation(location);
			return this;
		}

		@Override
		public BodyBuilder cacheControl(CacheControl cacheControl) {
			String ccValue = cacheControl.getHeaderValue();

			if (ccValue != null)
				this.headers.setCacheControl(cacheControl.getHeaderValue());

			return this;
		}

		@Override
		public BodyBuilder varyBy(String... requestHeaders) {
			this.headers.setVary(Arrays.asList(requestHeaders));
			return this;
		}

		@Override
		public <T> ResponseDetails<T> build() {
			return body(null);
		}

		@Override
		public <T> ResponseDetails<T> build(String error) {
			return body(null, null, error);
		}

		@Override
		public <T> ResponseDetails<T> body(@Nullable T body) {
			return new ResponseDetails<T>(new Body<T>(body, "", true), this.headers, this.httpStatus);
		}

		@Override
		public <T> ResponseDetails<T> body(@Nullable T body, String token) {
			return new ResponseDetails<T>(new Body<T>(body, "", true, token), this.headers, this.httpStatus);
		}

		public <T> ResponseDetails<T> body(@Nullable T body, String token, String error) {
			return new ResponseDetails<T>(new Body<T>(body, error, false), this.headers, this.httpStatus);
		}
	}

}

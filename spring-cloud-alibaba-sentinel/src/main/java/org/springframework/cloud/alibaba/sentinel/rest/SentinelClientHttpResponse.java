/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.alibaba.sentinel.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.alibaba.sentinel.custom.SentinelProtectInterceptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.AbstractClientHttpResponse;

/**
 * Using by {@link SentinelRestTemplate} and {@link SentinelProtectInterceptor}
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class SentinelClientHttpResponse extends AbstractClientHttpResponse {

	private final String BLOCK_STR = "RestTemplate request block by sentinel";

	@Override
	public int getRawStatusCode() throws IOException {
		return HttpStatus.OK.value();
	}

	@Override
	public String getStatusText() throws IOException {
		return BLOCK_STR;
	}

	@Override
	public void close() {
		// nothing do
	}

	@Override
	public InputStream getBody() throws IOException {
		return new ByteArrayInputStream(BLOCK_STR.getBytes());
	}

	@Override
	public HttpHeaders getHeaders() {
		Map<String, List<String>> headers = new HashMap<>();
		headers.put(HttpHeaders.CONTENT_TYPE,
				Arrays.asList(MediaType.APPLICATION_JSON_UTF8_VALUE));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.putAll(headers);
		return httpHeaders;
	}
}

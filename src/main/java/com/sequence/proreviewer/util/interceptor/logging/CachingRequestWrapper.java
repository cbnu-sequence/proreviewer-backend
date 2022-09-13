package com.sequence.proreviewer.util.interceptor.logging;

import io.micrometer.core.instrument.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

public class CachingRequestWrapper extends HttpServletRequestWrapper {

	private final Charset encoding;
	private byte[] rawData;

	public CachingRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);

		String characterEncoding = isEmpty(request.getCharacterEncoding())
			? StandardCharsets.UTF_8.name()
			: request.getCharacterEncoding();
		this.encoding = Charset.forName(characterEncoding);

		try (InputStream inputStream = request.getInputStream()) {
			this.rawData = IOUtils.toByteArray(inputStream);
		}
	}

	private boolean isEmpty(String characterEncoding) {
		return StringUtils.isEmpty(characterEncoding);
	}
}

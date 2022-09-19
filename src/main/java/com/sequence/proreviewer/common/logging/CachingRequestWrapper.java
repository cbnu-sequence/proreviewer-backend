package com.sequence.proreviewer.common.logging;

import io.micrometer.core.instrument.util.StringUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
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

	@Override
	public ServletInputStream getInputStream() {
		return new CachedServletInputStream(this.rawData);
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}

	private boolean isEmpty(String characterEncoding) {
		return StringUtils.isEmpty(characterEncoding);
	}

	private static class CachedServletInputStream extends ServletInputStream {

		private final ByteArrayInputStream buffer;

		public CachedServletInputStream(byte[] contents) {
			this.buffer = new ByteArrayInputStream(contents);
		}

		@Override
		public int read() throws IOException {
			return buffer.read();
		}

		@Override
		public boolean isFinished() {
			return buffer.available() == 0;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			throw new UnsupportedOperationException("not support");
		}
	}
}

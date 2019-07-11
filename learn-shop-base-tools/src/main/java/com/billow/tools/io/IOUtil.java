package com.billow.tools.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;



public class IOUtil {
	private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * 写日志到磁盘
	 * 
	 * @param dir
	 * @param fileName
	 * @param logText
	 * @param append
	 *            true 追加在文件末尾如果指定的文件已存在；false
	 *            覆盖以存在的文件；如果指定的文件还不存在，true和false的效果一样。
	 * @return 日志文件的绝对路径
	 */
	public static String write2File(String dir, String fileName, String logText, boolean append) throws IOException {
		FileOutputStream fos = null;
		PrintStream ps = null;
		String adir = null;
		try {
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			adir = file.getAbsolutePath();
			if (!adir.endsWith(File.separator)) {
				adir += File.separator;
			}
			fos = new FileOutputStream(adir + fileName, append);
			ps = new PrintStream(fos);
			ps.print(logText);
			ps.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return dir + fileName;
	}

	/**
	 * Copy and convert bytes from an <code>InputStream</code> to chars on a
	 * <code>Writer</code> use the specified encoding. If charset is null ,The
	 * platform's default encoding is used for the byte-to-char conversion.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @param charset
	 *            encoding name
	 * @throws IOException
	 *             In case of an I/O problem
	 */
	public static int copy(InputStream input, Writer output, String charset) throws IOException {
		InputStreamReader reader = null;
		if (charset == null || charset.equals("")) {
			reader = new InputStreamReader(input);
		} else {
			reader = new InputStreamReader(input, charset);
		}
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int count = 0;
		int n = 0;
		while (-1 != (n = reader.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Get the contents of an <code>InputStream</code> as a String using the
	 * default character encoding of the platform only if charset is null or "".
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param charset
	 *            character encoding
	 * @return the requested String
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String toString(InputStream input, String charset) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, charset);
		return sw.toString();
	}
}

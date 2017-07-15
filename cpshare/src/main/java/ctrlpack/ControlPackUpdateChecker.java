/* Copyright (c) 2011-2017 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControlPackUpdateChecker implements Runnable {
	private final String UpdateURL = "https://api.github.com/repos/uyjulian/ControlPack/releases/latest";
	private final String CurrentVersion = "5.94.1";

	@Override
	public void run() {
		Boolean foundNewVersion = false;
		while (!foundNewVersion) {
			InputStream currentInputStream = null;
			ByteArrayOutputStream currentByteArrayOutputStream;
			try {
				URL currentURL = new URL(UpdateURL);
				currentInputStream = currentURL.openStream();
				currentByteArrayOutputStream = new ByteArrayOutputStream();
				copy(currentInputStream, currentByteArrayOutputStream);
				Pattern pattern = Pattern.compile("\"tag_name\":\"(.+?)\"");
				String NewVersionJSON = new String(currentByteArrayOutputStream.toByteArray());

				Matcher matcher = pattern.matcher(NewVersionJSON);
				matcher.find();
				String NewVersion = matcher.group(1);

				if (!(NewVersion.equals(CurrentVersion)) && (NewVersion.length() <= 100)) {
					ControlPackMain.instance.chatMsg("§c§lUpdate available§r; check website for details");
					foundNewVersion = true;
				}
				Thread.sleep((1000 * 60) * 2);
			}
			catch (Exception currentException) {
				currentException.printStackTrace();
				try {
					Thread.sleep((1000 * 60) * 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			finally {
				closeQuietly(currentInputStream);
			}
		}
	}
	
	private static long copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
	{
		byte[] arrayOfByte = new byte[4096];
		long l = 0L;
		int i = 0;
		while ((i = paramInputStream.read(arrayOfByte)) != -1)
		{
			paramOutputStream.write(arrayOfByte, 0, i);
			l += i;
		}
		return l;
	}

	private static void closeQuietly(Closeable paramCloseable)
	{
		try
		{
			if (paramCloseable != null)
				paramCloseable.close();
		}
		catch (Exception currentException){}
	}
}

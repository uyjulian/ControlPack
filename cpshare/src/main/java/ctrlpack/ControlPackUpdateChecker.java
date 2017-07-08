/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
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

public class ControlPackUpdateChecker implements Runnable {
	private final String UpdateURL = "http://pastebin.com/raw/LxFxp7Xv";
	private final String CurrentVersion = "2";

	@Override
	public void run() {
		Boolean foundNewVersion = false;
		while (!foundNewVersion) {
			InputStream currentInputStream = null;
			ByteArrayOutputStream currentByteArrayOutputStream = null;
			try {
				URL currentURL = new URL(UpdateURL);
				currentInputStream = currentURL.openStream();
				currentByteArrayOutputStream = new ByteArrayOutputStream();
				copy(currentInputStream, currentByteArrayOutputStream);
				String NewVersion = new String(currentByteArrayOutputStream.toByteArray()).trim();
				if (!(NewVersion.equals(CurrentVersion)) && (NewVersion.length() <= 100)) {
					foundNewVersion = true;
					ControlPackMain.instance.chatMsg("§c§lUpdate available§r; check website for details");
				}
				Thread.sleep((1000 * 60) * 2);
			}
			catch (Exception currentException) {
				currentException.printStackTrace();
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

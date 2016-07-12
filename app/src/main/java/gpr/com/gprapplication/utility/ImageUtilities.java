package gpr.com.gprapplication.utility;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.os.Environment;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gpr.com.gprapplication.service.callback.GPRException;

public class ImageUtilities {
	public static final String CLASS_NAME = "ImageUtilities";

	/**
	 * This code is courtesy of Neil Davies at http://www.inter-fuser.com
	 * 
	 * @param originalImage
	 *            The original Bitmap image used to create the reflection
	 * @return the bitmap with a reflection
	 */
	public static Bitmap createReflectedImage(Bitmap originalImage) {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 1;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);

		// Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint defaultPaint = new Paint();
		// canvas.drawRect(0, height, width, height + reflectionGap,
		// defaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// Create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	// see http://www.androidsnippets.com/create-image-with-reflection

	/*
	 * Return the complete URL String Concatenates the URL base string from the
	 * GPR Constants with the image name. Returns null if the imageName is null
	 */
	public static String formUrl(String baseUrl, String imageName) {
		String fullUrlString = null;
		if (imageName != null && baseUrl != null)
			fullUrlString = baseUrl + "/" + imageName;
		return fullUrlString;
	}

	public static void saveImageToFile(Bitmap bmp, String fileName)
			throws GPRException {
		String TAG = "saveImageToFile";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (Exception e) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			throw ex;
		} finally {
			try {
				if (null != out)
					out.close();
			} catch (Throwable ignore) {
			}
		}
	}

	public static Bitmap cropSquare(Bitmap srcBmp) {
		Bitmap dstBmp;
		if (srcBmp.getWidth() >= srcBmp.getHeight()) {
			dstBmp = Bitmap.createBitmap(srcBmp,
					srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2, 0,
					srcBmp.getHeight(), srcBmp.getHeight());
		} else {
			dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight() / 2
							- srcBmp.getWidth() / 2, srcBmp.getWidth(),
					srcBmp.getWidth());
		}

		return dstBmp;
	}

	public static File getImageFilePath() {
		String path = Environment.getExternalStorageDirectory()
				+ File.separator + GPRConstants.PKG_NAME + File.separator;
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		return folder;
	}

	public static void copyImage(InputStream inputStream, File file) throws IOException {
		try {
			file.setWritable(true, false);
			OutputStream outputStream = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int length = 0;

			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}

			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

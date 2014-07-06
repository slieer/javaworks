package com.image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 生成带文字的图片
 * @author borqs
 */
public class ChartGraphics {

	BufferedImage image;

	void createImage(String fileLocation) {
		try {
			FileOutputStream fos = new FileOutputStream(fileLocation);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void graphicsGeneration(String name, String id, String classname,
			String imgurl) {

		int imageWidth = 5;// 图片的宽度

		int imageHeight = 4;// 图片的高度

		image = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
//		graphics.drawString("姓名 : " + name, 50, 75);
//		graphics.drawString("学号 : " + id, 50, 150);
//		graphics.drawString("班级 : " + classname, 50, 225);
		// ImageIcon imageIcon = new ImageIcon(imgurl);
		// graphics.drawImage(imageIcon.getImage(), 230, 0, null);

		// 改成这样:
		BufferedImage bimg = null;
		try {
			bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
		} catch (Exception e) {
		}

		if (bimg != null)
			graphics.drawImage(bimg, 230, 0, null);
		graphics.dispose();

		createImage("C:\\TDDOWNLOAD\\hehe.jpg");

	}

	public static void main(String[] args) {
		ChartGraphics cg = new ChartGraphics();
		try {
			cg
					.graphicsGeneration("ewew", "1", "12",
							"C:/Documents and Settings/Administrator/My Documents/My Pictures/7.jpg");
			
			System.out.println(Generate.graphicsGeneration());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Generate {
	public static byte[] graphicsGeneration() throws Exception {
		int imageWidth = 1;// 图片的宽度
		int imageHeight = 1;// 图片的高度

		BufferedImage image = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		graphics.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		byte[] re = baos.toByteArray();
		baos.close();
		return re;
	}
}
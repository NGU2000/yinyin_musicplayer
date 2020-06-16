package yinyin;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
public class PicToIcon {
	public static void ManageImageIcon(String imgPath , JButton jbutton)
	{
		try {
			BufferedImage tempBufferedImage  = ImageIO.read(new File(imgPath));
			tempBufferedImage = resizeBufferedImage(tempBufferedImage,jbutton.getWidth(),jbutton.getHeight(),true);
			ImageIcon imageIcon = new ImageIcon(tempBufferedImage);
			jbutton.setIcon(imageIcon);
		} catch (Exception e) {
			System.out.println("¶ÁÈ¡Í¼Æ¬´íÎó");
		}
	
	}
	public static BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
			int type = source.getType();
			BufferedImage target = null;
			double sx = (double) targetW / source.getWidth();
			double sy = (double) targetH / source.getHeight();
			if (flag && sx > sy) {
				sx = sy;
				targetW = (int) (sx * source.getWidth());
			} else if(flag && sx <= sy){
				sy = sx;
				targetH = (int) (sy * source.getHeight());
			}
			if (type == BufferedImage.TYPE_CUSTOM) {
				ColorModel cm = source.getColorModel();
				WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
				boolean alphaPremultiplied = cm.isAlphaPremultiplied();
				target = new BufferedImage(cm, raster, alphaPremultiplied, null);
			} else {
				target = new BufferedImage(targetW, targetH, type);
			}
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
			g.dispose();
			return target;
		}
	
}

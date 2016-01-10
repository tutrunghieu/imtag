package ds.imtag.image2vec;


import java.awt.Color;

public class Color2 {

	public static double[] rgb2double(int rgb)
	{
		Color c = new Color(rgb);
		return new double[] { c.getRed(), c.getGreen(), c.getBlue() };
	}

}

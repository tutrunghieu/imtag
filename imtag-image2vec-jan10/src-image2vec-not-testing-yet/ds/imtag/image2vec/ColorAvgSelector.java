package ds.imtag.image2vec;


import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class ColorAvgSelector extends RandomSelector 
{
	//private int featureLen = 1 + 2*2 + 4*4 + 8*8;

	public ColorAvgSelector() 
	{
		super(1 + 2*2 + 4*4 + 8*8);
	}

	@Override
	public double[] selectFeatures(Object obj) throws Exception
	{
		File fk = (File)obj;
		BufferedImage img = ImageIO.read(fk);
		
		int Rx = img.getWidth(), Ry = img.getHeight();
		double[] X248 = { Rx/2.0, Rx/4.0, Rx/8.0 };		
		double[] Y248 = { Ry/2.0, Ry/4.0, Ry/8.0 };		
		
		double[][] res = new double[featureLen][4]; 
		
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			int[] p = SpatialPyramid.computeLocations(x, y, X248, Y248);
			double[] c = Color2.rgb2double( img.getRGB(x, y) );
			add(res, c, p);
		}
		
		return flatten(res);
	}

	
	private void add(double[][] res, double[] c, int[] p) 
	{
		int n = c.length;
		
		for(int pk: p)
		{
			for(int k=0; k<n; k++) res[pk][k] += c[k];
			res[pk][n]++;
		}		
	}

	private double[] flatten(double[][] src)
	{
		int n = src.length*3;
		
		double[] res = new double[n];
		
		for(int t=0, k=0; k<src.length; k++)
		{
			res[t++] = src[k][0]/src[k][3];
			res[t++] = src[k][1]/src[k][3];
			res[t++] = src[k][2]/src[k][3];
		}
		
		return res;
	}


}

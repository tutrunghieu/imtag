package ds.imtag.image2vec;


public class SpatialPyramid 
{
	
	public static int[] computeLevels(int t, double w2, double w4, double w8) 
	{
		int x = t;
		int l1 = (x<w2 ? 0 : 1); x -= l1*w2;
		int l2 = (x<w4 ? 0 : 1); x -= l2*w4;
		int l3 = (x<w8 ? 0 : 1); 

		return new int[] { l1, l2+l1*2, l3+l2*2+l1*4 };
	}

	public static int[] computeLocations(int x, int y, double[] w, double[] h)
	{
		int[] lx = SpatialPyramid.computeLevels(x, w[0], w[1], w[2]);
		int[] ly = SpatialPyramid.computeLevels(y, h[0], h[1], h[2]);
		
		int[] l = { 0, 1 +  2*ly[0]+lx[0], 
					   5 +  4*ly[1]+lx[1],
					   21 + 8*ly[2]+lx[2] };
		return l;
	}

}

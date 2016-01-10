package ds.imtag.image2vec;


import java.util.Random;


public class RandomSelector implements FeatureSelector {

	protected int featureLen;
	protected static Random coin = new Random(1997);

	public RandomSelector(int len) 
	{
		featureLen = len;
	}

	@Override
	public int getFeatureLen() {
		return featureLen;
	}
	
	@Override
	public double[] selectFeatures(Object fk) throws Exception {
		double[] res = new double[featureLen];
		for(int k=0; k<res.length; k++) res[k] = coin.nextDouble();
		return res;
	}


}

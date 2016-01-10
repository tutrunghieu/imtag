package ds.imtag.image2vec;



public interface FeatureSelector
{
	public int getFeatureLen();
	
	public double[] selectFeatures(Object fk) throws Exception;
}

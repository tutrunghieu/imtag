package ds.imtag.plsa.testing1;

public class pLSA_model {

	public double[][] pWgZ;
	public double[][] pZgD;
	
	public void print() 
	{
		System.out.printf("pWgZ rows=%d cols=%d\n", pWgZ.length, pWgZ[0].length);
		System.out.printf("pZgD rows=%d cols=%d\n", pZgD.length, pZgD[0].length);		
	}

	public int nofTopics() {
		return pWgZ.length;
	}

	public int nofWords() {
		return pWgZ[0].length;
	}
	
	public int nofDocs() {
		return pZgD.length;
	}
}

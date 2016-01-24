package ds.imtag.plsa.testing1;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import ds.util.File2;

public class test1_generating_and_measuring {

	public static boolean headRow;
	public static String colnames;

	public static void main(String[] args) throws Exception
	{
		
		pLSA_model m = new pLSA_model();
		
		headRow = true;
		m.pWgZ = readCSV2( File2.desktopFile("tung-imtag-jan24/corel1000-plsa-pWgZ.csv") );
		
		headRow = true;
		m.pZgD = readCSV2( File2.desktopFile("tung-imtag-jan24/corel1000-plsa-pZgD.csv") );
		
		m.print();
		
		headRow = true;
		double[][] nDW = readCSV2( File2.desktopFile("tung-imtag-jan24/corel1000-bow.csv") );		
		double[][] pWgD = normalize(nDW);
		System.out.printf("pWgD rows=%d cols=%d\n", pWgD.length, pWgD[0].length);
		
		similar(pWgD, m);
	}

	private static void similar(double[][] pWgD, pLSA_model m) 
	{
		double s = 0;
		
		for(int d=0; d<pWgD.length; d++)
		{
			double[] dj = pWgD[d];
			double[] dj_hat = new double[dj.length];
			for(int z=0; z<m.nofTopics(); z++) 
			for(int w=0; w<m.nofWords(); w++) 
				dj_hat[w] += m.pZgD[d][z] * m.pWgZ[z][w];
			
			s += similar(dj, dj_hat);
		}
		
		System.out.println(s/pWgD.length);
	}

	private static double similar(double[] a, double[] b) 
	{
		int n = Math.max(a.length, b.length);
		double c = 0, s = 0;
		for(int k=0; k<n; k++) 
		{
			c += Math.min(a[k], b[k]);
			s += Math.max(a[k], b[k]);
		}
		return c/s;
	}

	private static double[][] normalize(double[][] rows) 
	{
		for(double[] rk: rows)
		{
			double s = 0;
			for(double x: rk) s += x;
			for(int j=0; j<rk.length; j++) rk[j] /= s;
		}
		
		return rows;
	}


	private static double[][] readCSV2(File f) throws Exception
	{
		return readCSV(f).toArray(new double[][] { });
	}

	public static List<Object> readCSV(File f) throws Exception
	{
		BufferedReader src = File2.openReaderUtf8(f);			
		List<Object> l = readCSV(src);
		src.close();
		
		return l;
	}

	private static List<Object> readCSV(BufferedReader src) throws Exception 
	{
		List<Object> res = new ArrayList<Object>();
		
		if(headRow) {
			colnames = src.readLine();
		}
		
		while(true)
		{
			String line = src.readLine();
			if(line == null) break;
			
			String[] arr = line.split(",");
			double[] vk = new double[arr.length];
			for(int k=0; k<vk.length; k++) vk[k] = Double.parseDouble(arr[k]);
			res.add(vk);
		}
		
		return res;
	}
	
}

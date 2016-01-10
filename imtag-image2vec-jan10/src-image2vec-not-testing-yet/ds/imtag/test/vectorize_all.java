package ds.imtag.test;

import java.io.File;
import java.io.PrintWriter;

import ds.imtag.image2vec.Collages;
import ds.imtag.image2vec.ColorAvgSelector;

public class vectorize_all 
{
	public static void main(String[] args) throws Exception
	{
		File f = new File("C:/Users/henrytu/Desktop/datasets/imtag-32");
		File tar = new File("C:/Users/henrytu/Desktop/out1.txt");
		
		PrintWriter out = new PrintWriter(tar);
		Collages.vectorizeAll(f, out, new ColorAvgSelector());
		out.close();
	}
	

}

package ds.imtag.image2vec;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Collages {


	public static void vectorizeAll(File f, PrintWriter out, FeatureSelector fsel) throws Exception 
	{
		Stack<File> todo = new Stack<File>();
		todo.add(f);
		
		out.println("dataset|group|name|" + rep("|", "f", fsel.getFeatureLen()) );
		
		while(!todo.isEmpty())
		{
			File par = todo.pop();
			
			for(File fk: par.listFiles())
			{
				if(fk.isDirectory())
				{
					if(!fk.getName().startsWith("z-")) todo.add(fk);
				}
				else 
				{
					String nk = fk.getName();
					if(nk.endsWith(".png") || nk.endsWith(".jpg") || nk.endsWith(".bmp") )
					{
						System.out.println("Vectorizing " + fk);
						
						File p1 = fk.getParentFile();
						File p2 = p1.getParentFile();
						double[] vk = fsel.selectFeatures(fk); 
						out.println(p2.getName() + "|" + p1.getName() + "|" + fk.getName() + "|" + join("|",vk));
					}
				}
			} //children
		} //parent
		
		return;
	}

	private static String rep(String sep, String pref, int len)
	{
		String res = "";
		for(int k=0; k<len; k++) res += (res.length()==0 ? "" : sep) + (pref + k);
		return res;
	}

	private static String join(String sep, double... arr) 
	{
		String res = "";
		for(double ak: arr) res += (res.length()==0 ? "" : sep) + ak;
		return res;
	}
		

}

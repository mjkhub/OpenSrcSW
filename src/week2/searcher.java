package week2;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;


public class searcher {

	private static final double BIAS = 0.000000000000001; //good idea
	private String query;
	private String path;
	public searcher(String query, String path) {
		this.query = query;
		this.path = path;
	}
	
	public double[] innerProduct(HashMap<String, String> mapReaded, String[] q ) throws Exception{
	
		double[] result = new double[5]; //내적을 한 결과값을 저장하는 컨테이너
		//내적을 하는 부분
		for(int i=0; i<q.length; i=i+2) { //query의 단어 개수만큼 돌자
			String key= q[i];
			double weightofK = Double.parseDouble(q[i+1]);
//			System.out.println(mapReaded.get(key));
			String[] value = mapReaded.get(key).split(" ");
			for(int j=0; j<value.length; j=j+2) {
				int index = Integer.parseInt(value[j]);
				double weightofdoc = Double.parseDouble(value[j+1]);
				result[index] += weightofK * weightofdoc;
			}
		}
		return result;
		
	}
	
	public static void calSim() {
		//empty ...
	}

	public static StringBuilder genKkma(String testString) {
		StringBuilder sb = new StringBuilder();
		KeywordExtractor ke = new KeywordExtractor();

		KeywordList kl = ke.extractKeyword(testString, true);

		for (int i = 0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			sb.append(kwrd.getString()).append(':').append(kwrd.getCnt());
			if (i != kl.size() - 1)
				sb.append('#');
		}

		return sb;
	}
	
	public boolean isZero(double[] arr) {
		for(int i=0; i<arr.length; i++) {
			if(arr[i]!=0.0) return false;
		}
		return true;
	}
}

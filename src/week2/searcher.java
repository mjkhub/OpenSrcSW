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

	private String query;
	private String path;
	public searcher(String query, String path) {
		this.query = query;
		this.path = path;
	}
	public void calSim() throws Exception{
		
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		
		HashMap<String, String> mapReaded = (HashMap)obj; 
		String[] q = genKkma(query).toString().split(":|#"); //이 두개를 가지고 처리하면 됨
		
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
	
		if(isZero(result)) {
			System.out.println("query의 모든 단어들을 포함하는 파일이 존재하지 않습니다.");
			System.exit(0);
		}
		//반환은 result를 해주면 될듯
		
		ArrayList<double[]> indexAndResult = new ArrayList<double[]>(); //0:index 1:weight
		for(int i=0; i<5; i++) indexAndResult.add(new double[]{i, result[i]});
	
//		for(int i=0; i<5; i++) System.out.println(indexAndResult.get(i)[1]);
//		System.out.println();
		Collections.sort(indexAndResult, new Comparator<double[]>() {
			@Override
			public int compare(double[] o1, double[] o2) {
				// TODO Auto-generated method stub
				if(o1[1] > o2[1]) return -1;
				else if(o1[1] < o2[1]) return 1;
				else { //weight의 값이 같을 때는 doc의 id 값이 작은걸로 정렬 -> 역방향으로 정렬
					return (-1) * (int)o1[0] - (int)o2[0];
				}
			}
		});
		
//		for(int i=0; i<5; i++) System.out.println(indexAndResult.get(i)[1]);

		File file = new File("./collection.xml");
		org.jsoup.nodes.Document xml = Jsoup.parse(file, "UTF-8", "", Parser.xmlParser());
		Elements titles = xml.select("title");
		for(int i=0; i<3; i++) { //collection.xml로부터 파일을 가져오는 부분
			if((int)indexAndResult.get(i)[1]!=0) {
//				System.out.println(i+1+"위: " + titles.get((int)indexAndResult.get(i)[0]).text() + " 문서번호: "+ (int)(indexAndResult.get(i)[0]+1));
				System.out.println(i+1+"위: " + titles.get((int)indexAndResult.get(i)[0]).text());
			}
		}
		
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
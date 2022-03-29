package week2;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class indexer { //week4_ParsingXmlToPost

	private static final int N = 5;
	private String xmlPath;
	
	public indexer(String xmlPath) throws Exception {
		this.xmlPath = xmlPath;
	}
	
	public void convertXmlToPost() throws  Exception{
		// TODO Auto-generated method stub
		File file = makeFile(xmlPath);
		org.jsoup.nodes.Document xml = Jsoup.parse(file, "UTF-8", "", Parser.xmlParser()); //xml로 읽어오기
		
		Elements bodys = xml.select("body");
		
		HashMap<String, ArrayList<Integer>> keyWordMap = new HashMap<>(); //key: String value: arraylist
		for(int i=0; i<N; i++){ //문서의 번호
			String[] arr = bodys.get(i).text().split("#|:");			
			for(int j=0; j<arr.length; j=j+2) {
				if(keyWordMap.containsKey(arr[j])){
					ArrayList<Integer> tempList = keyWordMap.get(arr[j]);
					tempList.add(i); tempList.add(Integer.parseInt(arr[j+1]));
					keyWordMap.put(arr[j], tempList);
				}else {
					ArrayList<Integer> tempList = new ArrayList<>();
					tempList.add(i); tempList.add(Integer.parseInt(arr[j+1]));
					keyWordMap.put(arr[j], tempList);
				}
			}
		}
		//init
	
		Set<String> keySet = keyWordMap.keySet();
		Iterator<String> keyIt = keySet.iterator();
		HashMap<String, String> mapToBeWrited = new HashMap<>();
		while(keyIt.hasNext()) {
			StringBuilder sb = new StringBuilder();
			String key = keyIt.next();
			ArrayList<Integer> value = keyWordMap.get(key);
			sb.append(key).append(" ->").append(' ');
			for(int i=0; i<value.size(); i=i+2) {
				//내 결과가 맞는 듯?
//				sb.append(value.get(i)).append(' ');
				//하나만 수정
				sb.append(value.get(i)).append(' ');
				sb.append(getWeight(value.get(i+1), value.size()/2)).append(' ');
			}
//			sb.append('\n');
			System.out.println(sb);
			mapToBeWrited.put(key, sb.toString());
		}
		//연산을 저장
	
		FileOutputStream fos = new FileOutputStream("./index.post");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(mapToBeWrited);
		oos.close();
		
		FileInputStream fis = new FileInputStream("./index.post");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		
//		System.out.println("읽어온 객체의 type + " + obj.getClass());
		printMap((HashMap)obj);
	}
	
	public static String getWeight(int tf, int df) {
		double w = tf * Math.log(N/df);
		return String.format("%.2f", w);
	} //자바로 소수점을 처리하는 방법
	

	public static File makeFile(String xmlPath) {
		File file = new File(xmlPath);
		return file;
	}
	
	public static void printMap(HashMap<String, String> map) {
//		HashMap<String, ArrayList<Integer>> mapReaded = (HashMap)obj;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			System.out.println(value);
		}
	}
	
}

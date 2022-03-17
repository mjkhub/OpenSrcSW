package week2;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main { //202010375 김만재

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

//		String path = "html"; 
//		String path = "./html";  //이 두가지 모두 같은 결과를 냄
//		File[] file = makeFileLsit(path);
//		
//		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//		
//		Document doc = docBuilder.newDocument();
//		
//		Element docs = doc.createElement("docs");
//		doc.appendChild(docs);
//			
//		
//		for(int i=0; i<5; i++) {
//			org.jsoup.nodes.Document html = Jsoup.parse(file[i], "UTF-8");
//			Element id = doc.createElement("doc");
//			docs.appendChild(id);
//			id.setAttribute("id", Integer.toString(i));
//			
//			Element title = doc.createElement("title");
//			String titleData = html.title();
//			title.appendChild(doc.createTextNode(titleData));
//			id.appendChild(title);			
//	
//			
//			Element body = doc.createElement("body");
//			String bodyData = html.body().text();
//			id.appendChild(body);			
//			body.appendChild(doc.createTextNode(bodyData));
//		
//			System.out.println(titleData);
//			System.out.println(bodyData);
//				
//		}
//		
//	TransformerFactory transformerFactory = TransformerFactory.newInstance();
//	
//	Transformer transformer = transformerFactory.newTransformer();
//	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//	
//	DOMSource source = new DOMSource(doc);
//	StreamResult result = new StreamResult(new FileOutputStream(new File("src/week2/collection.xml")));
//	
//	transformer.transform(source, result);
		

		printKkma();

	}
	
	public static File[] makeFileLsit(String path) {
		File dir = new File(path);
		return dir.listFiles();
	}
	
	public static void printKkma() {
		String testString = "꼬꼬마형태소분석기를테스트하고있어요.테스트결과를 볼게요.";
		
		KeywordExtractor ke = new KeywordExtractor();
		
		KeywordList kl = ke.extractKeyword(testString, true);
		
		for(int i =0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			System.out.println(kwrd.getString() +"\t" + kwrd.getCnt());
		}
	}

}

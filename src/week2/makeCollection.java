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
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class makeCollection { //week2_ParsingHtmlToXml

	private final int ELEMENT_NUM = 5;
	private final String path_to_write = "./collection.xml";
	
	private String DirectoryPath_of_htmlfiles; 
	
	public makeCollection(String DirectoryPath_of_htmlfiles) throws Exception {

		this.DirectoryPath_of_htmlfiles = DirectoryPath_of_htmlfiles;
		
	}	
	
	public void makeXml() throws Exception {
		File[] file = makeFileList(DirectoryPath_of_htmlfiles);

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element docs = doc.createElement("docs");
		doc.appendChild(docs);

		for (int i = 0; i < ELEMENT_NUM; i++) {
			org.jsoup.nodes.Document html = Jsoup.parse(file[i], "UTF-8"); // 파일을 html 형태로 읽어옴

			Element id = doc.createElement("doc"); // doc으로부터 element를 만들수 있는 메소드들이 있다
			docs.appendChild(id);
			id.setAttribute("id", Integer.toString(i));

			Element title = doc.createElement("title");
			String titleData = html.title();
			title.appendChild(doc.createTextNode(titleData));
			id.appendChild(title);

			Element body = doc.createElement("body");
			String bodyData = html.body().text();
			body.appendChild(doc.createTextNode(bodyData));
			id.appendChild(body);
		}
		// 여기까지하면 html 파일들을 읽어와서 필요한 정보들을 doc이라는 Document에 저장한 과정
		// 아래부터는 doc이라는 도큐먼트를 xml로 바꾸어서 파일 쓰는 과정

		transfromDocToXml(doc, path_to_write);
	}
	
	public void transfromDocToXml(Document doc, String path_to_write) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File(path_to_write)));

		transformer.transform(source, result);
	}
	
	public File[] makeFileList(String path) {
		File dir = new File(path);
		return dir.listFiles();
	}

}

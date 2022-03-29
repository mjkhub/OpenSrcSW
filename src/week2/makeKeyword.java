package week2;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeKeyword { //week3_ParsingXml_Kkma
	
	private final String path_to_write= "./index.xml";
	private String xmlPath;
	
	public makeKeyword(String xmlPath) throws Exception {
		this.xmlPath = xmlPath;
	}

	public void convertXml() throws Exception{
		// TODO Auto-generated method stub
		File file = makeFile(xmlPath);

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();

		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		// 여기까지
		org.jsoup.nodes.Document xml = Jsoup.parse(file, "UTF-8", "", Parser.xmlParser()); // xml로 읽어오기
																							// 이런식으로 스스로 해결
		// xml document에서 element를 가져오자
		Elements titles = xml.select("title");
		Elements bodys = xml.select("body");
		int count = titles.size(); // 좀더 세련되게 하려면 doc를 가져와서 그 안에 있는 걸 모두 parsing하는 방식을 하는게 좋을듯 이 부분 수정
									// 내가 뭘 가져와야할지 아는 상태에서 코드를 작성해서 찝찝하다 이 얘기임 count는 body와 title이 같으므로 이렇
		for (int i = 0; i < count; i++) {
			Element id = doc.createElement("doc"); // id
			docs.appendChild(id);
			id.setAttribute("id", Integer.toString(i));

			Element title = doc.createElement("title");
			String titleData = titles.get(i).text();
			title.appendChild(doc.createTextNode(titleData));
			id.appendChild(title);

			Element body = doc.createElement("body");
			StringBuilder bodydata = genKkma(bodys.get(i).text());
			body.appendChild(doc.createTextNode(bodydata.toString()));
			id.appendChild(body);
		}

		transfromDocToXml(doc);
	}
	
	public void transfromDocToXml(Document doc) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File(path_to_write)));

		transformer.transform(source, result);
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

	public static File makeFile(String xmlPath) {
		File file = new File(xmlPath);
		return file;
	}

}

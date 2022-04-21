package week2;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MidTerm {

	private String xmlPath;
	private String q;
	
	public MidTerm(String q, String xmlPath) {
		this.xmlPath=xmlPath;
		this.q = q;
	}
	
	public void showSnippet() throws Exception {
		File file = makeFile(xmlPath);
		String[] qarr = genKkma(q).toString().split(":|#"); //명사만 나오겠지
		// 여기까지
		org.jsoup.nodes.Document xml = Jsoup.parse(file, "UTF-8", "", Parser.xmlParser()); // xml로 읽어오기
																							// 이런식으로 스스로 해결
		Elements titles = xml.select("title");
		Elements bodys = xml.select("body");
		int count = titles.size(); 
		for (int i = 0; i < count; i++) { //문서의 개수 만큼 돌겠지
			String[] body = bodys.get(i).text().split("");

			int[] info = checksnip(body, qarr);
			
			//스니펫 정보 출력
			if(info[0]!= 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("문서번호: ").append(i)
				.append(" 스니펫: ");
				for(int k = info[1]; k<info[2]; k++) {
					sb.append(body[k]);			
				}
				
				sb.append(" 매칭점수: ").append(info[0]);
				System.out.println(sb);
			}
		}
	}
	
	public static int[] checksnip(String[] arr , String[] qarr) {
		int max = Integer.MIN_VALUE;
		int s;
		int e;
		int key=0;
		int[] returnarr = new int[4];
		for(int i=0; i<arr.length-30; i++) {
			int temp=0;
			int[] keytemp = new int[qarr.length];
			for(int j = i; j<i+30; j++) {
				for(int k=0; k<qarr.length; k++) {
					if(arr[j].equals(qarr[k])) {
						temp++;
					}
					if(keytemp[k] == 0 && !qarr[k].equals("")) {
						keytemp[k] =1;
					}

				}
			}
			if(temp > max) {
				max = temp;
				s = i;
				e = i+30;
				key =qarr.length; //걍 이렇게
				returnarr[0] = max; returnarr[1] = s;
				returnarr[2] = e;
				for(int x = 0; x<keytemp.length; x++) {
					returnarr[3] += keytemp[x];
				}
			}
		}
		
		return returnarr ;
	}
	
	public static File makeFile(String xmlPath) {
		File file = new File(xmlPath);
		return file;
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

}

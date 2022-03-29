package week2;

public class kuir { // main 함수

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// week2 String path = "./html"; "html //이 두가지 모두 같은 결과를 냄

		String command = args[0];
		String path = args[1];
		
		if(command.equals("-c")) {
			makeCollection collection = new makeCollection(path);
			collection.makeXml();
			//"src/week2/book.xml"; 에 write
		}
		else if(command.equals("-k")) {
			makeKeyword collection = new makeKeyword(path);
			collection.convertXml();
			//"./index.xml"; 에 write
		}
		else if(command.equals("-i")) {
			indexer collection = new indexer(path);
			collection.convertXmlToPost();
		}
//		makeCollection week2_result = new makeCollection("./html");
//		makeKeyword week3_result = new makeKeyword("./collection.xml");
//		indexer week4_result = new indexer("./index.xml");
		
		
		
		
		
		
//		week4_ParsingXmlToPost week4_result = new week4_ParsingXmlToPost("./index.xml", "./index.post");
		
		// 대면 수업 때 질문 -> 지난주에 교수님께서 이런식으로 클래스 구조를 만들라고하셨는데, 객체에는 뭘 남겨야할지 모르겠음
		// 이번에는 파일을 생성하는 역할만 해서 객체에 뭘 남길지 모르겟음 & 역할이 객체보다는 함수에 가깝지 않나?

	}

}

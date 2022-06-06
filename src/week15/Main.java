package week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String clientId = "Q9GflWp_jGutF1J3rh_A";
		String clientSecret = "931Nx8HcR7";
		
		BufferedReader br1 =  new BufferedReader(new InputStreamReader(System.in));
		System.out.print("검색어를 입력하세요: ");
		String text = URLEncoder.encode(br1.readLine(), "UTF-8");
		/* String text = URLEncoder.encode("그린팩토리", "UTF-8"); */
		/* String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; */
		String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text;
        URL url = new URL(apiURL);	
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode == HttpURLConnection.HTTP_OK) {
        	br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }else {
        	br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while((inputLine = br.readLine())!= null) {
        	response.append(inputLine);
        }
//        System.out.println(responseCode);
//        System.out.println(response);
        
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		JSONArray infoArray = (JSONArray) jsonObject.get("items");
		
//		for(int i=0; i<infoArray.size(); i++) {
//			System.out.println("=item"+i+"===============================");
//			JSONObject itemObject = (JSONObject) infoArray.get(i);
//			System.out.println("title:\t"+itemObject.get("title"));
//			System.out.println("link:\t"+itemObject.get("link"));
//			System.out.println("description:\t"+itemObject.get("description"));
//			System.out.println("bloggername:\t"+itemObject.get("bloggername"));
//		}
		
		for(int i=0; i<infoArray.size(); i++) {
			System.out.println("=item_"+i+"===============================");
			JSONObject itemObject = (JSONObject) infoArray.get(i);
			System.out.println("title:\t"+itemObject.get("title"));
			System.out.println("subtitle:\t"+itemObject.get("subtitle"));
			System.out.println("director:\t"+itemObject.get("director"));
			System.out.println("actor:\t"+itemObject.get("actor"));
			System.out.println("userRating:\t"+itemObject.get("userRating"));
		}
		
		
		
	}
	
	

}

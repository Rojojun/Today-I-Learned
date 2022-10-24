import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
public class ApiExplorer {
    static String serviceKey;
    static String encoding;
    static int cnt;
    static int rows;
    public static void main(String[] args) throws IOException {

        serviceKey = "=**";
        encoding = "UTF-8";

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/searchFestival"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("serviceKey",encoding) + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo",encoding) + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows",encoding) + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS",encoding) + "=" + URLEncoder.encode("ETC", "UTF-8")); /*모바일 OS 선택*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp",encoding) + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*어플리케이션의 이름 -> 사용자가 정한 이름*/
        urlBuilder.append("&" + URLEncoder.encode("_type",encoding) + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("listYN",encoding) + "=" + URLEncoder.encode("Y", "UTF-8")); /*List가 아닌지 확인 Y/N*/
        //urlBuilder.append("&" + URLEncoder.encode("stnId","UTF-8") + "=" + URLEncoder.encode("108", "UTF-8")); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("arrange",encoding) + "=" + URLEncoder.encode("C", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("eventStartDate",encoding) + "=" + URLEncoder.encode("20220701", "UTF-8")); /*축제 시작일을 전부 불러들여옴 형식은 YYYYMMDD*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        GetTotalCount getTotalCount = new GetTotalCount();
        getTotalCount.GetTotalCount(825, 10);
    }
}
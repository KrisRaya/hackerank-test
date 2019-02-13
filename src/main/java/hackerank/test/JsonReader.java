package hackerank.test;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

public class JsonReader {
    public static void main(String[]args){
        String title = "Waterworld";
        String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + title;
        String json = getHTML(url);JSONObject jsonObject = new JSONObject(json);
        int totalPages = jsonObject.getInt("total_pages");
        ArrayList<String> result = getMovieTitles(title, totalPages);
        System.out.println(result);
//        System.out.println("\nOutput: \n" + getHTML("https://jsonmock.hackerrank.com/api/movies/search/?Title=Spiderman"));
    }

    public static String getHTML(String url) {
        StringBuilder sb = new StringBuilder();
        URLConnection urlConnection = null;
        InputStreamReader in = null;
        try {
            URL url1 = new URL(url);
            urlConnection = url1.openConnection();
            if(urlConnection!=null)
                urlConnection.setReadTimeout(60*1000);
            if(urlConnection!=null && urlConnection.getInputStream()!=null) {
                in = new InputStreamReader(urlConnection.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if(bufferedReader!=null){
                    int cp;
                    while ((cp=bufferedReader.read())!= -1){
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    public static ArrayList<String> getMovieTitles(String substr, int pages) {
        ArrayList<String> movieTitle = new ArrayList<>();
        for(int i=1;i<=pages;i++){
            String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr+"&page="+i;
            String json = getHTML(url);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data = jsonObject.getJSONArray("data");
                for(int j=0;j<data.length();j++) {
                    JSONObject title = data.getJSONObject(j);
                    String titles = title.getString("Title");
                    movieTitle.add(titles);
                }
        }
        Collections.sort(movieTitle);
        System.out.println(movieTitle.size());
        return movieTitle;
    }
}

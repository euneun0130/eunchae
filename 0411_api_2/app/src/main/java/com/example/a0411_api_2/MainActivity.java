package com.example.a0411_api_2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a0411_api_2.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    TextView text;
    String data;
    String key="oLlzWidFsNCa2%2BmykJOGK%2Fs%2BhowqGq2FnepaHeLxWjiysDTXde0O1ncOJu4vxjpAeaJLtdu2jB75x366J7mJDg%3D%3D";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
    }
    // Button을 클릭했을 때 쓰레드 통해 해당 함수 실행
    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data= getXmlData();
                        // 아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data); // TextView에 문자열  data 출력
                            }
                        });
                    }
                }).start();
                break;
        }
    }
    String getXmlData(){
        StringBuffer buffer=new StringBuffer();
        String queryUrl="http://apis.data.go.kr/1741000/MisfortuneSituationNoticeMsg2/getMisfortuneSituationNoticeMsgList?serviceKey=5rBp0kk9vFRI9%2Fq7XvvLuKzPRG8r7SNQTGe6yk76wNHlR1NHxo0b8DMCfgxjkKgKPF%2Fo0OgZ7oE0FCqlyrQI4w%3D%3D&pageNo=1&numOfRows=100&type=xml&flag=Y";
        try {
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성
            InputStream is = url.openStream();

            url.openConnection().getInputStream(); // url위치로 인풋스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            // inputstream 으로부터 xml 입력받기
            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag = null;
            xpp.next();
            int eventType= xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작 \n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기

                        if (tag.equals("row"));
                        else if (tag.equals("inpt_date")) {
                            buffer.append("날짜 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if (tag.equals("clmy_pttn_nm")) {
                            buffer.append("재난 유형명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if (tag.equals("titl")) {
                            buffer.append("내용 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("row")) buffer.append("\n"); // 첫번째 검색결과 끝 줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        buffer.append("파싱 끝 \n");
        return buffer.toString(); // StringBuffer 문자열 객체 반환
    }
}

package com.example.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Main5Activity extends AppCompatActivity {
    TextView text;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
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
        String queryUrl="http://apis.data.go.kr/1741000/MisfortuneSituationNoticeMsg2/getMisfortuneSituationNoticeMsgList?ServiceKey=tFDslU68Wgy6h6z%2BT%2BPHzDZKGRpIFxJ6AUACNy1bfppSxy2kx%2Ba2nN95cM5CFokPM6R7bje0Y4c4j2RxtkU%2Fcg%3D%3D&type=xml&pageNo=1&numOfRows=100&flag=Y";
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
package com.example.hangul;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_2 extends Fragment {

    TextView txtFileTextView;
    ArrayList<String> array_string_list;

    private RadioButton  RadioBtn1, RadioBtn2;

    InputStream inputStream;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_2, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment_2, container, false);


         txtFileTextView = (TextView)rootView.findViewById(R.id.txtFileTextView);
        //라디오 버튼 설정
        RadioBtn1 = (RadioButton) rootView.findViewById(R.id.RadioBtn1);
        RadioBtn2 = (RadioButton) rootView.findViewById(R.id.RadioBtn2);

        txtFileTextView.setText("버튼을 눌러 파일을 불러오기 해주세요");

        Button Btn1 = (Button)rootView.findViewById(R.id.txtFileBtn);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RadioBtn1.isChecked()){
                    /* 문장 단위 */
                    load_Moonjang_0_Txt();
                }else if(RadioBtn2.isChecked()){
                    /* 원본 단위 */
                   txtFileTextView.setText(load_Original_Txt());
                }
                else{
                    Toast.makeText(getActivity(),"위에 2개 버튼중 하나를 선택해주세요",Toast.LENGTH_SHORT).show();
                    txtFileTextView.setText("위에 2개 버튼중 하나를 선택해주세요");
                }

            }
        });
        return rootView;
    }

    /* < 문장단위 > 한 줄씩 나누기 과정  */
    public void load_Moonjang_0_Txt(){
        inputStream = getResources().openRawResource(R.raw.test1);
        array_string_list = new ArrayList<String>();

        try {
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"EUC_KR"));
            while(true){
                String string= bufferedReader.readLine(); //엔터로 인식

                if(string != null){
                    array_string_list.add(string);
                }else{
                    break;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i=0;
        txtFileTextView.setText("");
        while(true){
            if(i>=array_string_list.size()){
                break;
            }else if(array_string_list.get(i) != null){
                txtFileTextView.append(array_string_list.get(i++)+"\n"+"\n");
            }
        }

    }

    /* < 원본단위 > raw파일 안 그대로 불러오기 */
    private String load_Original_Txt(){
        String data= null;
        inputStream = getResources().openRawResource(R.raw.test1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try{
           int i= inputStream.read();
            while(i != -1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(), "MS949");
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return data;

    }

}

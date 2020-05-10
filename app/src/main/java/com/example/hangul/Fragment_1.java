package com.example.hangul;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Fragment_1 extends Fragment {

    EditText editText1;
    String str1;
    String word;
    String result2;
    String result2_Jumja;

    TextView Orignal_Word;//단어
    TextView Changed_Word;//자음분리

    TextView Jumja_Word;

    InputStream inputStream;

    ViewFlipper v_fllipper_jumja_0;
    ViewFlipper v_fllipper_jumja_1;
    ViewFlipper v_fllipper_jumja_2;
    ViewFlipper v_fllipper_jumja_3;
    ViewFlipper v_fllipper_jumja_4;
    ViewFlipper v_fllipper_jumja_5;
    ViewFlipper v_fllipper_jumja_6;
    ViewFlipper v_fllipper_jumja_7;
    ViewFlipper v_fllipper_jumja_8;
    ViewFlipper v_fllipper_jumja_9;
    ViewFlipper v_fllipper_jumja_10;
    ViewFlipper v_fllipper_jumja_11;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_1, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment_1, container, false);


        Orignal_Word = (TextView)rootView.findViewById(R.id.OriginalWord);
        editText1 = (EditText)rootView.findViewById(R.id.EditText);

        Button Cut_Btn = (Button)rootView.findViewById(R.id.CutBtn);
        Button load_Btn = (Button)rootView.findViewById(R.id.loadBtn);
        Cut_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ChangeTextView1(editText1.getText().toString());
            }
        });
        load_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // load_Original_Txt();
                Orignal_Word.setText(load_Original_Txt());

            }
        });


        return rootView;
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
            Toast.makeText(getActivity(),"텍스트파일를 불러오기에 성공했습니다.",Toast.LENGTH_SHORT).show();

            inputStream.close();
        }catch (IOException e){
            Toast.makeText(getActivity(),"텍스트파일를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        ChangeTextView1(data);
        return  data;

    }

    /* **********************************************
     * 자음 모음 분리
     * 설연수 -> ㅅㅓㄹㅇㅕㄴㅅㅜ,    바보 -> ㅂㅏㅂㅗ
     * **********************************************/
    /** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ) */
    /** ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ */
    public static char[] arrChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
            0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
            0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
    /** ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ  */
    /** ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ  */
    public static String[] Jumja_ChoSung = {
            "000100" , "000001000100" , "100100", "010100" , "000001010100" , "000010", "100010", "000110" , "000001000110",
            "000001", "000001000001","000000", "000101", "000001000101", "000011", "110100", "110010", "100110", "010110"
    };

    /** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
    /* ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ */
    public static char[] arrJungSung = { 0x314f, 0x3150, 0x3151, 0x3152,
            0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
            0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162,
            0x3163 };
    /*  ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ */
    /** ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ */
    public static String[] Jumja_JungSung = {
            "110001", "111010", "001110", "001110111010", "011100", "101110", "100011", "001100", "101001","111001",
            "111001111010", "101111", "001101", "101100", "111100", "111100111010", "101100111010", "100101", "010101", "010111", "101010"
    };

    /** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
    /** X ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ */
    public static char[] arrJongSung = { 0x0000, 0x3131, 0x3132, 0x3133,
            0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
            0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
            0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
    /** X ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ */
    /** ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ */
    /** ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ */
    /** ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ */
    public static String[] Jumja_JongSung = {
            "000000", "100000", "100000100000", "100000001000", "010010", "010010101000", "010010001011",
            "001010", "010000", "010000100000", "01000010001", "010000110000", "010000001000", "010000011001",
            "010000010011", "010000001011", "010001","110000", "110000001000","001000","001000001000",
            "011011","101000","011000","011010","011001","010011","001011"
    };

    /** 가 (까) 나 다 (따) ^라^ 마 바 (빠) 사 (싸) ^아^ 자 (짜) ^차^ 카 타 파 하 */
    public static String[] Jumja_Yakja_Chosung = {
            "110101" ,"000001110101", "100100" , "010100","000001010100", "000010110001", " 100010", "000110","000001000110","111000","000001111000","110001","000101","000001000101","000011110001","110100","110010","100110","010110"
    };

    /** 0 1 2 3 4 5 6 7 8 9   */
    public static String[] Jumja_Yakja_Number = {
            "010110", "100000", "110000","100100","100110","100010","110100","110110","110010","010100"
    };


    public void ChangeTextView1(String str1) {
        editText1 = (EditText)getActivity().findViewById(R.id.EditText);
        Orignal_Word = (TextView)getActivity().findViewById(R.id.OriginalWord);
        Changed_Word = (TextView)getActivity().findViewById(R.id.ChangedWord_word);
        Jumja_Word = (TextView)getActivity().findViewById(R.id.JumjaWord);

         word 		= str1;		// 분리할 단어
         result2 = "";
         result2_Jumja = "";

        int checkYakja = 0; // 약자 사용 체크 용 변수
        int checkNumber = 0; // 숫자 사용 체크 용 변수

        for (int i = 0; i < word.length(); i++) {

            checkYakja = 0;
            checkNumber= 0;
            /*  한글자씩 읽어들인다. */
            char chars = (char) (word.charAt(i) - 0xAC00); // 한글+영어 한글자씩
            char charsNumber = word.charAt(i); // 숫자 한글자씩

 /* 숫자 일경우 */
                if (Character.getNumericValue(charsNumber) >= 0 && Character.getNumericValue(charsNumber) <= 9) {
                    int checkNumber2 = Character.getNumericValue(charsNumber)%10; //일의 자리 숫자 확인용
                    switch (checkNumber2){
    case 0:
        result2 = result2 +"0"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 1:
        result2 = result2 +"1"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 2:
        result2 = result2 +"2"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 3:
        result2 = result2 +"3"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 4:
        result2 = result2 +"4"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 5:
        result2 = result2 +"5"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 6:
        result2 = result2 +"6"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 7:
        result2 = result2 +"7"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 8:
        result2 = result2 +"8"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
    case 9:
        result2 = result2 +"9"+ ">>"+Jumja_Yakja_Number[checkNumber2] +"\n";
        result2_Jumja = result2_Jumja +Jumja_Yakja_Number[checkNumber2] + "/";
        checkNumber= 1;
        break;
}
                    Changed_Word.setText(result2);
                }
/* 숫자가 아닐경우*/
if(checkNumber == 0) {
    if (chars >= 0 && chars <= 11172) {
        /* A. 자음과 모음이 합쳐진 글자인경우 */

        /* A-1. 초/중/종성 분리 */
        int chosung = chars / (21 * 28);
        int jungsung = chars % (21 * 28) / 28;
        int jongsung = chars % (21 * 28) % 28;

 /* <것>  */
        if (Jumja_ChoSung[chosung] == "000100" && Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "001000") {
            checkYakja = 1;
            result2 = result2 + "것" + ">>" + "000111011100" + "\n";
            result2_Jumja = result2_Jumja +"000111011100" + "/";
        }

 /* < 억 언 얼 > */
        if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "100000") {
            /*  <억>   */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "억(약자)" + ">>" + "100111" + "\n";
                result2_Jumja = result2_Jumja + "100111" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄱ" + ">>" + "100111" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"100111" + "/";
            }
        } else if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "010010") {
            /*  <언>   */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "언(약자)" + ">>" + "011111" + "\n";
                result2_Jumja = result2_Jumja + "011111" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄴ" + ">>" + "011111" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"011111" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "010000") {
            /*  <얼>   */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "얼(약자)" + ">>" + "011110" + "\n";
                result2_Jumja = result2_Jumja + "011110" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄹ" + ">>" + "011110" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"011110" + "/";
            }
        }

/* < 연 열 영 > */
        if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "010010") {
            /*  <연>   */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "연(약자)" + ">>" + "100001" + "\n";
                result2_Jumja = result2_Jumja + "100001" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㄴ" + ">>" + "100001" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"100001" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "010000") {
            /*  <열>  , 종성이 ㅕ 일경우  */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "열(약자)" + ">>" + "110011" + "\n";
                result2_Jumja = result2_Jumja + "110011" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㄹ" + ">>" + "110011" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"110011" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "011011") {
            /*  <영>  , 종성이  일경우  */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "영(약자)" + ">>" + "110111" + "\n";
                result2_Jumja = result2_Jumja + "110111" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㅇ" + ">>" + "110111" + "\n";
                result2_Jumja = result2_Jumja + Jumja_ChoSung[chosung]+ "/" +"110111" + "/";
            }
        }

 /* < 옥 온 옹 > */
        if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "100000") {
            /*  <옥>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "옥(약자)" + ">>" + "101101" + "\n";
                result2_Jumja = result2_Jumja + "101101" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㄱ" + ">>" + "101101" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"101101" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "010010") {
            /*  <온>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "온(약자)" + ">>" + "111011" + "\n";
                result2_Jumja = result2_Jumja + "111011" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㄴ" + ">>" + "111011" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"111011" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "011011") {
            /*  <옹>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "옹(약자)" + ">>" + "111111" + "\n";
                result2_Jumja = result2_Jumja + "111111" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㅇ" + ">>" + "111111" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"111111" + "/";
            }
        }

 /* < 운 울 > */
        if (Jumja_JungSung[jungsung] == "101100" && Jumja_JongSung[jongsung] == "010010") {
            /*  <운>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "운(약자)" + ">>" + "110110" + "\n";
                result2_Jumja = result2_Jumja + "110110" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅜㄴ" + ">>" + "110110" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"110110" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "101100" && Jumja_JongSung[jongsung] == "010000") {
            /*  <울>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "울(약자)" + ">>" + "111101" + "\n";
                result2_Jumja = result2_Jumja + "111101" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅜㄹ" + ">>" + "111101" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"111101" + "/";
            }
        }

 /* < 은 을 > */
        if (Jumja_JungSung[jungsung] == "010101" && Jumja_JongSung[jongsung] == "010010") {
            /*  <은>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "은(약자)" + ">>" + "101011" + "\n";
                result2_Jumja = result2_Jumja + "101011" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅡㄴ" + ">>" + "101011" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"101011" + "/";
            }

        } else if (Jumja_JungSung[jungsung] == "010101" && Jumja_JongSung[jongsung] == "010000") {
            /*  <을>    */
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "을(약자)" + ">>" + "011101" + "\n";
                result2_Jumja = result2_Jumja + "011101" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅡㄹ" + ">>" + "011101" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"011101" + "/";
            }

        }

/* < 인 > */
        else if (Jumja_JungSung[jungsung] == "101010" && Jumja_JongSung[jongsung] == "010010") {
            checkYakja = 1;
            if (Jumja_ChoSung[chosung] == "000000") {
                /** 초성이  ' ㅇ ' 일경우 */
                result2 = result2 + "인(약자)" + ">>" + "111110" + "\n";
                result2_Jumja = result2_Jumja + "111110" + "/";
            }else{
                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅣㄴ" + ">>" + "111110" + "\n";
                result2_Jumja = result2_Jumja+ Jumja_ChoSung[chosung]+ "/" +"111110" + "/";
            }

        } else {
/* 약자 외 */
            if (checkYakja == 0) {
                if (Jumja_ChoSung[chosung] == "000000") {
                    /** 초성이  ' ㅇ ' 일경우 */
                    result2 = result2 + arrChoSung[chosung] + "+" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                    result2_Jumja = result2_Jumja +Jumja_JungSung[jungsung] + "/";
                    if (jongsung != 0x0000) {
                        /* A-3. 종성이 존재할경우 result에 담는다 */
                        result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_JongSung[jongsung] + "/";
                    }
                } // 초성이 'ㅇ' 일경우

                else if (Jumja_ChoSung[chosung] == "000010" || Jumja_ChoSung[chosung] == "000011") {
                    /* 초성이 'ㄹ, ㅊ' 일경우 */
                    if (Jumja_JungSung[jungsung] == "110001") {
                        /* 중성이 'ㅏ' 일경우 < 라, 차 > */
                        result2 = result2 + arrChoSung[chosung] + "+" + arrJungSung[jungsung] + ">>" + Jumja_Yakja_Chosung[chosung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_Yakja_Chosung[chosung] + "/";
                    } else {
                        result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_ChoSung[chosung]+ "/"+Jumja_JungSung[jungsung]+ "/";
                    }
                    if (jongsung != 0x0000) {
                        /* A-3. 종성이 존재할경우 result에 담는다 */
                        result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_JongSung[jongsung] + "/";
                    }
                }  // 초성이 'ㄹ, ㅊ' 일경우

                else if (Jumja_ChoSung[chosung] == "000001000100" || Jumja_ChoSung[chosung] == "000001010100" || Jumja_ChoSung[chosung] == "000001000110" || Jumja_ChoSung[chosung] == "000001000001" || Jumja_ChoSung[chosung] == "000001000101") {
                    /* 초성이 'ㄲ, ㄸ, ㅃ, ㅆ, ㅉ 일경우 */
                    if (Jumja_JungSung[jungsung] == "110001") {
                        /* 중성이 'ㅏ' 일경우 < 까, 따, 빠, 싸, 짜 > */
                        result2 = result2 + arrChoSung[chosung] + "+" + arrJungSung[jungsung] + ">>" + Jumja_Yakja_Chosung[chosung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_Yakja_Chosung[chosung] + "/";
                    } else {
                        result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                        result2_Jumja = result2_Jumja + Jumja_ChoSung[chosung]+ "/"+Jumja_JungSung[jungsung]+ "/";
                    }
                    if (jongsung != 0x0000) {
                        /* A-3. 종성이 존재할경우 result에 담는다 */
                        result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_JongSung[jongsung] + "/";
                    }
                }  // 초성이 'ㄹ, ㅊ' 일경우

                else {
                    /** 초성이 'ㄱ, ㄴ, ㄷ, ㅁ, ㅂ, ㅅ, ㅈ, ㅋ, ㅌ, ㅍ, ㅎ' 일경우 */
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                    result2_Jumja = result2_Jumja + Jumja_ChoSung[chosung]+ "/"+Jumja_JungSung[jungsung]+ "/";

                    if (jongsung != 0x0000) {
                        /* A-3. 종성이 존재할경우 result에 담는다 */
                        result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                        result2_Jumja = result2_Jumja +Jumja_JongSung[jongsung] + "/";
                    }
                }
            } // 초성이 나머지 일경우
        }
    } else {
/*  한글이 아니거나 자음만 있을경우 */
        result2 = result2 + ((char) (chars + 0xAC00));
    }//if
}

 }//for

        /* 분리된 문자를 보여주기 표현 */
        Orignal_Word.setText(word);
        Changed_Word.setText(result2);
        Jumja_Word.setText(result2_Jumja);

       Jumja_Change_Word(result2_Jumja); //그림으로 표현

    }

    public void Jumja_Change_Word( String str){
        String[] str2 = str.split("/");
        String[] str4 = new String[str2.length];



        v_fllipper_jumja_0 =getActivity().findViewById(R.id.flipper_jumja0);
        v_fllipper_jumja_1 =getActivity().findViewById(R.id.flipper_jumja1);
        v_fllipper_jumja_2 =getActivity().findViewById(R.id.flipper_jumja2);
        v_fllipper_jumja_3 =getActivity().findViewById(R.id.flipper_jumja3);
        v_fllipper_jumja_4 =getActivity().findViewById(R.id.flipper_jumja4);
        v_fllipper_jumja_5 =getActivity().findViewById(R.id.flipper_jumja5);
        v_fllipper_jumja_6 =getActivity().findViewById(R.id.flipper_jumja6);
        v_fllipper_jumja_7 =getActivity().findViewById(R.id.flipper_jumja7);
        v_fllipper_jumja_8 =getActivity().findViewById(R.id.flipper_jumja8);
        v_fllipper_jumja_9 =getActivity().findViewById(R.id.flipper_jumja9);
        v_fllipper_jumja_10 =getActivity().findViewById(R.id.flipper_jumja10);
        v_fllipper_jumja_11 =getActivity().findViewById(R.id.flipper_jumja11);

        int displayedChild = v_fllipper_jumja_0.getDisplayedChild(); // 현재 위치 인덱스를 가져옴 "0"
        int childCount  = v_fllipper_jumja_0.getChildCount(); //전체 아이템 갯수

        stop_Flipper();    /* 뷰플리퍼 멈추기  */
        remove_Flipper(); /* 다시 분해 버튼 누를 경우 모든 소스 제거 하고 초기 화면만 추가 */

        for(int i = 0 ; i < str2.length; i++){
            str4[i] = str2[i];

            for(int j = 0; j < str4[i].length(); j++) {

                int images[] = new int[str4[i].length()];
                ImageView imageView0 = new ImageView(getContext().getApplicationContext());
                ImageView imageView1 = new ImageView(getContext().getApplicationContext());
                ImageView imageView2 = new ImageView(getContext().getApplicationContext());
                ImageView imageView3 = new ImageView(getContext().getApplicationContext());
                ImageView imageView4 = new ImageView(getContext().getApplicationContext());
                ImageView imageView5 = new ImageView(getContext().getApplicationContext());
                ImageView imageView6 = new ImageView(getContext().getApplicationContext());
                ImageView imageView7 = new ImageView(getContext().getApplicationContext());
                ImageView imageView8 = new ImageView(getContext().getApplicationContext());
                ImageView imageView9 = new ImageView(getContext().getApplicationContext());
                ImageView imageView10 = new ImageView(getContext().getApplicationContext());
                ImageView imageView11 = new ImageView(getContext().getApplicationContext());

                int int4 =  Integer.parseInt(str4[i].substring(j,j+1));

                /*  분리하는 과정에서 6자리 + j == 0인 이유 */
                /*  만약 j== 0를 안 넣어주면 12자리 점자일 경우 점자67891011이 12번 반복됨 */
                /*  즉, 점자변환기 좌측, 우측이 달라지게 됨  */
                if(str4[i].length() == 6 && j == 0){
                    images[j]=R.drawable.white;

                    imageView6.setImageResource(images[j]);
                    imageView7.setImageResource(images[j]);
                    imageView8.setImageResource(images[j]);
                    imageView9.setImageResource(images[j]);
                    imageView10.setImageResource(images[j]);
                    imageView11.setImageResource(images[j]);

                    v_fllipper_jumja_6.addView(imageView6);
                    v_fllipper_jumja_7.addView(imageView7);
                    v_fllipper_jumja_8.addView(imageView8);
                    v_fllipper_jumja_9.addView(imageView9);
                    v_fllipper_jumja_10.addView(imageView10);
                    v_fllipper_jumja_11.addView(imageView11);
                }

                /*  스위치문을 사용하여 뷰플리퍼 값 입력   */
                switch (j){
                    case 0:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView0.setImageResource(images[j]);
                            v_fllipper_jumja_0.addView(imageView0);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView0.setImageResource(images[j]);
                            v_fllipper_jumja_0.addView(imageView0);
                        }
                        break;
                    case 1:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView1.setImageResource(images[j]);
                            v_fllipper_jumja_1.addView(imageView1);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView1.setImageResource(images[j]);
                            v_fllipper_jumja_1.addView(imageView1);
                        }
                        break;
                    case 2:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView2.setImageResource(images[j]);
                            v_fllipper_jumja_2.addView(imageView2);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView2.setImageResource(images[j]);
                            v_fllipper_jumja_2.addView(imageView2);
                        }
                        break;
                    case 3:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView3.setImageResource(images[j]);
                            v_fllipper_jumja_3.addView(imageView3);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView3.setImageResource(images[j]);
                            v_fllipper_jumja_3.addView(imageView3);
                        }
                        break;
                    case 4:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView4.setImageResource(images[j]);
                            v_fllipper_jumja_4.addView(imageView4);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView4.setImageResource(images[j]);
                            v_fllipper_jumja_4.addView(imageView4);
                        }
                        break;
                    case 5:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView5.setImageResource(images[j]);
                            v_fllipper_jumja_5.addView(imageView5);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView5.setImageResource(images[j]);
                            v_fllipper_jumja_5.addView(imageView5);
                        }
                        break;
                    case 6:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView6.setImageResource(images[j]);
                            v_fllipper_jumja_6.addView(imageView6);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView6.setImageResource(images[j]);
                            v_fllipper_jumja_6.addView(imageView6);
                        }
                        break;
                    case 7:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView7.setImageResource(images[j]);
                            v_fllipper_jumja_7.addView(imageView7);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView7.setImageResource(images[j]);
                            v_fllipper_jumja_7.addView(imageView7);
                        }
                        break;
                    case 8:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView8.setImageResource(images[j]);
                            v_fllipper_jumja_8.addView(imageView8);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView8.setImageResource(images[j]);
                            v_fllipper_jumja_8.addView(imageView8);
                        }
                        break;
                    case 9:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView9.setImageResource(images[j]);
                            v_fllipper_jumja_9.addView(imageView9);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView9.setImageResource(images[j]);
                            v_fllipper_jumja_9.addView(imageView9);
                        }
                        break;
                    case 10:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView10.setImageResource(images[j]);
                            v_fllipper_jumja_9.addView(imageView10);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView10.setImageResource(images[j]);
                            v_fllipper_jumja_10.addView(imageView10);
                        }
                        break;
                    case 11:
                        if(int4 ==1){
                            images[j]=R.drawable.black;
                            imageView11.setImageResource(images[j]);
                            v_fllipper_jumja_11.addView(imageView11);
                        }else if (int4 == 0){
                            images[j]=R.drawable.white;
                            imageView11.setImageResource(images[j]);
                            v_fllipper_jumja_11.addView(imageView11);
                        }
                        break;
                }
            }
        }
        change_Flipper();

        if (displayedChild == childCount -1 ) {

            Toast.makeText(getActivity(),"약"+(str2.length+1)*2 +"초 후 멈추기",Toast.LENGTH_SHORT).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stop_Flipper();
                    remove_Flipper();
                }
            },2000*(str2.length+1));

        }
    }

    /* 다시 분해 버튼 누를 경우 모든 소스 제거 하고 초기 화면만 추가 */
    public void remove_Flipper(){
        v_fllipper_jumja_0.removeAllViews();
        v_fllipper_jumja_1.removeAllViews();
        v_fllipper_jumja_2.removeAllViews();
        v_fllipper_jumja_3.removeAllViews();
        v_fllipper_jumja_4.removeAllViews();
        v_fllipper_jumja_5.removeAllViews();
        v_fllipper_jumja_6.removeAllViews();
        v_fllipper_jumja_7.removeAllViews();
        v_fllipper_jumja_8.removeAllViews();
        v_fllipper_jumja_9.removeAllViews();
        v_fllipper_jumja_10.removeAllViews();
        v_fllipper_jumja_11.removeAllViews();

        int addWhite = R.drawable.white;
        ImageView imageView0 = new ImageView(getContext().getApplicationContext());
        ImageView imageView1 = new ImageView(getContext().getApplicationContext());
        ImageView imageView2 = new ImageView(getContext().getApplicationContext());
        ImageView imageView3 = new ImageView(getContext().getApplicationContext());
        ImageView imageView4 = new ImageView(getContext().getApplicationContext());
        ImageView imageView5 = new ImageView(getContext().getApplicationContext());
        ImageView imageView6 = new ImageView(getContext().getApplicationContext());
        ImageView imageView7 = new ImageView(getContext().getApplicationContext());
        ImageView imageView8 = new ImageView(getContext().getApplicationContext());
        ImageView imageView9 = new ImageView(getContext().getApplicationContext());
        ImageView imageView10 = new ImageView(getContext().getApplicationContext());
        ImageView imageView11 = new ImageView(getContext().getApplicationContext());

        imageView0.setImageResource(addWhite);
        imageView1.setImageResource(addWhite);
        imageView2.setImageResource(addWhite);
        imageView3.setImageResource(addWhite);
        imageView4.setImageResource(addWhite);
        imageView5.setImageResource(addWhite);
        imageView6.setImageResource(addWhite);
        imageView7.setImageResource(addWhite);
        imageView8.setImageResource(addWhite);
        imageView9.setImageResource(addWhite);
        imageView10.setImageResource(addWhite);
        imageView11.setImageResource(addWhite);


        v_fllipper_jumja_0.addView(imageView0);
        v_fllipper_jumja_1.addView(imageView1);
        v_fllipper_jumja_2.addView(imageView2);
        v_fllipper_jumja_3.addView(imageView3);
        v_fllipper_jumja_4.addView(imageView4);
        v_fllipper_jumja_5.addView(imageView5);
        v_fllipper_jumja_6.addView(imageView6);
        v_fllipper_jumja_7.addView(imageView7);
        v_fllipper_jumja_8.addView(imageView8);
        v_fllipper_jumja_9.addView(imageView9);
        v_fllipper_jumja_10.addView(imageView10);
        v_fllipper_jumja_11.addView(imageView11);



    }

    /* 뷰플리퍼 멈추기  */
    public void stop_Flipper(){
        v_fllipper_jumja_0.stopFlipping();
        v_fllipper_jumja_1.stopFlipping();
        v_fllipper_jumja_2.stopFlipping();
        v_fllipper_jumja_3.stopFlipping();
        v_fllipper_jumja_4.stopFlipping();
        v_fllipper_jumja_5.stopFlipping();
        v_fllipper_jumja_6.stopFlipping();
        v_fllipper_jumja_7.stopFlipping();
        v_fllipper_jumja_8.stopFlipping();
        v_fllipper_jumja_9.stopFlipping();
        v_fllipper_jumja_10.stopFlipping();
        v_fllipper_jumja_11.stopFlipping();
    }

    /* 실질적으로 뷰플리퍼 사용하기 기능 */
    public void change_Flipper(){

        // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_jumja_0.setFlipInterval(2000);
        v_fllipper_jumja_1.setFlipInterval(2000);
        v_fllipper_jumja_2.setFlipInterval(2000);
        v_fllipper_jumja_3.setFlipInterval(2000);
        v_fllipper_jumja_4.setFlipInterval(2000);
        v_fllipper_jumja_5.setFlipInterval(2000);
        v_fllipper_jumja_6.setFlipInterval(2000);
        v_fllipper_jumja_7.setFlipInterval(2000);
        v_fllipper_jumja_8.setFlipInterval(2000);
        v_fllipper_jumja_9.setFlipInterval(2000);
        v_fllipper_jumja_10.setFlipInterval(2000);
        v_fllipper_jumja_11.setFlipInterval(2000);

        // 자동 시작 유무 설정
        v_fllipper_jumja_0.setAutoStart(true);
        v_fllipper_jumja_1.setAutoStart(true);
        v_fllipper_jumja_2.setAutoStart(true);
        v_fllipper_jumja_3.setAutoStart(true);
        v_fllipper_jumja_4.setAutoStart(true);
        v_fllipper_jumja_5.setAutoStart(true);
        v_fllipper_jumja_6.setAutoStart(true);
        v_fllipper_jumja_7.setAutoStart(true);
        v_fllipper_jumja_8.setAutoStart(true);
        v_fllipper_jumja_9.setAutoStart(true);
        v_fllipper_jumja_10.setAutoStart(true);
        v_fllipper_jumja_11.setAutoStart(true);

        // 시작
        v_fllipper_jumja_0.startFlipping();
        v_fllipper_jumja_1.startFlipping();
        v_fllipper_jumja_2.startFlipping();
        v_fllipper_jumja_3.startFlipping();
        v_fllipper_jumja_4.startFlipping();
        v_fllipper_jumja_5.startFlipping();
        v_fllipper_jumja_6.startFlipping();
        v_fllipper_jumja_7.startFlipping();
        v_fllipper_jumja_8.startFlipping();
        v_fllipper_jumja_9.startFlipping();
        v_fllipper_jumja_10.startFlipping();
        v_fllipper_jumja_11.startFlipping();

        // 애니메이션 시작화면
        v_fllipper_jumja_0.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_1.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_2.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_3.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_4.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_5.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_6.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_7.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_8.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_9.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_10.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);
        v_fllipper_jumja_11.setInAnimation(getContext().getApplicationContext(),android.R.anim.fade_in);

        //애니메이션 끝 화면
        v_fllipper_jumja_0.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_1.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_2.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_3.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_4.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_5.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_6.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_7.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_8.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_9.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_10.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);
        v_fllipper_jumja_11.setOutAnimation(getContext().getApplicationContext(),android.R.anim.fade_out);

    }

}

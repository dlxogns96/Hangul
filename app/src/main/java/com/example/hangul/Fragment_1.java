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

import java.util.Timer;
import java.util.TimerTask;

public class Fragment_1 extends Fragment {

    EditText editText1;
    String str1;
    String word;
    String result2;
    String result2_Jumja;

    TextView Orignal_Word;//단어
    TextView Changed_Word;//자음분리

    TextView Jumja_Word;

    ImageView[] Jumja;
    ImageView Jumja1; // 점자 표시
    ImageView Jumja2;
    ImageView Jumja3;
    ImageView Jumja4;
    ImageView Jumja5;
    ImageView Jumja6;
    ImageView Jumja7;
    ImageView Jumja8;
    ImageView Jumja9;
    ImageView Jumja10;
    ImageView Jumja11;
    ImageView Jumja12;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_1, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment_1, container, false);

        Button Btn1 = (Button)rootView.findViewById(R.id.Btn1);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ChangeTextView1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
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
    /**  억 언 얼 연 열 영 */
    /**  옥 온 옹 운 울 은 을 인 */
    /** 그래서 그러나 그러면 그러므로 그런데 그리고 그리하여 */
    /** ㅆ받침 */
    public static String[] Jumja_Yakja_o = {
            "100111","011111","011110","100001","110011","110111",
            "101101", "111011","111111","110110","111101","101011","011101","111110",

            "100000011100","100000100100","100000010010","100000010001","100000101110","100000101001","100000100011",
            "001100"
    };
    /** 것  */
    public static String[] Jumja_Yakja_rjt = {
                    "000111011100"
            };

    /** 0 1 2 3 4 5 6 7 8 9 */
    public static String[] arrNumber = { "0","1","2","3","4","5","6","7","8","9"};
    public static char[] arrNumber2 = { 0x48,0x49,0x50,0x51,0x52,0x53,0x54,0x55,0x56,0x57};



    /** 0 1 2 3 4 5 6 7 8 9   */
    public static String[] Jumja_Yakja_Number = {
            "010110", "100000", "110000","100100","100110","100010","110100","110110","110010","010100"
    };



    public void ChangeTextView1() throws InterruptedException {

        editText1 = (EditText)getActivity().findViewById(R.id.EditText);
        Orignal_Word = (TextView)getActivity().findViewById(R.id.OriginalWord);
        Changed_Word = (TextView)getActivity().findViewById(R.id.ChangedWord_word);

        Jumja_Word = (TextView)getActivity().findViewById(R.id.JumjaWord);
        Jumja1 = (ImageView)getActivity().findViewById(R.id.Jumja1) ;
        Jumja2 = (ImageView)getActivity().findViewById(R.id.Jumja2) ;
        Jumja3 = (ImageView)getActivity().findViewById(R.id.Jumja3) ;
        Jumja4 = (ImageView)getActivity().findViewById(R.id.Jumja4) ;
        Jumja5 = (ImageView)getActivity().findViewById(R.id.Jumja5) ;
        Jumja6 = (ImageView)getActivity().findViewById(R.id.Jumja6) ;
        Jumja7 = (ImageView)getActivity().findViewById(R.id.Jumja7) ;
        Jumja8 = (ImageView)getActivity().findViewById(R.id.Jumja8) ;
        Jumja9 = (ImageView)getActivity().findViewById(R.id.Jumja9) ;
        Jumja10 = (ImageView)getActivity().findViewById(R.id.Jumja10) ;
        Jumja11 = (ImageView)getActivity().findViewById(R.id.Jumja11) ;
        Jumja12 = (ImageView)getActivity().findViewById(R.id.Jumja12) ;


        Jumja = new ImageView[]{Jumja1, Jumja2,Jumja3,Jumja4,Jumja5,Jumja6,Jumja7,Jumja8,Jumja9,Jumja10,Jumja11,Jumja12 };


         str1= editText1.getText().toString();

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
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄱ" + ">>" + "100111" + "\n";
            result2_Jumja = result2_Jumja +"100111" + "/";
        } else if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "010010") {
            /*  <언>   */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄴ" + ">>" + "011111" + "\n";
            result2_Jumja = result2_Jumja +"011111" + "/";
        } else if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "010000") {
            /*  <얼>   */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄹ" + ">>" + "011110" + "\n";
            result2_Jumja = result2_Jumja +"011110" + "/";
        }

/* < 연 열 영 > */
        if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "010010") {
            /*  <연>   */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㄴ" + ">>" + "100001" + "\n";
            result2_Jumja = result2_Jumja +"100001" + "/";
        } else if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "010000") {
            /*  <열>  , 종성이 ㅕ 일경우  */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㄹ" + ">>" + "110011" + "\n";
            result2_Jumja = result2_Jumja +"110011" + "/";
        } else if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "011011") {
            /*  <영>  , 종성이  일경우  */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㅇ" + ">>" + "110111" + "\n";
            result2_Jumja = result2_Jumja +"110111" + "/";
        }

 /* < 옥 온 옹 > */
        if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "100000") {
            /*  <옥>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㄱ" + ">>" + "101101" + "\n";
            result2_Jumja = result2_Jumja +"101101" + "/";
        } else if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "010010") {
            /*  <온>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㄴ" + ">>" + "111011" + "\n";
            result2_Jumja = result2_Jumja +"111011" + "/";
        } else if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "011011") {
            /*  <옹>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㅇ" + ">>" + "111111" + "\n";
            result2_Jumja = result2_Jumja +"111111" + "/";
        }

 /* < 운 울 > */
        if (Jumja_JungSung[jungsung] == "101100" && Jumja_JongSung[jongsung] == "010010") {
            /*  <운>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅜㄴ" + ">>" + "110110" + "\n";
            result2_Jumja = result2_Jumja +"110110" + "/";
        } else if (Jumja_JungSung[jungsung] == "101100" && Jumja_JongSung[jongsung] == "010000") {
            /*  <울>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅜㄹ" + ">>" + "111101" + "\n";
            result2_Jumja = result2_Jumja +"111101" + "/";
        }

 /* < 은 을 > */
        if (Jumja_JungSung[jungsung] == "010101" && Jumja_JongSung[jongsung] == "010010") {
            /*  <은>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅡㄴ" + ">>" + "101011" + "\n";
            result2_Jumja = result2_Jumja +"101011" + "/";
        } else if (Jumja_JungSung[jungsung] == "010101" && Jumja_JongSung[jongsung] == "010000") {
            /*  <을>    */
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅡㄹ" + ">>" + "011101" + "\n";
            result2_Jumja = result2_Jumja +"011101" + "/";
        }

/* < 인 > */
        else if (Jumja_JungSung[jungsung] == "101010" && Jumja_JongSung[jongsung] == "010010") {
            checkYakja = 1;
            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅣㄴ" + ">>" + "111110" + "\n";
            result2_Jumja = result2_Jumja +"111110" + "/";
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
        Orignal_Word.setText(word);
        Changed_Word.setText(result2);
        Jumja_Word.setText(result2_Jumja);


        Jumja_Change_Word(result2_Jumja); //그림으로 표현

    }

    public void Jumja_Change_Word(String str){
        String [] str2 = str.split("/");


        for(int i = 0 ; i < str2.length; i++){
            final String str3 = str2[i];


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < str3.length(); j++) {
                        switch (str3.substring(j, j + 1)) {
                            case "1":
                                Jumja[j].setImageResource(R.drawable.black);
                                break;
                            case "0":
                                Jumja[j].setImageResource(R.drawable.white);
                                break;
                        }

                    }
                }
            },2000);

        }

    }
}

package com.example.hangul;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_1 extends Fragment {

    EditText editText1;
    TextView Orignal_Word;//단어
    TextView Changed_Word;//자음분리

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_1, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment_1, container, false);

        Button Btn1 = (Button)rootView.findViewById(R.id.Btn1);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextView1();
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
    public static char[] arrNumber = { 0x49,0x50,0x51,0x52,0x53,0x54,0x55,0x56,0x57 };

    /** 0 1 2 3 4 5 6 7 8 9   */
    public static String[] Jumja_Yakja_Number = {
            "010110", "100000", "110000","100100","100110","100010","110100","110110","110010","010100"
    };



    public void ChangeTextView1(){

        editText1 = (EditText)getActivity().findViewById(R.id.EditText);
        Orignal_Word = (TextView)getActivity().findViewById(R.id.OriginalWord);
        Changed_Word = (TextView)getActivity().findViewById(R.id.ChangedWord);


        String str1= editText1.getText().toString();

        String word 		= str1;		// 분리할 단어
        String result2      = "";

        int checkYakja = 0; // 약자 사용 체크 용 변수

        for (int i = 0; i < word.length(); i++) {

            /*  한글자씩 읽어들인다. */
            char chars = (char) (word.charAt(i) - 0xAC00);

            char chars2 = word.charAt(i);

            if(Character.isDigit(chars)==true){

                result2 = result2 + Character.getNumericValue(chars2)+ ">>" + "\n";

                //Character.getNumericValue(chars);
                Changed_Word.setText(result2);
            }


            checkYakja = 0;
            if (chars >= 0 && chars <= 11172) {
                /* A. 자음과 모음이 합쳐진 글자인경우 */

                /* A-1. 초/중/종성 분리 */
                int chosung = chars / (21 * 28);
                int jungsung = chars % (21 * 28) / 28;
                int jongsung = chars % (21 * 28) % 28;

                /* <것>  */
                if (Jumja_ChoSung[chosung] == "000100" && Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "001000") {
                    checkYakja =1;
                    result2 = result2 + "것" + ">>" + "000111011100" + "\n";
                }

                /* < 억 언 얼 > */
                if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "100000" ) {
                    /*  <억>   */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄱ" + ">>" + "100111" + "\n";
                } else if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "010010") {
                    /*  <언>   */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄴ" + ">>" + "011111" + "\n";
                } else if (Jumja_JungSung[jungsung] == "011100" && Jumja_JongSung[jongsung] == "010000") {
                    /*  <얼>   */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅓㄹ" + ">>" + "011110" + "\n";
                }

                /* < 연 열 영 > */
                if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "010010") {
                    /*  <연>   */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㄴ" + ">>" + "100001" + "\n";
                } else if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "010000") {
                    /*  <열>  , 종성이 ㅕ 일경우  */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㄹ" + ">>" + "110011" + "\n";
                } else if (Jumja_JungSung[jungsung] == "100011" && Jumja_JongSung[jongsung] == "011011") {
                    /*  <영>  , 종성이  일경우  */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅕㅇ" + ">>" + "110111" + "\n";
                }

                /* < 옥 온 옹 > */
                if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "100000") {
                    /*  <옥>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㄱ" + ">>" + "101101" + "\n";
                } else if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "010010") {
                    /*  <온>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㄴ" + ">>" + "111011" + "\n";
                } else if (Jumja_JungSung[jungsung] == "101001" && Jumja_JongSung[jongsung] == "011011") {
                    /*  <옹>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅗㅇ" + ">>" + "111111" + "\n";
                }

                /* < 운 울 > */
                if (Jumja_JungSung[jungsung] == "101100" && Jumja_JongSung[jongsung] == "010010") {
                    /*  <운>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅜㄴ" + ">>" + "110110" + "\n";
                } else if (Jumja_JungSung[jungsung] == "101100" && Jumja_JongSung[jongsung] == "010000") {
                    /*  <울>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅜㄹ" + ">>" + "111101" + "\n";
                }

                /* < 은 을 > */
                if (Jumja_JungSung[jungsung] == "010101" && Jumja_JongSung[jongsung] == "010010") {
                    /*  <은>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅡㄴ" + ">>" + "101011" + "\n";
                } else if (Jumja_JungSung[jungsung] == "010101" && Jumja_JongSung[jongsung] == "010000") {
                    /*  <을>    */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅡㄹ" + ">>" + "011101" + "\n";
                }

                /* < 인 > */
                else if (Jumja_JungSung[jungsung] == "101010" && Jumja_JongSung[jongsung] == "010010") {
                    /*  <인>  , 중성이 'ㅣ',  종성이 'ㄴ' 일경우  */
                    checkYakja =1;
                    result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + "ㅣㄴ" + ">>" + "111110" + "\n";
                }

                else {
                    if(checkYakja ==0){
                        if (Jumja_ChoSung[chosung] == "000000") {
                            /** 초성이  ' ㅇ ' 일경우 */
                                result2 = result2 + arrChoSung[chosung] +"+" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                                if (jongsung != 0x0000) {
                                    /* A-3. 종성이 존재할경우 result에 담는다 */
                                    result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                                }
                        } // 초성이 'ㅇ' 일경우

                        else if (Jumja_ChoSung[chosung] == "000010" || Jumja_ChoSung[chosung] == "000011") {
                            /* 초성이 'ㄹ, ㅊ' 일경우 */
                            if (Jumja_JungSung[jungsung] == "110001") {
                                /* 중성이 'ㅏ' 일경우 < 라, 차 > */
                                result2 = result2 + arrChoSung[chosung] + "+" + arrJungSung[jungsung] + ">>" + Jumja_Yakja_Chosung[chosung] + "\n";
                            } else {
                                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                            }
                            if (jongsung != 0x0000) {
                                /* A-3. 종성이 존재할경우 result에 담는다 */
                                result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                            }
                        }  // 초성이 'ㄹ, ㅊ' 일경우

                        else if (Jumja_ChoSung[chosung] == "000001000100" || Jumja_ChoSung[chosung] == "000001010100" || Jumja_ChoSung[chosung] == "000001000110" || Jumja_ChoSung[chosung] == "000001000001" || Jumja_ChoSung[chosung] == "000001000101") {
                            /* 초성이 'ㄲ, ㄸ, ㅃ, ㅆ, ㅉ 일경우 */
                            if (Jumja_JungSung[jungsung] == "110001") {
                                /* 중성이 'ㅏ' 일경우 < 까, 따, 빠, 싸, 짜 > */
                                result2 = result2 + arrChoSung[chosung] + "+" + arrJungSung[jungsung] + ">>" + Jumja_Yakja_Chosung[chosung] + "\n";
                            } else {
                                result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";
                            }
                            if (jongsung != 0x0000) {
                                /* A-3. 종성이 존재할경우 result에 담는다 */
                                result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                            }
                        }  // 초성이 'ㄹ, ㅊ' 일경우

                        else {
                            /** 초성이 'ㄱ, ㄴ, ㄷ, ㅁ, ㅂ, ㅅ, ㅈ, ㅋ, ㅌ, ㅍ, ㅎ' 일경우 */
                            result2 = result2 + arrChoSung[chosung] + ">>" + Jumja_ChoSung[chosung] + "\n" + arrJungSung[jungsung] + ">>" + Jumja_JungSung[jungsung] + "\n";

                            if (jongsung != 0x0000) {
                                /* A-3. 종성이 존재할경우 result에 담는다 */
                                result2 = result2 + arrJongSung[jongsung] + "(종성)>>" + Jumja_JongSung[jongsung] + "\n";
                            }
                        }
                    } // 초성이 나머지 일경우
                }
            } else {
                /* B. 한글이 아니거나 자음만 있을경우 */
                /* 자음분리 */
                result2 = result2 + ((char)(chars + 0xAC00));
            }//if
        }//for

        Orignal_Word.setText(word);
      //  Changed_Word.setText(result2);
    }
}

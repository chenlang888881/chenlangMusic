package com.example.lenovo.myapplication.util;

import java.io.UnsupportedEncodingException;

/**
 * 将汉字转为拼音的第一个首字母
 * Created by Lenovo on 2015/12/20.
 */
public class ChineseToFirstCapital {
    static final int GB_SP_DIFF=160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212,
            3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

    public static String getSpell(String str){
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i< str.length(); i++){
            char ch = str.charAt(i);
            if((ch >> 7) == 0){
                buffer.append(ch);
            }else {
                char spell = getFirstSpell(ch);
                buffer.append(spell);
            }
        }
        return buffer.toString();
    }

    private static Character getFirstSpell(char ch) {
        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(uniCode[0] < 128 && uniCode[0] > 0){ // 非汉字
            return null;
        }else {
          return convert(uniCode);
        }
    }

    private static char convert(byte[] uniCode) {
        char result = '-';
        int posValue = 0;
        for (int i = 0; i< uniCode.length; i++){
            uniCode[i] -= GB_SP_DIFF;
        }
        posValue = uniCode[0] * 100 + uniCode[1];
        for (int i = 0; i < 23; i++){
            if(posValue >= secPosValueList[i] && posValue < secPosValueList[i+1]){
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }
}

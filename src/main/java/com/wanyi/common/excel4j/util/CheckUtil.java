package com.wanyi.common.excel4j.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cocomao on 15/10/19.
 */
public class CheckUtil {
    static String phonePaternStr = "^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";// �ֻ��ŵ�������ʽ
    static Pattern phonePattern = Pattern.compile(phonePaternStr);
    static String nickNamePatternStr = "[\"\\\\]+";
    static Pattern nickNamePattern = Pattern.compile(nickNamePatternStr);
    /**
     * �ж��ṩ�ֻ����Ƿ��ǺϷ����ֻ���
     *
     * @param phone
     * @return
     */
    public static boolean isValidePhoneNum(String phone) {

        Matcher ma = phonePattern.matcher(phone);
        if (ma.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ��֤�ַ�����
     * @param str
     * @param length
     * @return
     */
    public static boolean isValideLength(String str, int length){
        if (str.length()>length){
            return false;
        }
        return true;
    }

    /**
     * �ǳƣ�ȥ���˿����ƻ�json��ʽ���ַ�
     * @param nickName
     * @return
     */
    public static boolean isValideNickName(String nickName){
        Matcher matcher=nickNamePattern.matcher(nickName);
        if (matcher.find()){
            return false;
        }
        return true;
    }
    /**
     * �ж������Ƿ�Ϸ���ֻ�ж�λ��
     * @param pwd
     * @return
     */
    public static boolean isValidPwd(String pwd){
        if (pwd.length()<6 || pwd.length()>18){
            return  false;
        }
        return true;
    }

    /**
     * ��� email�����Ƿ���ȷ ��ȷ����д�� ʽΪ username@domain
     *
     * @param value
     * @return
     */
    public static boolean checkEmail(String value) {
        return value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }
    /**
     * ��� email�����Ƿ���ȷ ��ȷ����д�� ʽΪ username@domain
     *
     * @param value
     * @return
     */
    public static boolean checkEmail(String value, int length) {
        return value.length() <= length && value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    /**
     * ��� email�����Ƿ���ȷ ��ȷ����д�� ʽΪ username@domain.xxx/username@domain
     *
     * @param value
     * @return
     */
    public static boolean checkEmailExt(String value) {
        return value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\\[.*\\]");
    }

    /**
     * �ж��ַ����Ƿ�Ϊ��
     *
     * @param param
     *            Ҫ�����ַ���
     * @return ����ַ���Ϊ�ջ��ߡ���ʱ������true
     */
    public static boolean isEmpty(String param) {
        if (null == param || "".equals(param.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * �ж��ַ����Ƿ�Ϊ��
     *
     * @param param
     *            Ҫ�����ַ���
     * @return ����ַ�����Ϊ�ղ��Ҳ�Ϊ����ʱ������true
     */
    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }

    /**
     * ���һ���������ǲ��ǿյ�
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        if (null==collection || collection.size()==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * ���һ���������Ƿ�ǿ�
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

    public static void main(String[] args) {
        //String str="ab\"\"dd\\dd";
        //isValideNickName(str);

        //String phone = "12158099751";
        //System.out.println(isValidePhoneNum(phone));

        String email = "6427213qq.com";
        System.out.println(checkEmail(email));
    }}

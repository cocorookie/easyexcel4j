package com.wanyi.common.excel4j.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cocomao on 15/10/19.
 */
public class CheckUtil {
    static String phonePaternStr = "^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";// 手机号的正则表达式
    static Pattern phonePattern = Pattern.compile(phonePaternStr);
    static String nickNamePatternStr = "[\"\\\\]+";
    static Pattern nickNamePattern = Pattern.compile(nickNamePatternStr);
    /**
     * 判断提供手机号是否是合法的手机号
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
     * 验证字符长度
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
     * 昵称，去掉了可能破坏json格式的字符
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
     * 判断密码是否合法，只判断位数
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
     * 检查 email输入是否正确 正确的书写格 式为 username@domain
     *
     * @param value
     * @return
     */
    public static boolean checkEmail(String value) {
        return value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }
    /**
     * 检查 email输入是否正确 正确的书写格 式为 username@domain
     *
     * @param value
     * @return
     */
    public static boolean checkEmail(String value, int length) {
        return value.length() <= length && value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    /**
     * 检查 email输入是否正确 正确的书写格 式为 username@domain.xxx/username@domain
     *
     * @param value
     * @return
     */
    public static boolean checkEmailExt(String value) {
        return value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\\[.*\\]");
    }

    /**
     * 判断字符串是否为空
     *
     * @param param
     *            要检查的字符串
     * @return 如果字符串为空或者“”时，返回true
     */
    public static boolean isEmpty(String param) {
        if (null == param || "".equals(param.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否不为空
     *
     * @param param
     *            要检查的字符串
     * @return 如果字符串不为空并且不为“”时，返回true
     */
    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }

    /**
     * 检查一个集合类是不是空的
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
     * 检查一个集合类是否非空
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

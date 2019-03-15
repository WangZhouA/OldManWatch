package com.saiyi.oldmanwatch.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author liwenbo
 * @Date 18/7/31
 * @Describe 验证工具类
 */
public class CheckUtils {

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */

    public static void setEditTextInhibitInputSpace(EditText editText) {

        InputFilter filter = new InputFilter() {

            @Override

            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source.equals(" "))
                    return "";

                else return null;

            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 输入的用户是否有效，a~z、A~Z、数字、下划线
     *
     * @param userName
     * @return
     */
    public static boolean isValidInputUserName(String userName) {
        boolean isMatch = true;
        String reguarEx = "[A-Za-z0-9_]";
        for (int i = 0; i < userName.length(); i++) {
            isMatch = (userName.charAt(i) + "").matches(reguarEx);
            if (!isMatch) {
                return isMatch;
            }
        }
        return isMatch;
    }

    /**
     * 判断是否只是中英文
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }


    /**
     * 是否数字
     *
     * @author guguofeng
     * @date May 31, 2014
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否内容全为数字
     *
     * @param text
     * @return
     */
    public static boolean isWholeDigit(String text) {
        boolean isWholeDigit = true;
        for (int i = 0; i < text.length(); i++) {
            boolean isDigit = Character.isDigit(text.charAt(i));
            if (!isDigit) {
                isWholeDigit = false;
                break;
            }
        }
        return isWholeDigit;
    }

    /**
     * 判断是不是一个合法的身份证号
     *
     * @param IdNumber
     * @return
     */
    public static boolean isIdNumber(String IdNumber) {
        boolean m = IdNumber
                .matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
        return m;
    }

    /**
     * 判断是不是一个合法的手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhone(String mobiles) {
        Pattern p = Pattern
                .compile("^((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");
        Matcher m = p.matcher(mobiles);
        return m.find();
    }

    /**
     * 判断是否是一个合法的手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern
                .compile("^((17[0-9])|(16[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.find();
    }

    /**
     * 判断是不是一个合法的电话号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isValidMobile(String mobiles) {
        Pattern p = Pattern
                .compile("^(\\d{3})-?(\\d{8})|(\\d{4})-?(\\d{7})$");
        Matcher m = p.matcher(mobiles);
        return m.find();
    }

    /**
     * 判断传真格式是否是一个合法的传真格式（只限制为一种格式）
     * 效果如：0111-1111111 或者 010-2341256
     *
     * @param fax
     * @return
     */
    public static boolean isValidFax(String fax) {
        Pattern p = Pattern
                .compile("^((0\\d{2,3}-)?\\d{7,8})$");
        Matcher m = p.matcher(fax);
        return m.find();
    }

    private final static Pattern emailer = Pattern
            //            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            .compile("^[A-Za-z0-9]+([._\\-]*[A-Za-z0-9])*@([A-Za-z0-9]+[-A-Za-z0-9]*[A-Za-z0-9]+.){1,63}[A-Za-z0-9]+$");

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email cand_tyliang@163.com
     * @return true
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }


    /**
     * @param passwd
     * @return boolean
     * @description 检验密码
     * @author guguofeng  feng4656@foxmail.com
     * @update 2015/05/21
     */
    public static boolean isValidPasswd(String passwd) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher m = p.matcher(passwd);
        if (!m.matches()) {
            return false;
        }

        if ((passwd.length() < 6 || passwd.length() > 16)) {
            return false;
        }
        return true;
    }

    /**
     * 有中文返回false 长度小于6和大于16位返回false
     *
     * @param passwd
     * @return
     */
    public static boolean isChiness(String passwd) {
        Pattern p = Pattern.compile(".*[\u4e00-\u9fa5].*");
        Matcher m = p.matcher(passwd);
        if (m.matches()) {
            return false;
        }

        if ((passwd.length() < 6 || passwd.length() > 16)) {
            return false;
        }
        return true;
    }

    /**
     * 有中文返回false
     *
     * @param passwd
     * @return
     */
    public static boolean isChinessTwo(String passwd) {
        Pattern p = Pattern.compile(".*[\u4e00-\u9fa5].*");
        Matcher m = p.matcher(passwd);
        if (m.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 密码 密码长度为6-16位数字,字母,符号至少包含两种
     *
     * @param passwd
     * @return
     */
    public static boolean isValidPasswdTwo(String passwd) {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,16}$");
        Matcher m = p.matcher(passwd);
        if (!m.matches()) {
            return false;
        }

        if ((passwd.length() < 6 || passwd.length() > 16)) {
            return false;
        }
        return true;
    }

    public static boolean isStrEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }


    /**
     * 检测网络连接
     *
     * @param con
     * @return
     */
    public static boolean isNetworkAvailable(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo == null) {
            return false;
        }
        if (netinfo.isConnected()) {
            return true;
        }
        return false;
    }


    /**
     * 获取设备Mac地址
     *
     * @return
     */
    public static String getMac() {
        String macSerial = (String) SharedPreferencesHelper.get("mac", "");
        return macSerial;
    }


    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }


}

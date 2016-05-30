package com.bricechou.weiboclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BriceChou on 16-5-27.
 */
public class RegEx {

    /**
     * 通用的邮箱正则表达式
     */
    private static final String MAIL_PATTERN = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    /**
     * 手机号:目前全国有27种手机号段。
     * 移动有16个号段：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188。其中147、157、188是3G号段，其他都是2G号段。
     * 联通有7种号段：130、131、132、155、156、185、186。其中186是3G（WCDMA）号段，其余为2G号段。
     * 电信有4个号段：133、153、180、189。其中189是3G号段（CDMA2000），133号段主要用作无线网卡号。
     * 150、151、152、153、155、156、157、158、159 九个;
     * 130、131、132、133、134、135、136、137、138、139 十个;
     * 180、182、185、186、187、188、189 七个;
     * 13、15、18三个号段共30个号段，154、181、183、184暂时没有，加上147共27个。
     */
    private static final String PHONE_NUMBER_PATTERN = "^(13[0-9]|15[01]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$";

    /**
     * @description 根据给定的相应的Type进行判断检查args是否符合要求
     * @param type 暂时只支持 mail 和 phoneNumber
     * @param args 输入需要判断的参数
     * @author BriceChou
     * @datetime 2016-05-27 16:29
     */
    public static boolean checkRegEx(String type, String args) {
        boolean flag;
        switch (type){
            case "mail":type = MAIL_PATTERN;
                break;
            case "phoneNumber":type = PHONE_NUMBER_PATTERN;
                break;
            default:
                break;
        }
        try {
            // 创建 Pattern 对象
            Pattern pattern = Pattern.compile(type);
            // 现在创建 matcher 对象
            Matcher mtc = pattern.matcher(args);
            // 判断是否匹配
            flag = mtc.matches();
            System.out.println(flag+" , "+ args);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    public static boolean  checkAllRegEx(String args){
        boolean flag ;
        if(checkRegEx("mail",args) || checkRegEx("phoneNumber",args)){
            flag = true;
        }
        else flag = false;
        return flag;
    }
}
package com.sunyard.sunfintech.core.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sunyard.sunfintech.core.dic.RoleType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.ibatis.annotations.Delete;

import com.google.common.collect.Lists;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 是否需要格式化帐户号标识。
     */
    public static final boolean needFormatAccountNo = true;

    /**
     * 将STRING转换成INT
     *
     * @param s
     * @return
     */
    public static int string2Int(String s) {
        if (s == null || "".equals(s) || "undefined".equals(s))
            return 0;
        int result = 0;
        try {
            if (s.indexOf(",") != -1) {
                s = s.substring(0, s.indexOf(","));
            } else if (s.indexOf(".") != -1) {
                s = s.substring(0, s.indexOf("."));
            } else if (s.indexOf("|") != -1) {
                s = s.substring(0, s.indexOf("|"));
            }
            result = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 禁止显示出NULL值
     *
     * @param obj 输入字串
     * @return 输出字串
     */
    public static String forbidNull(Object obj) {
        return (null == obj) ? "" : String.valueOf(obj).trim();
    }

    /**
     * <li>判断字串是否为空值</li> <li>NULL、空格均为空值</li>
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return null == value || value.trim().length() == 0;
    }

    /**
     * 重置档案
     *
     * @param filename
     * @param suffix
     * @return
     */
    public static String renameFileSuffix(String filename, String suffix) {
        int pos = filename.lastIndexOf(".");
        String tmp = filename.substring(0, pos);
        return tmp + suffix;
    }

    /**
     * 重复字串 如 repeatString("a",3) ==> "aaa"
     *
     * @param src
     * @param repeats
     * @return
     * @author uke
     */
    public static String repeatString(String src, int repeats) {
        if (null == src || repeats <= 0) {
            return src;
        } else {
            StringBuffer bf = new StringBuffer();
            for (int i = 0; i < repeats; i++) {
                bf.append(src);
            }
            return bf.toString();
        }
    }

    /**
     * 左对齐字串 * lpadString("X",3); ==>" X" *
     *
     * @param src
     * @param length
     * @return
     */
    public static String lpadString(String src, int length) {
        return lpadString(src, length, " ");
    }

    /**
     * 以指定字串填补空位，左对齐字串 * lpadString("X",3,"0"); ==>"00X"
     *
     * @param src
     * @param length
     * @param single
     * @return
     */
    public static String lpadString(String src, int length, String single) {
        if (src == null || length <= src.getBytes().length) {
            return src;
        } else {
            return repeatString(single, length - src.getBytes().length) + src;
        }
    }

    /**
     * 右对齐字串 * rpadString("9",3)==>"9 "
     *
     * @param src
     * @param byteLength
     * @return
     */
    public static String rpadString(String src, int byteLength) {
        return rpadString(src, byteLength, " ");
    }

    /**
     * 以指定字串填补空位，右对齐字串 rpadString("9",3,"0")==>"900"
     *
     * @param src
     * @param length
     * @param single
     * @return
     */
    public static String rpadString(String src, int length, String single) {
        if (src == null || length <= src.getBytes().length) {
            return src;
        } else {
            return src + repeatString(single, length - src.getBytes().length);
        }
    }

    /**
     * 去除,分隔符，用於金额值去格式化
     *
     * @param value
     * @return
     */
    public static String decimal(String value) {
        if (null == value || "".equals(value.trim())) {
            return "0";
        } else {
            return value.replaceAll(",", "");
        }
    }

    /**
     * 在阵列中查找字串
     *
     * @param params
     * @param name
     * @param ignoreCase
     * @return
     */
    public static int indexOf(String[] params, String name, boolean ignoreCase) {
        if (params == null)
            return -1;
        for (int i = 0, j = params.length; i < j; i++) {
            if (ignoreCase && params[i].equalsIgnoreCase(name)) {
                return i;
            } else if (params[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 将字元转阵列
     *
     * @param str
     * @return
     */
    public static String[] toArr(String str) {
        return toArr(str, ",");
    }

    public static String[] toArr(String str, String delim) {
        String inStr = str;
        String a[] = null;
        try {
            if (null != inStr) {
                StringTokenizer st = new StringTokenizer(inStr, delim);
                if (st.countTokens() > 0) {
                    a = new String[st.countTokens()];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        a[i++] = st.nextToken();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 字串阵列包装成字串
     *
     * @param ary
     * @param s   包装符号如 ' 或 "
     * @return
     */
    public static String toStr(String[] ary, String s) {
        if (ary == null || ary.length < 1)
            return "";
        StringBuffer bf = new StringBuffer();
        bf.append(s);
        bf.append(ary[0]);
        for (int i = 1; i < ary.length; i++) {
            bf.append(s).append(",").append(s);
            bf.append(ary[i]);
        }
        bf.append(s);
        return bf.toString();
    }

    /**
     * 设定MESSAGE中的变{0}...{N}
     *
     * @param msg
     * @param vars
     * @return
     */
    public static String getMessage(String msg, String[] vars) {
        for (int i = 0; i < vars.length; i++) {
            msg = msg.replaceAll("\\{" + i + "\\}", vars[i]);
        }
        return msg;
    }

    /**
     * @param msg
     * @param var
     * @return
     */
    public static String getMessage(String msg, String var) {
        return getMessage(msg, new String[]{var});
    }

    /**
     * @param msg
     * @param var
     * @param var2
     * @return
     */
    public static String getMessage(String msg, String var, String var2) {
        return getMessage(msg, new String[]{var, var2});
    }

    /**
     * 从Map中取String类型值
     *
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object getMapValue(Map map, Object key) {
        if (null == map || null == key)
            return "";
        Object value = map.get(key);
        return null == value ? "" : value;
    }

    /**
     * 从Map中取Integer类型值
     *
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object getMapIntValue(Map map, Object key) {
        if (null == map || null == key)
            return new Integer(0);
        Object value = map.get(key);
        return null == value ? new Integer(0) : value;
    }

    /**
     * 将key=value;key2=value2……转换成Map
     *
     * @param params
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map gerneryParams(String params) {
        Map args = new HashMap();
        if (!isEmpty(params)) {
            try {
                String map, key, value;
                StringTokenizer st = new StringTokenizer(params, ";");
                StringTokenizer stMap;
                while (st.hasMoreTokens()) {
                    map = st.nextToken();
                    if (isEmpty(map.trim()))
                        break;
                    stMap = new StringTokenizer(map, "=");
                    key = stMap.hasMoreTokens() ? stMap.nextToken() : "";
                    if (isEmpty(key.trim()))
                        continue;
                    value = stMap.hasMoreTokens() ? stMap.nextToken() : "";
                    args.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return args;
    }

    /**
     * 格式化卡号 6221731801000003428 ==>622**********3428
     *
     * @param noStr
     * @return
     */
    public static String noStr(String noStr) {
        noStr = noStr.trim();
        // 只有被设置为可以格式化帐号时才格式化
        if (needFormatAccountNo) {
            if (!isEmpty(noStr)) {
                String resutlt = "";
                StringBuffer flag = new StringBuffer();
                int noStrLength = noStr.length();
                if (noStrLength > 8) {
                    flag.append(noStr.substring(0, 4));// prefix string
                    flag.append("****"); //
                    flag.append(noStr.substring(noStrLength - 3, noStrLength)); //
                    return flag.toString();
                } else if (noStrLength > 4) {
                    flag.append(noStr.substring(0, 2));// prefix string
                    flag.append("****"); //
                    flag.append(noStr.substring(noStrLength - 2, noStrLength)); //
                    return flag.toString();
                } else if (noStrLength > 2) {
                    resutlt = noStr.substring(1, 2) + "**"
                            + noStr.substring(2, 3);
                    return resutlt;
                } else {
                    return noStr;
                }
            }
            return "";
        } else {
            return noStr;
        }

    }

    /**
     * 页面格式化日期:yyyyMMdd ---> yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        return isEmpty(date) ? "" : DateUtils.format(date, "yyyyMMdd",
                "yyyy-MM-dd");
    }

    public static String formatDates(String date) {
        return isEmpty(date) ? "" : DateUtils.format(date, "yyyy-MM-dd",
                "yyyyMMdd");
    }

    /**
     * 分析帐户状态字符串，判断有无帐户，并在原Map中存储一个Key为“ACCOUNT_STATE”的值；
     * 如果有，则为“has”，没有则为“hasNot”
     *
     * @param list    存储据的list列表
     * @param accKind 分析的种类标识：2. 存款业务账户明细 3.授信业务账户明细
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void analyzeAccState(List list, int accKind) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Map row = (Map) list.get(i);
                String state = StringUtils.trimToEmpty((String) row
                        .get("STATE"));
                String accountState = "has";
                // 判断是否全部都由0组成 ，或者 是否全部都由N组成，如果是，表示帐户状态是“无”
                if (StringUtils.containsOnly(state, "0")
                        || StringUtils.containsOnly(state, "N")) {
                    accountState = "hasNot";
                } else { // 如果不是，则表示帐户状态为“有”
                    // Do nothing
                }
                row.put("ACCOUNT_STATE", accountState);
            }
        }
    }

    /**
     * 去掉字符串空格
     *
     * @param str
     * @return
     */
    public static String delSpace(String str) {

        if (str == null) {
            return null;
        }

        // 先将半角空格删除
        str = str.trim();

        while (str.startsWith("　")) {

            // 只可惜String中没有提供replaceLast(), 否则就简单点了
            // 所以本循环完成以后，只能将字符串前端的空格删除，却不能删除后端的空格
            // 故而本循环完成后，又将字符串翻转后再去一次空格
            str = str.replaceFirst("　", "");

            // 一定要 trim()， 不然的话，如果前端的空格是全角和半角相间的话，就搞不定了
            str = str.trim();
        }

        // 将字符串翻转
        str = reverse(str);

        // 再去一次空格
        while (str.startsWith("　")) {

            str = str.replaceFirst("　", "");

            str = str.trim();
        }

        // 最后再将字符串翻转回去
        return str = reverse(str).trim();
    }

    // 自定义的字符串翻转方法。
    // 很多第三方的包的都有实现，但是 Java API 没有实现，这里自己实现一下 ^_^
    public static String reverse(String str) {
        char[] charsOld = str.toCharArray();

        char[] charsNew = new char[charsOld.length];

        int index = charsOld.length - 1;

        for (int i = 0; i < charsOld.length; i++) {

            charsNew[i] = charsOld[index - i];
        }

        return String.valueOf(charsNew);
    }

    public static String toCron(Date date) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int ss = calendar.get(Calendar.SECOND);
        int mm = calendar.get(Calendar.MINUTE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int MM = calendar.get(Calendar.MONTH) + 1;
        int yyyy = calendar.get(Calendar.YEAR);
        return "" + ss + " " + mm + " " + hh + " " + dd + " " + MM + " ? " + yyyy;
    }

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static BigDecimal Str2BigDecimal(String param) {
        if (param == null || param.trim().equals("")) {
            return BigDecimal.ZERO;
        } else {
            double result = Double.parseDouble(param);
            return BigDecimal.valueOf(result);
        }
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * 实用的时候要仔细验证
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param txt
     * @return 处理后的HTML
     */
    @Deprecated
    public static String toHtml(String txt) {
        if (txt == null) {
            return "";
        }
        return replace(replace(StringEscapeUtils.escapeHtml4(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String abbr2(String param, int length) {
        if (param == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        boolean isCode = false; // 是不是HTML代码
        boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            try {
                if (!isCode && !isHTML) {
                    n += String.valueOf(temp).getBytes("GBK").length;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (n <= length - 3) {
                result.append(temp);
            } else {
                result.append("...");
                break;
            }
        }
        // 取出截取字符串中的HTML标记
        String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
                "$1$2");
        // 去掉不需要结素标记的HTML标记
        temp_result = temp_result
                .replaceAll(
                        "</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
                        "");
        // 去掉成对的HTML标记
        temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
                "$2");
        // 用正则表达式取出标记
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
        Matcher m = p.matcher(temp_result);
        List<String> endHTML = Lists.newArrayList();
        while (m.find()) {
            endHTML.add(m.group(1));
        }
        // 补全不成对的HTML标记
        for (int i = endHTML.size() - 1; i >= 0; i--) {
            result.append("</");
            result.append(endHTML.get(i));
            result.append(">");
        }
        return result.toString();
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 获得i18n字符串
     */
//	public static String getMessage(String code, Object[] args) {
//		LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder.getBean(LocaleResolver.class);
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
//		Locale localLocale = localLocaleResolver.resolveLocale(request);
//		return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
//	}

//	/**
//	 * 获得用户远程地址
//	 */
//	public static String getRemoteAddr(HttpServletRequest request){
//		String remoteAddr = request.getHeader("X-Real-IP");
//        if (isNotBlank(remoteAddr)) {
//        	remoteAddr = request.getHeader("X-Forwarded-For");
//        }else if (isNotBlank(remoteAddr)) {
//        	remoteAddr = request.getHeader("Proxy-Client-IP");
//        }else if (isNotBlank(remoteAddr)) {
//        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
//        }
//        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
//	}

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString 对象串
     *                     例如：row.user.id
     *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i = 0; i < vals.length; i++) {
            val.append("." + vals[i]);
            result.append("!" + (val.substring(1)) + "?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    /**
     * map转换成字符串
     *
     * @param map
     * @return
     */
    public static String mapToStr(Map map) {
        Iterator<Entry> i = map.entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry e = i.next();
            sb.append(e.getKey());
            sb.append(':');
            sb.append(e.getValue());
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    /**
     * 隐藏银行卡号
     *
     * @param cardNo
     * @return
     */
    public static String hideCardNo(String cardNo) {
        StringBuilder sb;
        try {
            sb = new StringBuilder(cardNo.substring(0, 4));
            sb.append(" **** **** ")
                    .append(cardNo.substring(cardNo.length() - 3, cardNo.length()));
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.BANKCARD_ERROR);
        }
        return sb.toString();
    }

    /**
     * 隐藏身份证
     *
     * @param idCode
     * @return
     */
    public static String hideIdCode(String idCode) {
        StringBuilder sb;
        try {
            if(idCode.trim().length() == 18){
                sb = new StringBuilder(idCode.substring(0, 6));
                sb.append(" ")
                        .append(idCode.substring(6, 14))
                        .append(" ****");
            }else {
                sb = new StringBuilder();
                sb.append(idCode.substring(0, idCode.length() - 1 - 4));
                sb.append("****");
            }
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.IDCODE_ERROR);
        }
        return sb.toString();
    }

    /**
     * 隐藏手机号
     *
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        StringBuilder sb;
        try {
            sb = new StringBuilder(mobile.substring(0, 3));
            sb.append(" **** ")
                    .append(mobile.substring(mobile.length() - 4, mobile.length()));
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.MOBILE_ERROR);
        }
        return sb.toString();
    }

    public static String hidePlatcust(String platcust) {
        return platcust.substring(0, 4) + " **** **** " + platcust.substring(platcust.length() - 4);
    }

    /**
     * 角色转换
     *
     * @param role
     * @return
     */
    public static String formatRole(String role) {
        String[] roles = role.split(",");
        StringBuilder returnRole = new StringBuilder();
        for (int i = 0; i < roles.length; i++) {
            if (RoleType.INVESTOR.getCode().equals(roles[i])) {
                returnRole.append("出借人");
            } else if (RoleType.BORROWER.getCode().equals(roles[i])) {
                returnRole.append("借款人");
            } else if (RoleType.ADVANCER.getCode().equals(roles[i])) {
                returnRole.append("垫资人");
            } else if (RoleType.GUARANTOR.getCode().equals(roles[i])) {
                returnRole.append("担保人");
            }
            if (i < roles.length - 1) {
                returnRole.append("/");
            }
        }
        return returnRole.toString();
    }

    public static byte stringToByte(String str) {
        int str_int = Integer.valueOf(str);
        return (byte) str_int;
    }

    public static String byteToString(byte str_byte) {
        return String.valueOf(str_byte & 0xFF);
    }

    public static void main(String[] args) {
//        System.out.println(StringUtils.hideMobile("15088620383"));
//        System.out.println(hidePlatcust("832908134809490313"));

    }

}

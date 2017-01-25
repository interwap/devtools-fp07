package com.praescient.components.fgtit_fp07.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataUtils {

    private char[] getChar(int position) {
        String str = String.valueOf(position);
        if (str.length() == 1) {
            str = "0" + str;
        }
        char[] c = { str.charAt(0), str.charAt(1) };
        return c;
    }

    /**
     * 16进制字符串转换成数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringTobyte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        String temp = "";
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
            temp += result[i] + ",";
        }
        // uiHandler.obtainMessage(206, hex + "=read=" + new String(result))
        // .sendToTarget();
        return result;
    }

    public static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 数组转成16进制字符串
     *
     * @param b
     * @return
     */
    public static String toHexString(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    /**
     * 十六进制字符串转换成字符串
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 字符串转换成十六进制字符串
     */
    public static String str2Hexstr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    public static String byte2Hexstr(byte b) {
        String temp = Integer.toHexString(0xFF & b);
        if (temp.length() < 2) {
            temp = "0" + temp;
        }
        temp = temp.toUpperCase(Locale.getDefault());
        return temp;
    }

    public static String str2Hexstr(String str, int size) {
        byte[] byteStr = str.getBytes();
        byte[] temp = new byte[size];
        System.arraycopy(byteStr, 0, temp, 0, byteStr.length);
        temp[size - 1] = (byte) byteStr.length;
        String hexStr = toHexString(temp);
        return hexStr;
    }

    /**
     * 16进制字符串分割成若干块，每块32个16进制字符，即16字节
     *
     * @param str
     * @return
     */
    public static String[] hexStr2StrArray(String str) {
        // 32个十六进制字符串表示16字节
        int len = 32;
        int size = str.length() % len == 0 ? str.length() / len : str.length()
                / len + 1;
        String[] strs = new String[size];
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                String temp = str.substring(i * len);
                for (int j = 0; j < len - temp.length(); j++) {
                    temp = temp + "0";
                }
                strs[i] = temp;
            } else {
                strs[i] = str.substring(i * len, (i + 1) * len);
            }
        }
        return strs;
    }

    /**
     * 把16进制字符串压缩成字节数组，在把字节数组转换成16进制字符串
     *
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(data);
        gzip.close();
        return out.toByteArray();
    }

    /**
     * 把16进制字符串解压缩压缩成字节数组，在把字节数组转换成16进制字符串
     *
     * @param
     * @return
     * @throws IOException
     */
    public static byte[] uncompress(byte[] data) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static byte[] short2byte(short s) {
        byte[] size = new byte[2];
        size[0] = (byte) (s >>> 8);
        short temp = (short) (s << 8);
        size[1] = (byte) (temp >>> 8);

        // size[0] = (byte) ((s >> 8) & 0xff);
        // size[1] = (byte) (s & 0x00ff);
        return size;
    }

    public static short[] hexStr2short(String hexStr) {
        byte[] data = hexStringTobyte(hexStr);
        short[] size = new short[4];
        for (int i = 0; i < size.length; i++) {
            size[i] = getShort(data[i * 2], data[i * 2 + 1]);
        }
        return size;
    }

    public static short getShort(byte b1, byte b2) {
        short temp = 0;
        temp |= (b1 & 0xff);
        temp <<= 8;
        temp |= (b2 & 0xff);
        return temp;
    }

    public static boolean bytesEquals(byte d1[], byte d2[])
    {
        if(d1 == null && d2 == null)
            return true;
        if(d1 == null || d2 == null)
            return false;
        if(d1.length != d2.length)
            return false;
        for(int i = 0; i < d1.length; i++)
            if(d1[i] != d2[i])
                return false;

        return true;
    }

    public static char[] bytestochars(byte data[])
    {
        char cdata[] = new char[data.length];
        for(int i = 0; i < cdata.length; i++)
            cdata[i] = (char)(data[i] & 0xff);

        return cdata;
    }

    public static byte[] getRandomByteArray(int nlength)
    {
        byte data[] = new byte[nlength];
        Random rmByte = new Random(System.currentTimeMillis());
        for(int i = 0; i < nlength; i++)
            data[i] = (byte)rmByte.nextInt(256);

        return data;
    }

    public static void blackWhiteReverse(byte data[])
    {
        for(int i = 0; i < data.length; i++)
            data[i] = (byte)(~(data[i] & 0xff));

    }

    public static byte[] getSubBytes(byte org[], int start, int length)
    {
        byte ret[] = new byte[length];
        for(int i = 0; i < length; i++)
            ret[i] = org[i + start];

        return ret;
    }

    public static String byteToStr(byte rc)
    {
        String tmp = Integer.toHexString(rc & 0xff);
        tmp = tmp.toUpperCase(Locale.getDefault());
        String rec;
        if(tmp.length() == 1)
            rec = (new StringBuilder("0x0")).append(tmp).toString();
        else
            rec = (new StringBuilder("0x")).append(tmp).toString();
        return rec;
    }

    public static String bytesToStr(byte rcs[])
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < rcs.length; i++)
        {
            String tmp = Integer.toHexString(rcs[i] & 0xff);
            tmp = tmp.toUpperCase(Locale.getDefault());
            if(tmp.length() == 1)
                stringBuilder.append((new StringBuilder("0x0")).append(tmp).toString());
            else
                stringBuilder.append((new StringBuilder("0x")).append(tmp).toString());
            if(i % 16 != 15)
                stringBuilder.append(" ");
            else
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static byte[] cloneBytes(byte data[])
    {
        byte ret[] = new byte[data.length];
        for(int i = 0; i < data.length; i++)
            ret[i] = data[i];

        return ret;
    }

    public static byte bytesToXor(byte data[], int start, int length)
    {
        if(length == 0)
            return 0;
        if(length == 1)
            return data[start];
        int result = data[start] ^ data[start + 1];
        for(int i = start + 2; i < start + length; i++)
            result ^= data[i];

        return (byte)result;
    }

    public static byte[] byteArraysToBytes(byte data[][])
    {
        int length = 0;
        for(int i = 0; i < data.length; i++)
            length += data[i].length;

        byte send[] = new byte[length];
        int k = 0;
        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j < data[i].length; j++)
                send[k++] = data[i][j];

        }

        return send;
    }

    public static void copyBytes(byte orgdata[], int orgstart, byte desdata[], int desstart, int copylen)
    {
        for(int i = 0; i < copylen; i++)
            desdata[desstart + i] = orgdata[orgstart + i];

    }

}

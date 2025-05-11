package com.tt.admin.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64 编码/解码工具类
 * 支持标准Base64、URL安全Base64、MIME兼容Base64
 */
public class Base64Util {

    // ====================== 标准 Base64 ======================

    /**
     * Base64 编码（标准）
     *
     * @param input 原始字符串
     * @return 编码后的Base64字符串
     */
    public static String encode(String input) {
        return encode(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64 编码（标准）
     *
     * @param bytes 原始字节数组
     * @return 编码后的Base64字符串
     */
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64 解码（标准）
     *
     * @param encoded Base64字符串
     * @return 解码后的原始字符串
     */
    public static String decode(String encoded) {
        return new String(decodeToBytes(encoded), StandardCharsets.UTF_8);
    }

    /**
     * Base64 解码（标准）
     *
     * @param encoded Base64字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeToBytes(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    // ====================== URL安全 Base64 ======================

    /**
     * URL安全 Base64 编码（替换 "+/" -> "-_"）
     *
     * @param input 原始字符串
     * @return 编码后的URL安全Base64字符串
     */
    public static String encodeUrlSafe(String input) {
        return encodeUrlSafe(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * URL安全 Base64 编码（替换 "+/" -> "-_"）
     *
     * @param bytes 原始字节数组
     * @return 编码后的URL安全Base64字符串
     */
    public static String encodeUrlSafe(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * URL安全 Base64 解码
     *
     * @param encoded URL安全Base64字符串
     * @return 解码后的原始字符串
     */
    public static String decodeUrlSafe(String encoded) {
        return new String(decodeUrlSafeToBytes(encoded), StandardCharsets.UTF_8);
    }

    /**
     * URL安全 Base64 解码
     *
     * @param encoded URL安全Base64字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeUrlSafeToBytes(String encoded) {
        return Base64.getUrlDecoder().decode(encoded);
    }

    // ====================== MIME兼容 Base64 ======================

    /**
     * MIME兼容 Base64 编码（每76字符换行）
     *
     * @param input 原始字符串
     * @return 编码后的MIME兼容Base64字符串
     */
    public static String encodeMime(String input) {
        return encodeMime(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * MIME兼容 Base64 编码（每76字符换行）
     *
     * @param bytes 原始字节数组
     * @return 编码后的MIME兼容Base64字符串
     */
    public static String encodeMime(byte[] bytes) {
        return Base64.getMimeEncoder().encodeToString(bytes);
    }

    /**
     * MIME兼容 Base64 解码
     *
     * @param encoded MIME兼容Base64字符串
     * @return 解码后的原始字符串
     */
    public static String decodeMime(String encoded) {
        return new String(decodeMimeToBytes(encoded), StandardCharsets.UTF_8);
    }

    /**
     * MIME兼容 Base64 解码
     *
     * @param encoded MIME兼容Base64字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeMimeToBytes(String encoded) {
        return Base64.getMimeDecoder().decode(encoded);
    }
}
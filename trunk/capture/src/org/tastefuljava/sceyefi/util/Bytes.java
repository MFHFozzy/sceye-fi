/*
    Sceye-Fi Photo capture
    Copyright (C) 2011-2012  Maurice Perry <maurice@perry.ch>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tastefuljava.sceyefi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Bytes {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static byte[] hex2bin(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string with odd length");
        }
        char[] chars = hex.toCharArray();
        byte[] result = new byte[chars.length/2];
        int j = 0;
        for (int i = 0; i < result.length; ++i) {
            int val = (charValue(chars[j++])*16 + charValue(chars[j++]));
            result[i] = (byte)val;
        }
        return result;
    }

    public static String bin2hex(byte[] data) {
        return bin2hex(data, 0, data.length);
    }

    public static String bin2hex(byte[] data, int offs, int len) {
        int end = offs + len;
        char[] chars = new char[2*len];
        int j = 0;
        for (int i = offs; i < end; ++i) {
            int b = data[i] & 0xFF;
            chars[j++] = HEX_CHARS[b/16];
            chars[j++] = HEX_CHARS[b%16];
        }
        return new String(chars);
    }

    public static boolean equals(byte[] a, byte[] b) {
        int len = a.length;
        if (len != b.length) {
            return false;
        }
        for (int i = 0; i < len; ++i) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] randomBytes(int size) {
        byte[] result = new byte[size];
        Random random = new Random();
        random.nextBytes(result);
        return result;
    }

    public static byte[] md5(byte[]... args) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            for (byte[] arg : args) {
                digest.update(arg);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private static int charValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        } else {
            throw new IllegalArgumentException("Illegal hex char " + c);
        }
    }
}

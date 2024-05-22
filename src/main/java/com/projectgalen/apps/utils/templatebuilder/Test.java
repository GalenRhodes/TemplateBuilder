// ERROR: Unable to create template: java.lang.RuntimeException: ${t_parse} cannot be applied to Character.

package com.projectgalen.apps.utils.templatebuilder;
// ================================================================================================================================
//     PROJECT: TemplateBuilder
//    FILENAME: Test.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 22, 2024
//
// Copyright © 2024 Project Galen. All rights reserved.
//
// Permission to use, copy, modify, and distribute this software for any purpose with or without fee is hereby granted, provided
// that the above copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
// CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
// ================================================================================================================================

import org.jetbrains.annotations.NotNull;

import static java.lang.System.arraycopy;

public class Test {
    public Test() { }
    
    /**
     * @author Galen Rhodes
     */
    public static boolean @NotNull [] append(boolean src, boolean @NotNull [] dest) {
        boolean[] array = new boolean[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static char @NotNull [] append(char src, char @NotNull [] dest) {
        char[] array = new char[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static byte @NotNull [] append(byte src, byte @NotNull [] dest) {
        byte[] array = new byte[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static short @NotNull [] append(short src, short @NotNull [] dest) {
        short[] array = new short[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static int @NotNull [] append(int src, int @NotNull [] dest) {
        int[] array = new int[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static long @NotNull [] append(long src, long @NotNull [] dest) {
        long[] array = new long[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static float @NotNull [] append(float src, float @NotNull [] dest) {
        float[] array = new float[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    /**
     * @author Galen Rhodes
     */
    public static double @NotNull [] append(double src, double @NotNull [] dest) {
        double[] array = new double[dest.length + 1];
        arraycopy(dest, 0, array, 0, dest.length);
        array[dest.length] = src;
        return array;
    }
    
    public static boolean safeParse(String str, boolean defaultValue) { try { return Boolean.parseBoolean(str); } catch(Exception e) { return defaultValue; } }

    // ERROR: Unable to create template: java.lang.RuntimeException: ${t_parse} cannot be applied to Character.

    
    public static byte safeParse(String str, byte defaultValue) { try { return Byte.parseByte(str); } catch(Exception e) { return defaultValue; } }
    
    public static short safeParse(String str, short defaultValue) { try { return Short.parseShort(str); } catch(Exception e) { return defaultValue; } }
    
    public static int safeParse(String str, int defaultValue) { try { return Integer.parseInt(str); } catch(Exception e) { return defaultValue; } }
    
    public static long safeParse(String str, long defaultValue) { try { return Long.parseLong(str); } catch(Exception e) { return defaultValue; } }
    
    public static float safeParse(String str, float defaultValue) { try { return Float.parseFloat(str); } catch(Exception e) { return defaultValue; } }
    
    public static double safeParse(String str, double defaultValue) { try { return Double.parseDouble(str); } catch(Exception e) { return defaultValue; } }
}

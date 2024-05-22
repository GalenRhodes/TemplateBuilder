package com.projectgalen.apps.utils.templatebuilder;
// ================================================================================================================================
//     PROJECT: TemplateBuilder
//    FILENAME: Main.java
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;
import static java.util.regex.Matcher.quoteReplacement;

public class Main {

    // ${template_start_{id}}
    private final Pattern    pat1 = Pattern.compile("^[ \\t]*//[ \\t]*\\$\\{template_start_([^}]+)}.*?\\R", Pattern.MULTILINE);
    private final Pattern    pat2 = Pattern.compile("^[ \\t]*//[ \\t]*\\$\\{template_end_([^}]+)}.*?\\R", Pattern.MULTILINE);
    private final Pattern    pat3 = Pattern.compile("\\$\\{t_(\\w+)}", Pattern.MULTILINE);
    private final Pattern    pat4 = Pattern.compile("^([ \\t]+)//!", Pattern.MULTILINE);
    private final Pattern    pat9 = Pattern.compile("^--C(\\w+)=(.+)$", Pattern.DOTALL);
    //@f0
    private final String[][] foo  = { { "primitive", "object",    "parse"        },
                                      { "boolean",   "Boolean",   "parseBoolean" },
                                      { "char",      "Character", null           },
                                      { "byte",      "Byte",      "parseByte"    },
                                      { "short",     "Short",     "parseShort"   },
                                      { "int",       "Integer",   "parseInt"     },
                                      { "long",      "Long",      "parseLong"    },
                                      { "float",     "Float",     "parseFloat"   },
                                      { "double",    "Double",    "parseDouble"  } };
    //@f1

    private       boolean             onlyNumbers = false;
    private final Map<String, String> custom      = new TreeMap<>();

    public Main() {
    }

    public int run(String @NotNull ... args) throws Exception {
        for(String arg : args) {
            if("-#".equals(arg)) {
                onlyNumbers = true;
            }
            else if("-#-".equals(arg)) {
                onlyNumbers = false;
            }
            else if(!isCustom(arg)) {
                String  text   = readFile(arg);
                Matcher mStart = pat1.matcher(text);

                if(mStart.find()) {
                    StringBuilder sb   = new StringBuilder();
                    int           idx1 = 0;

                    do {
                        try {
                            Rep rep = findEnd(text, mStart.group(1), mStart.end()).map(mEnd -> getReplacement(text, mStart, mEnd, (onlyNumbers ? 3 : 1))).orElse(null);
                            if(rep != null) {
                                sb.append(text, idx1, mStart.start()).append(pat4.matcher(rep.replacementString).replaceAll("$1"));
                                idx1 = rep.endIndex;
                            }
                        }
                        catch(Exception e) {
                            sb.append(text, idx1, mStart.start()).append("\n    // ERROR: Unable to create template: %s\n\n".formatted(e));
                            idx1 = mStart.start();
                        }
                    }
                    while(mStart.find());

                    writeFile(arg, sb.append(text, idx1, text.length()).toString());
                }
            }
        }
        return 0;
    }

    private boolean isCustom(String arg) {
        Matcher m = pat9.matcher(arg);
        if(!m.matches()) return false;
        custom.put(m.group(1), m.group(2));
        return true;
    }

    private Optional<Matcher> findEnd(String text, String id, int startIndex) {
        Matcher m = pat2.matcher(text);
        if(m.find(startIndex)) do if(id.equals(m.group(1))) return Optional.of(m); while(m.find());
        return Optional.empty();
    }

    private @NotNull Rep getReplacement(@NotNull String text, @NotNull Matcher mStart, @NotNull Matcher mEnd, int offset) {
        String        sub = text.substring(mStart.end(), mEnd.start());
        StringBuilder sb  = new StringBuilder();

        Arrays.stream(foo, offset, foo.length).forEach(a -> {
            try { sb.append(pat3.matcher(sub).replaceAll(m -> quoteReplacement(getSingleReplacement(m, a)))); }
            catch(Exception e) { sb.append("\n    // ERROR: Unable to create template: %s\n\n".formatted(e)); }
        });

        return new Rep(sb.toString(), mEnd.end());
    }

    private String getSingleReplacement(@NotNull MatchResult m, String @NotNull [] a) {
        String key = m.group(1);
        return ofNullable(switch(key) {
            case "primitive" -> a[0];
            case "object" -> a[1];
            case "parse" -> ofNullable(a[2]).map(s -> "%s.%s".formatted(a[1], s)).orElseThrow(() -> new RuntimeException("%s cannot be applied to %s.".formatted(m.group(), a[1])));
            default -> custom.getOrDefault(key, null);
        }).orElseGet(m::group);
    }

    private @NotNull String readFile(String filename) throws IOException {
        try(Reader r = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)) {
            StringBuilder sb     = new StringBuilder();
            char[]        buffer = new char[8192];
            int           cc     = r.read(buffer);

            while(cc >= 0) {
                sb.append(buffer, 0, cc);
                cc = r.read(buffer);
            }
            return sb.toString();
        }
    }

    private void writeFile(String filename, String text) throws IOException {
        try(Writer w = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8)) {
            w.write(text);
        }
    }

    public static void main(String... args) {
        try {
            System.exit(new Main().run(args));
        }
        catch(Throwable t) {
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }

    record Rep(String replacementString, int endIndex) { }
}

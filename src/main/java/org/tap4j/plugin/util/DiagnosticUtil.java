/* 
 * The MIT License
 * 
 * Copyright (c) 2010 Bruno P. Kinoshita
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.tap4j.plugin.util;

import org.tap4j.model.TestResult;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Used to create YAML view.
 * 
 * @since 1.0
 */
public class DiagnosticUtil {

    private enum RENDER_TYPE {
        TEXT, IMAGE
    }

    private static final String INNER_TABLE_HEADER = "<tr>\n<td colspan='4' class='yaml'>\n<table width=\"100%\" class=\"yaml\">";

    private static final String INNER_TABLE_FOOTER = "</table>\n</td>\n</tr>";

    private DiagnosticUtil() {
        super();
    }

    private static String getInnerTableHeader(String id, String clazz) {
        String innerTableHeader = "<tr TR_ID TR_CLASS>\n" +
                "<td colspan='4' class='yaml' >\n" +
                "<table width='100%' class='yaml' >";
        if (id == null) {
            innerTableHeader = innerTableHeader.replace("TR_ID", "");
        } else {
            innerTableHeader = innerTableHeader.replace("TR_ID", "id='tr_" + id + "'");
        }
        if (clazz == null) {
            innerTableHeader = innerTableHeader.replace("TR_CLASS", "");
        } else {
            innerTableHeader = innerTableHeader.replace("TR_CLASS", "class='tr_" + clazz + "'");
        }
        return innerTableHeader;
    }

    public static String createDiagnosticTable(String tapFile, Map<String, Object> diagnostic, TestResult tapLine) {
        StringBuilder sb = new StringBuilder();
        // 1 is the first depth
        createDiagnosticTableRecursively(tapFile, null, diagnostic, sb, 1, tapLine);
        return sb.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void createDiagnosticTableRecursively(String tapFile, String parentKey, 
            Map<String, Object> diagnostic, StringBuilder sb, int depth, TestResult tapLine) {

        String id = getId(tapLine, tapFile);
        String clazz = getClazz(tapLine);
        sb.append(getInnerTableHeader(id, clazz));

        RENDER_TYPE renderType = getMapEntriesRenderType(diagnostic);

        if(renderType == RENDER_TYPE.IMAGE) {
            for (Entry<String, Object> entry : diagnostic.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append("<tr>");
    
                for (int i = 0; i < depth; ++i) {
                    sb.append("<td width='5%' class='hidden'> </td>");
                }
                sb.append("<td style=\"width: auto;\">").append(key).append(jspm(getKeyId(key, id))).append("</td>");
                if(key.equals("File-Content")) {
                    String fileName = "attachment";
                    Object o = diagnostic.get("File-Name");
                    if(o instanceof String) {
                        fileName = (String)o;
                    }
                    String downloadKey = fileName;
                    if(parentKey != null){
                        if(depth > 3 && !parentKey.trim().equalsIgnoreCase("files") && !parentKey.trim().equalsIgnoreCase("extensions")) {
                            downloadKey = parentKey;
                        }
                    }
                    sb.append(td(key,id)).append("<a href='downloadAttachment?f=").append(tapFile).append("&key=").append(downloadKey).append("'>").append(fileName).append("</a></td>");
                } else {
                    sb.append(td(key,id)).append("<pre>").append(org.apache.commons.lang.StringEscapeUtils.escapeHtml(value.toString())).append("</pre></td>");
                }
                sb.append("</tr>");
            }
        } else {
            for (Entry<String, Object> entry : diagnostic.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append("<tr>");
    
                for (int i = 0; i < depth; ++i) {
                    sb.append("<td width='5%' class='hidden'> </td>");
                }
                sb.append("<td style=\"width: auto;\">").append(key).append(jspm(getKeyId(key, id))).append("</td>");
                if (value instanceof java.util.Map) {
                    sb.append(td(key,id)).append("</td>");
                    createDiagnosticTableRecursively(tapFile, key, (java.util.Map) value, sb,
                            (depth + 1), tapLine);
                } else {
                    sb.append(td(key,id)).append("<pre>").append(org.apache.commons.lang.StringEscapeUtils.escapeHtml(value.toString())).append("</pre></td>");
                }
                sb.append("</tr>");
            }
        }

        sb.append(INNER_TABLE_FOOTER);
    }

    private static String td(String key, String id) {
        return "<td class='detail_body'' id='" + getKeyId(key, id) + "' >";
    }

    private static RENDER_TYPE getMapEntriesRenderType(
            Map<String, Object> diagnostic) {
        RENDER_TYPE renderType = RENDER_TYPE.TEXT;
        final Set<String> keys = diagnostic.keySet();
        if (keys.contains("File-Type")
                && (keys.contains("File-Location") || keys
                        .contains("File-Content"))) {
            renderType = RENDER_TYPE.IMAGE;
        }
        return renderType;
    }

    private static String getClazz(TestResult tapLine) {
        return Util.getClazz("details_", tapLine);
    }

    private static String getKeyId(String key, String id) {
        return key + "_" + id;
    }

    private static String getId(TestResult tapLine, String file) {
        return Util.getId("details_", tapLine, file);
    }

    private static String jspm(String id) {
        return " <u class=\"jsPM\" onclick='extendedTapsetInteractives().showHideBlockOne(\"" + id + "\")'>+/-</u>";
    }

}

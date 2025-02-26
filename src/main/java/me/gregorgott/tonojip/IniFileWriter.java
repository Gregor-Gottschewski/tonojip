/*
 * Copyright (c) 2025 Gregor Gottschewski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal in the
 * Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.gregorgott.tonojip;

import me.gregorgott.tonojip.iniobjects.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static me.gregorgott.tonojip.Symbols.*;

/**
 * The {@code IniFileWriter}-class handles INI-File writing.
 * It writes the content of an {@link IniFile}-object to a writer.
 *
 * <p>Example usage:
 * <pre>{@code
 * try (IniFileWriter writer = new IniFileWriter(new FileWriter("file.ini"))) {
 *    writer.write(iniFile);
 * }
 * }</pre>
 *
 * @author Gregor Gottschewski
 * @version 2025-02-02 (ISO 8601)
 * @see IniFile
 */
public class IniFileWriter implements Closeable {
    private final Writer out;
    private boolean sectionNewline;

    /**
     * Constructs a new {@code IniFileWriter} with the given writer.
     * The {@code sectionNewline} attribute is set to false by default.
     *
     * @param out the writer to write the INI-File to.
     */
    public IniFileWriter(Writer out) {
        this.out = out;
        sectionNewline = false;
    }

    /**
     * Writes the given {@code IniFile}.
     *
     * @param iniFile the {@code IniFile}-object to write
     * @throws IOException if an I/O-error occurs.
     */
    public void write(IniFile iniFile) throws IOException {
        for (Map.Entry<Key, Value> entry : iniFile.getGlobalValues().entrySet()) {
            out.write(getKeyValuePair(entry.getKey(), entry.getValue()));
        }

        for (Map.Entry<String, Section> entry : iniFile.getSections().entrySet()) {
            writeSection(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Writes the given section to the writer.
     * The section header is written first, the key-value-pairs follow.
     * If the {@code sectionNewline} attribute is set to true, an empty line is written at the end.
     *
     * @param sectionName name of the section without brackets.
     * @param section     {@code Section}-object to write.
     * @throws IOException if an I/O-error occurs.
     */
    private void writeSection(String sectionName, Section section) throws IOException {
        out.write(getSectionHeader(section, sectionName.trim()));

        for (Map.Entry<Key, Value> entry : section.getPairMap().entrySet()) {
            out.write(getKeyValuePair(entry.getKey(), entry.getValue()));
        }

        if (sectionNewline) {
            out.write(NEW_LINE);
        }
    }

    /**
     * Sets newline at section end parameter that is {@code false} by default.
     */
    public void setNewlineAtSection(boolean sectionNewline) {
        this.sectionNewline = sectionNewline;
    }

    private String getSectionHeader(Section section, String name) {
        return writeComment(section).append(SECTION_START).append(name).append(SECTION_END).append(NEW_LINE).toString();
    }

    /**
     * Builds a key value pair of the following structure (with new line at the end):
     *
     * <pre>{@code
     * # <comment>
     * <key>=<value>
     * }</pre>
     *
     * @return a {@code String} containing the key value pair
     */
    private String getKeyValuePair(Key key, Value value) {
        return writeComment(key).append(key).append(ASSIGN).append(value).append(NEW_LINE).toString();
    }

    /**
     * Creates a {@code StringBuilder} and appends the comment of the given
     * {@code Commentable}-object. A hashtag introduces a comment.
     * If the given object has no comment, it just returns an empty string builder.
     *
     * @return a string builder containing the comment of the given commentable-object.
     */
    private StringBuilder writeComment(Commentable commentable) {
        StringBuilder stringBuilder = new StringBuilder();

        if (commentable.hasComment()) {
            stringBuilder.append(COMMENT_HASHTAG).append(" ").append(commentable.getComment().replace(NEW_LINE, EMPTY)).append(NEW_LINE);
        }

        return stringBuilder;
    }

    /**
     * Closes the writer. A closed writer cannot be reopened.
     */
    @Override
    public void close() throws IOException {
        out.close();
    }
}

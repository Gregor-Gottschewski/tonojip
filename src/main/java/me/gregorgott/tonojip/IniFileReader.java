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

import me.gregorgott.tonojip.exceptions.FileSyntaxError;
import me.gregorgott.tonojip.iniobjects.*;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

import static me.gregorgott.tonojip.Symbols.*;

/**
 * A reader for INI-files that parses the content into an {@link IniFile} object.
 *
 * <p>This class implements {@link AutoCloseable} so you can create a try-with-resource
 * block:
 * <pre>{@code
 * try (IniFileReader reader = new IniFileReader(in)) {
 *     IniFile file = reader.parse();
 *     // do something with the file
 * }
 * }</pre>
 *
 * @author Gregor Gottschewski
 * @version 2025-20-01 (ISO 8601)
 */
public class IniFileReader implements Closeable {
    private final BufferedReader in;
    private int lineNum;
    private IniFile iniFile;
    private boolean parseComments;
    private Section currentSection;
    private StringBuilder commentBuilder;

    /**
     * Constructs a new INI-file reader. Comment parsing is enabled by default.
     *
     * @param in {@code reader} containing the INI-File.
     */
    public IniFileReader(Reader in) {
        this.in = new BufferedReader(in);
        parseComments = true;
    }

    /**
     * Parses the INI-file in the input reader into an {@code IniFile}-object.
     *
     * @return an {@code IniFile}-object representing the INI-file.
     * @throws IOException if an I/O-error occurs.
     */
    public IniFile parse() throws IOException {
        reset();

        String line;

        while ((line = in.readLine()) != null) {
            lineNum++;

            if (line.isBlank()) {
                continue;
            }

            if (handleComment(line)) {
                continue;
            }

            if (handleSection(line)) {
                continue;
            }

            if (handleKeyAssignment(line)) {
                continue;
            }

            throw new FileSyntaxError(lineNum, line);
        }

        return iniFile;
    }

    public boolean isParsingComments() {
        return parseComments;
    }

    /**
     * By disabling comment parsing, all comments will be ignored.
     * Be aware, that after writing the parsed {@code IniFile}-object to the
     * same file as the input came from, all comments will be lost.
     *
     * @param parseComments a boolean setting the parse-comments-mode.
     */
    public void setParseComments(boolean parseComments) {
        this.parseComments = parseComments;
    }

    private void reset() {
        iniFile = new IniFile();
        currentSection = null;
        commentBuilder = new StringBuilder();
        lineNum = 0;
    }

    /**
     * Handles a comment line.
     *
     * @param line the line to handle.
     * @return {@code true} if the line is a comment, {@code false} otherwise
     */
    private boolean handleComment(String line) {
        if (!isComment(line)) {
            return false;
        }

        commentBuilder.append(line.substring(1));

        return true;
    }

    /**
     * Handles a section header line.
     *
     * @param line the line to handle
     * @return {@code true} if the line is a section header, {@code false} otherwise
     */
    private boolean handleSection(String line) {
        if (!isSection(line)) {
            return false;
        }

        String sectionName = line.replace(SECTION_START, EMPTY).replace(SECTION_END, EMPTY);

        if (sectionName.startsWith(".")) {
            throw new FileSyntaxError(lineNum, line, "child section without parent");
        }

        currentSection = new Section();
        currentSection.setComment(getComment());
        iniFile.getSections().put(sectionName, currentSection);
        return true;
    }


    /**
     * This method checks if the line contains a key-value assignment.
     * If found, it parses the key-value-pair to the current section or global values.
     *
     * @param line the line to handle
     * @return {@code true} if the line contains a key-value assignment, {@code false} otherwise
     */
    private boolean handleKeyAssignment(String line) {
        if (!line.contains(ASSIGN)) {
            return false;
        }
        sortKeyValuePair(getKey(line), getValue(line));
        return true;
    }

    /**
     * Saves the {@code commentBuilder} in a string and resets the builder if comment parsing is enabled.
     *
     * @return the current comment as a string if comment parsing is enabled, an empty string otherwise.
     */
    private String getComment() {
        if (!parseComments) {
            return "";
        }

        String comment = commentBuilder.toString();
        commentBuilder.setLength(0);
        return comment;
    }

    /**
     * Checks if the given line is a section header.
     * A section header has to match these rules
     * <ol>
     *     <li>the line starts with an opening bracket '{@code [}'</li>
     *     <li>the line ends with a closing bracket '{@code ]}'</li>
     *     <li>the opening bracket is the first character in the line</li>
     *     <li>the closing bracket is the last character in the line</li>
     *     <li>there is only one closing and opening bracket in the line</li>
     * </ol>
     *
     * @param line the line to check
     * @return {@code true} if the line is a section header, {@code false} otherwise
     */
    private static boolean isSection(String line) {
        return line.startsWith(SECTION_START) && line.endsWith(SECTION_END) && line.indexOf(SECTION_START) == line.lastIndexOf(SECTION_START) && line.indexOf(SECTION_END) == line.lastIndexOf(SECTION_END);
    }

    private void sortKeyValuePair(Key key, Value value) {
        if (currentSection == null) {
            iniFile.getGlobalValues().put(key, value);
        } else {
            currentSection.getPairMap().put(key, value);
        }
    }

    private Key getKey(String line) {
        Key key = new Key(line.substring(0, line.indexOf(ASSIGN)).trim());
        key.setComment(getComment());
        return key;
    }

    private Value getValue(String line) {
        int index = line.indexOf(ASSIGN);
        return index < line.length() - 1 ? new Value(line.substring(index + 1).trim()) : Value.EMPTY_VALUE;
    }

    /**
     * Checks if the given line is a comment by checking whether it starts with
     * an {@code #}, {@code ;} or not.
     *
     * @param line the line to check
     * @return {@code true} if the line is a comment, {@code false} otherwise
     */
    private static boolean isComment(String line) {
        return line.isBlank() || line.startsWith(COMMENT_HASHTAG) || line.startsWith(COMMENT_SEMICOLON);
    }

    /**
     * Closes the input stream.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}
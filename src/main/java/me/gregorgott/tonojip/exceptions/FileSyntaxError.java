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

package me.gregorgott.tonojip.exceptions;

/**
 * Exception thrown when a syntax error is found in a file.
 *
 * @author Gregor Gottschewski
 */
public class FileSyntaxError extends RuntimeException {
    /**
     * Creates a file syntax error exception without an error message.
     *
     * @param lineNum line number where error occurred.
     * @param line line/text where error occurred.
     */
    public FileSyntaxError(int lineNum, String line) {
        super("Error in line " + lineNum + ": '" + line + "'");
    }

    /**
     * Creates a file syntax error exception with an error message.
     *
     * @param lineNum line number where error occurred.
     * @param line line/text where error occurred.
     * @param errorMessage specific error message.
     */
    public FileSyntaxError(int lineNum, String line, String errorMessage) {
        super("Error '" + errorMessage + "' in line " + lineNum + ": '" + line + "'");
    }
}

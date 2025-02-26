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

package me.gregorgott.tonojip.iniobjects;

import java.util.*;

/**
 * Represents a section.
 *
 * @author Gregor Gottschewski
 */
public class Section extends Commentable {
    private final KeyValuePairMap pairMap = new KeyValuePairMap();

    /**
     * Creates a new empty section.
     */
    public Section() {
        this(null);
    }

    /**
     * Creates a new empty section.
     *
     * @param comment comment of this section.
     */
    public Section(String comment) {
        setComment(comment);
    }

    /**
     * @return the key-value pair map of this section.
     * @see KeyValuePairMap
     */
    public KeyValuePairMap getPairMap() {
        return pairMap;
    }

    /**
     * Compares this section with another object.
     * Comments are ignored.
     *
     * @param o the object to compare with.
     * @return {@code true} if the object is a section and has the same key-value pair map, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(pairMap, section.pairMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pairMap);
    }
}

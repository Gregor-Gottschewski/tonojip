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

import java.util.HashMap;

/**
 * The {@code IniFile}-object represents an INI-File with global key-value-pairs and multiple
 * sections which can be identified by their names.
 *
 * <p>Global key-value-pairs are pairs that are not und any section. E.g.
 * <pre>{@code
 * name=Anna Appleseed
 * age=25
 * mother_language=german
 *
 * [johnappleseed]
 * name=John Appleseed
 * age=25
 * mother_language=english
 * }</pre>
 *
 * Here, the keys {@code name}, {@code age} and {@code mother_language} at the top are global.
 */
public class IniFile {
    private final KeyValuePairMap globalValues;
    private final HashMap<String, Section> sections;

    /**
     * Creates an empty {@code IniFile} with no global values and sections.
     */
    public IniFile() {
        this(new KeyValuePairMap(), new HashMap<>());
    }

    /**
     * Creates an empty {@code IniFile} with no given global values and no sections.
     *
     * @param globalValues global key-value-pairs
     */
    public IniFile(KeyValuePairMap globalValues) {
        this(globalValues, new HashMap<>());
    }

    /**
     *
     * @param globalValues
     * @param sections
     */
    public IniFile(KeyValuePairMap globalValues, HashMap<String, Section> sections) {
        this.globalValues = globalValues;
        this.sections = sections;
    }

    public KeyValuePairMap getGlobalValues() {
        return globalValues;
    }

    public HashMap<String, Section> getSections() {
        return sections;
    }
}

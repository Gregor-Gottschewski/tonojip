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

import java.util.Objects;

/**
 * Represents a commentable key.
 * When parsing the following example INI-file:
 *
 * <pre>{@code
 * [my_section]
 * ; This is a comment
 * key = value
 * }</pre>
 *
 * the key {@code key} is a commented key.
 *
 * @author Gregor Gottschewski
 */
public class Key extends Commentable {
    private final String key;

    /**
     * Constructs a new key with the given string.
     *
     * @param key the key as a string.
     */
    public Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    /**
     * Checks if two keys are the same. Ignores comment.
     *
     * @param obj object to check equality
     * @return {@code true} if the given object has the same value, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() == obj.getClass()) {
            Key val = (Key) obj;
            return Objects.equals(key, val.getKey());
        }

        if (obj.getClass() == String.class) {
            String val = (String) obj;
            return Objects.equals(val, key);
        }

        return false;
    }

    /**
     * @return the key as a string.
     */
    @Override
    public String toString() {
        return getKey();
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}

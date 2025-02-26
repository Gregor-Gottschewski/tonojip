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

import me.gregorgott.tonojip.exceptions.KeyNullException;

import java.util.HashMap;

/**
 * A map that stores key-value pairs.
 * This map does not allow null keys.
 *
 * @see Key
 * @see Value
 * @author Gregor Gottschewski
 */
public class KeyValuePairMap extends HashMap<Key, Value> {

    /**
     * Associates the specified value with the specified key in this map.
     * If the key is null or empty, a {@link KeyNullException} is thrown.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the key, or {@code null} if there was no mapping for the key.
     */
    @Override
    public Value put(Key key, Value value) {
        if (key == null || key.getKey().isBlank()) {
            throw new KeyNullException();
        }

        return super.put(key, value);
    }
}

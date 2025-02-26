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

import me.gregorgott.tonojip.exceptions.ValueConvertError;

import java.util.Objects;

/**
 * This class represents a value of a key.
 * The value is stored as a string but can be cast to another datatype if necessary.
 *
 * @author Gregor Gottschewski
 */
public class Value {
    public final static Value EMPTY_VALUE = new Value(null);
    private final String value;

    /**
     * Constructs a new value with the given string.
     *
     * @param value the value as a string.
     */
    public Value(String value) {
        this.value = value;
    }

    public String getAsString() {
        return value;
    }

    /**
     * Converts the value to an integer.
     * Throws a {@link ValueConvertError} if the value is not a valid integer.
     *
     * @return the value as an integer.
     */
    public int getAsInt() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValueConvertError(value, "Integer");
        }
    }

    /**
     * Converts the value to a long.
     * Throws a {@link ValueConvertError} if the value is not a valid long.
     *
     * @return the value as a long.
     */
    public long getAsLong() {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ValueConvertError(value, "Long");
        }
    }

    /**
     * Converts the value to a double.
     * Throws a {@link ValueConvertError} if the value is not a valid double.
     *
     * @return the value as a double.
     */
    public double getAsDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ValueConvertError(value, "Double");
        }
    }

    /**
     * Converts the value to a boolean.
     * Throws a {@link ValueConvertError} if the value is not a valid boolean.
     * The following values are considered as {@code true}: "True", "Yes" and {@code false}: "False", "No".
     *
     * @return the value as a boolean.
     */
    public boolean getAsBoolean() {
        if (Objects.equals(value, "False") || Objects.equals(value, "No")) {
            return false;
        }

        if (Objects.equals(value, "True") || Objects.equals(value, "Yes")) {
            return true;
        }

        throw new ValueConvertError(value, "Boolean");
    }

    /**
     * Checks if the value is {@code null}.
     *
     * @return {@code true} if the value is {@code null}, {@code false} otherwise.
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     * @return the value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this value to another object.
     * Comments are ignored.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Value val = (Value) o;
        return Objects.equals(value, val.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

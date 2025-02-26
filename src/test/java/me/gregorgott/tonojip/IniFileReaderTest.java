package me.gregorgott.tonojip;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import me.gregorgott.tonojip.exceptions.FileSyntaxError;
import me.gregorgott.tonojip.iniobjects.IniFile;
import me.gregorgott.tonojip.iniobjects.Key;

class IniFileReaderTest {

    @Test
    void parseFileWithGlobalValues() {
        String s = """
                key1=value1
                
                [section1]
                key2=value2
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertEquals("value1", iniFile.getGlobalValues().get(new Key("key1")).getAsString());
            assertEquals("value2", iniFile.getSections().get("section1").getPairMap().get(new Key("key2")).getAsString());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithComments() {
        String s = """
                ; This is a comment
                [section1]
                key1=value1
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertEquals("value1", iniFile.getSections().get("section1").getPairMap().get(new Key("key1")).getAsString());
            assertEquals("This is a comment", iniFile.getSections().get("section1").getTrimmedComment());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithEmptyLines() {
        String s = """
                [section1]
                
                key1=value1
                
                [section2]
                
                key2=value2
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertEquals("value1", iniFile.getSections().get("section1").getPairMap().get(new Key("key1")).getAsString());
            assertEquals("value2", iniFile.getSections().get("section2").getPairMap().get(new Key("key2")).getAsString());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithInvalidSyntax() {
        String s = """
                [section1]
                key1=value1
                invalid_line
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertThrowsExactly(FileSyntaxError.class, iniFileReader::parse);
        assertDoesNotThrow(() -> {
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithEmptyContent() {
        String s = "";

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertTrue(iniFile.getSections().isEmpty());
            assertTrue(iniFile.getGlobalValues().isEmpty());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithMultipleSectionsAndGlobalValues() {
        String s = """
                key1=value1
                key2=value2
                
                [section1]
                key3=value3
                
                [section2]
                key4=value4
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertEquals("value1", iniFile.getGlobalValues().get(new Key("key1")).getAsString());
            assertEquals("value2", iniFile.getGlobalValues().get(new Key("key2")).getAsString());
            assertEquals("value3", iniFile.getSections().get("section1").getPairMap().get(new Key("key3")).getAsString());
            assertEquals("value4", iniFile.getSections().get("section2").getPairMap().get(new Key("key4")).getAsString());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithEmptySection() {
        String s = """
                [section1]
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertTrue(iniFile.getSections().containsKey("section1"));
            assertTrue(iniFile.getSections().get("section1").getPairMap().isEmpty());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithWhitespaceAroundKeyAndValue() {
        String s = """
                [section1]
                key1 = value1
                key2= value2 
                key3 =value3
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertEquals("value1", iniFile.getSections().get("section1").getPairMap().get(new Key("key1")).getAsString());
            assertEquals("value2", iniFile.getSections().get("section1").getPairMap().get(new Key("key2")).getAsString());
            assertEquals("value3", iniFile.getSections().get("section1").getPairMap().get(new Key("key3")).getAsString());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithDuplicateKeysInSection() {
        String s = """
                [section1]
                key1=value1
                key1=value2
                """;

        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertEquals("value2", iniFile.getSections().get("section1").getPairMap().get(new Key("key1")).getAsString());
            iniFileReader.close();
        });
    }

    @Test
    void parseFileWithEmptyValues() {
        String s = """
                key1=
                """;
        IniFileReader iniFileReader = new IniFileReader(new StringReader(s));
        assertDoesNotThrow(() -> {
            IniFile iniFile = iniFileReader.parse();
            assertNull(iniFile.getGlobalValues().get(new Key("key1")).getAsString());
            iniFileReader.close();
        });
    }
}

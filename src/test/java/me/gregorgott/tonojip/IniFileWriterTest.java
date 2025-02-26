package me.gregorgott.tonojip;

import me.gregorgott.tonojip.iniobjects.IniFile;
import me.gregorgott.tonojip.iniobjects.Key;
import me.gregorgott.tonojip.iniobjects.Section;
import me.gregorgott.tonojip.iniobjects.Value;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class IniFileWriterTest {
    @Test
    void writeIniFileWithGlobalValues() throws IOException {
        IniFile iniFile = new IniFile();
        iniFile.getGlobalValues().put(new Key("key1"), new Value("value1"));
        StringWriter writer = new StringWriter();
        IniFileWriter iniFileWriter = new IniFileWriter(writer);

        iniFileWriter.write(iniFile);
        iniFileWriter.close();

        assertEquals("key1=value1\n", writer.toString());
    }

    @Test
    void writeIniFileWithSections() throws IOException {
        IniFile iniFile = new IniFile();
        Section section = new Section();
        section.getPairMap().put(new Key("key1"), new Value("value1"));
        iniFile.getSections().put("section1", section);
        StringWriter writer = new StringWriter();
        IniFileWriter iniFileWriter = new IniFileWriter(writer);

        iniFileWriter.write(iniFile);
        iniFileWriter.close();

        assertEquals("[section1]\nkey1=value1\n", writer.toString());
    }

    @Test
    void writeIniFileWithComments() throws IOException {
        IniFile iniFile = new IniFile();
        Section section = new Section();
        section.setComment("Section comment");
        Key key = new Key("key1");
        key.setComment("Key-Value comment");
        section.getPairMap().put(key, new Value("value1"));
        iniFile.getSections().put("section1", section);
        StringWriter writer = new StringWriter();
        IniFileWriter iniFileWriter = new IniFileWriter(writer);

        iniFileWriter.write(iniFile);
        iniFileWriter.close();

        assertEquals("# Section comment\n[section1]\n# Key-Value comment\nkey1=value1\n", writer.toString());
    }

    @Test
    void writeIniFileWithEmptySection() throws IOException {
        IniFile iniFile = new IniFile();
        iniFile.getSections().put("section1", new Section());
        StringWriter writer = new StringWriter();
        IniFileWriter iniFileWriter = new IniFileWriter(writer);

        iniFileWriter.write(iniFile);
        iniFileWriter.close();

        assertEquals("[section1]\n", writer.toString());
    }

    @Test
    void writeIniFileWithNewlineAtSectionEnd() throws IOException {
        IniFile iniFile = new IniFile();
        Section section = new Section();
        section.getPairMap().put(new Key("key1"), new Value("value1"));
        iniFile.getSections().put("section1", section);
        StringWriter writer = new StringWriter();
        IniFileWriter iniFileWriter = new IniFileWriter(writer);
        iniFileWriter.setNewlineAtSection(true);

        iniFileWriter.write(iniFile);
        iniFileWriter.close();

        assertEquals("[section1]\nkey1=value1\n\n", writer.toString());
    }
}
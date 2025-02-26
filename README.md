# TonoJip

_TonoJip_ (short form of "The one 'n only Java INI-parser") is a Java library to read and write INI-files.
The advantages of TonoJip over other INI-parsers are:

* no dependencies
* small code base ($\sim 365$ source code lines without empty lines and comment lines)
* full documentation
* faster than Ini4J (read more under [Performance comparison](#performance-comparison))
* MINT-License

> **Table of Contents**
> 
> 1. [Quickstart](#quickstart)
> 2. [The Ironic Story Behind TonoJip](#the-ironic-story-behind-tonojip)
> 3. [The Reader](#the-reader)
> 4. [The Writer](#the-writer)
> 5. [Performance comparison](#performance-comparison)

## Quickstart

**Reading**

The parser can be set up in a few lines of code. The following example shows how to parse an INI-file:

```java
try (InputStreamReader is = new InputStreamReader(iniFile)) {
    IniFileReader reader = new IniFileReader(is);
    IniFile iniFile = reader.parse();
    // doing some stuff with the parsed data ...
    Section settingsSection = iniFile.getSections().get("settings");
    System.out.println("Theme: " + settingsSection.getPairMap().get("theme"));
}
```

**Writing**

Writing goes the same easy way. Build your data structure first and then write it to an INI-file:

```java
// create a new file and section
IniFile iniFile = new IniFile();
Section section = new Section();

// add key-value-pair to the section
section.getPairMap().put(new Key("key1"), new Value("value1"));

// add section to the iniFile
iniFile.getSections().put("section1", section);

// write iniFile to file, string, etc.
StringWriter writer = new StringWriter();
IniFileWriter iniFileWriter = new IniFileWriter(writer);
iniFileWriter.write(iniFile);
iniFileWriter.close();
```

## The Ironic Story Behind TonoJip

Professors in university tried their best to teach me the "five golden aspects of programming":

* Don't reinvent the wheel.
* Never optimize what doesn’t need optimizing.
* Don't invent problems just to solve them.
* Don't turn simple tasks into epic quests.

Of course, I ignored every single one of these aspects and developed this INI-file parser – _even though the world already has plenty (yet another)._

Why? Because I wanted a challenge: to create the fastest and smallest parser ever.
So, here’s my overly ambitious and completely unnecessary masterpiece. Enjoy!

## Performance comparison

My overly ambitious and completely unnecessary masterpiece is not only the fastest and smallest parser ever,
but it also outperforms all other parsers.
Okay, that's maybe not true...  
I was too lazy and just compared it to Ini4J, and the results show that TonoJip is for sure faster than Ini4J.

```
Benchmark                      Mode  Cnt  Score   Error  Units
BenchmarkRunner.ini4j          avgt   25  0,044 ± 0,001  ms/op
BenchmarkRunner.tonojip        avgt   25  0,029 ± 0,001  ms/op
```

> That's an increase of **34%** in performance. Useless for an INI-parser, but still cool.  
> _Sidenote: I'm maybe not too lazy to compare the performance with more parser, but I'm a bit afraid of the results._

> Thanks for taking part of my project.  
> **Happy Coding** 🚀

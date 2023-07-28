package com.project.datadriventesting.json;


import static com.project.component.TestBase.jsonFile;

public class JsonReader {

    public static String readJsonName() {
        return jsonFile.fileName();
    }
}
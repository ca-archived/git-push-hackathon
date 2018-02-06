package com.example.masato.githubfeed.model.diff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffParser {

    public static List<DiffCodeLine> parseDiffCode(String code) {
        List<DiffCodeLine> codeLines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(code));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                codeLines.add(new DiffCodeLine(line));
                line = bufferedReader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return codeLines;
    }

}

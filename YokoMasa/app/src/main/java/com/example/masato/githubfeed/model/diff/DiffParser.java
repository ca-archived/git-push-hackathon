package com.example.masato.githubfeed.model.diff;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffParser {

    public static List<DiffFile> parseDiffFiles(String diffString) {
        List<DiffFile> diffFiles = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(diffString));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                while (!line.startsWith("+++ b/")) {
                    line = bufferedReader.readLine();
                }
                String fileName = line.substring(5, line.length());

                while (!line.startsWith("@@ ")) {
                    line = bufferedReader.readLine();
                }

                StringBuilder builder = new StringBuilder();
                while (!line.startsWith("diff --git")) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                }
                DiffFile diffFile = parseDiffFile(builder.toString(), fileName);
                diffFiles.add(diffFile);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return diffFiles;
    }

    public static DiffFile parseDiffFile(String code, String fileName) {
        DiffFile diffFile = new DiffFile();
        List<DiffCodeLine> codeLines = new ArrayList<>();
        int additions = 0;
        int deletions = 0;
        BufferedReader bufferedReader = new BufferedReader(new StringReader(code));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                int status = 0;
                if (line.startsWith("+")) {
                    status = DiffCodeLine.ADDED;
                    additions++;
                } else if (line.startsWith("-")) {
                    status = DiffCodeLine.REMOVED;
                    deletions++;
                } else {
                    status = DiffCodeLine.NORMAL;
                }
                codeLines.add(new DiffCodeLine(line, status));
                line = bufferedReader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        diffFile.fileName = fileName;
        diffFile.codeLines = codeLines;
        diffFile.additions = additions;
        diffFile.deletions = deletions;
        return diffFile;
    }

}

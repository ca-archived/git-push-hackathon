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
                String before = "";
                String after= "";
                while (line != null) {
                    if (line.startsWith("---")) {
                        before = line.substring(4);
                    } else if (line.startsWith("+++")) {
                        after = line.substring(4);
                        break;
                    }
                    line = bufferedReader.readLine();
                }
                String fileName = extractFileName(before, after);

                StringBuilder builder = new StringBuilder();
                line = bufferedReader.readLine();
                while (line != null) {
                    if (line.startsWith("diff --git")) {
                        break;
                    }
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
                    line = bufferedReader.readLine();
                }

                DiffFile diffFile = parseDiffFile(builder.toString(), fileName);
                diffFiles.add(diffFile);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return diffFiles;
    }

    private static String extractFileName(String beforeFile, String afterFile) {
        if (beforeFile.equals("") || afterFile.equals("")) {
            return "";
        }
        if (afterFile.equals("/dev/null")) {
            return beforeFile.substring(1);
        }
        return afterFile.substring(1);
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
                } else if (line.startsWith("@@")) {
                    status = DiffCodeLine.CHANGE_LINES;
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

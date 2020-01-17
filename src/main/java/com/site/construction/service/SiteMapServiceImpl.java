package com.site.construction.service;

import com.site.construction.constants.Block;
import com.site.construction.validation.ValidationErrors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//service processes the input site map file and generates the Input lines and site map before simulator runs.
public class SiteMapServiceImpl implements SiteMapService{
    public List<String> processInputFile(String fileName, ValidationErrors validationErrors) {
        BufferedReader reader;
        List<String> inputLines = new ArrayList<>();
        int columns = 0;
        try {
            InputStream resourceAsStream = SiteMapServiceImpl.class.getClassLoader().getResourceAsStream(fileName);
            reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line = reader.readLine();
            while (line != null) {
                if (columns == 0) {
                    columns = line.trim().length();
                } else if (columns != line.trim().length()) {
                    validationErrors.getErrors().add("ERROR: Site map should have the same number of columns on each row");
                    return null;
                }
                inputLines.add(line.trim());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            validationErrors.getErrors().add("ERROR: Cannot read input site map due to exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
        if (inputLines.size() == 0) {
            validationErrors.getErrors().add("ERROR: Site Map cannot be empty");
            return null;
        }
        return inputLines;
    }

    public char[][] generateSiteMap(List<String> inputLines, ValidationErrors validationErrors) {
        char[][] siteMap = null;
        for (int rows = 0; rows < inputLines.size(); rows++) {
            char[] charsInLine = inputLines.get(rows).toCharArray();
            for (char ch : charsInLine) {
                //validate if siteMap contains weird characters
                if (Arrays.stream(Block.values()).map(Block::getValue).noneMatch(character -> character.equals(ch))) {
                    validationErrors.getErrors().add("ERROR: Site Map should have only the allowed characters 'o', 'r', 't' and 'T'");
                    return null;
                }
            }
            if (siteMap == null) {
                siteMap = new char[inputLines.size()][charsInLine.length];
            }
            siteMap[rows] = charsInLine;
        }
        return siteMap;
    }

    public void printSiteMap(char[][] siteMap) {
        if (siteMap == null || siteMap.length == 0) {
            return;
        }

        for (int row = 0; row < siteMap.length; row++) {
            for (int col = 0; col < siteMap[row].length; col++) {
                System.out.print(siteMap[row][col] + " ");
            }
            System.out.println();
        }
    }
}

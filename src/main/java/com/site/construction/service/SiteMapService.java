package com.site.construction.service;

import com.site.construction.validation.ValidationErrors;

import java.util.List;

public interface SiteMapService {
    List<String> processInputFile(String fileName, ValidationErrors validationErrors);

    char[][] generateSiteMap(List<String> inputLines, ValidationErrors validationErrors);

    void printSiteMap(char[][] siteMap);
}

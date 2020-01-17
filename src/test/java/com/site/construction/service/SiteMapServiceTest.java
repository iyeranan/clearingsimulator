package com.site.construction.service;

import com.site.construction.validation.ValidationErrors;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SiteMapServiceTest {
    private static SiteMapService siteMapService;
    private static ValidationErrors validationErrors;

    @BeforeAll
    public static void setUp() {
        siteMapService = new SiteMapServiceImpl();
    }

    @BeforeEach
    public void validationErrorsSetup() {
        validationErrors = new ValidationErrors();
    }

    @Test
    public void testGenerateSiteMapValidationError() {
        List<String> inputLines = new ArrayList<>();
        inputLines.add("ootooooooo");
        inputLines.add("oooooooToo");
        inputLines.add("rrrooooToo");
        inputLines.add("rrrroooooo");
        inputLines.add("rrrrJtoooo");

        siteMapService.generateSiteMap(inputLines, validationErrors);
        assertEquals(validationErrors.getErrors().size(), 1);
        assertEquals(validationErrors.getErrors().get(0), "ERROR: Site Map should have only the allowed characters 'o', 'r', 't' and 'T'");
    }

    @Test
    public void testGenerateSiteMap() {
        List<String> inputLines = new ArrayList<>();
        inputLines.add("ootooooooo");
        inputLines.add("oooooooToo");
        inputLines.add("rrrooooToo");
        inputLines.add("rrrroooooo");
        inputLines.add("rrrrotoooo");

        char[][] chars = siteMapService.generateSiteMap(inputLines, validationErrors);
        assertEquals(chars.length, 5);
        assertEquals(chars[0].length, 10);
    }

    @Test
    public void testProcessInputFileValidationInconsistentColumns() {
        List<String> input = siteMapService.processInputFile("testSiteMapInconsistentColumns.txt", validationErrors);
        assertNull(input);
        assertEquals(validationErrors.getErrors().size(), 1);
        assertEquals(validationErrors.getErrors().get(0), "ERROR: Site map should have the same number of columns on each row");
    }

    @Test
    public void testProcessInputFileValidationEmptySiteMap() {
        List<String> input = siteMapService.processInputFile("testEmptySiteMap.txt", validationErrors);
        assertNull(input);
        assertEquals(validationErrors.getErrors().size(), 1);
        assertEquals(validationErrors.getErrors().get(0), "ERROR: Site Map cannot be empty");
    }

    @Test
    public void testProcessInputFile() {
        List<String> input = siteMapService.processInputFile("testSiteMap.txt", validationErrors);
        assertNotNull(input);
        assertEquals(validationErrors.getErrors().size(), 0);
        assertEquals(input.size(), 5);
    }

    @AfterAll
    public static void tearDown() {
        siteMapService = null;
    }

    @AfterEach
    public void validationErrorsTearDown() {
        validationErrors = null;
    }
}

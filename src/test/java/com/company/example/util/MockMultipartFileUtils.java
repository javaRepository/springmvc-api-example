package com.company.example.util;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class MockMultipartFileUtils {

    public static MockMultipartFile makeMockMultipartFile(String formFileName, String uri) throws IOException {
        URL sourceUrl = MockMultipartFileUtils.class.getResource(uri);
        File sourceFile = new File(sourceUrl.getFile());
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(sourceFile));

        return new MockMultipartFile(formFileName, sourceFile.getName(), null, bytes);
    }

}

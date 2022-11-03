package com.rojojun.s3practice.etc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UploadUtils{
    ArrayList<String> extensions;
    UUID randomFilename;

    public boolean setExtensions(String customExt) {
        boolean result = false;
        if (customExt.length() <= 10) {
            new RuntimeException("확장자의 최대 길이는 10글자를 넘을 수 없습니다.");
        }
        else
        {
            extensions.add(customExt);
            result = true;
        }
        return result;
    }

    public HashMap<String, String> randomFileMap(String fileConvertString, String dirName) {

        String originalFileName = fileConvertString;
        String savedFileName = dirName + "/" + randomFilename;

        return randomFileMap(originalFileName, savedFileName);
    }
}

package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CaptchaBuilder {
    public static int captchaBuilder() {
        FileHandle dir = new FileHandle("assets/captcha");
        int random = ThreadLocalRandom.current().nextInt(0, 30);
        return Integer.parseInt(dir.list()[random].name().substring(0, 4));
    }
}

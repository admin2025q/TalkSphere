package com.tt.admin.util;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

@Slf4j
public class CaptchaUtil {

    private static final int WIDTH = 120; // 验证码图片宽度
    private static final int HEIGHT = 40; // 验证码图片高度
    private static final int CODE_COUNT = 4; // 验证码字符数
    private static final int FONT_SIZE = 30; // 字体大小

    /**
     * 生成验证码图片
     *
     * @param outputStream 输出流，用于返回图片
     * @return 验证码字符串
     */
    public static String generateCaptcha(OutputStream outputStream) {
        // 验证码字符集
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 填充背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        // 设置字体
        g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

        // 生成验证码字符串
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < CODE_COUNT; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));
            captcha.append(c);

            // 随机颜色
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

            // 绘制字符
            g.drawString(String.valueOf(c), 20 + i * 20, 30);
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        try {
            // 释放资源
            g.dispose();

            // 输出图片到流
            ImageIO.write(image, "PNG", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error ImageIO processing input: {}", e);
        }

        return captcha.toString();
    }
}
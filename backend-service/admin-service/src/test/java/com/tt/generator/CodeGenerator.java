package com.tt.generator;

import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class CodeGenerator {
        private final static String projectPath = System.getProperty("user.dir");

        public static void main(String[] args) {
                VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
                // 1. 创建代码生成器
                FastAutoGenerator.create(
                                "jdbc:mysql://127.0.0.1:3306/talksphere?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                                "talksphere",
                                "Aa123456...").globalConfig(builder -> {
                                        builder.author("tt") // 设置作者
                                                        // .enableSwagger() // 开启 Swagger 模式
                                                        .disableOpenDir() // 禁止打开输出目录
                                                        .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                                }).packageConfig(builder -> {
                                        builder.parent("com.tt") // 设置父包名
                                                        .moduleName("admin") // 设置模块名
                                                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath
                                                                        + "/src/main/resources/mapper/admin")); // 设置
                                                                                                                // XML
                                                                                                                // 文件生成路径
                                }).strategyConfig(builder -> {
                                        builder.addInclude("admin_user_permission") // 设置需要生成的表名
                                                        .addTablePrefix("t_") // 设置过滤表前缀
                                                        .entityBuilder()
                                                        .enableLombok() // 启用 Lombok
                                                        .naming(NamingStrategy.underline_to_camel) // 表名生成策略
                                                        .columnNaming(NamingStrategy.underline_to_camel) // 字段名生成策略
                                                        .mapperBuilder()
                                                        .enableBaseResultMap() // 生成 BaseResultMap
                                                        .enableBaseColumnList() // 生成 BaseColumnList
                                                        .controllerBuilder()
                                                        .enableRestStyle() // 生成 @RestController 控制器
                                                        .enableHyphenStyle()
                                                        .serviceBuilder()
                                                        .formatServiceFileName("%sService"); // 去掉 I 前缀; // URL 驼峰转连字符
                                })
                                .templateEngine(velocityTemplateEngine) // 使用 Velocity 模板引擎
                                .execute();
        }
}
package com.isc.backend.mvc.entity.Util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MBPAutoGenerator  {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql:///iscweb";
        String username = "root";
        String password = "zyy20031222";
        String moduleName = "mvc";
        String mapper = "C:\\Users\\86153\\Desktop\\GithubRep\\ISCWeb_Project\\backend\\src\\main\\resources\\mapper\\" + moduleName;
        String tables = "activity,activity_volunteer_relation,organizer,regulator,volunteer";

        FastAutoGenerator.create(dbUrl, username, password)
                .globalConfig(builder -> {
                    builder.author("711lxsky") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("C:\\Users\\86153\\Desktop\\GithubRep\\ISCWeb_Project\\backend\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.isc.backend") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapper)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }


}

package com.tt.admin.handler;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tt.admin.repository.repositoryHandler.LocalCacheHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartupRunner implements CommandLineRunner {

    private final List <LocalCacheHandler> localCacheHandlers;

    @Override
    public void run(String... args) throws Exception {
        log.info("Application started with arguments: {}", Arrays.toString(args));

        // 在这里执行你的启动任务
        initializeCache();
        loadInitialData();
        startScheduler();
    }   

    private void initializeCache() {
        log.info("Initializing cache...");
        // 你的缓存初始化逻辑
        localCacheHandlers.forEach(LocalCacheHandler::refresh);
    }

    private void loadInitialData() {
        log.info("Loading initial data...");
        // 你的加载初始数据逻辑
    }

    private void startScheduler() {
        log.info("Starting scheduler...");
        // 你的启动调度器逻辑
    }

    
}
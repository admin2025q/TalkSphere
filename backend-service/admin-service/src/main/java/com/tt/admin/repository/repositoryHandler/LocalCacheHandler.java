package com.tt.admin.repository.repositoryHandler;

import org.springframework.web.bind.annotation.ResponseBody;

import com.github.benmanes.caffeine.cache.Cache;


@ResponseBody
public abstract class LocalCacheHandler<T> {

    // 抽象方法，强制子类提供 Cache 实例
    protected abstract Cache<String, T> getCache();

    // 抽象方法，强制子类提供 Cache 实例
    public abstract void refresh();

    public T loadCache(String key) {
        Cache<String, T> cache = getCache();
        if (cache == null) {
            throw new IllegalStateException("Cache 未初始化");
        }
        return cache.getIfPresent(key);
    }

    /**
     * 缓存数据
     * @param key
     * @param value
     */
    public void clearMenuCache() {
        cacheAble()
        .invalidateAll();
    }

    private Cache<String, T> cacheAble(){
        Cache<String,T> catche = getCache();
        if (catche == null) {
            throw new IllegalStateException("Cache 未初始化");
        }
        return catche;
    }
}

package com.century.cleaner.data.model

data class CacheInfo(
    private val appName: String,
    private val packageName: String,
    private val cacheSize: Long)
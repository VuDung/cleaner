package com.century.cleaner.data.model

data class TotalCache(
  val currentCount: Int,
  val currentPackage: String,
  val singleCaches: List<SingleCache>,
  val totalBytes: Long,
  val totalCount: Int
)
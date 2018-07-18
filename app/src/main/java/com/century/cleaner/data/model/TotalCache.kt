package com.century.cleaner.data.model

data class TotalCache(
  val count: Int,
  val singleCaches: List<SingleCache>,
  val totalBytes: Long,
  val totalCount: Int
)
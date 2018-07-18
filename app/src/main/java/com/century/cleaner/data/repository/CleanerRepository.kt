package com.century.cleaner.data.repository

import com.century.cleaner.data.model.TotalCache
import io.reactivex.Flowable

interface CleanerRepository{
  fun loadCaches(): Flowable<TotalCache>
  fun clearCaches()
}
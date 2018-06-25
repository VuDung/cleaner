package com.century.cleaner.data.repository

import com.century.cleaner.data.model.CacheInfo
import com.century.cleaner.data.State
import com.century.cleaner.data.repository.CleanerRepositoryImplement.Info
import io.reactivex.Flowable
import io.reactivex.subjects.Subject

interface CleanerRepository{
  fun loadCaches(): Flowable<CleanerRepositoryImplement.Info>
  fun clearCaches()
}
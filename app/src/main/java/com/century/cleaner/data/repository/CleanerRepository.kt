package com.century.cleaner.data.repository

import io.reactivex.Flowable

interface CleanerRepository{
  fun loadCaches(): Flowable<CleanerRepositoryImplement.Info>
  fun clearCaches()
}
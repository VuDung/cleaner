package com.century.cleaner.data.repository

import com.century.cleaner.data.model.CacheInfo
import com.century.cleaner.data.State
import io.reactivex.subjects.Subject

interface CleanerRepository{
//  val totalCache: LiveData<Long>
//  val count: LiveData<Int>
//  val cacheInfos: LiveData<List<CacheInfo>>
  val mTotalCache: Subject<Long>
  val mCount: Subject<IntArray>
  val mCacheInfos: Subject<MutableList<CacheInfo>>
  val mState: Subject<State>

  fun loadCaches()
  fun clearCaches()
}
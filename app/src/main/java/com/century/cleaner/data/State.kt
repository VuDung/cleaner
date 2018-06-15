package com.century.cleaner.data

sealed class State{

  class InProgress : State(){
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false
      return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
  }

  class Success : State()
  data class Failure(val errorMessage: String?, val e: Throwable?) : State()

  companion object {
    fun  inProgress(): State = InProgress()

    fun  success(): State = Success()

    fun  failure(errorMessage: String?, e: Throwable?): State = Failure(
        errorMessage, e)
  }
}

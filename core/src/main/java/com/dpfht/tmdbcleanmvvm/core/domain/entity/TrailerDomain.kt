package com.dpfht.tmdbcleanmvvm.core.domain.entity

data class TrailerDomain(
  val id: Int = -1,
  val results: List<TrailerEntity> = arrayListOf()
)

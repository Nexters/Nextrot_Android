package com.nextrot.troter.data

interface TestRepository {
    fun giveData(): String
}

class TestRepositoryImpl : TestRepository {
    override fun giveData(): String = "TEST DATA"
}
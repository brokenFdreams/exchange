package lu.exchange.domain

import kotlin.random.Random

class TestAccountIdGenerator : AccountIdGenerator {
    override fun generate(): AccountId = AccountId(Random.nextLong())
}
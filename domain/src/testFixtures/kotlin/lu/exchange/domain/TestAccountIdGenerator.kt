package lu.exchange.domain

private var lastAccountId = 0L

fun getNextAccountId(): AccountId {
    return AccountId(++lastAccountId)
}

class TestAccountIdGenerator : AccountIdGenerator {
    override fun generate(): AccountId = getNextAccountId()
}
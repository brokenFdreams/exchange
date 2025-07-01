package lu.exchange.domain.provider

import lu.exchange.domain.Account

fun interface AccountPersister {
    fun persist(account: Account)
}
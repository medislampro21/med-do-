package com.shub39.grit.billing

import com.mhd.grit.billing.SubscriptionResult

class BillingHandler {
    suspend fun isPlusUser(): Boolean = false
    suspend fun userResult(): SubscriptionResult = SubscriptionResult.Subscribed
}
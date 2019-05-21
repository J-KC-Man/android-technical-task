package com.example.minimoneybox.datasource.model.investorProducts

data class ProductResponses (
    private val Moneybox: String,
    private val SubscriptionAmount: String,
    private val TotalFees: String,
    private val IsFavourite: String,
    private val PlanValue: String,
    private val Product: Product,
    private val IsSelected: String,
    private val Id: String,
    private val Personalisation: Personalisation,
    private val InvestorAccount: InvestorAccount
)

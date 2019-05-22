package com.example.minimoneybox.datasource.model.investorProducts

data class ProductResponses (
     val Moneybox: String,
     val SubscriptionAmount: String,
     val TotalFees: String,
     val IsFavourite: String,
     val PlanValue: String,
     val Product: Product,
     val IsSelected: String,
     val Id: String,
     val Personalisation: Personalisation,
     val InvestorAccount: InvestorAccount
)

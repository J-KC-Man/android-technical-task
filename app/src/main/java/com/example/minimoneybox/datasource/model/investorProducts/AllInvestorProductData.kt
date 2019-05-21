package com.example.minimoneybox.datasource.model.investorProducts

data class AllInvestorProductData (
     private val TotalPlanValue: String,
     private val TotalEarnings: String,
     private val TotalContributionsNet: String,
     private val TotalEarningsAsPercentage: String,
     private val ProductResponses: Array<ProductResponses>
)
package com.example.minimoneybox.datasource.model.investorProducts

data class AllInvestorProductData (
      val TotalPlanValue: String,
      val TotalEarnings: String,
      val TotalContributionsNet: String,
      val TotalEarningsAsPercentage: String,
      val ProductResponses: Array<ProductResponses>
)
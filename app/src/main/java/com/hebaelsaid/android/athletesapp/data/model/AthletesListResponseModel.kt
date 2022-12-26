package com.hebaelsaid.android.athletesapp.data.model

data class AthletesListResponseModel(
    val athletes: List<Athlete?>?
) {
    data class Athlete(
        val brief: String?, // Michael Jordan (MJ) is considered the greatest basketball player of all time. He was voted NBA most valuable player a record five times. Playing most of his career for the Chicago Bulls, he won six NBA Championships. Michael Jordan also became one of the most marketed sportsmen, with lucrative endorsements with Nike, helping to make the Nike Air shoe one of best known trainers in the world. His career and high profile, coincided with a rapid growth in the popularity of NBA basketball, and his personal achievements are considered a major factor in boosting the popularity of basketball.
        val image: String?, // http://i.imgur.com/xNLMcVJ.jpg
        val name: String? // Michael Jordan
    )
}
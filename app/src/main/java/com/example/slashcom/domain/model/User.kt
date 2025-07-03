package com.example.slashcom.domain.model

import com.google.firebase.database.PropertyName

data class User(
    var username: String = "",
    var email: String = "",
    @get:PropertyName("isIbu") @set:PropertyName("isIbu")
    var isIbu: Boolean = false,
    var id: String? = ""
)


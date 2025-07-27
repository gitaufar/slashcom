package com.example.slashcom.cache

import com.example.slashcom.domain.model.Mood

object UserData {
    var userName: String = ""
    var uid: String = ""
    var id: String = ""
    var isIbu: Boolean = false
    var lastMood: Mood? = null
    var listMoods: List<Mood>? = null
    var email: String = ""
    var listPendamping: List<String> = emptyList()
}

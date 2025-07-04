package com.example.slashcom.domain.model

data class IbuData(
    var id: String = "",
    var mood: List<Mood> = emptyList(),
    var pendamping: Map<String, String> = emptyMap()
)

package com.example.slashcom.cache

object ListQuestion {
    private val list = listOf(
        "Ceritakan apa yang terjadi hari ini 🌿",
        "Menurut Ibu kenapa itu bisa terjadi ☺️",
        "Bagaimana situasi tersebut membuat Ibu merasa - secara emosional 🎭",
        "Bagaimana hubungan dengan bayi/pasangan/keluarga 💞",
        "Apa yang ingin Ibu lakukan dan katakan sebagai akibat dari perasaan tersebut 🗣️",
        "Apa hal paling mengejutkan yang terjadi minggu ini? 😮",
        "Apa hal yang paling Ibu syukuri hari ini? 🙏",
        "Bagaimana kualitas tidur Ibu semalam? 😴",
        "Apa harapan Ibu untuk hari esok? 🌅",
        "Apakah Ibu merasa didengar hari ini? 👂",
        "Apa yang membuat Ibu merasa tenang atau damai? 🕊️",
        "Siapa orang yang paling mendukung Ibu minggu ini? 👩‍❤️‍👨",
        "Apa yang ingin Ibu katakan kepada diri sendiri saat ini? 🪞",
        "Bagaimana perasaan Ibu saat berinteraksi dengan anak? 👶",
        "Apa hal kecil yang membuat Ibu tersenyum hari ini? 😊",
        "Apakah Ibu merasa cukup istirahat hari ini? 🛌",
        "Ceritakan momen ketika Ibu merasa bangga pada diri sendiri 🏆",
        "Apa tantangan terbesar Ibu minggu ini? 🧗",
        "Apa yang Ibu butuhkan saat ini untuk merasa lebih baik? 💡",
        "Bagaimana Ibu menjaga diri saat merasa lelah? 💆‍♀️"
    )

    fun getRandomQuestions(count: Int = 5): List<String> {
        return list.shuffled().take(count)
    }
}

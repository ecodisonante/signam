package com.ecodisonante.signam.model


data class VideoQuiz(
    val id: Int,
    val url: String,
    val categoryId: Int,
    val choiceId: Int,
    val desc: String,
)

data class Category(
    val id: Int,
    val name: String
)

data class Choice(
    val id: Int,
    val categoryId: Int,
    val text: String
)




class GameDataProvider {
    companion object {

        val categoryList = mutableListOf(
            Category(1, "Frases"),
            Category(1, "Palabras"),
        )

        val choiceList = mutableListOf(
            Choice(1, 1, "Hola"),
            Choice(2, 1, "Adiós"),
            Choice(3, 1, "Buenos días"),
            Choice(4, 1, "Buenas tardes"),
            Choice(5, 1, "Buenas noches"),
        )

        val videoList = mutableListOf(
            VideoQuiz(1, "raw/buenos_dias.mp4", 1, 3, "Buenos días")
        )
    }
}
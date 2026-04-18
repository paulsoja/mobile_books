package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BooksIndexResponse(
    val books: List<BookIndexItemResponse>,
)

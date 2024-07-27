package com.diogo.library.domain.book

import com.diogo.library.domain.author.Author
import com.diogo.library.domain.language.Language
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "book")
data class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var isbn: String,

    @Column(nullable = false)
    var releaseDate: LocalDate,

    @Column(nullable = false, columnDefinition = "TEXT")
    var synopsis: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var language: Language,

    @Column(nullable = false)
    var publisher: String,

    @Column(nullable = false)
    var collection: String,

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    var author: Author

)
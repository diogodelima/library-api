package com.diogo.library.domain.author

import com.diogo.library.domain.book.Book
import jakarta.persistence.*

@Entity
@Table(name = "author")
data class Author(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var biography: String,

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
    var books: MutableCollection<Book> = mutableListOf()

){

    fun addBook(book: Book) {
        this.books.add(book)
    }

    fun removeBook(book: Book) {
        this.books.remove(book)
    }

}
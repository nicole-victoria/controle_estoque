package com.br.gerenciadorestoque.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Produto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val nome: String="",
    val secao: String="",
    val preco: Float?= null,
    var estoque: Int? = null
) {
}
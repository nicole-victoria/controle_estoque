package com.br.gerenciadorestoque.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class Venda(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne val produto: Produto,
    val quantidadeVendida: Int,
    val valorTotal: Float,
    val dataVenda: LocalDateTime = LocalDateTime.now()
) {
    val dataFormatada: String
        get() = dataVenda.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
}
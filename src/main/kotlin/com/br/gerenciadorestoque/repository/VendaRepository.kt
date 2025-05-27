package com.br.gerenciadorestoque.repository

import com.br.gerenciadorestoque.model.Venda
import org.springframework.data.jpa.repository.JpaRepository

interface VendaRepository: JpaRepository<Venda, Long> {
}
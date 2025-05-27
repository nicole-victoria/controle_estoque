package com.br.gerenciadorestoque.repository
import com.br.gerenciadorestoque.model.Produto
import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository: JpaRepository<Produto, Long> {
}
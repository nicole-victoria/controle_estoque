package com.br.gerenciadorestoque.service

import org.springframework.stereotype.Service
import com.br.gerenciadorestoque.repository.VendaRepository
import com.br.gerenciadorestoque.repository.ProdutoRepository
import com.br.gerenciadorestoque.model.Venda
import jakarta.transaction.Transactional

@Service
class VendaService(
    private val vendaRepository: VendaRepository,
    private val produtoRepository: ProdutoRepository
) {

    @Transactional
    fun registrarVenda(produtoId: Long, quantidadeVendida: Int) {
        val produto = produtoRepository.findById(produtoId).orElseThrow()

        require(produto.estoque!! >= quantidadeVendida) {
            "Estoque insuficiente para o produto: ${produto.nome}"
        }

        val valorTotal = quantidadeVendida * produto.preco!!
        val venda = Venda(
            produto = produto,
            quantidadeVendida = quantidadeVendida,
            valorTotal = valorTotal
        )

        // Atualiza o estoque
        produto.estoque = produto.estoque!! - quantidadeVendida
        produtoRepository.save(produto)

        vendaRepository.save(venda)
    }

    @Transactional
    fun deletarVenda(idVenda: Long, idProduto: Long) {
        val venda = vendaRepository.findById(idVenda).orElseThrow()
        val produto = produtoRepository.findById(idProduto).orElseThrow()

        if (venda.produto.id != idProduto) {
            throw IllegalArgumentException("Produto não pertence a esta venda")
        }

        val estoqueAtual = produto.estoque ?: 0
        produto.estoque = estoqueAtual + venda.quantidadeVendida

        produtoRepository.save(produto)
        vendaRepository.deleteById(idVenda)
    }
}

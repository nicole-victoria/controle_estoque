package com.br.gerenciadorestoque.controller
import org.springframework.ui.Model
import com.br.gerenciadorestoque.repository.ProdutoRepository
import com.br.gerenciadorestoque.repository.VendaRepository
import org.springframework.stereotype.Controller
import com. br.gerenciadorestoque.service.VendaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class VendaController(
    private val produtoRepository: ProdutoRepository,
    private val vendaRepository: VendaRepository,
    private val vendaService: VendaService
) {
    @GetMapping("/vendas")
    fun listarVendas(model: Model): String {
        model.addAttribute("vendas", vendaRepository.findAll())
        return "lista-vendas" // esse é o template que será renderizado
    }

    @GetMapping("/formulario/venda")
    fun formVenda(model: Model): String {
        val produtos = produtoRepository.findAll()
        model.addAttribute("produtos", produtos)
        return "registro-venda"
    }

    @PostMapping("/vendas")
    fun salvarVenda(
        @RequestParam produtoId: Long,
        @RequestParam quantidadeVendida: Int,
        redirectAttributes: RedirectAttributes
    ): String {
        return try{
            vendaService.registrarVenda(produtoId, quantidadeVendida)
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Venda registrada com sucesso!")
            "redirect:/vendas"
        } catch (e: IllegalArgumentException){
            redirectAttributes.addFlashAttribute("erroEstoque", e.message)
            "redirect:/vendas"
        }
    }

    @GetMapping("/vendas/excluir/{id}/{produtoId}")
    fun excluirVenda(@PathVariable("id") id: Long, @PathVariable("produtoId") produtoId:Long): String{
        vendaService.deletarVenda(id, produtoId)
        return "redirect:/vendas"
    }
}
package com.br.gerenciadorestoque.controller

import com.br.gerenciadorestoque.model.Produto
import com.br.gerenciadorestoque.repository.ProdutoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ProdutosController{

    @Autowired
    lateinit var  repositorio: ProdutoRepository

    @GetMapping("/formulario/cadastro")
    fun abrirFormCadastro(model: Model): String{

        val produto = Produto()

        model.addAttribute("produtoNovo", produto)
        return "cadastro"
    }

    @PostMapping("/cadastrar")
    fun cadastrarProduto(produto: Produto, redirectAttributes: RedirectAttributes): String{

        repositorio.save(produto)
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto cadastrado com sucesso!")
        return "redirect:/home"
    }

    @GetMapping("/home")
    fun abrirHome(model: Model): String{

        val produtos = repositorio.findAll()

        model.addAttribute("produtos", produtos)
        return "home"
    }


    @GetMapping("/formulario/edicao/{id}")
    fun abrirFormsEdicao(@PathVariable("id") id: Long, model: Model): String{

        val produto = repositorio.findById(id).orElse(null)

        model.addAttribute("produtoEdit", produto)
        return "edicao"
    }

    @PostMapping("/editar/{id}")
    fun editarProduto(produto: Produto, @PathVariable("id") id: Long): String{
        produto.id = id
        repositorio.save(produto)
        return "redirect:/home"
    }

}
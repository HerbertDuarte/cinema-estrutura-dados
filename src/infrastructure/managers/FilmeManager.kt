package infrastructure.managers

import CentralRepository
import domain.entity.Filme
import domain.enum.TipoProducao
import java.util.Scanner

class FilmeManager(private val repo: CentralRepository) {

    private val scanner = Scanner(System.`in`)

    fun createFilme() {
        println("\n--- Criar Filme ---")
        print("Título: ")
        val titulo = scanner.nextLine()
        print("Descrição: ")
        val descricao = scanner.nextLine()
        print("Duração (em minutos): ")
        val duracaoMinutos = scanner.nextInt()
        print("Recomendação (idade mínima): ")
        val recomendacao = scanner.nextInt()
        print("Avaliação (0.0 a 10.0): ")
        val avaliacao = scanner.nextDouble()
        scanner.nextLine() // Consumir quebra de linha
        print("Tipo de Produção (0 para NACIONAL, 1 para INTERNACIONAL): ")
        val tipoProducaoCode = scanner.nextInt()
        scanner.nextLine() // Consumir quebra de linha

        // Converte o código para o enum TipoProducao
        val tipoProducao = TipoProducao.values().find { it.cod == tipoProducaoCode }
            ?: run {
                println("Código inválido! Considerando 'NACIONAL' como padrão.")
                TipoProducao.NACIONAL
            }

        val filme = Filme(
            titulo = titulo,
            descricao = descricao,
            duracaoMinutos = duracaoMinutos,
            recomendacao = recomendacao,
            avaliacao = avaliacao,
            tipoProducao = tipoProducao
        )
        repo.filmes.add(filme)
        println("Filme adicionado com sucesso!")
    }

    fun listFilmes() {
        println("\n--- Lista de Filmes ---")
        if (repo.filmes.isEmpty()) {
            println("Nenhum filme cadastrado.")
        } else {
            repo.filmes.forEachIndexed { index, filme ->
                println("${index + 1}. ${filme.titulo} - ${filme.tipoProducao} - ${filme.avaliacao}/10")
            }
        }
    }

    fun updateFilme() {
        println("\n--- Atualizar Filme ---")
        if (repo.filmes.isEmpty()) {
            println("Nenhum filme cadastrado.")
            return
        }
        listFilmes()
        print("Selecione o número do filme para atualizar: ")
        val index = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (index !in repo.filmes.indices) {
            println("Filme não encontrado.")
            return
        }

        val filme = repo.filmes[index]
        println("Atualizando o filme: ${filme.titulo}")
        print("Novo Título (atual: ${filme.titulo}): ")
        val titulo = scanner.nextLine()
        print("Nova Descrição (atual: ${filme.descricao}): ")
        val descricao = scanner.nextLine()
        print("Nova Duração (atual: ${filme.duracaoMinutos} minutos): ")
        val duracaoMinutos = scanner.nextInt()
        print("Nova Recomendação (atual: ${filme.recomendacao}): ")
        val recomendacao = scanner.nextInt()
        print("Nova Avaliação (atual: ${filme.avaliacao}): ")
        val avaliacao = scanner.nextDouble()
        scanner.nextLine() // Consumir quebra de linha
        print("Novo Tipo de Produção (0 para NACIONAL, 1 para INTERNACIONAL): ")
        val tipoProducaoCode = scanner.nextInt()
        scanner.nextLine() // Consumir quebra de linha

        // Converte o código para o enum TipoProducao
        val tipoProducao = TipoProducao.values().find { it.cod == tipoProducaoCode }
            ?: run {
                println("Código inválido! Considerando 'NACIONAL' como padrão.")
                TipoProducao.NACIONAL
            }

        repo.filmes[index] = Filme(
            titulo = titulo,
            descricao = descricao,
            duracaoMinutos = duracaoMinutos,
            recomendacao = recomendacao,
            avaliacao = avaliacao,
            tipoProducao = tipoProducao
        )
        println("Filme atualizado com sucesso!")
    }

    fun deleteFilme() {
        println("\n--- Excluir Filme ---")
        if (repo.filmes.isEmpty()) {
            println("Nenhum filme cadastrado.")
            return
        }
        listFilmes()
        print("Selecione o número do filme para excluir: ")
        val index = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (index !in repo.filmes.indices) {
            println("Filme não encontrado.")
            return
        }
        val removedFilme = repo.filmes.removeAt(index)
        println("Filme '${removedFilme.titulo}' excluído com sucesso!")
    }
}

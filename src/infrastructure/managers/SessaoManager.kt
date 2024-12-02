package infrastructure.managers

import CentralRepository
import domain.entity.*
import domain.enum.TipoEntrada
import domain.enum.TipoReproducao
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner

class SessaoManager(private val repo: CentralRepository) {
    private val scanner = Scanner(System.`in`)
    private val sessoes = mutableListOf<Sessao>()

    fun createSessao() {
        println("\n--- Criar Sessão ---")
        if (repo.salas.isEmpty()) {
            println("Nenhuma sala disponível. Crie uma sala primeiro.")
            return
        }
        if (repo.filmes.isEmpty()) {
            println("Nenhum filme disponível. Cadastre um filme primeiro.")
            return
        }

        println("Selecione uma sala:")
        repo.salas.forEachIndexed { index, sala ->
            println("${index + 1}. ${sala.nome} - Capacidade: ${sala.getCapacidade()}")
        }
        print("Sala escolhida: ")
        val salaIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (salaIndex !in repo.salas.indices) {
            println("Sala inválida.")
            return
        }
        val sala = repo.salas[salaIndex]

        println("Selecione um filme:")
        repo.filmes.forEachIndexed { index, filme ->
            println("${index + 1}. ${filme.titulo} - Duração: ${filme.duracaoMinutos} minutos")
        }
        print("Filme escolhido: ")
        val filmeIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (filmeIndex !in repo.filmes.indices) {
            println("Filme inválido.")
            return
        }
        val filme = repo.filmes[filmeIndex]

        println("Selecione o tipo de reprodução:")
        TipoReproducao.values().forEach { tipo -> println("${tipo.cod}. ${tipo.name}") }
        print("Tipo de reprodução: ")
        val tipoReproducaoCod = scanner.nextInt()
        scanner.nextLine() // Consumir quebra de linha
        val tipoReproducao = TipoReproducao.values().find { it.cod == tipoReproducaoCod }
        if (tipoReproducao == null) {
            println("Tipo de reprodução inválido.")
            return
        }

        print("Horário (formato: dd/MM/yyyy HH:mm): ")
        val horarioStr = scanner.nextLine()
        val horario = try {
            LocalDateTime.parse(horarioStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        } catch (e: Exception) {
            println("Data inválida.")
            return
        }

        print("Preço da entrada inteira: R$ ")
        val valorEntrada = scanner.nextDouble()
        scanner.nextLine() // Consumir quebra de linha

        val gerenciadorAssentos = GerenciadorAssentos(sala)
        val sessao = Sessao(
            filme = filme,
            reproducao3D = tipoReproducao == TipoReproducao.DUBLADO_COM_LEGENDA,
            gerenciadorAssentos = gerenciadorAssentos,
            tipoAudio = tipoReproducao,
            horario = horario,
            valorEntradaInteira = valorEntrada
        )
        sessoes.add(sessao)
        println("Sessão criada com sucesso!")
    }

    fun listSessoes() {
        println("\n--- Lista de Sessões ---")
        if (sessoes.isEmpty()) {
            println("Nenhuma sessão cadastrada.")
        } else {
            sessoes.forEachIndexed { index, sessao ->
                println(
                    """
                    Sessão ${index + 1}
                    Filme: ${sessao.filme.titulo}
                    Sala: ${sessao.gerenciadorAssentos.sala.nome}
                    Horário: ${sessao.horario}
                    Tipo de Reprodução: ${sessao.tipoAudio.name}
                    Valor Entrada Inteira: R$ ${sessao.valorEntradaInteira}
                    """.trimIndent()
                )
            }
        }
    }

    fun comprarIngresso() {
        println("\n--- Comprar Ingresso ---")
        if (sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (sessaoIndex !in sessoes.indices) {
            println("Sessão inválida.")
            return
        }
        val sessao = sessoes[sessaoIndex]

        println("Mapa de assentos:")
        sessao.gerenciadorAssentos.exibeAssentos()

        print("Selecione o assento (ex: A1, B2): ")
        val idAssento = scanner.nextLine()

        println("Selecione o tipo de entrada:")
        TipoEntrada.values().forEach { tipo -> println("${tipo.ordinal + 1}. ${tipo.name}") }
        print("Tipo de entrada: ")
        val tipoEntradaOrdinal = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        val tipoEntrada = TipoEntrada.values().getOrNull(tipoEntradaOrdinal)
        if (tipoEntrada == null) {
            println("Tipo de entrada inválido.")
            return
        }

        try {
            val ingresso = sessao.comprarIngresso(idAssento, tipoEntrada)
            println("Ingresso comprado com sucesso!")
            println("Detalhes do ingresso:")
            println("Sessão: ${ingresso.sessao.filme.titulo}")
            println("Assento: ${ingresso.idAssento}")
            println("Tipo de Entrada: ${ingresso.tipoEntrada}")
        } catch (e: Exception) {
            println("Erro ao comprar ingresso: ${e.message}")
        }
    }

    fun listarAssentos() {
        println("\n--- Listar Assentos ---")
        if (sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (sessaoIndex !in sessoes.indices) {
            println("Sessão inválida.")
            return
        }
        val sessao = sessoes[sessaoIndex]

        println("Mapa de assentos:")
        sessao.gerenciadorAssentos.exibeAssentos()

    }
}

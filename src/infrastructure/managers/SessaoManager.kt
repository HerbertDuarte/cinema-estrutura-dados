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

        println("Selecione se é uma reprodução 3D:")
        TipoReproducao.values().forEach { tipo -> println("${tipo.cod}. ${tipo.name}") }
        println("Opções:")
        println("0. Reprodução normal")
        println("1. Reprodução 3D")

        val respostaIs3D = scanner.nextInt()
        scanner.nextLine()
        val reproducao3D = respostaIs3D == 1

        if (respostaIs3D > 1 || respostaIs3D < 0) {
            println("Resposta inválida.")
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
            reproducao3D = reproducao3D,
            gerenciadorAssentos = gerenciadorAssentos,
            tipoAudio = tipoReproducao,
            horario = horario,
            valorEntradaInteira = valorEntrada,
            cod = null
        )

        if (!validaDataSessao(sessao)) {
            println("Conflito de horário detectado! A sessão não pode ser criada.")
            return
        }
        repo.sessoes.add(sessao)
        println("Sessão criada com sucesso!")
    }

    fun listSessoes() {
        println("\n--- Lista de Sessões ---")
        if (repo.sessoes.isEmpty()) {
            println("Nenhuma sessão cadastrada.")
        } else {
            repo.sessoes.forEachIndexed { index, sessao ->
                println()
                println(
                    """
                    Sessão ${index + 1}
                    Filme: ${sessao.filme.titulo}
                    Sala: ${sessao.gerenciadorAssentos.sala.nome}
                    Horário: ${sessao.horario}
                    3D: ${if (sessao.reproducao3D) "Sim" else "Não"}
                    Tipo de Reprodução: ${sessao.tipoAudio.name}
                    Valor Entrada Inteira: R$ ${sessao.valorEntradaInteira}
                    """.trimIndent()
                )
                println()
            }
        }
    }

    fun comprarIngresso() {
        println("\n--- Comprar Ingresso ---")
        if (repo.sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (sessaoIndex !in repo.sessoes.indices) {
            println("Sessão inválida.")
            return
        }
        val sessao = repo.sessoes[sessaoIndex]

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

            repo.ingressos.add(ingresso)
        } catch (e: Exception) {
            println("Erro ao comprar ingresso: ${e.message}")
        }
    }

    fun listarAssentos() {
        println("\n--- Listar Assentos ---")
        if (repo.sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (sessaoIndex !in repo.sessoes.indices) {
            println("Sessão inválida.")
            return
        }
        val sessao = repo.sessoes[sessaoIndex]

        println("Mapa de assentos:")
        sessao.gerenciadorAssentos.exibeAssentos()

    }

    fun deleteSessao() {
        println("\n--- Deletar Sessão ---")
        if (repo.sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão para deletar: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (sessaoIndex !in repo.sessoes.indices) {
            println("Sessão inválida.")
            return
        }

        val sessaoRemovida = repo.sessoes.removeAt(sessaoIndex)
        println("Sessão '${sessaoRemovida.filme.titulo}' na sala '${sessaoRemovida.gerenciadorAssentos.sala.nome}' removida com sucesso!")
    }



    fun calcularTaxaOcupacao() {
        println("\n--- Calcular Taxa de Ocupação ---")
        if (repo.sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (sessaoIndex !in repo.sessoes.indices) {
            println("Sessão inválida.")
            return
        }

        val sessao = repo.sessoes[sessaoIndex]
        val totalAssentos = sessao.gerenciadorAssentos.sala.getCapacidade()
        val assentosOcupados = totalAssentos - sessao.gerenciadorAssentos.getAssentosDisponiveis().size
        val taxaOcupacao = (assentosOcupados.toDouble() / totalAssentos) * 100

        println("Taxa de Ocupação: %.2f%%".format(taxaOcupacao))
    }

    fun calcularFaturamento() {
        println("\n--- Calcular Faturamento ---")
        if (repo.sessoes.isEmpty()) {
            println("Nenhuma sessão disponível.")
            return
        }

        listSessoes()
        print("Selecione a sessão: ")
        val sessaoIndex = scanner.nextInt() - 1
        scanner.nextLine()
        if (sessaoIndex !in repo.sessoes.indices) {
            println("Sessão inválida.")
            return
        }

        val sessao = repo.sessoes[sessaoIndex]
        val ingressos = repo.ingressos.filter {
            it.sessao.cod == sessao.cod
        }
        var faturamentoTotal = 0.0
        ingressos.forEach {
            faturamentoTotal += it.precoFinal;
        }
        println("Faturamento total: R$ %.2f".format(faturamentoTotal))
    }

    private fun validaDataSessao(novaSessao: Sessao): Boolean {

        val inicioNovaSessao = novaSessao.horario
        val fimNovaSessao = novaSessao.horario.plusMinutes(novaSessao.filme.duracaoMinutos.toLong())

        repo.sessoes.filter { it.gerenciadorAssentos.sala.cod == novaSessao.gerenciadorAssentos.sala.cod }
            .forEach { sessaoExistente ->
                    val inicioExistente = sessaoExistente.horario
                    val fimExistente = sessaoExistente.horario.plusMinutes(sessaoExistente.filme.duracaoMinutos.toLong())
                    if (fimNovaSessao.isAfter(inicioExistente.minusMinutes(20)) &&
                        inicioNovaSessao.isBefore(fimExistente.plusMinutes(20))) {
                        return false
                    }
            }
        return true
    }

}

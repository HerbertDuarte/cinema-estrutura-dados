package infrastructure.managers

import CentralRepository
import domain.entity.Sala
import java.util.Scanner

class SalaManager (private val repo: CentralRepository) {
    private val scanner = Scanner(System.`in`)

    fun createSala() {
        println("\n--- Criar Sala ---")
        print("Nome da Sala: ")
        val nome = scanner.nextLine()
        print("Quantidade de Filas: ")
        val qtdFilas = scanner.nextInt()
        print("Quantidade de Assentos por Fila: ")
        val qtdAssentoPorFila = scanner.nextInt()
        scanner.nextLine() // Consumir quebra de linha

        val sala = Sala(
            qtdFilas = qtdFilas,
            qtdAssentoPorFila = qtdAssentoPorFila,
            nome = nome
        )
        repo.salas.add(sala)
        println("Sala '${sala.nome}' adicionada com sucesso!")
    }

    fun listSalas() {
        println("\n--- Lista de repo.salas ---")
        if (repo.salas.isEmpty()) {
            println("Nenhuma sala cadastrada.")
        } else {
            repo.salas.forEachIndexed { index, sala ->
                println("${index + 1}. ${sala.nome} - Código: ${sala.cod} - Capacidade: ${sala.getCapacidade()} lugares")
            }
        }
    }

    fun updateSala() {
        println("\n--- Atualizar Sala ---")
        if (repo.salas.isEmpty()) {
            println("Nenhuma sala cadastrada.")
            return
        }
        listSalas()
        print("Selecione o número da sala para atualizar: ")
        val index = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (index !in repo.salas.indices) {
            println("Sala não encontrada.")
            return
        }

        val sala = repo.salas[index]
        println("Atualizando a sala: ${sala.nome}")
        print("Novo Nome (atual: ${sala.nome}): ")
        val nome = scanner.nextLine()
        print("Nova Quantidade de Filas (atual: ${sala.qtdFilas}): ")
        val qtdFilas = scanner.nextInt()
        print("Nova Quantidade de Assentos por Fila (atual: ${sala.qtdAssentoPorFila}): ")
        val qtdAssentoPorFila = scanner.nextInt()
        scanner.nextLine() // Consumir quebra de linha

        repo.salas[index] = Sala(
            qtdFilas = qtdFilas,
            qtdAssentoPorFila = qtdAssentoPorFila,
            nome = nome
        )
        println("Sala atualizada com sucesso!")
    }

    fun deleteSala() {
        println("\n--- Excluir Sala ---")
        if (repo.salas.isEmpty()) {
            println("Nenhuma sala cadastrada.")
            return
        }
        listSalas()
        print("Selecione o número da sala para excluir: ")
        val index = scanner.nextInt() - 1
        scanner.nextLine() // Consumir quebra de linha
        if (index !in repo.salas.indices) {
            println("Sala não encontrada.")
            return
        }
        val removedSala = repo.salas.removeAt(index)
        println("Sala '${removedSala.nome}' excluída com sucesso!")
    }
}
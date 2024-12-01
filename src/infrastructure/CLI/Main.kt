package infrastructure.CLI

import java.util.Scanner
import kotlin.system.exitProcess

// Classe principal
fun main() {
    val cliApp = CLIApp()
    cliApp.start()
}

// Classe da aplicação CLI
class CLIApp {
    private val scanner = Scanner(System.`in`)
    private val menuManager = MenuManager()

    fun start() {
        println("Bem-vindo ao CLI App!")
        while (true) {
            try {
                menuManager.showMainMenu()
                print("Selecione uma opção: ")
                val input = scanner.nextLine().trim()
                menuManager.handleOption(input)
            } catch (e: Exception) {
                println("Erro: ${e.message}")
            }
        }
    }
}

// Gerenciador de menus
class MenuManager {
    private val scanner = Scanner(System.`in`)

    fun showMainMenu() {
        println("\n--- Menu Principal ---")
        println("1. Exibir informações")
        println("2. Realizar requisição")
        println("3. Sair")
    }

    fun handleOption(option: String) {
        when (option) {
            "1" -> showInfoMenu()
            "2" -> performRequest()
            "3" -> exitCLI()
            else -> println("Opção inválida. Tente novamente.")
        }
    }

    private fun showInfoMenu() {
        println("\n--- Informações ---")
        println("Esta é uma aplicação CLI interativa escrita em Kotlin!")
        println("Pressione Enter para voltar ao menu principal.")
        scanner.nextLine()
    }

    private fun performRequest() {
        println("\n--- Informações ---")
        println("Esta é uma aplicação CLI interativa escrita em Kotlin!")
        println("Pressione Enter para voltar ao menu principal.")
        scanner.nextLine()
    }

    private fun exitCLI() {
        println("Saindo da aplicação. Até mais!")
        exitProcess(0)
    }
}

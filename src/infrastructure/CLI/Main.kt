import infrastructure.managers.FilmeManager
import infrastructure.managers.SalaManager
import infrastructure.managers.SessaoManager
import java.util.Scanner

fun main() {
    val cliApp = CLIApp()
    cliApp.start()
}

// Classe da aplicação CLI
class CLIApp {
    private val scanner = Scanner(System.`in`)
    private val repository = CentralRepository()
    private val menuManager = MenuManager(repository)

    fun start() {
        println("Bem-vindo ao Gerenciador de Filmes!")
        while (true) {
            try {
                menuManager.showMainMenu()
                print("Selecione uma opção: ")
                val input = scanner.nextLine().trim()
                menuManager.handleOption(input)
            } catch (e: Exception) {
                println("Erro: ${e.message}")
            } finally {
                repository.saveData()
            }
        }
    }
}

// Gerenciador de menus
class MenuManager(repository: CentralRepository) {
    private val scanner = Scanner(System.`in`)
    val salaManager = SalaManager(repository)
    val filmeManager = FilmeManager(repository)
    val sessaoManager = SessaoManager(repository)


    fun showMainMenu() {
        println("\n--- Menu Principal ---")
        println("1. Gerenciar Filmes")
        println("2. Gerenciar Salas")
        println("3. Gerenciar Sessões")
        println("4. Sair")
    }

    fun handleOption(option: String) {
        when (option) {
            "1" -> showFilmeMenu()
            "2" -> showSalaMenu()
            "3" -> showSessaoMenu()
            "4" -> exitCLI()
            else -> println("Opção inválida. Tente novamente.")
        }
    }

    private fun showFilmeMenu() {
        while (true) {
            println("\n--- Gerenciar Filmes ---")
            println("1. Criar Filme")
            println("2. Listar Filmes")
            println("3. Atualizar Filme")
            println("4. Excluir Filme")
            println("5. Voltar ao Menu Principal")
            print("Selecione uma opção: ")
            val option = scanner.nextLine().trim()
            when (option) {
                "1" -> filmeManager.createFilme()
                "2" -> filmeManager.listFilmes()
                "3" -> filmeManager.updateFilme()
                "4" -> filmeManager.deleteFilme()
                "5" -> return
                else -> println("Opção inválida. Tente novamente.")
            }
        }
    }

    private fun showSalaMenu() {
        while (true) {
            println("\n--- Gerenciar Salas ---")
            println("1. Criar Sala")
            println("2. Listar Salas")
            println("3. Atualizar Sala")
            println("4. Excluir Sala")
            println("5. Voltar ao Menu Principal")
            print("Selecione uma opção: ")
            val option = scanner.nextLine().trim()
            when (option) {
                "1" -> salaManager.createSala()
                "2" -> salaManager.listSalas()
                "3" -> salaManager.updateSala()
                "4" -> salaManager.deleteSala()
                "5" -> return
                else -> println("Opção inválida. Tente novamente.")
            }
        }
    }

    private fun showSessaoMenu() {
        while (true) {
            println("\n--- Gerenciar Sessões ---")
            println("1. Criar Sessão")
            println("2. Listar Sessões")
            println("3. Comprar ingresso")
            println("4. Deletar Sessão")
            println("5. Listar assentos")
            println("6. Verificar faturamento")
            println("7. Verificar taxa de ocupação")
            println("8. Voltar ao Menu Principal")
            print("Selecione uma opção: ")
            val option = scanner.nextLine().trim()
            when (option) {
                "1" -> sessaoManager.createSessao()
                "2" -> sessaoManager.listSessoes()
                "3" -> sessaoManager.comprarIngresso()
                "4" -> sessaoManager.deleteSessao()
                "5" -> sessaoManager.listarAssentos()
                "6" -> sessaoManager.calcularFaturamento()
                "7" -> sessaoManager.calcularTaxaOcupacao()
                "8" -> return
                else -> println("Opção inválida. Tente novamente.")
            }
        }
    }

    private fun exitCLI() {
        println("Saindo da aplicação. Até mais!")
        kotlin.system.exitProcess(0)
    }
}
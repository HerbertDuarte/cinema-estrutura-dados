package domain.utils

object StringUtils {
     fun formatarParaVermelho(texto: String): String {
        val vermelho = "\u001B[31m"
        val reset = "\u001B[0m"
        return "$vermelho$texto$reset"
    }
}
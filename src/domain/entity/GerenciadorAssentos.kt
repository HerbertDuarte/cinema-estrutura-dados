package domain.entity

import domain.enum.TipoEntrada
import domain.utils.StringUtils

class GerenciadorAssentos{
    val sala: Sala
    val mapaAssento: Array<Array<Assento>>

    constructor(sala: Sala){
        this.sala = sala
        this.mapaAssento = createMapaAssento(sala)
    }

    private fun createMapaAssento(sala: Sala): Array<Array<Assento>> {
        val mapa = Array(sala.qtdFilas) { fila ->
            Array(sala.qtdAssentoPorFila) { assento ->
                val nomeAssento = "${(fila + 65).toChar()}${assento + 1}" // Exemplo: A1, A2, B1, etc.
                Assento(nome = nomeAssento)
            }
        }
        return mapa
    }

    fun exibeAssentos() {
        mapaAssento.forEach { filas ->
            var assentosString = ""
            filas.forEach { assento ->
                if (assento.ocupado) {
                    assentosString += "[${StringUtils.formatarParaVermelho(assento.nome)}] "
                } else {
                    assentosString += "[${assento.nome}] "
                }
            }
            println(assentosString)
        }
        var tela = ""
        tela += "==".repeat(sala.qtdAssentoPorFila * 1)
        tela+= "tela"
        tela += "==".repeat(sala.qtdAssentoPorFila * 1)

        println(tela)

    }

    fun confirmarOcupacao(nomeAssento:String):Assento? {
        var assentoEncontrado: Assento? = null
        mapaAssento.forEach { filas ->
            filas.forEach { assento ->
                if (assento.nome == nomeAssento) {
                    if (assento.ocupado) {
                        throw Exception("Assento ocupado.")
                    }
                    assento.ocupar()
                    assentoEncontrado = assento
                }
            }
        }
        return assentoEncontrado
    }

}
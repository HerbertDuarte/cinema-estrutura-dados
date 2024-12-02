package domain.entity

import domain.enum.TipoEntrada
import domain.enum.TipoReproducao
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Sessao(
    val filme: Filme,
    val reproducao3D:Boolean,
    val gerenciadorAssentos: GerenciadorAssentos,
    val tipoAudio: TipoReproducao,
    val horario: LocalDateTime,
    val valorEntradaInteira: Double
    ) {

    fun comprarIngresso(idAssento:String, tipoEntrada: TipoEntrada): Ingresso {
        this.gerenciadorAssentos.confirmarOcupacao((idAssento).uppercase(Locale.getDefault()))
        return Ingresso(
            sessao = this,
            idAssento = idAssento,
            tipoEntrada = tipoEntrada
        )
    }
}
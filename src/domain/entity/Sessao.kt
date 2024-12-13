package domain.entity

import domain.enum.TipoEntrada
import domain.enum.TipoReproducao
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class Sessao {
    val cod: String
    val filme: Filme
    val reproducao3D:Boolean
    val gerenciadorAssentos: GerenciadorAssentos
    val tipoAudio: TipoReproducao
    val horario: LocalDateTime
    val valorEntradaInteira: Double

    @OptIn(ExperimentalUuidApi::class)
    constructor(filme: Filme,
                reproducao3D:Boolean,
                 gerenciadorAssentos: GerenciadorAssentos,
                 tipoAudio: TipoReproducao,
                 horario: LocalDateTime,
                valorEntradaInteira: Double,
                cod: String?){
        if(reproducao3D)
            this.valorEntradaInteira = valorEntradaInteira * 1.25
        else
            this.valorEntradaInteira  = valorEntradaInteira
        this.filme = filme
        this.reproducao3D = reproducao3D
        this.gerenciadorAssentos = gerenciadorAssentos
        this.tipoAudio = tipoAudio
        this.horario = horario
        this.cod = cod ?: Uuid.random().toString()
    }

    fun comprarIngresso(idAssento:String, tipoEntrada: TipoEntrada): Ingresso {
        this.gerenciadorAssentos.confirmarOcupacao((idAssento).uppercase(Locale.getDefault()))

        return Ingresso(
            sessao = this,
            idAssento = idAssento,
            tipoEntrada = tipoEntrada,
            codIngresso = null
        )
    }
}
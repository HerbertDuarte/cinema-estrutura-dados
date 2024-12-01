package domain.entity

import domain.enum.TipoReproducao
import java.time.LocalDate

class Sessao(
    val filme: Filme,
    val reproducao3D:Boolean,
    val gerenciadorAssentos: GerenciadorAssentos,
    val tipoAudio: TipoReproducao,
    val horario: LocalDate,) {

}
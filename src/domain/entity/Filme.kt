package domain.entity

import domain.enum.TipoProducao

class Filme(
    val duracaoMinutos: Int,
    val tipoProducao: TipoProducao,
    val descricao:String,
    val recomendacao: Int,
    val avaliacao: Double,
    val titulo: String) {

}
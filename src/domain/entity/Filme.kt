package domain.entity

import domain.enum.TipoProducao

class Filme {
    val duracaoMinutos: Int
    val tipoProducao: TipoProducao
    val descricao:String
    val recomendacao: Int
    val avaliacao: Double
    val reproducao3D: Double

    constructor(
        duracaoMinutos: Int,
        tipoProducao: TipoProducao,
        descricao:String,
        recomendacao: Int,
        avaliacao: Double,
        reproducao3D: Double,
    ){
        this.duracaoMinutos = duracaoMinutos
        this.tipoProducao = tipoProducao
        this.descricao = descricao
        this.recomendacao = recomendacao
        this.avaliacao = avaliacao
        this.reproducao3D = reproducao3D
    }

}
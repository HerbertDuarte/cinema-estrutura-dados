package domain.entity

import domain.enum.TipoEntrada
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class Ingresso {
    val tipoEntrada:TipoEntrada
    val idAssento: String
    val sessao:Sessao
    val precoFinal: Double
    val codIngresso: String


    @OptIn(ExperimentalUuidApi::class)
    constructor(tipoEntrada: TipoEntrada, idAssento: String, sessao: Sessao) {
        this.tipoEntrada = tipoEntrada
        this.idAssento = idAssento
        this.sessao = sessao
        this.codIngresso = Uuid.random().toString()
        this.precoFinal = calculaPreco(tipoEntrada, sessao)
    }

    private fun calculaPreco(tipoEntrada: TipoEntrada,sessao:Sessao):Double{
        var preco:Double
        if(tipoEntrada == TipoEntrada.INTEIRA)
            preco = sessao.valorEntradaInteira
        else
            preco = sessao.valorEntradaInteira /2
        if(sessao.reproducao3D)
            return preco * 1.25
        return preco
    }

    override fun toString(): String {
        return "Ingresso(tipoEntrada=$tipoEntrada, idAssento='$idAssento', sessao=$sessao, precoFinal=$precoFinal, codIngresso='$codIngresso')"
    }


}
package domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class Sala {
    val qtdFilas: Int
    val qtdAssentoPorFila: Int
    val nome: String
    val cod: String

    @OptIn(ExperimentalUuidApi::class)
    constructor(qtdFilas: Int, qtdAssentoPorFila: Int, nome: String){
        this.qtdFilas = qtdFilas
        this.nome = nome
        this.qtdAssentoPorFila = qtdAssentoPorFila
        this.cod = Uuid.random().toString()
    }

    fun getCapacidade () : Int{
        return this.qtdFilas * this.qtdAssentoPorFila;
    }
}
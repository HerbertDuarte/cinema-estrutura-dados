package infrastructure.TEST

import domain.entity.Filme
import domain.entity.GerenciadorAssentos
import domain.entity.Sala
import domain.entity.Sessao
import domain.enum.TipoEntrada
import domain.enum.TipoProducao
import domain.enum.TipoReproducao
import java.time.LocalDateTime

fun main(){

    val sala = Sala(
        nome = "Sala 1",
        qtdAssentoPorFila = 10,
        qtdFilas = 5
    )
    val filme = Filme(
        titulo = "Homem aranha 2",
        duracaoMinutos = 120,
        avaliacao = 4.5,
        descricao = "Filme da Marvel",
        recomendacao = 14,
        tipoProducao = TipoProducao.INTERNACIONAL
    )
    val sessao = Sessao(
        filme = filme,
        gerenciadorAssentos = GerenciadorAssentos(sala),
        reproducao3D = true,
        horario = LocalDateTime.of(2025,1,1,12,0),
        tipoAudio = TipoReproducao.DUBLADO,
        valorEntradaInteira = 22.0
    )

    sessao.gerenciadorAssentos.exibeAssentos()
    println(sessao.comprarIngresso("A1",TipoEntrada.INTEIRA).toString())
    println(sessao.comprarIngresso("C2",TipoEntrada.MEIA).toString())
    println(sessao.comprarIngresso("D5",TipoEntrada.INTEIRA).toString())
    sessao.gerenciadorAssentos.exibeAssentos()

}
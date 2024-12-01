package infrastructure.TEST

import domain.entity.Filme
import domain.entity.GerenciadorAssentos
import domain.entity.Sala
import domain.entity.Sessao
import domain.enum.TipoEntrada
import domain.enum.TipoReproducao
import domain.utils.StringUtils
import java.time.LocalDate

fun main(){

    val sala: Sala = Sala(
        nome = "Sala 1",
        qtdAssentoPorFila = 10,
        qtdFilas = 5
    )
    val sessao:Sessao = Sessao(
        filme = Filme(),
        gerenciadorAssentos = GerenciadorAssentos(sala),
        reproducao3D = true,
        horario = LocalDate.of(2025,1,1),
        tipoAudio = TipoReproducao.DUBLADO,
    )

    sessao.gerenciadorAssentos.exibeAssentos()
    sessao.gerenciadorAssentos.confirmarOcupacao("A1",TipoEntrada.INTEIRA)
    sessao.gerenciadorAssentos.confirmarOcupacao("C2",TipoEntrada.INTEIRA)
    sessao.gerenciadorAssentos.confirmarOcupacao("D5",TipoEntrada.INTEIRA)
    sessao.gerenciadorAssentos.exibeAssentos()

}
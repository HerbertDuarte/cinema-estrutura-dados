import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import domain.entity.Filme
import domain.entity.Ingresso
import domain.entity.Sala
import domain.entity.Sessao
import infrastructure.managers.LocalDateAdapter
import infrastructure.managers.LocalDateTimeAdapter
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

class CentralRepository {
    val salas: MutableList<Sala> = carregarSalas()
    val filmes: MutableList<Filme> = carregarFilmes()
    val sessoes: MutableList<Sessao> = carregarSessoes()
    val ingressos: MutableList<Ingresso> = carregarIngressos()

    fun saveData() {
        salvarSalas(salas)
        salvarFilmes(filmes)
        salvarSessoes(sessoes)
        salvarIngressos(ingressos)
    }

    fun carregarFilmes(): MutableList<Filme> {
        val file = File("filmes.json")
        if (!file.exists()) return mutableListOf()

        val gson = buildGson()

        val type = object : TypeToken<MutableList<Filme>>() {}.type
        return gson.fromJson(file.readText(), type) ?: mutableListOf()
    }

    fun salvarFilmes(filmes: MutableList<Filme>) {
        val gson = buildGson()
        val json = gson.toJson(filmes)
        File("filmes.json").writeText(json)
    }

    fun carregarSalas(): MutableList<Sala> {
        val file = File("salas.json")
        if (!file.exists()) return mutableListOf()

        val gson = buildGson()
        val type = object : TypeToken<MutableList<Sala>>() {}.type
        return gson.fromJson(file.readText(), type) ?: mutableListOf()
    }

    fun salvarSalas(salas: MutableList<Sala>) {
        val gson = buildGson()
        val json = gson.toJson(salas)
        File("salas.json").writeText(json)
    }

    fun carregarSessoes(): MutableList<Sessao> {
        val file = File("sessoes.json")
        if (!file.exists()) return mutableListOf()

        val gson = buildGson()

        val type = object : TypeToken<MutableList<Sessao>>() {}.type
        return gson.fromJson(file.readText(), type) ?: mutableListOf()
    }

    fun salvarSessoes(sessoes: MutableList<Sessao>) {
        val gson = buildGson()
        val json = gson.toJson(sessoes)
        File("sessoes.json").writeText(json)
    }

    fun carregarIngressos(): MutableList<Ingresso> {
        val file = File("ingressos.json")
        if (!file.exists()) return mutableListOf()

        val gson = buildGson()
        val type = object : TypeToken<MutableList<Ingresso>>() {}.type
        return gson.fromJson(file.readText(), type) ?: mutableListOf()
    }

    fun salvarIngressos(ingressos: MutableList<Ingresso>) {
        val gson = buildGson()
        val json = gson.toJson(ingressos)
        File("ingressos.json").writeText(json)
    }

    private fun buildGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
    }
}
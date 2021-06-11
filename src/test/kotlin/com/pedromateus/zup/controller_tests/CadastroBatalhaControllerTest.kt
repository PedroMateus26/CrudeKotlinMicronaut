package com.pedromateus.zup.controller_tests

import com.pedromateus.zup.controller.dto.BatalhaRequest
import com.pedromateus.zup.entity.BatalhaEntity
import com.pedromateus.zup.repository.BatalhaRepository
import com.sun.istack.logging.Logger
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

@MicronautTest(transactional = false)
class CadastroBatalhaControllerTest {

    private val LOGGER= Logger.getLogger(this::class.java)

    @field:Inject
    lateinit var repository: BatalhaRepository

    @field:Client("/")
    @field:Inject
    lateinit var client: HttpClient

    @BeforeEach
    fun cleanUp(){
        repository.deleteAll()
    }

    @Test
    fun `deve cadastrar o soldado para batalha`() {
        //cenário
        val soldadoRequest = BatalhaRequest(
            classe = "Shaman",
            raca = "Orc",
            profissao = "Hebalista"
        )
        //Executar ação
        val request = HttpRequest.POST("/soldados", soldadoRequest)
        val response = client.toBlocking().exchange(request, BatalhaRequest::class.java)

        //comparação
        with(response) {
            assertEquals(HttpStatus.CREATED, status)
        }

    }

    @Test
    fun `não deve cadastrar quando algum campo for estiver em branco`() {
        //cenário
        val soldadoRequest = BatalhaRequest(
            classe = "",
            raca = "",
            profissao = ""
        )
        //Executar ação
        val request = HttpRequest.POST("/soldados", soldadoRequest)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, BatalhaRequest::class.java)
        }


        //comparação
        with(thrown) {
            assertEquals(HttpStatus.BAD_REQUEST, status)
        }

    }

    @Test
    fun `deve fazer a requisição de todos os cadastrados`() {

        //cenário
        val soldadosCadastrados = repository.saveAll(
            listOf(
                BatalhaEntity(
                    classe = "Shaman",
                    raca = "Orc",
                    profissao = "Hebalista"
                ),
                BatalhaEntity(
                    classe = "Warrior",
                    raca = "Orc",
                    profissao = "Minner"
                )
            )
        )

        //Executar ação
        val request = HttpRequest.GET<BatalhaRequest>("/soldados")
        val response = client.toBlocking().exchange(request, Argument.listOf(BatalhaRequest::class.java))

        //comparação
        with(response) {
            assertEquals(HttpStatus.OK, status)
            body().forEachIndexed{index, elem->
                assertEquals(elem.classe, soldadosCadastrados[index].classe)
                assertEquals(elem.raca, soldadosCadastrados[index].raca)
                assertEquals(elem.profissao, soldadosCadastrados[index].profissao)
            }
        }

    }

    @Test
    fun `deve fazer a requisição de um soldado cadastrado e retornar o mesmo`() {

        //cenário
        val soldadoCadastrado = repository.save(
            BatalhaEntity(
                classe = "Shaman",
                raca = "Orc",
                profissao = "Hebalista"
            )
        )

        LOGGER.info("Buscando o id: ${soldadoCadastrado.id}")

        //Executar ação
        val request = HttpRequest.GET<BatalhaRequest>("/soldados/${soldadoCadastrado.id}")
        val response = client.toBlocking().exchange(request, BatalhaRequest::class.java)

        //comparação
        with(response) {
            assertEquals(HttpStatus.OK, status)
            assertEquals(soldadoCadastrado.classe, body().classe)
            assertEquals(soldadoCadastrado.raca, body().raca)
            assertEquals(soldadoCadastrado.profissao, body().profissao)

        }


    }

    @Test
    fun `não deve retornar quando o id não constar no banco`() {

        //cenário
        val id=1
        //Executar ação
        val request = HttpRequest.GET<BatalhaRequest>("/soldados/${id}")
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, BatalhaRequest::class.java)
        }

        //comparação
        with(thrown) {
            assertEquals(HttpStatus.NOT_FOUND, status)
        }

    }


    @Test
    fun `deve fazer o update do objeto no banco`() {

        //cenário
        val soldadoCadastrado = repository.save(
            BatalhaEntity(
                classe = "Shaman",
                raca = "Orc",
                profissao = "Hebalista"
            )
        )

        val soldadoUpdate= BatalhaRequest(

                classe = "Warrior",
                raca = "Orc",
                profissao = "Miner"
            )

        //Executar ação
        val request = HttpRequest.PUT("/soldados/${soldadoCadastrado.id}",soldadoUpdate)
        val response=client.toBlocking().exchange(request, BatalhaRequest::class.java)


        //comparação
        with(response) {
            assertEquals(HttpStatus.OK, status)
            assertEquals(repository.findById(soldadoCadastrado.id).get().classe,soldadoUpdate.classe)
            assertEquals(repository.findById(soldadoCadastrado.id).get().raca,soldadoUpdate.raca)
            assertEquals(repository.findById(soldadoCadastrado.id).get().profissao,soldadoUpdate.profissao)
        }

    }

    @Test
    fun `não deve atualizar quando o id não constar no banco`() {

        //cenário
        val id=1
        val soldadoUpdate= BatalhaRequest(

            classe = "Warrior",
            raca = "Orc",
            profissao = "Miner"
        )
        //Executar ação
        val request = HttpRequest.PUT("/soldados/${id}",soldadoUpdate)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, BatalhaRequest::class.java)
        }

        //comparação
        with(thrown) {
            assertEquals(HttpStatus.NOT_FOUND, status)
        }

    }

    @Test
    fun `deve deltar o soldado do banco quando o id constar no banco`(){
        //cenário
        val soldadoCadastrado = repository.save(
            BatalhaEntity(
                classe = "Shaman",
                raca = "Orc",
                profissao = "Hebalista"
            )
        )

        //Executar ação
        val request = HttpRequest.DELETE<BatalhaRequest>("/soldados/${soldadoCadastrado.id}")
        val response=client.toBlocking().exchange(request, BatalhaRequest::class.java)


        //comparação

            assertEquals(HttpStatus.NO_CONTENT, response.status)
    }


    @Test
    fun `não deve deletar quando o id não constar no banco`() {

        //cenário
        val id=1
        //Executar ação
        val request = HttpRequest.DELETE<BatalhaRequest>("/soldados/${id}")
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, BatalhaRequest::class.java)
        }

        //comparação
            assertEquals(HttpStatus.NOT_FOUND, thrown.status)

    }
}
package com.softer.post;

import com.softer.post.model.Post;
import com.softer.post.repository.TestH2Repository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Se va a ejecutar en un puerto random
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationTest {

    //Configurando entorno de pruebas

    //Para obtener el puerto en el que se esta ejecutando
    @LocalServerPort
    private  int port;

    // Voy a concatenar la url por partes
    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository testH2Repository;

    @BeforeAll //Este se ejecutara antes pero de manera apartada sin secuencia con las otras pruebas
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach //Quiero que se ejecute antes de cada una de las pruebas que hare
    public void setUp(){
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/posts");
    // como port es int, le pongo + "" para hacerlo string
    }

    //----------------------- Integration Tets ----------------
    @Test
    @Order(1)
    public void testAddPost(){
        // Creo en memoria una publicacion
        Post post = new Post(1, 1, "Tittle 1", "Body 1", null);
        // Envio una request tipo post que devuelve el endpoint de la api
        // restTemplate = envia un ojecto mediante json, es como si alguien interactyara con postman
        Post response = restTemplate.postForObject(baseUrl, post, Post.class);

        //Ahora comparar y que tengan el mismo titulo
        assertEquals("Tittle 1", response.getTitle());
        assertEquals(1, testH2Repository.findAll().size());

    }

    @Test
    @Order(2)
    public void testGetPosts(){
        List<Post> response = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, response.size());
        assertEquals(1, testH2Repository.findAll().size());

    }

    //Para que el segundo test funcione primero debe pasar por el primero
    // Ejecutar toda la clase en ves de los test de manera separada
    // POR TEORIA, LAS PRUEBAS NUNCA DEPENDEN DE OTRAS PRUEBAS, DEBEN DE SER
    // INDEPENDIENTES (queda para el sabado )
}

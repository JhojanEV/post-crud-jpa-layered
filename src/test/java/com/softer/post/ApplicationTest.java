package com.softer.post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

//Se va a ejecutar en un puerto random
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    //Configurando entorno de pruebas

    //Para obtener el puerto en el que se esta ejecutando
    @LocalServerPort
    private  int port;

    // Voy a concatenar la url por partes
    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    @BeforeAll //Este se ejecutara antes pero de manera apartada sin secuencia con las otras pruebas
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach //Quiero que se ejecute antes de cada una de las pruebas que hare
    public void setUp(){
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/posts");
    // como port es int, le pongo + "" para hacerlo string
    }
}

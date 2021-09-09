package one.digitalinnovation.personapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Json para adicionar pessoas no PostMan
{
    "firstName": "Rodrigo",
    "lastName": "Pereias",
    "cpf": "889.689.476-00",
    "phones": [
        {
            "type": "MOBILE",
            "number": "(11)999999999"
        }
    ]
}
 */
@SpringBootApplication
public class PersonapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonapiApplication.class, args);
	}

}

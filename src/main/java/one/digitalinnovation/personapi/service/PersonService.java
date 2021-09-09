package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Classe para separar as regras de negocio do Controller
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    //instancia um mapper
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    //metodo para criar pessoa
    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        //transfere os dados do personDTO para uma entidade person usando um mapper do MapStruct
        Person personToSave = personMapper.toModel(personDTO);

        //salva os dados no bd
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    //metodo para listar pessoas
    public List<PersonDTO> listAll() {
        //pega do bd a lista de pessoas
        List<Person> allPeople = personRepository.findAll();
        //converte a lista de person para personDTO e retorna
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    //metodo que retorna pessoa pelo id
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    //metodo para deletar pessoa pelo id
    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }

    //metodo que verifica se pessoa existe dado o id
    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}

package org.top.hairsalonapp.rdb.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.top.hairsalonapp.entity.Client;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository testClientRepository;

    @Test
    void findClientByPhoneNumber() {
        //given

        String phoneNumber = "1112223333";
        Optional<Client> testClient = Optional.of(new Client());
        testClient.get().setClientEmail("client@email.ru");
        testClient.get().setClientName("Client");
        testClient.get().setClientLastName("ClientLastName");
        testClient.get().setPhoneNumber(phoneNumber);
        testClientRepository.save(testClient.get());
        //when
        Optional<Client> expectedClient =  testClientRepository.findByPhoneNumber(phoneNumber);
        boolean matching = false;
        if(testClient.get().getPhoneNumber().equals(expectedClient.get().getPhoneNumber())) matching = true;
        //then
        Assertions.assertThat(expectedClient).isEqualTo(testClient);
        Assertions.assertThat(expectedClient).isNotEmpty();

    }

    @Test
    public void ClientRepository_Save_ReturnSavedClient(){
        //Arrange
        Client client = Client.builder()
                .phoneNumber("3334445555")
                .clientName("User")
                .clientLastName("Userov")
                .clientEmail("user@ya.ru")
                .marketing(true)
                .build();

        //Act
        Client savedClient = testClientRepository.save(client);

        //Assert
        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getId()).isGreaterThan(0);
    }

    @Test
    public void ClientRepository_GetAll_ReturnMoreThenOneClient() {
        //Arrange
        Client client1 = Client.builder()
                .phoneNumber("1112223333")
                .clientName("Client_1")
                .clientLastName("LastName Client_1")
                .clientEmail("client_1@ya.ru")
                .build();

        Client client2 = Client.builder()
                .phoneNumber("2223334444")
                .clientName("Client_2")
                .clientLastName("LastName Client_2")
                .clientEmail("client_2@ya.ru")
                .build();

        testClientRepository.save(client1);
        testClientRepository.save(client2);

        //Act
        Iterable<Client> clientList = testClientRepository.findAll();

        //Assert
        Assertions.assertThat(clientList).isNotEmpty();
        Assertions.assertThat(clientList.iterator().hasNext()).isTrue();
    }

    @Test
    public void ClientRepository_FindById_ReturnOneClient() {
        //Arrange
        Client client1 = Client.builder()
                .phoneNumber("1112223333")
                .clientName("Client_1")
                .clientLastName("LastName Client_1")
                .clientEmail("client_1@ya.ru")
                .build();

        testClientRepository.save(client1);


        //Act
        Client returnClient = testClientRepository.findById(client1.getId()).get();

        //Assert
        Assertions.assertThat(returnClient).isNotNull();
        Assertions.assertThat(returnClient.getId()).isEqualTo(1);
    }

}






















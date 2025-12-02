package com.editaliza.models;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ProposerTest {
    private static final Logger logger = LoggerFactory.getLogger(ProposerTest.class);

    @Test
    @DisplayName("Testa um CNPJ VÁLIDO")
    void testValidCnpj() {
        logger.debug("Iniciando teste de \"VALIDAR CNPJ VÁLIDO\"...");
        try {

            Proposer p = new Proposer();
            p.setCnpj("12.345.678/0001-95");

            Assertions.assertTrue(p.isValidCnpj());
            logger.info("CNPJ válido reconhecido corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar CNPJ válido!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um CNPJ INVÁLIDO, não pode ser número repitido")
    void testInvalidCnpj() {
        logger.debug("Iniciando teste de \"VALIDAR CNPJ INVÁLIDO\"...");
        try {

            Proposer p = new Proposer();
            p.setCnpj("11.111.111/1111-11");

            Assertions.assertFalse(p.isValidCnpj());
            logger.info("CNPJ inválido detectado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar CNPJ inválido!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se a formatação do CNPJ está funcionando")
    void testFormattedCnpj() {
        logger.debug("Iniciando teste de \"FORMATAR CNPJ\"...");
        try {

            Proposer p = new Proposer();
            p.setCnpj("12345678000195");

            Assertions.assertEquals("12.345.678/0001-95", p.getFormattedCnpj());
            logger.info("Formatação de CNPJ realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao formatar CNPJ!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se lança exceção ao atribuir um NOME inválido")
    void testValidateNameThrows() {
        logger.debug("Iniciando teste de \"NOME INVÁLIDO\"...");

            Proposer p = new Proposer();
            p.setName("");

            Assertions.assertThrows(IllegalArgumentException.class, () -> p.validateName());
            logger.info("Validação de nome inválido funcionou!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se lança exceção ao atribuir um EMAIL inválido")
    void testValidateEmailThrows() {
        logger.debug("Iniciando teste de \"EMAIL INVÁLIDO\"...");

            Proposer p = new Proposer();
            p.setEmail("email-invalido");

            Assertions.assertThrows(IllegalArgumentException.class, () -> p.validateEmail());

            logger.info("Validação de e-mail inválido funcionou!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @Displayname("Lança exceção por CNPJ inválido, falta informação")
    void testValidateCnpjThrows() {
        logger.debug("Iniciando teste de \"VALIDAR CNPJ INVÁLIDO (EXCEÇÃO)\"...");

            Proposer p = new Proposer();
            p.setCnpj("123");

            Assertions.assertThrows(IllegalArgumentException.class, () -> p.validateCnpj());
            logger.info("Exceção de CNPJ inválido lançada corretamente!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se o edital foi ADICIONADO na lista do proposer")
    void testAddEdital() {
        logger.debug("Iniciando teste de \"ADICIONAR EDITAL AO PROPOSER\"...");
        try {

            Proposer p = new Proposer("123", "Nome", "email@email.com", null, "12.345.678/0001-95");
            Edital edital = new Edital();

            p.addEdital(edital);

            Assertions.assertEquals(1, p.getListEdital().size());
            Assertions.assertEquals(p, edital.getProposer());

            logger.info("Edital adicionado corretamente ao proposer!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar edital!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se o edital foi REMOVIDO da lista do proposer")
    void testRemoveEdital() {
        logger.debug("Iniciando teste de \"REMOVER EDITAL DO PROPOSER\"...");
        try {

            Proposer p = new Proposer("123", "Nome", "email@email.com", null, "12.345.678/0001-95");

            Edital edital = new Edital();
            edital.setId(10L);

            p.addEdital(edital);
            p.removeEdital(10L);

            Assertions.assertEquals(0, p.getListEdital().size());

            logger.info("Edital removido corretamente do proposer!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao remover edital!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Conta quantos editais estão em cada status")
    void testCountEditalsByStatus() {
        logger.debug("Iniciando teste de \"CONTAR EDITAIS POR STATUS\"...");
        try {

            Proposer p = new Proposer();

            Edital e1 = new Edital();
            e1.setStatus(EditalStatus.OPEN);

            Edital e2 = new Edital();
            e2.setStatus(EditalStatus.OPEN);

            Edital e3 = new Edital();
            e3.setStatus(EditalStatus.CLOSED);

            p.addEdital(e1);
            p.addEdital(e2);
            p.addEdital(e3);

            Assertions.assertEquals(2, p.countEditalsByStatus(EditalStatus.OPEN));
            Assertions.assertEquals(1, p.countEditalsByStatus(EditalStatus.CLOSED));

            logger.info("Contagem de editais por status validada corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao contar editais por status!", e);
        }
        logger.info("Finalizou o programa!");
    }
}
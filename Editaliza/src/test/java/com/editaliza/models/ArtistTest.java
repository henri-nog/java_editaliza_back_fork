package com.editaliza.models;

import com.editaliza.editaliza.models.Artist;
import com.editaliza.editaliza.models.TagData;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ArtistTest {
    private static final Logger logger = LoggerFactory.getLogger(ArtistTest.class);

    @Test
    @DisplayName("Testa um CPF válido")
    void testCpfValido() {
        //ESTA É A ANTIGA CLASSE
//    logger.debug("Iniciando teste de validação do CPF...");
//        Artist artist = new Artist("123", "João", "email@email.com", null,
//                "52998224725");
//
//        assertTrue(artist.isValidCpf());
//        logger.info("Teste finalizado.");

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"VALIDAÇÃO\" do CPF...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "João", "email@email.com", null,
                    "52998224725");

            assertTrue(artist.isValidCpf());
            logger.info("CPF validado com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Ocorreu um erro. O CPF cadastrado não segue a regra de negócio", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um CPF inválido")
    void testCpfInvalido() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist("123", "João", "email@email.com", null,
//                "11111111111");
//
//        assertFalse(artist.isValidCpf());

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"INVALIDAÇÃO\" do CPF...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "João", "email@email.com", null,
                    "11111111111");

            assertFalse(artist.isValidCpf());
            logger.info("CPF inválido! Comportamento esperado ocorrido.");
        } catch (IllegalArgumentException e) {
            logger.error("O CPF cadastrado é válido", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }


    @Test
    @DisplayName("Verifica se uma exceção é lançada")
    void testValidateCpfLancaExcecao() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist("123", "João", "email@email.com", null,
//                "12345678900");
//
//        assertThrows(IllegalArgumentException.class, () -> artist.validateCpf());

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"lANÇAMENTO DE EXCEÇÃO\" do CPF...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "João", "email@email.com", null,
                    "12345678900");

            assertThrows(IllegalArgumentException.class, () -> artist.validateCpf());
            logger.info("Exceção lançada! O teste passa, pois o comportamento era o esperado.");
        } catch (IllegalArgumentException e) {
            logger.error("Erro: exceção não lançada! CPF inválido!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um email válido")
    void testEmailValido() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist("123", "João", "teste@example.com", null, "52998224725");
//
//        assertDoesNotThrow(() -> artist.validateEmail());
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"VALIDAÇÃO\" do email...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "João", "teste@example.com", null, "52998224725");

            assertDoesNotThrow(() -> artist.validateEmail());
            logger.info("Não lança a exceção! Procedimento previsto.");
        } catch (IllegalArgumentException e) {
            logger.error("A exceção foi disparada! Email inválido!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um email inválido")
    void testEmailInvalido() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist("123", "João", "email-invalido", null, "52998224725");
//
//        assertThrows(IllegalArgumentException.class, () -> artist.validateEmail());
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"INVALIDAÇÃO\" do email...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "João", "email-invalido", null, "52998224725");

            assertThrows(IllegalArgumentException.class, () -> artist.validateEmail());
            logger.info("Lança a exceção! Sucesso.");
        } catch (IllegalArgumentException e) {
            logger.error("A exceção foi disparada! O email na verdade é válido!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um nome válido")
    void testNameValido() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist("123", "Maria", "email@email.com", null, "52998224725");
//
//        assertDoesNotThrow(() -> artist.validateName());
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"VALIDAÇÃO\" do nome...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "Maria", "email@email.com", null, "52998224725");

            assertDoesNotThrow(() -> artist.validateName());
            logger.info("Exceção não disparada! O teste passou");
        } catch (IllegalArgumentException e) {
            logger.error("A exceção foi disparada! Isso significa que o \"nome\" é inválido", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }


    @Test
    @DisplayName("Testa um nome inválido")
    void testNameInvalido() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist("123", "", "email@email.com", null, "52998224725");
//
//        assertThrows(IllegalArgumentException.class, () -> artist.validateName());
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"INVALIDAÇÃO\" do nome...");
        try {
            Thread.sleep(5000);
            Artist artist = new Artist("123", "", "email@email.com", null, "52998224725");

            assertThrows(IllegalArgumentException.class, () -> artist.validateName());
            logger.info("Disparo da exceção! O teste falhou");
        } catch (IllegalArgumentException e) {
            logger.error("Exceçao não lançada! O \"nome\" é válido sim!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se um objeto na lista e se o id é o mesmo que foi passado")
    void testAddTag() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist();
//        TagData tag = new TagData();
//        tag.setId(1L);
//        tag.setName("Pintura");
//
//        artist.addTag(tag);
//
//        assertEquals(1, artist.getListTags().size());
//        assertTrue(artist.hasTag(1L));
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        //Nota: este 'logger.debug' pode melhorar
        logger.debug("Iniciando teste de \"VERIFICAÇÃO\" de objeto na lista e o id equivalente...");
        try
    {
        Thread.sleep(5000);
        Artist artist = new Artist();
        TagData tag = new TagData();
        tag.setId(1L);
        tag.setName("Pintura");

        artist.addTag(tag);

        assertEquals(1, artist.getListTags().size());
        assertTrue(artist.hasTag(1L));
        logger.info("Verificou que exite 1 objeto na lista & que há o id atribuido é igual a \"1L\". Teste aprovado!");
    } catch(
            IllegalArgumentException e)

    {
        logger.error("O dados não correspondem! Falha", e);
    } catch(
    InterruptedException e)

    {
        logger.error("Error de thread", e);
    }
            logger.info("Finalizou o programa!");
}

    @Test
    @DisplayName("Não permite adicionar a mesma tag duas vezes")
    void testAddTagDuplicadaNaoRepete() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist();
//        TagData tag = new TagData();
//        tag.setId(1L);
//        tag.setName("Arte");
//
//        artist.addTag(tag);
//        artist.addTag(tag);
//
//        assertEquals(1, artist.getListTags().size());
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"IMPEDIMENTO\" de repetição de tags...");
        try

    {
        Thread.sleep(5000);
        Artist artist = new Artist();
        TagData tag = new TagData();
        tag.setId(1L);
        tag.setName("Arte");

        artist.addTag(tag);
        artist.addTag(tag);

        assertEquals(1, artist.getListTags().size());
        logger.info("Há somente uma tag na lista. Teste validado!");
    } catch(
        IllegalArgumentException e)

    {
        logger.error("Erro: tag duplicada!", e);
    } catch(
    InterruptedException e)

    {
        logger.error("Error de thread", e);
    }
            logger.info("Finalizou o programa!");
}

    @Test
    @DisplayName("Verifica se removeu uma tag")
    void testRemoveTag() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist();
//
//        TagData tag1 = new TagData();
//        tag1.setId(10L);
//        tag1.setName("Dança");
//
//        artist.addTag(tag1);
//
//        artist.removeTag(10L);
//
//        assertEquals(0, artist.getListTags().size());
//        assertFalse(artist.hasTag(10L));
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"REMOÇÃO\" da tag...");
        try

    {
        Thread.sleep(5000);
        Artist artist = new Artist();

        TagData tag = new TagData();
        tag.setId(10L);
        tag.setName("Dança");

        artist.addTag(tag);

        artist.removeTag(10L);

        assertEquals(0, artist.getListTags().size());
        assertFalse(artist.hasTag(10L));
        logger.info("Tag removida com sucesso!");
    } catch(
        IllegalArgumentException e)

    {
        logger.error("A tag não foi excluída!", e);
    } catch(
    InterruptedException e)

    {
        logger.error("Error de thread", e);
    }
            logger.info("Finalizou o programa!");
}

    @Test
    @DisplayName("Deve retornar a lista com nomes das tags do artista")
    void testGetTagNames() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist();
//
//        TagData tag = new TagData();
//        tag.setId(3L);
//        tag.setName("Fotografia");
//
//        artist.addTag(tag);
//
//        List<String> nomes = artist.getTagNames();
//
//        assertEquals(1, nomes.size());
//        assertEquals("Fotografia", nomes.get(0));
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"LISTAGEM\" das tags...");
        try

    {
        Thread.sleep(5000);
        Artist artist = new Artist();

        TagData tag = new TagData();
        tag.setId(3L);
        tag.setName("Fotografia");

        artist.addTag(tag);

        List<String> nomes = artist.getTagNames();

        assertEquals(1, nomes.size());
        assertEquals("Fotografia", nomes.get(0));
        logger.info("A lista foi retornada!");
    } catch(
        IllegalArgumentException e)

    {
        logger.error("Houve um erro ao tentar retorna a lista!", e);
    } catch(
    InterruptedException e)

    {
        logger.error("Error de thread", e);
    }
            logger.info("Finalizou o programa!");
}

    @Test
    @DisplayName("Retorna todas as tags com a cor especificada")
    void testGetTagsByColor() {
        //ESTA É A ANTIGA CLASSE
//        Artist artist = new Artist();
//
//        TagData tag1 = new TagData();
//        tag1.setId(1L);
//        tag1.setColor("red");
//
//        TagData tag2 = new TagData();
//        tag2.setId(2L);
//        tag2.setColor("blue");
//
//        TagData tag3 = new TagData();
//        tag3.setId(3L);
//        tag3.setColor("red");
//
//        artist.addTag(tag1);
//        artist.addTag(tag2);
//        artist.addTag(tag3);
//
//        List<TagData> tagsRed = artist.getTagsByColor("red");
//
//        assertEquals(2, tagsRed.size());
//    }

        //ESTE É A CLASSE REFATORADA COM O LOG
        logger.debug("Iniciando teste de \"LISTAGEM\" das tags...");
        try

    {
        Thread.sleep(5000);
        Artist artist = new Artist();

        TagData tag1 = new TagData();
        tag1.setId(1L);
        tag1.setColor("red");

        TagData tag2 = new TagData();
        tag2.setId(2L);
        tag2.setColor("blue");

        TagData tag3 = new TagData();
        tag3.setId(3L);
        tag3.setColor("red");

        artist.addTag(tag1);
        artist.addTag(tag2);
        artist.addTag(tag3);

        List<TagData> tagsRed = artist.getTagsByColor("red");

        assertEquals(2, tagsRed.size());
        logger.info("A lista com as cores foi retornada corretamente!");
    } catch(
        IllegalArgumentException e)

    {
        logger.error("Houve um erro ao tentar retorna a lista!", e);
    } catch(
    InterruptedException e)

    {
        logger.error("Error de thread", e);
    }
            logger.info("Finalizou o programa!");
}
}


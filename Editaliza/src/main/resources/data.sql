-- data.sql

-------------------------
-- 1. USERS (PROPOSER e ARTIST)
-------------------------

-- PROPOSER (CNPJ preenchido, CPF = NULL)
INSERT INTO users (user_type, password, name, email, img_url, created_at, updated_at, cnpj, cpf)
VALUES (
    'PROPOSER', 
    'senha123', 
    'Secretaria Municipal de Cultura', 
    'sec.cultura@gov.br', 
    'url_img_sec.jpg', 
    CURRENT_TIMESTAMP(), 
    CURRENT_TIMESTAMP(), 
    '01234567000189', 
    NULL
);

-- ARTIST 1 (CPF preenchido, CNPJ = NULL)
INSERT INTO users (user_type, password, name, email, img_url, created_at, updated_at, cnpj, cpf)
VALUES (
    'ARTIST', 
    'artista456', 
    'Ana Beatriz Silva', 
    'ana.silva@arte.com', 
    'url_img_ana.jpg', 
    CURRENT_TIMESTAMP(), 
    CURRENT_TIMESTAMP(), 
    NULL, 
    '12345678900'
);

-- ARTIST 2 (CPF preenchido, CNPJ = NULL)
INSERT INTO users (user_type, password, name, email, img_url, created_at, updated_at, cnpj, cpf)
VALUES (
    'ARTIST', 
    'artista789', 
    'Bruno Mendes - Fotógrafo', 
    'bruno@foto.com', 
    'url_img_bruno.jpg', 
    CURRENT_TIMESTAMP(), 
    CURRENT_TIMESTAMP(), 
    NULL, 
    '98765432101'
);

-------------------------
-- 2. TAGS
-------------------------

INSERT INTO tags (name, description, color, created_at, updated_at)
VALUES
    ('Música Clássica', 'Editais voltados para orquestras e música de concerto.', '#3366FF', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Artes Visuais', 'Editais para exposições, pinturas e esculturas.', '#33CC33', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Dança Contemporânea', 'Editais para companhias e projetos de dança moderna.', '#9933CC', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-------------------------
-- 3. EDITAIS
-------------------------

-- Inserções de editais... (supondo que a tabela editais e o enum EditalStatus existam)

-- Edital 1 (Proponente: Secretaria Municipal de Cultura / ID 1)
INSERT INTO editais (title, description, publish_date, end_date, status, inscription_link, complete_edital_link, img_cover_url, created_at, updated_at, proposer_id)
VALUES (
    'Fundo de Apoio à Cultura 2025',
    'Edital abrangente para projetos culturais em diversas áreas.',
    CURRENT_TIMESTAMP(),
    DATEADD('DAY', 30, CURRENT_TIMESTAMP()), -- Exemplo: termina em 30 dias
    'OPEN',
    'http://link.inscricao.com/fundo2025',
    'http://link.edital.com/fundo2025_completo',
    'url_capa_fundo.jpg',
    CURRENT_TIMESTAMP(),
    CURRENT_TIMESTAMP(),
    1 -- ID do PROPOSER: Secretaria Municipal de Cultura
);

-- Edital 2 (Proponente: Secretaria Municipal de Cultura / ID 1)
INSERT INTO editais (title, description, publish_date, end_date, status, inscription_link, complete_edital_link, img_cover_url, created_at, updated_at, proposer_id)
VALUES (
    'Bolsa de Residência Artística',
    'Residência voltada para artistas visuais em início de carreira.',
    DATEADD('DAY', -10, CURRENT_TIMESTAMP()), -- Publicado há 10 dias
    DATEADD('DAY', 15, CURRENT_TIMESTAMP()), -- Termina em 15 dias
    'OPEN',
    'http://link.inscricao.com/residencia',
    'http://link.edital.com/residencia_completo',
    'url_capa_residencia.jpg',
    CURRENT_TIMESTAMP(),
    CURRENT_TIMESTAMP(),
    1 -- ID do PROPOSER: Secretaria Municipal de Cultura
);

-------------------------
-- 4. RELACIONAMENTOS (ManyToMany)
-------------------------

-- editais_tags (Edital 1)
INSERT INTO edital_tags (edital_id, tag_id) VALUES (1, 1); -- Música Clássica
INSERT INTO edital_tags (edital_id, tag_id) VALUES (1, 2); -- Artes Visuais

-- editais_tags (Edital 2)
INSERT INTO edital_tags (edital_id, tag_id) VALUES (2, 2); -- Artes Visuais

-- artist_tags (Artist 1: Ana Beatriz Silva / ID 2)
INSERT INTO artist_tags (artist_id, tag_id) VALUES (2, 3); -- Dança Contemporânea
INSERT INTO artist_tags (artist_id, tag_id) VALUES (2, 1); -- Música Clássica

-- artist_tags (Artist 2: Bruno Mendes / ID 3)
INSERT INTO artist_tags (artist_id, tag_id) VALUES (3, 2); -- Artes Visuais

-------------------------
-- 5. COMMENTS
-------------------------

-- Comentário 1 (Autor: Ana Beatriz Silva / ID 2)
INSERT INTO comments (author_name, content, approved, status, created_at, updated_at, user_id, edital_id)
VALUES (
    'Ana Beatriz Silva',
    'Excelente iniciativa! Gostaria de saber se a bolsa cobre custos de material.',
    TRUE,
    'APPROVED',
    CURRENT_TIMESTAMP(),
    CURRENT_TIMESTAMP(),
    2, -- ID do ARTIST: Ana Beatriz Silva
    2 -- ID do EDITAL: Bolsa de Residência Artística
);

-- Comentário 2 (Autor: Bruno Mendes / ID 3)
INSERT INTO comments (author_name, content, approved, status, created_at, updated_at, user_id, edital_id)
VALUES (
    'Bruno Mendes - Fotógrafo',
    'O prazo de 30 dias é suficiente para a submissão. Ótimo edital.',
    TRUE,
    'APPROVED',
    CURRENT_TIMESTAMP(),
    CURRENT_TIMESTAMP(),
    3, -- ID do ARTIST: Bruno Mendes
    1 -- ID do EDITAL: Fundo de Apoio à Cultura 2025
);
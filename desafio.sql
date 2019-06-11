CREATE TABLE IF NOT EXISTS tb_conta (
  id                 bigserial NOT NULL,
  email_notificacao  varchar(255) NOT NULL,
  saldo_inicial      numeric(15,2) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_empresa (
  id              bigserial NOT NULL,
  nome            varchar NOT NULL,
  preco_acao      numeric(15,2) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_monitoramento (
  id               bigserial NOT NULL,
  empresa_id       bigint NOT NULL,
  conta_id         bigint NOT NULL,
  preco_compra     numeric(15,2) NOT NULL,
  preco_venda      numeric(15,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (empresa_id) REFERENCES tb_empresa(id),
  FOREIGN KEY (conta_id)   REFERENCES tb_conta(id)
);

CREATE TABLE IF NOT EXISTS tb_negociacao_acao (
  id                   bigserial NOT NULL,
  data_negociacao      timestamp NOT NULL,
  quantidade_acoes     numeric(15,2) NOT NULL,
  valor_acao           numeric(15,2) NOT NULL,
  valor_negociacao     numeric(15,2) NOT NULL,
  empresa_id           bigint NOT NULL,
  conta_id             bigint NOT NULL,
  tipo_negociacao_acao varchar NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (empresa_id) REFERENCES tb_empresa(id),
  FOREIGN KEY (conta_id)   REFERENCES tb_conta(id)
);

CREATE TABLE IF NOT EXISTS tb_acao_empresa (
  id               bigserial NOT NULL,
  empresa_id       bigint NOT NULL,
  conta_id         bigint NOT NULL,
  quantidade_acoes numeric(15,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (empresa_id) REFERENCES tb_empresa(id),
  FOREIGN KEY (conta_id)   REFERENCES tb_conta(id)
);

INSERT INTO tb_conta (id, email_notificacao, saldo_inicial) VALUES
  (1, 'funck.felipe@hotmail.com', 550);

INSERT INTO tb_empresa (id, nome, preco_acao) VALUES
  (1, 'Intel', 10.13);

INSERT INTO tb_empresa (id, nome, preco_acao) VALUES
  (2, 'SoftExpert', 10.53);

INSERT INTO tb_monitoramento (id, empresa_id, conta_id, preco_compra, preco_venda) VALUES
  (1, 1, 1, 10.08, 10.70);

INSERT INTO tb_monitoramento (id, empresa_id, conta_id, preco_compra, preco_venda) VALUES
  (2, 2, 1, 10.15, 10.60);





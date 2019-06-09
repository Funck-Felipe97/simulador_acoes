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
)

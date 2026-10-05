-- =====================================================================
-- V1__init_schema.sql
-- Initial schema for Five Networks site.
-- =====================================================================

-- ---------------------------------------------------------------------
-- users: администраторы и менеджеры (не абоненты!)
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id              BIGSERIAL       PRIMARY KEY,
    email           VARCHAR(255)    NOT NULL UNIQUE,
    password_hash   VARCHAR(255)    NOT NULL,
    full_name       VARCHAR(255)    NOT NULL,
    role            VARCHAR(32)     NOT NULL,
    active          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users (email);

-- ---------------------------------------------------------------------
-- site_settings: key-value настройки сайта
-- ---------------------------------------------------------------------
CREATE TABLE site_settings (
    id              BIGSERIAL       PRIMARY KEY,
    key             VARCHAR(128)    NOT NULL UNIQUE,
    value           TEXT,
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

-- ---------------------------------------------------------------------
-- stored_file: метаданные файлов
-- ---------------------------------------------------------------------
CREATE TABLE stored_file (
    id              BIGSERIAL       PRIMARY KEY,
    storage_key     VARCHAR(512)    NOT NULL,
    original_name   VARCHAR(512)    NOT NULL,
    content_type    VARCHAR(128),
    size_bytes      BIGINT          NOT NULL,
    entity_type     VARCHAR(64),
    entity_id       BIGINT,
    uploaded_by     BIGINT          REFERENCES users (id) ON DELETE SET NULL,
    uploaded_at     TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_stored_file_entity ON stored_file (entity_type, entity_id);

-- ---------------------------------------------------------------------
-- Seed: дефолтные настройки сайта
-- ---------------------------------------------------------------------
INSERT INTO site_settings (key, value) VALUES
    ('site.name',        'ООО «Пять сетей»'),
    ('site.short_name',  'Пять сетей'),
    ('site.description', 'Подрядчик в сфере связи: ВОЛС, РРЛ, WiFi-мосты, обслуживание объектов ПАО МТС');
    

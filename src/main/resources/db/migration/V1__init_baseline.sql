CREATE TABLE categories
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    color      VARCHAR(255) NULL,
    icon       VARCHAR(255) NULL,
    name       VARCHAR(255) NULL,
    slug       VARCHAR(255) NULL,
    type       INT NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE enriched_categories
(
    dtype             VARCHAR(31) NOT NULL,
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime NULL,
    updated_at        datetime NULL,
    budget_or_goal    DECIMAL(19, 2) NULL,
    current_amount    DECIMAL(19, 2) NULL,
    type              INT NULL,
    budget            DECIMAL(19, 2) NULL,
    category_id       BIGINT      NOT NULL,
    monthly_budget_id BIGINT      NOT NULL,
    user_id           BIGINT      NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);


CREATE TABLE monthly_budgets
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    end_date   date NULL,
    start_date date NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE profiles
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    updated_at      datetime NULL,
    currency        VARCHAR(255) NULL,
    starting_amount DECIMAL(19, 2) NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE transactions
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    created_at              datetime NULL,
    updated_at              datetime NULL,
    amount                  DECIMAL(19, 2) NULL,
    date                    date NULL,
    transaction_description VARCHAR(255) NULL,
    transaction_type        INT NULL,
    category_id             BIGINT NOT NULL,
    enriched_category_id    BIGINT NULL,
    monthly_budget_id       BIGINT NULL,
    user_id                 BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE users
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    updated_at      datetime NULL,
    account_balance DECIMAL(19, 2) NULL,
    confirmed       BIT(1) NOT NULL,
    currency        VARCHAR(3) NULL,
    email           VARCHAR(255) NULL,
    sub             VARCHAR(255) NULL,
    profile_id      BIGINT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (sub)
);

ALTER TABLE transactions
    ADD CONSTRAINT FK1rfe8badd0cxyxet36g1geggp FOREIGN KEY (monthly_budget_id) REFERENCES monthly_budgets (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FK1rfe8badd0cxyxet36g1geggp ON transactions (monthly_budget_id);

ALTER TABLE enriched_categories
    ADD CONSTRAINT FK3epjnqkf3vwn88vp2bv8y8x8f FOREIGN KEY (monthly_budget_id) REFERENCES monthly_budgets (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FK3epjnqkf3vwn88vp2bv8y8x8f ON enriched_categories (monthly_budget_id);

ALTER TABLE enriched_categories
    ADD CONSTRAINT FKaq74rknh4oc00wenacy274f8c FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKaq74rknh4oc00wenacy274f8c ON enriched_categories (user_id);

ALTER TABLE enriched_categories
    ADD CONSTRAINT FKaqaj1i9qka5ap3o2x6ybi5dk0 FOREIGN KEY (category_id) REFERENCES categories (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKaqaj1i9qka5ap3o2x6ybi5dk0 ON enriched_categories (category_id);

ALTER TABLE categories
    ADD CONSTRAINT FKghuylkwuedgl2qahxjt8g41kb FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKghuylkwuedgl2qahxjt8g41kb ON categories (user_id);

ALTER TABLE transactions
    ADD CONSTRAINT FKnkafppk6enne2utks4lsqj6bh FOREIGN KEY (enriched_category_id) REFERENCES enriched_categories (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKnkafppk6enne2utks4lsqj6bh ON transactions (enriched_category_id);

ALTER TABLE users
    ADD CONSTRAINT FKq2e6rj0p6p1gec2cslmaxugw1 FOREIGN KEY (profile_id) REFERENCES profiles (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKq2e6rj0p6p1gec2cslmaxugw1 ON users (profile_id);

ALTER TABLE monthly_budgets
    ADD CONSTRAINT FKqt35xjpv4pc52dtk2yb7j4sin FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKqt35xjpv4pc52dtk2yb7j4sin ON monthly_budgets (user_id);

ALTER TABLE transactions
    ADD CONSTRAINT FKqwv7rmvc8va8rep7piikrojds FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKqwv7rmvc8va8rep7piikrojds ON transactions (user_id);

ALTER TABLE transactions
    ADD CONSTRAINT FKsqqi7sneo04kast0o138h19mv FOREIGN KEY (category_id) REFERENCES categories (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE INDEX FKsqqi7sneo04kast0o138h19mv ON transactions (category_id);
CREATE TABLE roles (
                       id           BIGINT        PRIMARY KEY AUTO_INCREMENT,
                       name         VARCHAR(64)   NOT NULL,
                       description  VARCHAR(255),

                       CONSTRAINT uk_roles_name UNIQUE (name)
);

INSERT INTO roles (name, description) VALUES
                                          ('ROLE_ADMIN', 'Full administrative privileges'),
                                          ('ROLE_USER',  'Standard registered user');

CREATE TABLE user_roles (
                            user_id  BIGINT NOT NULL,
                            role_id  BIGINT NOT NULL,

                            PRIMARY KEY (user_id, role_id),

                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_roles_role   ON user_roles(role_id);
CREATE INDEX idx_user_roles_user   ON user_roles(user_id);

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE  user_roles(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,

    PRIMARY KEY(user_id, role_id),

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
);
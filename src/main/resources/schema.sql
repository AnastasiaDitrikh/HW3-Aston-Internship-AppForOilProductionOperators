CREATE TABLE IF NOT EXISTS operators
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY UNIQUE,
    name          VARCHAR(200) NOT NULL,
    surname VARCHAR(200) NOT NULL
    );

CREATE TABLE IF NOT EXISTS location
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY UNIQUE,
    lat                NUMERIC,
    lon                NUMERIC
);

CREATE TABLE IF NOT EXISTS well_clusters(
   id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY UNIQUE,
   number            INT NOT NULL,
   location_id       BIGINT    NOT NULL,
   operator_id       BIGINT                      NOT NULL,
   CONSTRAINT fk_well_cluster_to_operator FOREIGN KEY (operator_id) REFERENCES operators (id),
   CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE IF NOT EXISTS wells(
id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY UNIQUE,
equipment          VARCHAR(20)      NOT NULL,
productivity         NUMERIC      NOT NULL,
well_cluster_id       BIGINT                      NOT NULL,
CONSTRAINT fk_well_to_cluster FOREIGN KEY (well_cluster_id) REFERENCES well_clusters (id)
);
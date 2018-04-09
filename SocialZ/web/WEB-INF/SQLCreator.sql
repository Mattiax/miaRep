/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  MATTI
 * Created: 1-apr-2018
 */
CREATE TABLE PERSONA (
    email       VARCHAR (255) UNIQUE
                              NOT NULL
                              PRIMARY KEY,
    password    VARCHAR (255) NOT NULL,
    nome        VARCHAR (255) NOT NULL,
    cognome     VARCHAR (255) NOT NULL,
    indirizzo   VARCHAR (255),
    sesso       CHAR,
    dataNascita DATE,
    foto        BLOB,
    telefono    VARCHAR (15),
    permesso    BOOLEAN       NOT NULL
);
CREATE TABLE GRUPPO (
    nome        VARCHAR (255) PRIMARY KEY
                              UNIQUE
                              NOT NULL,
    descrizione VARCHAR (255) 
);
CREATE TABLE HOBBIES (
    hobby VARCHAR (255) NOT NULL
                      UNIQUE
                      PRIMARY KEY
);
CREATE TABLE PARTECIPANTE (
    nome  VARCHAR (255) REFERENCES GRUPPO (nome) ON DELETE CASCADE
                                                 ON UPDATE CASCADE,
    email VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                   ON UPDATE CASCADE
);
CREATE TABLE HOBBYPRATICATO (
    email VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                   ON UPDATE CASCADE,
    hobby VARCHAR (255) REFERENCES HOBBIES (hobby) ON DELETE CASCADE
                                                   ON UPDATE CASCADE
);
CREATE TABLE MESSAGGIO (
    id           INTEGER       PRIMARY KEY AUTOINCREMENT,
    mittente     VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                          ON UPDATE CASCADE
                               NOT NULL,
    destinatario VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                          ON UPDATE CASCADE
                               NOT NULL,
    messaggio    VARCHAR,
    allegato     BLOB,
    dataOra      DATETIME      NOT NULL
);
CREATE TABLE MESSAGGIOGRUPPO (
    id           INTEGER       PRIMARY KEY
                               NOT NULL,
    mittente     VARCHAR (255) REFERENCES PERSONA (email) ON UPDATE CASCADE
                               NOT NULL,
    destinatario VARCHAR (255) REFERENCES GRUPPO (nome) ON DELETE CASCADE
                                                        ON UPDATE CASCADE
                               NOT NULL,
    messaggio    VARCHAR       NOT NULL,
    allegato     BLOB,
    dataOra      DATETIME      NOT NULL
);
CREATE TABLE AMMINISTRATOREGRUPPO (
    nomeGruppo VARCHAR (255) REFERENCES GRUPPO (nome) 
                             NOT NULL,
    email      VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                        ON UPDATE CASCADE
                             NOT NULL
);
CREATE TABLE AMMINISTRATORESOCIAL (
    email PRIMARY KEY
         UNIQUE
         REFERENCES PERSONA (email) ON DELETE CASCADE
                                    ON UPDATE CASCADE
);
CREATE TABLE RICHIESTAAMMINISTRATORES (
    idRichiesta    INTEGER       PRIMARY KEY AUTOINCREMENT,
    richiesta      VARCHAR (255) NOT NULL,
    amministratore VARCHAR       NOT NULL
                                 REFERENCES AMMINISTRATORISOCIAL (email) 
);
CREATE TABLE RICHIESTA (
    idRichiesta    INTEGER PRIMARY KEY
                           NOT NULL,
    descrizione    VARCHAR,
    amministratore VARCHAR,
    richiedente    VARCHAR REFERENCES PERSONA (email),
    destinatario   VARCHAR REFERENCES GRUPPO (nome) 
);
CREATE TABLE RICHIESTAHOBBY (
    idRichiesta INTEGER       PRIMARY KEY,
    hobby       VARCHAR (255) NOT NULL
);


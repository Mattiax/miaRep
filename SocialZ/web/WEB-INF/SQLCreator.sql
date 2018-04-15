/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  MATTI
 * Created: 1-apr-2018
 */
DROP TABLE IF EXISTS PERSONA;
DROP TABLE IF EXISTS HOBBY;
DROP TABLE IF EXISTS HOBBYPRATICATO;
DROP TABLE IF EXISTS GRUPPO;
DROP TABLE IF EXISTS PARTECIPANTE;
DROP TABLE IF EXISTS MESSAGGIOGRUPPO;
DROP TABLE IF EXISTS MESSAGGIO;
DROP TABLE IF EXISTS AMMINISTRATOREGRUPPO;
DROP TABLE IF EXISTS AMMINISTATORESOCIAL;
DROP TABLE IF EXISTS RICHIESTA;
DROP TABLE IF EXISTS RICHIESTAAMMINISTRATORES;

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
CREATE TABLE HOBBY (
    hobby VARCHAR (255) NOT NULL
                      UNIQUE
                      PRIMARY KEY
);

CREATE TABLE HOBBYPRATICATO (
    email VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                   ON UPDATE CASCADE,
    hobby VARCHAR (255) REFERENCES HOBBY (hobby) ON DELETE CASCADE
                                                 ON UPDATE CASCADE
);
CREATE TABLE GRUPPO (
    nome        VARCHAR (255) PRIMARY KEY
                              UNIQUE
                              NOT NULL,
    descrizione VARCHAR (255) 
);
CREATE TABLE PARTECIPANTE (
    nome  VARCHAR (255) REFERENCES GRUPPO (nome) ON DELETE CASCADE
                                                 ON UPDATE CASCADE,
    email VARCHAR (255) REFERENCES PERSONA (email) ON DELETE CASCADE
                                                   ON UPDATE CASCADE
);
CREATE TABLE MESSAGGIOGRUPPO (
    id           INTEGER       PRIMARY KEY
                               NOT NULL,
    mittente     VARCHAR (255) REFERENCES PARTECIPANTE (email) ON DELETE CASCADE
                                                               ON UPDATE CASCADE
                               NOT NULL,
    destinatario VARCHAR (255) REFERENCES GRUPPO (nome) ON DELETE CASCADE
                                                        ON UPDATE CASCADE
                               NOT NULL,
    messaggio    VARCHAR       NOT NULL,
    allegato     BLOB,
    dataOra      DATETIME      NOT NULL
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
CREATE TABLE RICHIESTA (
    idRichiesta    INTEGER PRIMARY KEY
                           NOT NULL,
    descrizione    VARCHAR,
    amministratore VARCHAR,
    richiedente    VARCHAR REFERENCES PERSONA (email),
    destinatario   VARCHAR REFERENCES GRUPPO (nome) 
);
CREATE TABLE RICHIESTAAMMINISTRATORES (
    idRichiesta    INTEGER       PRIMARY KEY AUTOINCREMENT,
    richiesta      VARCHAR (255) NOT NULL,
    amministratore VARCHAR       NOT NULL
                                 REFERENCES AMMINISTRATORESOCIAL (email) 
);

PRAGMA FOREIG_KEY=true;

INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("admin@admin.it","admin1","Admin","AdminCognome",null,'M',"25/10/1991",null,null,1);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("mario@rossi.it","mario1","Mario","Rossi",null,'M',"13/02/1985",null,null,1);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("marina@vulcani.it","marina1","Marina","Vulcani","Via Monte Paradiso 3",'F',"31/01/1990",null,"1234567890",0);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("tizio@mail.it","tizio1","Tizio","Tazio",null,'M',"26/12/1978",null,null,0);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("caio@mail.it","caio11","Caio","Coio",null,'M',"08/06/1963",null,null,1);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("sempronio@mail.it","sempronio1","Sempronio","Sempre","Via Speranza",'M',"19/08/1996",null,"9876543210",0);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("sempronia@mail.it","sempronia1","Sempronia","Sempre",null,'F',"03/02/2000",null,null,1);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("carlo@mail.it","carlo1","Carlo","Sempre",null,'M',"16/11/1971",null,null,0);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("annalisa@google.it","annalisa1","Annalisa","Mercati",null,'F',"11/09/1975",null,null,1);
INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso)
VALUES("pippo@disney.com","pippo1","Pippo","Disney","Via America",'M',"12/06/1911",null,null,0);

INSERT INTO HOBBY(hobby) VALUES("calcio");
INSERT INTO HOBBY(hobby) VALUES("pallavolo");
INSERT INTO HOBBY(hobby) VALUES("tennis");
INSERT INTO HOBBY(hobby) VALUES("ping-pong");
INSERT INTO HOBBY(hobby) VALUES("basket");
INSERT INTO HOBBY(hobby) VALUES("nuoto");
INSERT INTO HOBBY(hobby) VALUES("scherma");
INSERT INTO HOBBY(hobby) VALUES("pugilato");

INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("tizio@mail.it","calcio");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("sempronio@mail.it","pugilato");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("annalisa@google.it","nuoto");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("marina@vulcani.it","tennis");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("marina@vulcani.it","nuoto");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("caio@mail.it","tennis");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("tizio@mail.it","calcio");
INSERT INTO HOBBYPRATICATO(email,hobby) VALUES("sempronia@mail.it","nuoto");

INSERT INTO GRUPPO(nome,descrizione) VALUES("Famiglia","Famiglia Sempre");
INSERT INTO GRUPPO(nome,descrizione) VALUES("Classe 5IA","La migliore dello Zuccante");
INSERT INTO GRUPPO(nome,descrizione) VALUES("Gruppo nuoto",null);
INSERT INTO GRUPPO(nome,descrizione) VALUES("Capodanno","2018/2019 festone");
INSERT INTO GRUPPO(nome,descrizione) VALUES("Esami 2018","Che Dio ci aiuti");

INSERT INTO PARTECIPANTE(nome,email) VALUES("Famiglia","sempronia@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Famiglia","carlo@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Famiglia","sempronio@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Famiglia","annalisa@google.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Gruppo nuoto","annalisa@google.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Gruppo nuoto","marina@vulcani.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Gruppo nuoto","sempronia@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Esami 2018","carlo@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Esami 2018","sempronia@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Esami 2018","mario@rossi.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Esami 2018","marina@vulcani.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Esami 2018","pippo@disney.com");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Capodanno","sempronia@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Capodanno","carlo@mail.it");
INSERT INTO PARTECIPANTE(nome,email) VALUES("Capodanno","mario@rossi.it");

INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("annalisa@google.it","Gruppo nuoto","A che ora ci troviamo?",null,"15.30 15/04/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("marina@vulcani.it","Gruppo nuoto","Il corso comincia alle 6, quindi pensavo sulle 5.30?",null,"15.32 15/04/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("annalisa@google.it","Gruppo nuoto","Per me va bene fie, ditemi voi",null,"15.36 15/04/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("sempronia@mail.it","Gruppo nuoto","Va bon, ci vediamo al solito posto",null,"15.50 15/04/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("sempronia@mail.it","Gruppo nuoto","eskereeeeeeeeeeeeeee",null,"15.59 15/04/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("sempronia@mail.it","Esami 2018","Foio sto in ansia",null,"00.12 2/03/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("mario@rossi.it","Esami 2018","Lascia sta, che c'è da fora per domani?",null,"00.18 2/03/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("mario@rossi.it","Esami 2018","fare*",null,"00.18 2/03/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("marina@vulcani.it","Esami 2018","No ma ci rendiamo conto chi abbiamo come commissari interni?!",null,"00.35 2/03/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("marina@vulcani.it","Esami 2018","VOGLIO MORIRE",null,"00.39 2/03/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("carlo@mail.it","Esami 2018","AHAHAH ma calmatevi oche",null,"00.48 2/03/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("annalisa@google.it","Famiglia","BUONGIORNISSIMO!!",null,"06.18 29/02/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("carlo@mail.it","Famiglia","Amore ti prego.....",null,"06.25 29/02/2018");
INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("sempronio@mail.it","Famiglia","Dighe papà",null,"07.15 29/02/2018");

INSERT INTO MESSAGGIO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("sempronio@mail.it","annalisa@google.it","Mamma domani posso stare a casa da scuola?",null,"06/12/2017");
INSERT INTO MESSAGGIO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("caio@mail.it","tizio@mail.it","Weeee",null,"10.25 25/05/2016");
INSERT INTO MESSAGGIO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("tizio@mail.it","caio@mail.it","Oi dimmi",null,"12.14 25/05/2016");
INSERT INTO MESSAGGIO(mittente,destinatario,messaggio,allegato,dataOra)
VALUES("caio@mail.it","tizio@mail.it","Nulla AHAHAH",null,"12.21 25/05/2016");

INSERT INTO AMMINISTRATOREGRUPPO(nomeGruppo,email) VALUES("Famiglia","carlo@mail.it");
INSERT INTO AMMINISTRATOREGRUPPO(nomeGruppo,email) VALUES("Gruppo nuoto","annalisa@google.it");
INSERT INTO AMMINISTRATOREGRUPPO(nomeGruppo,email) VALUES("Esami 2018","mario@rossi.it");
INSERT INTO AMMINISTRATOREGRUPPO(nomeGruppo,email) VALUES("Capodanno","mario@rossi.it");
INSERT INTO AMMINISTRATOREGRUPPO(nomeGruppo,email) VALUES("Classe 5IA","admin@admin.it");

INSERT INTO AMMINISTRATORESOCIAL(email) VALUES("admin@admin.it");

INSERT INTO RICHIESTA(descrizione,amministratore,richiedente,destinatario)
VALUES("{mittente:admin@admin, destinatario:Famiglia, richiesta:partecipazione al gruppo}","carlo@mail.it","admin@admin","Famiglia");
INSERT INTO RICHIESTA(descrizione,amministratore,richiedente,destinatario)
VALUES("{mittente:annalisa@google.it, destinatario:Capodanno, richiesta:partecipazione al gruppo}","mario@rossi.it","annalisa@google.it","Capodanno");
INSERT INTO RICHIESTA(descrizione,amministratore,richiedente,destinatario)
VALUES("{mittente:mario@rossi.it, destinatario:Gruppo nuoto, richiesta:partecipazione al gruppo}","annalisa@google.it","mario@rossi.it","Gruppo nuoto");

INSERT INTO RICHIESTAAMMINISTRATORES(richiesta,amministratore)
VALUES("Richiesta aggiunta hobby #%=pattinaggio","admin@admin.it");
INSERT INTO RICHIESTAAMMINISTRATORES(richiesta,amministratore)
VALUES("Richiesta aggiunta hobby #%=spam","admin@admin.it");
INSERT INTO RICHIESTAAMMINISTRATORES(richiesta,amministratore)
VALUES("Richiesta aggiunta hobby #%=canoa","admin@admin.it");
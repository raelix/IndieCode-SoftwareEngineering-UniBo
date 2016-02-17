--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: affitti; Type: TABLE; Schema: public; Owner: pgadmin; Tablespace: 
--

CREATE TABLE affitti (
    idauto integer NOT NULL,
    idcliente integer NOT NULL,
    datainizio date DEFAULT ('now'::text)::date NOT NULL,
    costo numeric DEFAULT 0 NOT NULL,
    datafine date DEFAULT ('now'::text)::date NOT NULL,
    guidatore text,
    seggiolini integer DEFAULT 0 NOT NULL,
    navigatore boolean DEFAULT false NOT NULL,
    idagenzia integer NOT NULL
);


ALTER TABLE public.affitti OWNER TO pgadmin;

--
-- Name: auto; Type: TABLE; Schema: public; Owner: pgadmin; Tablespace: 
--

CREATE TABLE auto (
    id integer NOT NULL,
    marca text NOT NULL,
    modello text NOT NULL,
    cilindrata integer NOT NULL,
    anno date NOT NULL,
    categoria integer NOT NULL
);


ALTER TABLE public.auto OWNER TO pgadmin;

--
-- Name: auto_ID_seq; Type: SEQUENCE; Schema: public; Owner: pgadmin
--

CREATE SEQUENCE "auto_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."auto_ID_seq" OWNER TO pgadmin;

--
-- Name: auto_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pgadmin
--

ALTER SEQUENCE "auto_ID_seq" OWNED BY auto.id;


--
-- Name: clients; Type: TABLE; Schema: public; Owner: pgadmin; Tablespace: 
--

CREATE TABLE clients (
    id integer NOT NULL,
    nome text NOT NULL,
    cognome text NOT NULL,
    nascita date NOT NULL,
    cf text NOT NULL,
    patente text NOT NULL
);


ALTER TABLE public.clients OWNER TO pgadmin;

--
-- Name: clients_ID_seq; Type: SEQUENCE; Schema: public; Owner: pgadmin
--

CREATE SEQUENCE "clients_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."clients_ID_seq" OWNER TO pgadmin;

--
-- Name: clients_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pgadmin
--

ALTER SEQUENCE "clients_ID_seq" OWNED BY clients.id;


--
-- Name: test; Type: TABLE; Schema: public; Owner: pgadmin; Tablespace: 
--

CREATE TABLE test (
    id text NOT NULL,
    "int" numeric NOT NULL
);


ALTER TABLE public.test OWNER TO pgadmin;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: pgadmin
--

ALTER TABLE ONLY auto ALTER COLUMN id SET DEFAULT nextval('"auto_ID_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: pgadmin
--

ALTER TABLE ONLY clients ALTER COLUMN id SET DEFAULT nextval('"clients_ID_seq"'::regclass);


--
-- Data for Name: affitti; Type: TABLE DATA; Schema: public; Owner: pgadmin
--



--
-- Data for Name: auto; Type: TABLE DATA; Schema: public; Owner: pgadmin
--

INSERT INTO auto VALUES (94, 'Renault', 'Clio', 1200, '2010-09-20', 2);
INSERT INTO auto VALUES (95, 'Lamborghini', 'Gallardo', 3000, '2011-09-09', 1);
INSERT INTO auto VALUES (107, 'Renault', 'Clio', 1400, '2010-09-02', 2);
INSERT INTO auto VALUES (108, 'Maserati', 'Ghibli', 2500, '2010-01-04', 1);


--
-- Name: auto_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: pgadmin
--

SELECT pg_catalog.setval('"auto_ID_seq"', 111, true);


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: pgadmin
--

INSERT INTO clients VALUES (0, 'antonio', 'rossi', '1980-02-10', 'sbjds0w48sfw0f2fsd02s', 'asd920912');
INSERT INTO clients VALUES (2, 'andrea', 'bruni', '1987-07-19', 'ojncf8943fnsd982dde', 'vfvrw1213');
INSERT INTO clients VALUES (1, 'luca', 'verdi', '1985-04-18', 'cspkdmf9845rg08df24e', 'gdf4323');


--
-- Name: clients_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: pgadmin
--

SELECT pg_catalog.setval('"clients_ID_seq"', 58, true);


--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: pgadmin
--

INSERT INTO test VALUES ('zero', 0);
INSERT INTO test VALUES ('one', 1);
INSERT INTO test VALUES ('two', 2);


--
-- Name: affitti_pkey; Type: CONSTRAINT; Schema: public; Owner: pgadmin; Tablespace: 
--

ALTER TABLE ONLY affitti
    ADD CONSTRAINT affitti_pkey PRIMARY KEY (idauto, idcliente);


--
-- Name: auto_pkey1; Type: CONSTRAINT; Schema: public; Owner: pgadmin; Tablespace: 
--

ALTER TABLE ONLY auto
    ADD CONSTRAINT auto_pkey1 PRIMARY KEY (id);


--
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: pgadmin; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: test_pkey; Type: CONSTRAINT; Schema: public; Owner: pgadmin; Tablespace: 
--

ALTER TABLE ONLY test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


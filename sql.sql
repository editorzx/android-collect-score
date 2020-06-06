--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-02-23 01:24:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 206 (class 1259 OID 16551)
-- Name: logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.logs (
    id integer NOT NULL,
    s_id integer NOT NULL,
    score real NOT NULL,
    state text NOT NULL
);


ALTER TABLE public.logs OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16537)
-- Name: scores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scores (
    id integer NOT NULL,
    uid integer NOT NULL,
    subject text NOT NULL,
    score real DEFAULT 0 NOT NULL,
    "timestamp" timestamp without time zone
);


ALTER TABLE public.scores OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16535)
-- Name: scores_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.scores ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.scores_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 203 (class 1259 OID 16527)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    uid integer NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16525)
-- Name: users_uid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN uid ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_uid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 2839 (class 0 OID 16551)
-- Dependencies: 206
-- Data for Name: logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2838 (class 0 OID 16537)
-- Dependencies: 205
-- Data for Name: scores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (1, 1, 'MOBILE APP', 50, '2020-02-22 17:21:33');
INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (3, 1, 'SDX', 15, NULL);
INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (4, 1, 'struc', 5, '2020-02-22 17:30:55');
INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (5, 1, 'Pre test', 50, '2020-02-22 17:31:51');
INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (8, 2, 'SDXssa', 15, NULL);
INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (9, 3, 'Mobile App', 35.5, '2020-02-22 18:16:02');
INSERT INTO public.scores (id, uid, subject, score, "timestamp") OVERRIDING SYSTEM VALUE VALUES (10, 3, 'Data Struc', 18, '2020-02-22 18:19:44');


--
-- TOC entry 2836 (class 0 OID 16527)
-- Dependencies: 203
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (uid, username, password, name) OVERRIDING SYSTEM VALUE VALUES (1, 'test', '1234', 'natdanai');
INSERT INTO public.users (uid, username, password, name) OVERRIDING SYSTEM VALUE VALUES (2, 'test2', '1', 'test2');
INSERT INTO public.users (uid, username, password, name) OVERRIDING SYSTEM VALUE VALUES (3, 'eiei', 'eiei', 'EIXIEEE');


--
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 204
-- Name: scores_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.scores_id_seq', 10, true);


--
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 202
-- Name: users_uid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_uid_seq', 3, true);


--
-- TOC entry 2706 (class 2606 OID 16558)
-- Name: logs logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logs
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);


--
-- TOC entry 2704 (class 2606 OID 16545)
-- Name: scores scores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_pkey PRIMARY KEY (id);


--
-- TOC entry 2702 (class 2606 OID 16534)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (uid);


--
-- TOC entry 2708 (class 2606 OID 16559)
-- Name: logs logs_s_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.logs
    ADD CONSTRAINT logs_s_id_fkey FOREIGN KEY (s_id) REFERENCES public.scores(id);


--
-- TOC entry 2707 (class 2606 OID 16546)
-- Name: scores uid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT uid_fk FOREIGN KEY (uid) REFERENCES public.users(uid);


-- Completed on 2020-02-23 01:24:30

--
-- PostgreSQL database dump complete
--


--
-- PostgreSQL database dump
--

--
-- TOC entry 3244 (class 0 OID 224715)
-- Dependencies: 2776
-- Data for Name: m21_termino_annotation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m21_termino_annotation_type (id, label, annotation_type) VALUES (1, 'label_fr', 'string');
INSERT INTO m21_termino_annotation_type (id, label, annotation_type) VALUES (2, 'label_en', 'string');
INSERT INTO m21_termino_annotation_type (id, label, annotation_type) VALUES (3, 'label_sp', 'string');

--
-- TOC entry 3251 (class 0 OID 224750)
-- Dependencies: 2783
-- Data for Name: m22_role_in_tcrelation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m22_role_in_tcrelation (id, label) VALUES (1, 'arg1');
INSERT INTO m22_role_in_tcrelation (id, label) VALUES (2, 'arg2');

--
-- TOC entry 3254 (class 0 OID 224759)
-- Dependencies: 2786
-- Data for Name: m22_termino_onto_annotation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m22_termino_onto_annotation_type (id, label, annotation_type) VALUES (2, 'label_fr', 'string');
INSERT INTO m22_termino_onto_annotation_type (id, label, annotation_type) VALUES (3, 'label_en', 'string');
INSERT INTO m22_termino_onto_annotation_type (id, label, annotation_type) VALUES (1, 'preferred_label', 'string');
INSERT INTO m22_termino_onto_annotation_type (id, label, annotation_type) VALUES (4, 'label_sp', 'string');

--
-- TOC entry 3258 (class 0 OID 224773)
-- Dependencies: 2790
-- Data for Name: m22_terminoontology; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m22_terminoontology (id, name, namespace, "language") VALUES (1, 'Default termino-ontology', 'http://org.dafoe.terminoonto', NULL);

--
-- TOC entry 3261 (class 0 OID 224788)
-- Dependencies: 2793
-- Data for Name: m23_annotation_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m23_annotation_type (id, label, annotation_type) VALUES (1, 'Etiquette', 'string');
INSERT INTO m23_annotation_type (id, label, annotation_type) VALUES (2, 'Commentaire', 'string');


--
-- TOC entry 3265 (class 0 OID 224808)
-- Dependencies: 2797
-- Data for Name: m23_basic_datatype; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m23_basic_datatype (id, label) VALUES (1, 'string');
INSERT INTO m23_basic_datatype (id, label) VALUES (2, 'integer');
INSERT INTO m23_basic_datatype (id, label) VALUES (3, 'float');
INSERT INTO m23_basic_datatype (id, label) VALUES (4, 'boolean');

--
-- TOC entry 3282 (class 0 OID 224872)
-- Dependencies: 2814
-- Data for Name: m23_ontology; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO m23_ontology (rid, name, namespace, "language") VALUES (1, 'Dafoe ontology', 'http://www.dafoe.org/default', 'fr');


-- Completed on 2009-12-02 12:07:21

--
-- PostgreSQL database dump complete
--



-- ---------------------------------------
-- Host      : localhost
-- Database  : DAFOE
-- Version   : PostgreSQL 8.3.5, compiled by Visual C++ build 1400


--
-- Structure for table m11_corpus (OID = 19008) : 
--
CREATE TABLE public.m11_corpus (
    corpus_id integer,
    name character varying,
    project_id integer,
    "language" character varying
) WITH OIDS;
ALTER TABLE ONLY public.m11_corpus ALTER COLUMN corpus_id SET STATISTICS 0;
ALTER TABLE ONLY public.m11_corpus ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m11_corpus ALTER COLUMN project_id SET STATISTICS 0;
--
-- Structure for table m11_document (OID = 19014) : 
--
CREATE TABLE public.m11_document (
    doc_id integer,
    name character varying,
    corpus_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m11_document ALTER COLUMN doc_id SET STATISTICS 0;
ALTER TABLE ONLY public.m11_document ALTER COLUMN name SET STATISTICS 0;
--
-- Structure for table m11_sentence (OID = 19020) : 
--
CREATE TABLE public.m11_sentence (
    id integer,
    content character varying,
    doc_id integer,
    num_order integer
) WITH OIDS;
ALTER TABLE ONLY public.m11_sentence ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m11_sentence ALTER COLUMN content SET STATISTICS 0;
ALTER TABLE ONLY public.m11_sentence ALTER COLUMN doc_id SET STATISTICS 0;
--
-- Structure for table m11_termoccurrence (OID = 19026) : 
--
CREATE TABLE public.m11_termoccurrence (
    id integer,
    term_id integer,
    "position" integer,
    length integer,
    sentence_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m11_termoccurrence ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m11_termoccurrence ALTER COLUMN term_id SET STATISTICS 0;
--
-- Structure for table m12_terminoconceptoccurrence (OID = 19029) : 
--
CREATE TABLE public.m12_terminoconceptoccurrence (
    id integer,
    terminoconcept_id integer,
    termoccur_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m12_terminoconceptoccurrence ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m12_terminoconceptoccurrence ALTER COLUMN terminoconcept_id SET STATISTICS 0;
--
-- Structure for table m13_class_instance (OID = 19032) : 
--
CREATE TABLE public.m13_class_instance (
    id integer,
    class_id integer,
    property_label character varying,
    property_value character varying,
    is_object_property boolean
) WITH OIDS;
ALTER TABLE ONLY public.m13_class_instance ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m13_class_instance ALTER COLUMN class_id SET STATISTICS 0;
--
-- Structure for table m21_annotation_value_2_term (OID = 19038) : 
--
CREATE TABLE public.m21_annotation_value_2_term (
    id integer,
    annotation_type_id integer,
    term_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m21_annotation_value_2_term ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_annotation_value_2_term ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_annotation_value_2_term ALTER COLUMN term_id SET STATISTICS 0;
--
-- Structure for table m21_entity (OID = 19044) : 
--
CREATE TABLE public.m21_entity (
    id integer,
    label character varying
) WITH OIDS;
ALTER TABLE ONLY public.m21_entity ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_entity ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m21_method (OID = 19050) : 
--
CREATE TABLE public.m21_method (
    id integer,
    pattern character varying,
    tool character varying,
    type_rel_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_method ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_method ALTER COLUMN pattern SET STATISTICS 0;
ALTER TABLE ONLY public.m21_method ALTER COLUMN tool SET STATISTICS 0;
ALTER TABLE ONLY public.m21_method ALTER COLUMN type_rel_id SET STATISTICS 0;
--
-- Structure for table m21_method_2_type_relation (OID = 19056) : 
--
CREATE TABLE public.m21_method_2_type_relation (
    id integer,
    method_id integer,
    type_rel_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_method_2_type_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_method_2_type_relation ALTER COLUMN method_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_method_2_type_relation ALTER COLUMN type_rel_id SET STATISTICS 0;
--
-- Structure for table m21_origin_relation (OID = 19059) : 
--
CREATE TABLE public.m21_origin_relation (
    id integer,
    method_id integer,
    sentence_id integer,
    term_rel_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_origin_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_origin_relation ALTER COLUMN method_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_origin_relation ALTER COLUMN sentence_id SET STATISTICS 0;
--
-- Structure for table m21_term (OID = 19062) : 
--
CREATE TABLE public.m21_term (
    id integer,
    label character varying,
    star_term_id integer,
    terminology_id integer,
    status integer,
    is_named_entity integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_term ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m21_term_2_termproperty (OID = 19068) : 
--
CREATE TABLE public.m21_term_2_termproperty (
    id integer,
    term_id integer,
    termproperty_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_term_2_termproperty ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_2_termproperty ALTER COLUMN term_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_2_termproperty ALTER COLUMN termproperty_id SET STATISTICS 0;
--
-- Structure for table m21_term_2_variante (OID = 19071) : 
--
CREATE TABLE public.m21_term_2_variante (
    id integer,
    variante_id integer,
    term_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_term_2_variante ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_2_variante ALTER COLUMN variante_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_2_variante ALTER COLUMN term_id SET STATISTICS 0;
--
-- Structure for table m21_term_relation (OID = 19074) : 
--
CREATE TABLE public.m21_term_relation (
    id integer,
    term1_id integer,
    term2_id integer,
    type_rel_id integer,
    left_content character varying,
    right_content character varying,
    middle character varying,
    is_examinated boolean DEFAULT false,
    additional_info character varying,
    terminology_id integer,
    status integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_term_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_relation ALTER COLUMN term1_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_relation ALTER COLUMN term2_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_relation ALTER COLUMN type_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_relation ALTER COLUMN right_content SET STATISTICS 0;
--
-- Structure for table m21_term_relation_syntactic (OID = 19081) : 
--
CREATE TABLE public.m21_term_relation_syntactic (
    id integer,
    head_term_id integer,
    connection_info character varying,
    modifier_term_id integer,
    terminology_id integer,
    status integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_term_relation_syntactic ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_term_relation_syntactic ALTER COLUMN head_term_id SET STATISTICS 0;
--
-- Structure for table m21_term_saillance (OID = 19087) : 
--
CREATE TABLE public.m21_term_saillance (
    saillance_id integer,
    frequency bigint,
    term_id integer,
    prod_t integer,
    prod_e integer,
    nb_voisin_t integer,
    nb_voisin_e integer,
    tfidf integer,
    nb_var integer,
    typo_weight integer,
    position_weight integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_term_saillance ALTER COLUMN saillance_id SET STATISTICS 0;
--
-- Structure for table m21_termino_annotation_type (OID = 19090) : 
--
CREATE TABLE public.m21_termino_annotation_type (
    id integer,
    label character varying,
    annotation_type character varying
) WITH OIDS;
ALTER TABLE ONLY public.m21_termino_annotation_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_termino_annotation_type ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m21_termino_annotation_type ALTER COLUMN annotation_type SET STATISTICS 0;
--
-- Structure for table m21_terminology (OID = 19096) : 
--
CREATE TABLE public.m21_terminology (
    id integer,
    name character varying,
    namespace character varying,
    "language" character varying,
    project_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_terminology ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_terminology ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m21_terminology ALTER COLUMN namespace SET STATISTICS 0;
--
-- Structure for table m21_termproperty (OID = 19102) : 
--
CREATE TABLE public.m21_termproperty (
    id integer,
    label character varying,
    target_data_type_id integer,
    target_entity_type_id integer,
    "type" character varying
) WITH OIDS;
ALTER TABLE ONLY public.m21_termproperty ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_termproperty ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m21_termproperty ALTER COLUMN target_data_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_termproperty ALTER COLUMN target_entity_type_id SET STATISTICS 0;
--
-- Structure for table m21_type_relation (OID = 19108) : 
--
CREATE TABLE public.m21_type_relation (
    id integer,
    label character varying,
    status integer,
    terminology_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m21_type_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m21_type_relation ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m22_annotation_value_2_tc (OID = 19114) : 
--
CREATE TABLE public.m22_annotation_value_2_tc (
    id integer,
    annotation_type_id integer,
    tc_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_annotation_value_2_tc ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_annotation_value_2_tc ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_annotation_value_2_tc ALTER COLUMN tc_id SET STATISTICS 0;
--
-- Structure for table m22_annotation_value_2_tcr (OID = 19120) : 
--
CREATE TABLE public.m22_annotation_value_2_tcr (
    id integer,
    annotation_type_id integer,
    tcr_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_annotation_value_2_tcr ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_annotation_value_2_tcr ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_annotation_value_2_tcr ALTER COLUMN tcr_id SET STATISTICS 0;
--
-- Structure for table m22_annotation_value_2_tcr_type (OID = 19126) : 
--
CREATE TABLE public.m22_annotation_value_2_tcr_type (
    id integer,
    annotation_type_id integer,
    tcr_type_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_annotation_value_2_tcr_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_annotation_value_2_tcr_type ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_annotation_value_2_tcr_type ALTER COLUMN tcr_type_id SET STATISTICS 0;
--
-- Structure for table m22_basic_tc_type (OID = 19132) : 
--
CREATE TABLE public.m22_basic_tc_type (
    id integer,
    label character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_basic_tc_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_basic_tc_type ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m22_binary_basic_tc_relation (OID = 19138) : 
--
CREATE TABLE public.m22_binary_basic_tc_relation (
    id integer,
    tc_id integer,
    basic_tc_id integer,
    tc_type_relation_id integer,
    status integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_binary_basic_tc_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_binary_basic_tc_relation ALTER COLUMN tc_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_binary_basic_tc_relation ALTER COLUMN basic_tc_id SET STATISTICS 0;
--
-- Structure for table m22_language (OID = 19141) : 
--
CREATE TABLE public.m22_language (
    id integer,
    label character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_language ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_language ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m22_role_in_tcrelation (OID = 19147) : 
--
CREATE TABLE public.m22_role_in_tcrelation (
    id integer,
    label character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_role_in_tcrelation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_role_in_tcrelation ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m22_tc_2parent2_tc (OID = 19153) : 
--
CREATE TABLE public.m22_tc_2parent2_tc (
    id integer,
    tc_parent_id integer,
    tc_child_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_tc_2parent2_tc ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_2parent2_tc ALTER COLUMN tc_parent_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_2parent2_tc ALTER COLUMN tc_child_id SET STATISTICS 0;
--
-- Structure for table m22_tc_relation_member (OID = 19156) : 
--
CREATE TABLE public.m22_tc_relation_member (
    id integer,
    tc_id integer,
    tc_rel_id integer,
    role_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_tc_relation_member ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_relation_member ALTER COLUMN tc_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_relation_member ALTER COLUMN tc_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_relation_member ALTER COLUMN role_id SET STATISTICS 0;
--
-- Structure for table m22_termino_onto_annotation_type (OID = 19159) : 
--
CREATE TABLE public.m22_termino_onto_annotation_type (
    id integer,
    label character varying,
    annotation_type character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_termino_onto_annotation_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_termino_onto_annotation_type ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m22_termino_onto_annotation_type ALTER COLUMN annotation_type SET STATISTICS 0;
--
-- Structure for table m22_terminoconcept (OID = 19165) : 
--
CREATE TABLE public.m22_terminoconcept (
    id integer,
    label character varying,
    status integer,
    termino_ontology_id integer,
    nl_definition character varying,
    parent_diff character varying,
    parent_similar character varying,
    sibling_diff character varying,
    sibling_similar character varying,
    term_id integer,
    is_datatype boolean,
    is_simple boolean
) WITH OIDS;
ALTER TABLE ONLY public.m22_terminoconcept ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoconcept ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m22_terminoconcept_2_terminoproperty (OID = 19171) : 
--
CREATE TABLE public.m22_terminoconcept_2_terminoproperty (
    id integer,
    tc_id integer,
    terminoproperty_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_terminoconcept_2_terminoproperty ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoconcept_2_terminoproperty ALTER COLUMN tc_id SET STATISTICS 0;
--
-- Structure for table m22_terminoconcept_relation (OID = 19174) : 
--
CREATE TABLE public.m22_terminoconcept_relation (
    id integer,
    type_reltc_id integer,
    status integer,
    termino_ontology_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_terminoconcept_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoconcept_relation ALTER COLUMN type_reltc_id SET STATISTICS 0;
--
-- Structure for table m22_terminoontology (OID = 19177) : 
--
CREATE TABLE public.m22_terminoontology (
    id integer,
    name character varying,
    namespace character varying,
    "language" character varying,
    project_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_terminoontology ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoontology ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoontology ALTER COLUMN namespace SET STATISTICS 0;
--
-- Structure for table m22_terminoproperty (OID = 19183) : 
--
CREATE TABLE public.m22_terminoproperty (
    id integer,
    label character varying,
    "type" character varying
) WITH OIDS;
ALTER TABLE ONLY public.m22_terminoproperty ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoproperty ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m22_terminoproperty ALTER COLUMN "type" SET STATISTICS 0;
--
-- Structure for table m22_type_relation_tc (OID = 19189) : 
--
CREATE TABLE public.m22_type_relation_tc (
    id integer,
    name character varying,
    parent_id integer,
    status integer,
    termino_ontology_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_type_relation_tc ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_type_relation_tc ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m22_type_relation_tc ALTER COLUMN parent_id SET STATISTICS 0;
--
-- Structure for table m23_annotation_type (OID = 19195) : 
--
CREATE TABLE public.m23_annotation_type (
    id integer,
    label character varying,
    annotation_type character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_annotation_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_type ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_type ALTER COLUMN annotation_type SET STATISTICS 0;
--
-- Structure for table m23_annotation_value_2_class (OID = 19201) : 
--
CREATE TABLE public.m23_annotation_value_2_class (
    id integer,
    annotation_type_id integer,
    class_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_annotation_value_2_class ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_value_2_class ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_value_2_class ALTER COLUMN class_id SET STATISTICS 0;
--
-- Structure for table m23_annotation_value_2_datatype_prop (OID = 19207) : 
--
CREATE TABLE public.m23_annotation_value_2_datatype_prop (
    id integer,
    annotation_type_id integer,
    datatype_prop_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_annotation_value_2_datatype_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_value_2_datatype_prop ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_value_2_datatype_prop ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table m23_annotation_value_2_object_prop (OID = 19213) : 
--
CREATE TABLE public.m23_annotation_value_2_object_prop (
    id integer,
    annotation_type_id integer,
    object_prop_id integer,
    annotation_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_annotation_value_2_object_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_value_2_object_prop ALTER COLUMN annotation_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_annotation_value_2_object_prop ALTER COLUMN object_prop_id SET STATISTICS 0;
--
-- Structure for table m23_basic_datatype (OID = 19219) : 
--
CREATE TABLE public.m23_basic_datatype (
    id integer,
    label character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_basic_datatype ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_basic_datatype ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m23_class (OID = 19225) : 
--
CREATE TABLE public.m23_class (
    id integer,
    label character varying,
    ontology_id integer,
    status integer,
    is_primitive boolean,
    logical_definition character varying,
    sql_instance_query character varying,
    namespace character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_class ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_class ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m23_class ALTER COLUMN ontology_id SET STATISTICS 0;
--
-- Structure for table m23_class_2domain2_datatype_property (OID = 19231) : 
--
CREATE TABLE public.m23_class_2domain2_datatype_property (
    id integer,
    class_id integer,
    datatype_property_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_class_2domain2_datatype_property ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_class_2domain2_datatype_property ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_class_2domain2_datatype_property ALTER COLUMN datatype_property_id SET STATISTICS 0;
--
-- Structure for table m23_class_2domain2_object_property (OID = 19234) : 
--
CREATE TABLE public.m23_class_2domain2_object_property (
    id integer,
    class_id integer,
    object_property_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_class_2domain2_object_property ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_class_2domain2_object_property ALTER COLUMN class_id SET STATISTICS 0;
--
-- Structure for table m23_class_2parent2_class (OID = 19237) : 
--
CREATE TABLE public.m23_class_2parent2_class (
    id integer,
    class_parent_id integer,
    class_child_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_class_2parent2_class ALTER COLUMN class_parent_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_class_2parent2_class ALTER COLUMN class_child_id SET STATISTICS 0;
--
-- Structure for table m23_constraint_all_value_from (OID = 19240) : 
--
CREATE TABLE public.m23_constraint_all_value_from (
    id integer,
    class_id integer,
    object_prop_id integer,
    target_class_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_all_value_from ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_all_value_from ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_all_value_from ALTER COLUMN object_prop_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_all_value_from ALTER COLUMN target_class_id SET STATISTICS 0;
--
-- Structure for table m23_constraint_has_value (OID = 19243) : 
--
CREATE TABLE public.m23_constraint_has_value (
    id integer,
    class_id integer,
    datatype_prop_id integer,
    match_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_has_value ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_has_value ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_has_value ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table m23_constraint_high_than (OID = 19249) : 
--
CREATE TABLE public.m23_constraint_high_than (
    id integer,
    class_id integer,
    datatype_prop_id integer,
    match_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_high_than ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_high_than ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_high_than ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table m23_constraint_less_than (OID = 19255) : 
--
CREATE TABLE public.m23_constraint_less_than (
    id integer,
    class_id integer,
    datatype_prop_id integer,
    match_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_less_than ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_less_than ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_less_than ALTER COLUMN datatype_prop_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_less_than ALTER COLUMN match_value SET STATISTICS 0;
--
-- Structure for table m23_constraint_like (OID = 19261) : 
--
CREATE TABLE public.m23_constraint_like (
    id integer,
    class_id integer,
    datatype_prop_id integer,
    match_value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_like ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_like ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_like ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table m23_constraint_min_max (OID = 19267) : 
--
CREATE TABLE public.m23_constraint_min_max (
    id integer,
    class_id integer,
    object_prop_id integer,
    match_min_value integer,
    match_max_value integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_min_max ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_min_max ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_min_max ALTER COLUMN object_prop_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_min_max ALTER COLUMN match_min_value SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_min_max ALTER COLUMN match_max_value SET STATISTICS 0;
--
-- Structure for table m23_constraint_one_of (OID = 19270) : 
--
CREATE TABLE public.m23_constraint_one_of (
    id integer,
    class_id integer,
    datatype_prop_id integer,
    enumeration_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_one_of ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_one_of ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_one_of ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table m23_constraint_some_value_from (OID = 19273) : 
--
CREATE TABLE public.m23_constraint_some_value_from (
    id integer,
    class_id integer,
    object_prop_id integer,
    target_class_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_constraint_some_value_from ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_some_value_from ALTER COLUMN class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_some_value_from ALTER COLUMN object_prop_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_constraint_some_value_from ALTER COLUMN target_class_id SET STATISTICS 0;
--
-- Structure for table m23_datatype_property (OID = 19276) : 
--
CREATE TABLE public.m23_datatype_property (
    id integer,
    label character varying,
    range_basic_id integer,
    status integer,
    ontology_id integer,
    parent_id integer,
    card_min integer,
    card_max integer,
    namespace character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_datatype_property ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_datatype_property ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m23_datatype_property ALTER COLUMN range_basic_id SET STATISTICS 0;
--
-- Structure for table m23_enumeration (OID = 19282) : 
--
CREATE TABLE public.m23_enumeration (
    id integer,
    label character varying,
    ontology_id integer,
    namespace character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_enumeration ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_enumeration ALTER COLUMN label SET STATISTICS 0;
--
-- Structure for table m23_enumeration_value (OID = 19288) : 
--
CREATE TABLE public.m23_enumeration_value (
    id integer,
    label character varying,
    enum_id integer,
    ordinal integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_enumeration_value ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_enumeration_value ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m23_enumeration_value ALTER COLUMN enum_id SET STATISTICS 0;
--
-- Structure for table m23_object_property (OID = 19294) : 
--
CREATE TABLE public.m23_object_property (
    id integer,
    label character varying,
    range_id integer,
    is_symetric boolean,
    is_transitive boolean,
    inverse_of integer,
    ontology_id integer,
    status integer,
    parent_id integer,
    card_min integer,
    card_max integer,
    namespace character varying
) WITH OIDS;
ALTER TABLE ONLY public.m23_object_property ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_object_property ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m23_object_property ALTER COLUMN range_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_object_property ALTER COLUMN is_symetric SET STATISTICS 0;
ALTER TABLE ONLY public.m23_object_property ALTER COLUMN is_transitive SET STATISTICS 0;
ALTER TABLE ONLY public.m23_object_property ALTER COLUMN inverse_of SET STATISTICS 0;
--
-- Structure for table m23_ontology (OID = 19300) : 
--
CREATE TABLE public.m23_ontology (
    rid integer,
    name character varying,
    namespace character varying,
    "language" character varying,
    project_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_ontology ALTER COLUMN rid SET STATISTICS 0;
ALTER TABLE ONLY public.m23_ontology ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m23_ontology ALTER COLUMN namespace SET STATISTICS 0;
--
-- Structure for table m23_property (OID = 19306) : 
--
CREATE TABLE public.m23_property (
    id integer,
    label character varying,
    domain_class_id integer,
    range_class_object_id integer,
    range_datatype_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m23_property ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_property ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m23_property ALTER COLUMN domain_class_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_property ALTER COLUMN range_class_object_id SET STATISTICS 0;
ALTER TABLE ONLY public.m23_property ALTER COLUMN range_datatype_id SET STATISTICS 0;
--
-- Structure for table m24_column (OID = 19312) : 
--
CREATE TABLE public.m24_column (
    id integer,
    name character varying,
    table_id integer,
    sql_type character varying
) WITH OIDS;
ALTER TABLE ONLY public.m24_column ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m24_column ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m24_column ALTER COLUMN table_id SET STATISTICS 0;
--
-- Structure for table m24_constraint (OID = 19318) : 
--
CREATE TABLE public.m24_constraint (
    id integer,
    name character varying,
    constraint_type character varying,
    expression character varying,
    fk_table_id integer,
    column_id integer,
    table_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m24_constraint ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m24_constraint ALTER COLUMN constraint_type SET STATISTICS 0;
ALTER TABLE ONLY public.m24_constraint ALTER COLUMN expression SET STATISTICS 0;
ALTER TABLE ONLY public.m24_constraint ALTER COLUMN fk_table_id SET STATISTICS 0;
--
-- Structure for table m24_schema (OID = 19324) : 
--
CREATE TABLE public.m24_schema (
    id integer,
    name character varying
) WITH OIDS;
ALTER TABLE ONLY public.m24_schema ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m24_schema ALTER COLUMN name SET STATISTICS 0;
--
-- Structure for table m24_table (OID = 19330) : 
--
CREATE TABLE public.m24_table (
    id integer,
    name character varying,
    schema_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m24_table ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m24_table ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m24_table ALTER COLUMN schema_id SET STATISTICS 0;
--
-- Structure for table m3_attribut (OID = 19336) : 
--
CREATE TABLE public.m3_attribut (
    id integer,
    name character varying,
    entity_id integer,
    is_dynamic boolean
) WITH OIDS;
ALTER TABLE ONLY public.m3_attribut ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut ALTER COLUMN entity_id SET STATISTICS 0;
--
-- Structure for table m3_attribut_type (OID = 19342) : 
--
CREATE TABLE public.m3_attribut_type (
    id integer,
    is_simple_type boolean,
    st_name character varying,
    st_java_type character varying,
    st_sql_type character varying,
    et_target_entity_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m3_attribut_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut_type ALTER COLUMN is_simple_type SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut_type ALTER COLUMN st_name SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut_type ALTER COLUMN st_java_type SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut_type ALTER COLUMN st_sql_type SET STATISTICS 0;
ALTER TABLE ONLY public.m3_attribut_type ALTER COLUMN et_target_entity_id SET STATISTICS 0;
--
-- Structure for table m3_dynamic_element (OID = 19348) : 
--
CREATE TABLE public.m3_dynamic_element (
    id integer,
    entity_id integer,
    attribut_id integer,
    value character varying
) WITH OIDS;
ALTER TABLE ONLY public.m3_dynamic_element ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_dynamic_element ALTER COLUMN entity_id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_dynamic_element ALTER COLUMN attribut_id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_dynamic_element ALTER COLUMN value SET STATISTICS 0;
--
-- Structure for table m3_entity (OID = 19354) : 
--
CREATE TABLE public.m3_entity (
    id integer,
    name character varying,
    model_id integer,
    is_abstract boolean,
    related_table_name character varying,
    parent_id integer,
    is_dynamic boolean
) WITH OIDS;
ALTER TABLE ONLY public.m3_entity ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_entity ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m3_entity ALTER COLUMN model_id SET STATISTICS 0;
--
-- Structure for table m3_entity_type (OID = 19360) : 
--
CREATE TABLE public.m3_entity_type (
    id integer,
    target_entity_id integer
) WITH OIDS;
--
-- Structure for table m3_m3instance_2_m2class (OID = 19363) : 
--
CREATE TABLE public.m3_m3instance_2_m2class (
    id integer,
    entity_id integer,
    m2_class_name character varying
) WITH OIDS;
ALTER TABLE ONLY public.m3_m3instance_2_m2class ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_m3instance_2_m2class ALTER COLUMN entity_id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_m3instance_2_m2class ALTER COLUMN m2_class_name SET STATISTICS 0;
--
-- Structure for table m3_mapping (OID = 19369) : 
--
CREATE TABLE public.m3_mapping (
    id integer,
    name character varying,
    source_model_id integer,
    target_model_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m3_mapping ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_mapping ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m3_mapping ALTER COLUMN source_model_id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_mapping ALTER COLUMN target_model_id SET STATISTICS 0;
--
-- Structure for table m3_model (OID = 19375) : 
--
CREATE TABLE public.m3_model (
    id integer,
    name character varying,
    name_space character varying
) WITH OIDS;
ALTER TABLE ONLY public.m3_model ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_model ALTER COLUMN name SET STATISTICS 0;
--
-- Structure for table m3_morphism (OID = 19381) : 
--
CREATE TABLE public.m3_morphism (
    id integer,
    mapping_id integer,
    source_attribut_id integer,
    target_attribut_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m3_morphism ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_morphism ALTER COLUMN mapping_id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_morphism ALTER COLUMN source_attribut_id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_morphism ALTER COLUMN target_attribut_id SET STATISTICS 0;
--
-- Structure for table m3_simple_type (OID = 19384) : 
--
CREATE TABLE public.m3_simple_type (
    id integer,
    name character varying,
    associated_java_type character varying,
    associated_sql_type character varying
) WITH OIDS;
ALTER TABLE ONLY public.m3_simple_type ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m3_simple_type ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m3_simple_type ALTER COLUMN associated_java_type SET STATISTICS 0;
ALTER TABLE ONLY public.m3_simple_type ALTER COLUMN associated_sql_type SET STATISTICS 0;
--
-- Structure for table map_m21_m22_term_2_terminoconcept (OID = 19390) : 
--
CREATE TABLE public.map_m21_m22_term_2_terminoconcept (
    id integer,
    term_id integer,
    tc_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m21_m22_term_2_terminoconcept ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_term_2_terminoconcept ALTER COLUMN term_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_term_2_terminoconcept ALTER COLUMN tc_id SET STATISTICS 0;
--
-- Structure for table map_m21_m22_term_relation_2_tc_relation (OID = 19393) : 
--
CREATE TABLE public.map_m21_m22_term_relation_2_tc_relation (
    id integer,
    term_rel_id integer,
    tc_rel_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m21_m22_term_relation_2_tc_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_term_relation_2_tc_relation ALTER COLUMN term_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_term_relation_2_tc_relation ALTER COLUMN tc_rel_id SET STATISTICS 0;
--
-- Structure for table map_m21_m22_term_type_relation_2_tc_type_relation (OID = 19396) : 
--
CREATE TABLE public.map_m21_m22_term_type_relation_2_tc_type_relation (
    id integer,
    term_type_rel_id integer,
    tc_type_rel_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m21_m22_term_type_relation_2_tc_type_relation ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_term_type_relation_2_tc_type_relation ALTER COLUMN term_type_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_term_type_relation_2_tc_type_relation ALTER COLUMN tc_type_rel_id SET STATISTICS 0;
--
-- Structure for table map_m21_m22_terminoloy_2_terminoontology (OID = 19399) : 
--
CREATE TABLE public.map_m21_m22_terminoloy_2_terminoontology (
    id integer,
    terminology_id integer,
    terminoontology_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m21_m22_terminoloy_2_terminoontology ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_terminoloy_2_terminoontology ALTER COLUMN terminology_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m21_m22_terminoloy_2_terminoontology ALTER COLUMN terminoontology_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tc_2_class (OID = 19402) : 
--
CREATE TABLE public.map_m22_m23_tc_2_class (
    id integer,
    tc_id integer,
    class_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tc_2_class ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_2_class ALTER COLUMN tc_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_2_class ALTER COLUMN class_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tc_2_datatype_prop (OID = 19405) : 
--
CREATE TABLE public.map_m22_m23_tc_2_datatype_prop (
    id integer,
    tc_id integer,
    datatype_prop_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tc_2_datatype_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_2_datatype_prop ALTER COLUMN tc_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_2_datatype_prop ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tc_2_object_prop (OID = 19408) : 
--
CREATE TABLE public.map_m22_m23_tc_2_object_prop (
    id integer,
    tc_id integer,
    object_prop_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tc_2_object_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_2_object_prop ALTER COLUMN tc_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_2_object_prop ALTER COLUMN object_prop_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tc_relation_2_class (OID = 19411) : 
--
CREATE TABLE public.map_m22_m23_tc_relation_2_class (
    id integer,
    tc_rel_id integer,
    class_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_class ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_class ALTER COLUMN tc_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_class ALTER COLUMN class_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tc_relation_2_datatype_prop (OID = 19414) : 
--
CREATE TABLE public.map_m22_m23_tc_relation_2_datatype_prop (
    id integer,
    tc_rel_id integer,
    datatype_prop_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_datatype_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_datatype_prop ALTER COLUMN tc_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_datatype_prop ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tc_relation_2_object_prop (OID = 19417) : 
--
CREATE TABLE public.map_m22_m23_tc_relation_2_object_prop (
    id integer,
    tc_rel_id integer,
    object_prop_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_object_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_object_prop ALTER COLUMN tc_rel_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tc_relation_2_object_prop ALTER COLUMN object_prop_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tcr_type_2_class (OID = 19420) : 
--
CREATE TABLE public.map_m22_m23_tcr_type_2_class (
    id integer,
    tcr_type_id integer,
    class_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_class ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_class ALTER COLUMN tcr_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_class ALTER COLUMN class_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tcr_type_2_datatype_prop (OID = 19423) : 
--
CREATE TABLE public.map_m22_m23_tcr_type_2_datatype_prop (
    id integer,
    tcr_type_id integer,
    datatype_prop_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_datatype_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_datatype_prop ALTER COLUMN tcr_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_datatype_prop ALTER COLUMN datatype_prop_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_tcr_type_2_object_prop (OID = 19426) : 
--
CREATE TABLE public.map_m22_m23_tcr_type_2_object_prop (
    id integer,
    tcr_type_id integer,
    object_prop_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_object_prop ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_object_prop ALTER COLUMN tcr_type_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_tcr_type_2_object_prop ALTER COLUMN object_prop_id SET STATISTICS 0;
--
-- Structure for table map_m22_m23_terminoontology_2_ontology (OID = 19429) : 
--
CREATE TABLE public.map_m22_m23_terminoontology_2_ontology (
    id integer,
    terminoontology_id integer,
    ontology_id integer
) WITH OIDS;
ALTER TABLE ONLY public.map_m22_m23_terminoontology_2_ontology ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_terminoontology_2_ontology ALTER COLUMN terminoontology_id SET STATISTICS 0;
ALTER TABLE ONLY public.map_m22_m23_terminoontology_2_ontology ALTER COLUMN ontology_id SET STATISTICS 0;
--
-- Structure for table m0_project (OID = 19432) : 
--
CREATE TABLE public.m0_project (
    id integer,
    name character varying,
    date_creation timestamp without time zone,
    date_last_update timestamp without time zone
) WITH OIDS;
ALTER TABLE ONLY public.m0_project ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m0_project ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY public.m0_project ALTER COLUMN date_creation SET STATISTICS 0;
ALTER TABLE ONLY public.m0_project ALTER COLUMN date_last_update SET STATISTICS 0;
--
-- Structure for table m22_tc_collection (OID = 19473) : 
--
CREATE TABLE public.m22_tc_collection (
    id integer,
    label character varying,
    is_ordered boolean,
    termino_ontology_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_tc_collection ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_collection ALTER COLUMN label SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_collection ALTER COLUMN is_ordered SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_collection ALTER COLUMN termino_ontology_id SET STATISTICS 0;
--
-- Structure for table m22_tc_2_tc_collection (OID = 19479) : 
--
CREATE TABLE public.m22_tc_2_tc_collection (
    id integer,
    tc_id integer,
    tc_collection_id integer
) WITH OIDS;
ALTER TABLE ONLY public.m22_tc_2_tc_collection ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_2_tc_collection ALTER COLUMN tc_id SET STATISTICS 0;
ALTER TABLE ONLY public.m22_tc_2_tc_collection ALTER COLUMN tc_collection_id SET STATISTICS 0;
--
-- Comments
--
-- COMMENT ON SCHEMA public IS 'Standard public schema';
-- COMMENT ON TYPE chkpass IS 'password type with checks';
-- COMMENT ON TYPE ean13 IS 'International European Article Number (EAN13)';
-- COMMENT ON TYPE isbn13 IS 'International Standard Book Number 13 (ISBN13)';
-- COMMENT ON TYPE ismn13 IS 'International Standard Music Number 13 (ISMN13)';
-- COMMENT ON TYPE issn13 IS 'International Standard Serial Number 13 (ISSN13)';
-- COMMENT ON TYPE isbn IS 'International Standard Book Number (ISBN)';
-- COMMENT ON TYPE ismn IS 'International Standard Music Number (ISMN)';
-- COMMENT ON TYPE issn IS 'International Standard Serial Number (ISSN)';
-- COMMENT ON TYPE upc IS 'Universal Product Code (UPC)';
-- COMMENT ON TYPE seg IS 'floating point interval ''FLOAT .. FLOAT'', ''.. FLOAT'', ''FLOAT ..'' or ''FLOAT''';
COMMENT ON COLUMN public.m11_termoccurrence."position" IS 'postion de debut de l''occurence dans la phrase';
COMMENT ON COLUMN public.m11_termoccurrence.length IS 'postion de fin de l''occurence dans la phrase';
COMMENT ON COLUMN public.m21_term_relation.is_examinated IS 'false= phrase non évaluée et true= phrase évaluée';
COMMENT ON COLUMN public.m21_term_relation.additional_info IS 'information supplémentaire sur une relation. par exemple SUJ/COMPLEMENT dans le cas de la relation d''Expansion';
COMMENT ON COLUMN public.m23_class.logical_definition IS 'logical definition of a class using logical operator.';
COMMENT ON COLUMN public.m23_class.sql_instance_query IS 'sql query used to load instances of class';

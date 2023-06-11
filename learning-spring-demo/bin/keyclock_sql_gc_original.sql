create table ADMIN_EVENT_ENTITY
(
    ID               VARCHAR(36) not null
        primary key,
    ADMIN_EVENT_TIME BIGINT,
    REALM_ID         VARCHAR(255),
    OPERATION_TYPE   VARCHAR(255),
    AUTH_REALM_ID    VARCHAR(255),
    AUTH_CLIENT_ID   VARCHAR(255),
    AUTH_USER_ID     VARCHAR(255),
    IP_ADDRESS       VARCHAR(255),
    RESOURCE_PATH    VARCHAR(2550),
    REPRESENTATION   CLOB,
    ERROR            VARCHAR(255),
    RESOURCE_TYPE    VARCHAR(64)
);

create table AUTHENTICATOR_CONFIG_ENTRY
(
    AUTHENTICATOR_ID VARCHAR(36)  not null,
    VALUE            CLOB,
    NAME             VARCHAR(255) not null,
    primary key (AUTHENTICATOR_ID, NAME)
);

create table BROKER_LINK
(
    IDENTITY_PROVIDER   VARCHAR(255) not null,
    STORAGE_PROVIDER_ID VARCHAR(255),
    REALM_ID            VARCHAR(36)  not null,
    BROKER_USER_ID      VARCHAR(255),
    BROKER_USERNAME     VARCHAR(255),
    TOKEN               CLOB,
    USER_ID             VARCHAR(255) not null,
    constraint CONSTR_BROKER_LINK_PK
        primary key (IDENTITY_PROVIDER, USER_ID)
);

create table CLIENT
(
    ID                           VARCHAR(36)           not null
        primary key,
    ENABLED                      BOOLEAN default FALSE not null,
    FULL_SCOPE_ALLOWED           BOOLEAN default FALSE not null,
    CLIENT_ID                    VARCHAR(255),
    NOT_BEFORE                   INT,
    PUBLIC_CLIENT                BOOLEAN default FALSE not null,
    SECRET                       VARCHAR(255),
    BASE_URL                     VARCHAR(255),
    BEARER_ONLY                  BOOLEAN default FALSE not null,
    MANAGEMENT_URL               VARCHAR(255),
    SURROGATE_AUTH_REQUIRED      BOOLEAN default FALSE not null,
    REALM_ID                     VARCHAR(36),
    PROTOCOL                     VARCHAR(255),
    NODE_REREG_TIMEOUT           INT     default 0,
    FRONTCHANNEL_LOGOUT          BOOLEAN default FALSE not null,
    CONSENT_REQUIRED             BOOLEAN default FALSE not null,
    NAME                         NVARCHAR(255),
    SERVICE_ACCOUNTS_ENABLED     BOOLEAN default FALSE not null,
    CLIENT_AUTHENTICATOR_TYPE    VARCHAR(255),
    ROOT_URL                     VARCHAR(255),
    DESCRIPTION                  NVARCHAR(255),
    REGISTRATION_TOKEN           VARCHAR(255),
    STANDARD_FLOW_ENABLED        BOOLEAN default TRUE  not null,
    IMPLICIT_FLOW_ENABLED        BOOLEAN default FALSE not null,
    DIRECT_ACCESS_GRANTS_ENABLED BOOLEAN default FALSE not null,
    ALWAYS_DISPLAY_IN_CONSOLE    BOOLEAN default FALSE not null,
    constraint UK_B71CJLBENV945RB6GCON438AT
        unique (REALM_ID, CLIENT_ID)
);

create index IDX_CLIENT_ID
    on CLIENT (CLIENT_ID);

create table CLIENT_ATTRIBUTES
(
    CLIENT_ID VARCHAR(36)  not null,
    VALUE     VARCHAR(4000),
    NAME      VARCHAR(255) not null,
    primary key (CLIENT_ID, NAME),
    constraint FK3C47C64BEACCA966
        foreign key (CLIENT_ID) references CLIENT
);

create index IDX_CLIENT_ATT_BY_NAME_VALUE
    on CLIENT_ATTRIBUTES (NAME);

create table CLIENT_AUTH_FLOW_BINDINGS
(
    CLIENT_ID    VARCHAR(36)  not null,
    FLOW_ID      VARCHAR(36),
    BINDING_NAME VARCHAR(255) not null,
    constraint C_CLI_FLOW_BIND
        primary key (CLIENT_ID, BINDING_NAME)
);

create table CLIENT_NODE_REGISTRATIONS
(
    CLIENT_ID VARCHAR(36)  not null,
    VALUE     INT,
    NAME      VARCHAR(255) not null,
    primary key (CLIENT_ID, NAME),
    constraint FK4129723BA992F594
        foreign key (CLIENT_ID) references CLIENT
);

create table CLIENT_SCOPE
(
    ID          VARCHAR(36) not null,
    NAME        VARCHAR(255),
    REALM_ID    VARCHAR(36),
    DESCRIPTION NVARCHAR(255),
    PROTOCOL    VARCHAR(255),
    constraint PK_CLI_TEMPLATE
        primary key (ID),
    constraint UK_CLI_SCOPE
        unique (REALM_ID, NAME)
);

create index IDX_REALM_CLSCOPE
    on CLIENT_SCOPE (REALM_ID);

create table CLIENT_SCOPE_ATTRIBUTES
(
    SCOPE_ID VARCHAR(36)  not null,
    VALUE    VARCHAR(2048),
    NAME     VARCHAR(255) not null,
    constraint PK_CL_TMPL_ATTR
        primary key (SCOPE_ID, NAME),
    constraint FK_CL_SCOPE_ATTR_SCOPE
        foreign key (SCOPE_ID) references CLIENT_SCOPE
);

create index IDX_CLSCOPE_ATTRS
    on CLIENT_SCOPE_ATTRIBUTES (SCOPE_ID);

create table CLIENT_SCOPE_CLIENT
(
    CLIENT_ID     VARCHAR(255)          not null,
    SCOPE_ID      VARCHAR(255)          not null,
    DEFAULT_SCOPE BOOLEAN default FALSE not null,
    constraint C_CLI_SCOPE_BIND
        primary key (CLIENT_ID, SCOPE_ID)
);

create index IDX_CLSCOPE_CL
    on CLIENT_SCOPE_CLIENT (CLIENT_ID);

create index IDX_CL_CLSCOPE
    on CLIENT_SCOPE_CLIENT (SCOPE_ID);

create table CLIENT_SCOPE_ROLE_MAPPING
(
    SCOPE_ID VARCHAR(36) not null,
    ROLE_ID  VARCHAR(36) not null,
    constraint PK_TEMPLATE_SCOPE
        primary key (SCOPE_ID, ROLE_ID),
    constraint FK_CL_SCOPE_RM_SCOPE
        foreign key (SCOPE_ID) references CLIENT_SCOPE
);

create index IDX_CLSCOPE_ROLE
    on CLIENT_SCOPE_ROLE_MAPPING (SCOPE_ID);

create index IDX_ROLE_CLSCOPE
    on CLIENT_SCOPE_ROLE_MAPPING (ROLE_ID);

create table DATABASECHANGELOG
(
    ID            VARCHAR(255) not null,
    AUTHOR        VARCHAR(255) not null,
    FILENAME      VARCHAR(255) not null,
    DATEEXECUTED  TIMESTAMP    not null,
    ORDEREXECUTED INT          not null,
    EXECTYPE      VARCHAR(10)  not null,
    MD5SUM        VARCHAR(35),
    DESCRIPTION   VARCHAR(255),
    COMMENTS      VARCHAR(255),
    TAG           VARCHAR(255),
    LIQUIBASE     VARCHAR(20),
    CONTEXTS      VARCHAR(255),
    LABELS        VARCHAR(255),
    DEPLOYMENT_ID VARCHAR(10)
);

create table DATABASECHANGELOGLOCK
(
    ID          INT     not null,
    LOCKED      BOOLEAN not null,
    LOCKGRANTED TIMESTAMP,
    LOCKEDBY    VARCHAR(255),
    constraint PK_DATABASECHANGELOGLOCK
        primary key (ID)
);

create table EVENT_ENTITY
(
    ID           VARCHAR(36) not null
        primary key,
    CLIENT_ID    VARCHAR(255),
    DETAILS_JSON VARCHAR(2550),
    ERROR        VARCHAR(255),
    IP_ADDRESS   VARCHAR(255),
    REALM_ID     VARCHAR(255),
    SESSION_ID   VARCHAR(255),
    EVENT_TIME   BIGINT,
    TYPE         VARCHAR(255),
    USER_ID      VARCHAR(255)
);

create index IDX_EVENT_TIME
    on EVENT_ENTITY (REALM_ID, EVENT_TIME);

create table FEDERATED_USER
(
    ID                  VARCHAR(255) not null,
    STORAGE_PROVIDER_ID VARCHAR(255),
    REALM_ID            VARCHAR(36)  not null,
    constraint CONSTR_FEDERATED_USER
        primary key (ID)
);

create table FED_USER_ATTRIBUTE
(
    ID                  VARCHAR(36)  not null,
    NAME                VARCHAR(255) not null,
    USER_ID             VARCHAR(255) not null,
    REALM_ID            VARCHAR(36)  not null,
    STORAGE_PROVIDER_ID VARCHAR(36),
    VALUE               VARCHAR(2024),
    constraint CONSTR_FED_USER_ATTR_PK
        primary key (ID)
);

create index IDX_FU_ATTRIBUTE
    on FED_USER_ATTRIBUTE (USER_ID, REALM_ID, NAME);

create table FED_USER_CONSENT
(
    ID                      VARCHAR(36)  not null,
    CLIENT_ID               VARCHAR(255),
    USER_ID                 VARCHAR(255) not null,
    REALM_ID                VARCHAR(36)  not null,
    STORAGE_PROVIDER_ID     VARCHAR(36),
    CREATED_DATE            BIGINT,
    LAST_UPDATED_DATE       BIGINT,
    CLIENT_STORAGE_PROVIDER VARCHAR(36),
    EXTERNAL_CLIENT_ID      VARCHAR(255),
    constraint CONSTR_FED_USER_CONSENT_PK
        primary key (ID)
);

create index IDX_FU_CNSNT_EXT
    on FED_USER_CONSENT (USER_ID, CLIENT_STORAGE_PROVIDER, EXTERNAL_CLIENT_ID);

create index IDX_FU_CONSENT
    on FED_USER_CONSENT (USER_ID, CLIENT_ID);

create index IDX_FU_CONSENT_RU
    on FED_USER_CONSENT (REALM_ID, USER_ID);

create table FED_USER_CONSENT_CL_SCOPE
(
    USER_CONSENT_ID VARCHAR(36) not null,
    SCOPE_ID        VARCHAR(36) not null,
    primary key (USER_CONSENT_ID, SCOPE_ID)
);

create table FED_USER_CREDENTIAL
(
    ID                  VARCHAR(36)  not null,
    SALT                BLOB,
    TYPE                VARCHAR(255),
    CREATED_DATE        BIGINT,
    USER_ID             VARCHAR(255) not null,
    REALM_ID            VARCHAR(36)  not null,
    STORAGE_PROVIDER_ID VARCHAR(36),
    USER_LABEL          VARCHAR(255),
    SECRET_DATA         CLOB,
    CREDENTIAL_DATA     CLOB,
    PRIORITY            INT,
    constraint CONSTR_FED_USER_CRED_PK
        primary key (ID)
);

create index IDX_FU_CREDENTIAL
    on FED_USER_CREDENTIAL (USER_ID, TYPE);

create index IDX_FU_CREDENTIAL_RU
    on FED_USER_CREDENTIAL (REALM_ID, USER_ID);

create table FED_USER_GROUP_MEMBERSHIP
(
    GROUP_ID            VARCHAR(36)  not null,
    USER_ID             VARCHAR(255) not null,
    REALM_ID            VARCHAR(36)  not null,
    STORAGE_PROVIDER_ID VARCHAR(36),
    constraint CONSTR_FED_USER_GROUP
        primary key (GROUP_ID, USER_ID)
);

create index IDX_FU_GROUP_MEMBERSHIP
    on FED_USER_GROUP_MEMBERSHIP (USER_ID, GROUP_ID);

create index IDX_FU_GROUP_MEMBERSHIP_RU
    on FED_USER_GROUP_MEMBERSHIP (REALM_ID, USER_ID);

create table FED_USER_REQUIRED_ACTION
(
    REQUIRED_ACTION     VARCHAR(255) default ' ' not null,
    USER_ID             VARCHAR(255)             not null,
    REALM_ID            VARCHAR(36)              not null,
    STORAGE_PROVIDER_ID VARCHAR(36),
    constraint CONSTR_FED_REQUIRED_ACTION
        primary key (REQUIRED_ACTION, USER_ID)
);

create index IDX_FU_REQUIRED_ACTION
    on FED_USER_REQUIRED_ACTION (USER_ID, REQUIRED_ACTION);

create index IDX_FU_REQUIRED_ACTION_RU
    on FED_USER_REQUIRED_ACTION (REALM_ID, USER_ID);

create table FED_USER_ROLE_MAPPING
(
    ROLE_ID             VARCHAR(36)  not null,
    USER_ID             VARCHAR(255) not null,
    REALM_ID            VARCHAR(36)  not null,
    STORAGE_PROVIDER_ID VARCHAR(36),
    constraint CONSTR_FED_USER_ROLE
        primary key (ROLE_ID, USER_ID)
);

create index IDX_FU_ROLE_MAPPING
    on FED_USER_ROLE_MAPPING (USER_ID, ROLE_ID);

create index IDX_FU_ROLE_MAPPING_RU
    on FED_USER_ROLE_MAPPING (REALM_ID, USER_ID);

create table KEYCLOAK_GROUP
(
    ID           VARCHAR(36) not null
        primary key,
    NAME         NVARCHAR(255),
    PARENT_GROUP VARCHAR(36) not null,
    REALM_ID     VARCHAR(36),
    constraint SIBLING_NAMES
        unique (REALM_ID, PARENT_GROUP, NAME)
);

create table GROUP_ATTRIBUTE
(
    ID       VARCHAR(36) default 'sybase-needs-something-here' not null
        primary key,
    NAME     VARCHAR(255)                                      not null,
    VALUE    NVARCHAR(255),
    GROUP_ID VARCHAR(36)                                       not null,
    constraint FK_GROUP_ATTRIBUTE_GROUP
        foreign key (GROUP_ID) references KEYCLOAK_GROUP
);

create index IDX_GROUP_ATTR_GROUP
    on GROUP_ATTRIBUTE (GROUP_ID);

create table GROUP_ROLE_MAPPING
(
    ROLE_ID  VARCHAR(36) not null,
    GROUP_ID VARCHAR(36) not null,
    primary key (ROLE_ID, GROUP_ID),
    constraint FK_GROUP_ROLE_GROUP
        foreign key (GROUP_ID) references KEYCLOAK_GROUP
);

create index IDX_GROUP_ROLE_MAPP_GROUP
    on GROUP_ROLE_MAPPING (GROUP_ID);

create table MIGRATION_MODEL
(
    ID          VARCHAR(36)      not null
        primary key,
    VERSION     VARCHAR(36),
    UPDATE_TIME BIGINT default 0 not null
);

create index IDX_UPDATE_TIME
    on MIGRATION_MODEL (UPDATE_TIME);

create table OFFLINE_CLIENT_SESSION
(
    USER_SESSION_ID         VARCHAR(36)                  not null,
    CLIENT_ID               VARCHAR(255)                 not null,
    OFFLINE_FLAG            VARCHAR(4)                   not null,
    TIMESTAMP               INT,
    DATA                    CLOB,
    CLIENT_STORAGE_PROVIDER VARCHAR(36)  default 'local' not null,
    EXTERNAL_CLIENT_ID      VARCHAR(255) default 'local' not null,
    primary key (USER_SESSION_ID, CLIENT_ID, CLIENT_STORAGE_PROVIDER, EXTERNAL_CLIENT_ID, OFFLINE_FLAG)
);

create index IDX_OFFLINE_CSS_PRELOAD
    on OFFLINE_CLIENT_SESSION (CLIENT_ID, OFFLINE_FLAG);

create index IDX_US_SESS_ID_ON_CL_SESS
    on OFFLINE_CLIENT_SESSION (USER_SESSION_ID);

create table OFFLINE_USER_SESSION
(
    USER_SESSION_ID      VARCHAR(36)   not null,
    USER_ID              VARCHAR(255)  not null,
    REALM_ID             VARCHAR(36)   not null,
    CREATED_ON           INT           not null,
    OFFLINE_FLAG         VARCHAR(4)    not null,
    DATA                 CLOB,
    LAST_SESSION_REFRESH INT default 0 not null,
    primary key (USER_SESSION_ID, OFFLINE_FLAG)
);

create index IDX_OFFLINE_USS_BY_USER
    on OFFLINE_USER_SESSION (USER_ID, REALM_ID, OFFLINE_FLAG);

create index IDX_OFFLINE_USS_BY_USERSESS
    on OFFLINE_USER_SESSION (REALM_ID, OFFLINE_FLAG, USER_SESSION_ID);

create index IDX_OFFLINE_USS_CREATEDON
    on OFFLINE_USER_SESSION (CREATED_ON);

create index IDX_OFFLINE_USS_PRELOAD
    on OFFLINE_USER_SESSION (OFFLINE_FLAG, CREATED_ON, USER_SESSION_ID);

create table PROTOCOL_MAPPER
(
    ID                   VARCHAR(36)  not null
        primary key,
    NAME                 VARCHAR(255) not null,
    PROTOCOL             VARCHAR(255) not null,
    PROTOCOL_MAPPER_NAME VARCHAR(255) not null,
    CLIENT_ID            VARCHAR(36),
    CLIENT_SCOPE_ID      VARCHAR(36),
    constraint FK_CLI_SCOPE_MAPPER
        foreign key (CLIENT_SCOPE_ID) references CLIENT_SCOPE,
    constraint FK_PCM_REALM
        foreign key (CLIENT_ID) references CLIENT
);

create index IDX_CLSCOPE_PROTMAP
    on PROTOCOL_MAPPER (CLIENT_SCOPE_ID);

create index IDX_PROTOCOL_MAPPER_CLIENT
    on PROTOCOL_MAPPER (CLIENT_ID);

create table PROTOCOL_MAPPER_CONFIG
(
    PROTOCOL_MAPPER_ID VARCHAR(36)  not null,
    VALUE              CLOB,
    NAME               VARCHAR(255) not null,
    primary key (PROTOCOL_MAPPER_ID, NAME),
    constraint FK_PMCONFIG
        foreign key (PROTOCOL_MAPPER_ID) references PROTOCOL_MAPPER
);

create table REALM
(
    ID                           VARCHAR(36)               not null
        primary key,
    ACCESS_CODE_LIFESPAN         INT,
    USER_ACTION_LIFESPAN         INT,
    ACCESS_TOKEN_LIFESPAN        INT,
    ACCOUNT_THEME                VARCHAR(255),
    ADMIN_THEME                  VARCHAR(255),
    EMAIL_THEME                  VARCHAR(255),
    ENABLED                      BOOLEAN     default FALSE not null,
    EVENTS_ENABLED               BOOLEAN     default FALSE not null,
    EVENTS_EXPIRATION            BIGINT,
    LOGIN_THEME                  VARCHAR(255),
    NAME                         VARCHAR(255)
        constraint UK_ORVSDMLA56612EAEFIQ6WL5OI
            unique,
    NOT_BEFORE                   INT,
    PASSWORD_POLICY              VARCHAR(2550),
    REGISTRATION_ALLOWED         BOOLEAN     default FALSE not null,
    REMEMBER_ME                  BOOLEAN     default FALSE not null,
    RESET_PASSWORD_ALLOWED       BOOLEAN     default FALSE not null,
    SOCIAL                       BOOLEAN     default FALSE not null,
    SSL_REQUIRED                 VARCHAR(255),
    SSO_IDLE_TIMEOUT             INT,
    SSO_MAX_LIFESPAN             INT,
    UPDATE_PROFILE_ON_SOC_LOGIN  BOOLEAN     default FALSE not null,
    VERIFY_EMAIL                 BOOLEAN     default FALSE not null,
    MASTER_ADMIN_CLIENT          VARCHAR(36),
    LOGIN_LIFESPAN               INT,
    INTERNATIONALIZATION_ENABLED BOOLEAN     default FALSE not null,
    DEFAULT_LOCALE               VARCHAR(255),
    REG_EMAIL_AS_USERNAME        BOOLEAN     default FALSE not null,
    ADMIN_EVENTS_ENABLED         BOOLEAN     default FALSE not null,
    ADMIN_EVENTS_DETAILS_ENABLED BOOLEAN     default FALSE not null,
    EDIT_USERNAME_ALLOWED        BOOLEAN     default FALSE not null,
    OTP_POLICY_COUNTER           INT         default 0,
    OTP_POLICY_WINDOW            INT         default 1,
    OTP_POLICY_PERIOD            INT         default 30,
    OTP_POLICY_DIGITS            INT         default 6,
    OTP_POLICY_ALG               VARCHAR(36) default 'HmacSHA1',
    OTP_POLICY_TYPE              VARCHAR(36) default 'totp',
    BROWSER_FLOW                 VARCHAR(36),
    REGISTRATION_FLOW            VARCHAR(36),
    DIRECT_GRANT_FLOW            VARCHAR(36),
    RESET_CREDENTIALS_FLOW       VARCHAR(36),
    CLIENT_AUTH_FLOW             VARCHAR(36),
    OFFLINE_SESSION_IDLE_TIMEOUT INT         default 0,
    REVOKE_REFRESH_TOKEN         BOOLEAN     default FALSE not null,
    ACCESS_TOKEN_LIFE_IMPLICIT   INT         default 0,
    LOGIN_WITH_EMAIL_ALLOWED     BOOLEAN     default TRUE  not null,
    DUPLICATE_EMAILS_ALLOWED     BOOLEAN     default FALSE not null,
    DOCKER_AUTH_FLOW             VARCHAR(36),
    REFRESH_TOKEN_MAX_REUSE      INT         default 0,
    ALLOW_USER_MANAGED_ACCESS    BOOLEAN     default FALSE not null,
    SSO_MAX_LIFESPAN_REMEMBER_ME INT         default 0     not null,
    SSO_IDLE_TIMEOUT_REMEMBER_ME INT         default 0     not null,
    DEFAULT_ROLE                 VARCHAR(255)
);

create table AUTHENTICATION_FLOW
(
    ID          VARCHAR(36)                      not null
        primary key,
    ALIAS       VARCHAR(255),
    DESCRIPTION NVARCHAR(255),
    REALM_ID    VARCHAR(36),
    PROVIDER_ID VARCHAR(36) default 'basic-flow' not null,
    TOP_LEVEL   BOOLEAN     default FALSE        not null,
    BUILT_IN    BOOLEAN     default FALSE        not null,
    constraint FK_AUTH_FLOW_REALM
        foreign key (REALM_ID) references REALM
);

create table AUTHENTICATION_EXECUTION
(
    ID                 VARCHAR(36)           not null
        primary key,
    ALIAS              VARCHAR(255),
    AUTHENTICATOR      VARCHAR(36),
    REALM_ID           VARCHAR(36),
    FLOW_ID            VARCHAR(36),
    REQUIREMENT        INT,
    PRIORITY           INT,
    AUTHENTICATOR_FLOW BOOLEAN default FALSE not null,
    AUTH_FLOW_ID       VARCHAR(36),
    AUTH_CONFIG        VARCHAR(36),
    constraint FK_AUTH_EXEC_FLOW
        foreign key (FLOW_ID) references AUTHENTICATION_FLOW,
    constraint FK_AUTH_EXEC_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_AUTH_EXEC_FLOW
    on AUTHENTICATION_EXECUTION (FLOW_ID);

create index IDX_AUTH_EXEC_REALM_FLOW
    on AUTHENTICATION_EXECUTION (REALM_ID, FLOW_ID);

create index IDX_AUTH_FLOW_REALM
    on AUTHENTICATION_FLOW (REALM_ID);

create table AUTHENTICATOR_CONFIG
(
    ID       VARCHAR(36) not null
        primary key,
    ALIAS    VARCHAR(255),
    REALM_ID VARCHAR(36),
    constraint FK_AUTH_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_AUTH_CONFIG_REALM
    on AUTHENTICATOR_CONFIG (REALM_ID);

create table CLIENT_INITIAL_ACCESS
(
    ID              VARCHAR(36) not null,
    REALM_ID        VARCHAR(36) not null,
    TIMESTAMP       INT,
    EXPIRATION      INT,
    COUNT           INT,
    REMAINING_COUNT INT,
    constraint CNSTR_CLIENT_INIT_ACC_PK
        primary key (ID),
    constraint FK_CLIENT_INIT_ACC_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_CLIENT_INIT_ACC_REALM
    on CLIENT_INITIAL_ACCESS (REALM_ID);

create table COMPONENT
(
    ID            VARCHAR(36) not null,
    NAME          VARCHAR(255),
    PARENT_ID     VARCHAR(36),
    PROVIDER_ID   VARCHAR(36),
    PROVIDER_TYPE VARCHAR(255),
    REALM_ID      VARCHAR(36),
    SUB_TYPE      VARCHAR(255),
    constraint CONSTR_COMPONENT_PK
        primary key (ID),
    constraint FK_COMPONENT_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_COMPONENT_PROVIDER_TYPE
    on COMPONENT (PROVIDER_TYPE);

create index IDX_COMPONENT_REALM
    on COMPONENT (REALM_ID);

create table COMPONENT_CONFIG
(
    ID           VARCHAR(36)  not null,
    COMPONENT_ID VARCHAR(36)  not null,
    NAME         VARCHAR(255) not null,
    VALUE        NVARCHAR(4000),
    constraint CONSTR_COMPONENT_CONFIG_PK
        primary key (ID),
    constraint FK_COMPONENT_CONFIG
        foreign key (COMPONENT_ID) references COMPONENT
);

create index IDX_COMPO_CONFIG_COMPO
    on COMPONENT_CONFIG (COMPONENT_ID);

create table DEFAULT_CLIENT_SCOPE
(
    REALM_ID      VARCHAR(36)           not null,
    SCOPE_ID      VARCHAR(36)           not null,
    DEFAULT_SCOPE BOOLEAN default FALSE not null,
    constraint R_DEF_CLI_SCOPE_BIND
        primary key (REALM_ID, SCOPE_ID),
    constraint FK_R_DEF_CLI_SCOPE_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_DEFCLS_REALM
    on DEFAULT_CLIENT_SCOPE (REALM_ID);

create index IDX_DEFCLS_SCOPE
    on DEFAULT_CLIENT_SCOPE (SCOPE_ID);

create table IDENTITY_PROVIDER
(
    INTERNAL_ID                VARCHAR(36)           not null
        primary key,
    ENABLED                    BOOLEAN default FALSE not null,
    PROVIDER_ALIAS             VARCHAR(255),
    PROVIDER_ID                VARCHAR(255),
    STORE_TOKEN                BOOLEAN default FALSE not null,
    AUTHENTICATE_BY_DEFAULT    BOOLEAN default FALSE not null,
    REALM_ID                   VARCHAR(36),
    ADD_TOKEN_ROLE             BOOLEAN default TRUE  not null,
    TRUST_EMAIL                BOOLEAN default FALSE not null,
    FIRST_BROKER_LOGIN_FLOW_ID VARCHAR(36),
    POST_BROKER_LOGIN_FLOW_ID  VARCHAR(36),
    PROVIDER_DISPLAY_NAME      VARCHAR(255),
    LINK_ONLY                  BOOLEAN default FALSE not null,
    constraint UK_2DAELWNIBJI49AVXSRTUF6XJ33
        unique (PROVIDER_ALIAS, REALM_ID),
    constraint FK2B4EBC52AE5C3B34
        foreign key (REALM_ID) references REALM
);

create index IDX_IDENT_PROV_REALM
    on IDENTITY_PROVIDER (REALM_ID);

create table IDENTITY_PROVIDER_CONFIG
(
    IDENTITY_PROVIDER_ID VARCHAR(36)  not null,
    VALUE                CLOB,
    NAME                 VARCHAR(255) not null,
    primary key (IDENTITY_PROVIDER_ID, NAME),
    constraint FKDC4897CF864C4E43
        foreign key (IDENTITY_PROVIDER_ID) references IDENTITY_PROVIDER
);

create table IDENTITY_PROVIDER_MAPPER
(
    ID              VARCHAR(36)  not null
        primary key,
    NAME            VARCHAR(255) not null,
    IDP_ALIAS       VARCHAR(255) not null,
    IDP_MAPPER_NAME VARCHAR(255) not null,
    REALM_ID        VARCHAR(36)  not null,
    constraint FK_IDPM_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_ID_PROV_MAPP_REALM
    on IDENTITY_PROVIDER_MAPPER (REALM_ID);

create table IDP_MAPPER_CONFIG
(
    IDP_MAPPER_ID VARCHAR(36)  not null,
    VALUE         CLOB,
    NAME          VARCHAR(255) not null,
    primary key (IDP_MAPPER_ID, NAME),
    constraint FK_IDPMCONFIG
        foreign key (IDP_MAPPER_ID) references IDENTITY_PROVIDER_MAPPER
);

create table KEYCLOAK_ROLE
(
    ID                      VARCHAR(36)           not null
        primary key,
    CLIENT_REALM_CONSTRAINT VARCHAR(255),
    CLIENT_ROLE             BOOLEAN default FALSE not null,
    DESCRIPTION             NVARCHAR(255),
    NAME                    NVARCHAR(255),
    REALM_ID                VARCHAR(255),
    CLIENT                  VARCHAR(36),
    REALM                   VARCHAR(36),
    constraint "UK_J3RWUVD56ONTGSUHOGM184WW2-2"
        unique (NAME, CLIENT_REALM_CONSTRAINT),
    constraint FK_6VYQFE4CN4WLQ8R6KT5VDSJ5C
        foreign key (REALM) references REALM
);

create table COMPOSITE_ROLE
(
    COMPOSITE  VARCHAR(36) not null,
    CHILD_ROLE VARCHAR(36) not null,
    primary key (COMPOSITE, CHILD_ROLE),
    constraint FK_A63WVEKFTU8JO1PNJ81E7MCE2
        foreign key (COMPOSITE) references KEYCLOAK_ROLE,
    constraint FK_GR7THLLB9LU8Q4VQA4524JJY8
        foreign key (CHILD_ROLE) references KEYCLOAK_ROLE
);

create index IDX_COMPOSITE
    on COMPOSITE_ROLE (COMPOSITE);

create index IDX_COMPOSITE_CHILD
    on COMPOSITE_ROLE (CHILD_ROLE);

create index IDX_KEYCLOAK_ROLE_CLIENT
    on KEYCLOAK_ROLE (CLIENT);

create index IDX_KEYCLOAK_ROLE_REALM
    on KEYCLOAK_ROLE (REALM);

create index IDX_REALM_MASTER_ADM_CLI
    on REALM (MASTER_ADMIN_CLIENT);

create table REALM_ATTRIBUTE
(
    NAME     VARCHAR(255) not null,
    REALM_ID VARCHAR(36)  not null,
    VALUE    CLOB,
    primary key (NAME, REALM_ID),
    constraint FK_8SHXD6L3E9ATQUKACXGPFFPTW
        foreign key (REALM_ID) references REALM
);

create index IDX_REALM_ATTR_REALM
    on REALM_ATTRIBUTE (REALM_ID);

create table REALM_DEFAULT_GROUPS
(
    REALM_ID VARCHAR(36) not null,
    GROUP_ID VARCHAR(36) not null
        constraint CON_GROUP_ID_DEF_GROUPS
            unique,
    constraint CONSTR_REALM_DEFAULT_GROUPS
        primary key (REALM_ID, GROUP_ID),
    constraint FK_DEF_GROUPS_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_REALM_DEF_GRP_REALM
    on REALM_DEFAULT_GROUPS (REALM_ID);

create table REALM_ENABLED_EVENT_TYPES
(
    REALM_ID VARCHAR(36)  not null,
    VALUE    VARCHAR(255) not null,
    constraint CONSTR_REALM_ENABL_EVENT_TYPES
        primary key (REALM_ID, VALUE),
    constraint FK_H846O4H0W8EPX5NWEDRF5Y69J
        foreign key (REALM_ID) references REALM
);

create index IDX_REALM_EVT_TYPES_REALM
    on REALM_ENABLED_EVENT_TYPES (REALM_ID);

create table REALM_EVENTS_LISTENERS
(
    REALM_ID VARCHAR(36)  not null,
    VALUE    VARCHAR(255) not null,
    constraint CONSTR_REALM_EVENTS_LISTENERS
        primary key (REALM_ID, VALUE),
    constraint FK_H846O4H0W8EPX5NXEV9F5Y69J
        foreign key (REALM_ID) references REALM
);

create index IDX_REALM_EVT_LIST_REALM
    on REALM_EVENTS_LISTENERS (REALM_ID);

create table REALM_LOCALIZATIONS
(
    REALM_ID VARCHAR(255) not null,
    LOCALE   VARCHAR(255) not null,
    TEXTS    CLOB         not null,
    primary key (REALM_ID, LOCALE)
);

create table REALM_REQUIRED_CREDENTIAL
(
    TYPE       VARCHAR(255)          not null,
    FORM_LABEL VARCHAR(255),
    INPUT      BOOLEAN default FALSE not null,
    SECRET     BOOLEAN default FALSE not null,
    REALM_ID   VARCHAR(36)           not null,
    primary key (REALM_ID, TYPE),
    constraint FK_5HG65LYBEVAVKQFKI3KPONH9V
        foreign key (REALM_ID) references REALM
);

create table REALM_SMTP_CONFIG
(
    REALM_ID VARCHAR(36)  not null,
    VALUE    VARCHAR(255),
    NAME     VARCHAR(255) not null,
    primary key (REALM_ID, NAME),
    constraint FK_70EJ8XDXGXD0B9HH6180IRR0O
        foreign key (REALM_ID) references REALM
);

create table REALM_SUPPORTED_LOCALES
(
    REALM_ID VARCHAR(36)  not null,
    VALUE    VARCHAR(255) not null,
    constraint CONSTR_REALM_SUPPORTED_LOCALES
        primary key (REALM_ID, VALUE),
    constraint FK_SUPPORTED_LOCALES_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_REALM_SUPP_LOCAL_REALM
    on REALM_SUPPORTED_LOCALES (REALM_ID);

create table REDIRECT_URIS
(
    CLIENT_ID VARCHAR(36)  not null,
    VALUE     VARCHAR(255) not null,
    primary key (CLIENT_ID, VALUE),
    constraint FK_1BURS8PB4OUJ97H5WUPPAHV9F
        foreign key (CLIENT_ID) references CLIENT
);

create index IDX_REDIR_URI_CLIENT
    on REDIRECT_URIS (CLIENT_ID);

create table REQUIRED_ACTION_CONFIG
(
    REQUIRED_ACTION_ID VARCHAR(36)  not null,
    VALUE              CLOB,
    NAME               VARCHAR(255) not null,
    primary key (REQUIRED_ACTION_ID, NAME)
);

create table REQUIRED_ACTION_PROVIDER
(
    ID             VARCHAR(36)           not null
        primary key,
    ALIAS          VARCHAR(255),
    NAME           VARCHAR(255),
    REALM_ID       VARCHAR(36),
    ENABLED        BOOLEAN default FALSE not null,
    DEFAULT_ACTION BOOLEAN default FALSE not null,
    PROVIDER_ID    VARCHAR(255),
    PRIORITY       INT,
    constraint FK_REQ_ACT_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_REQ_ACT_PROV_REALM
    on REQUIRED_ACTION_PROVIDER (REALM_ID);

create table RESOURCE_SERVER
(
    ID                   VARCHAR(36)           not null,
    ALLOW_RS_REMOTE_MGMT BOOLEAN default FALSE not null,
    POLICY_ENFORCE_MODE  VARCHAR(15)           not null,
    DECISION_STRATEGY    TINYINT default '1'   not null,
    constraint PK_RESOURCE_SERVER
        primary key (ID)
);

create table RESOURCE_SERVER_POLICY
(
    ID                 VARCHAR(36)  not null
        primary key,
    NAME               VARCHAR(255) not null,
    DESCRIPTION        NVARCHAR(255),
    TYPE               VARCHAR(255) not null,
    DECISION_STRATEGY  VARCHAR(20),
    LOGIC              VARCHAR(20),
    RESOURCE_SERVER_ID VARCHAR(36)  not null,
    OWNER              VARCHAR(255),
    constraint UK_FRSRPT700S9V50BU18WS5HA6
        unique (NAME, RESOURCE_SERVER_ID),
    constraint FK_FRSRPO213XCX4WNKOG82SSRFY
        foreign key (RESOURCE_SERVER_ID) references RESOURCE_SERVER
);

create table ASSOCIATED_POLICY
(
    POLICY_ID            VARCHAR(36) not null,
    ASSOCIATED_POLICY_ID VARCHAR(36) not null,
    primary key (POLICY_ID, ASSOCIATED_POLICY_ID),
    constraint FK_FRSR5S213XCX4WNKOG82SSRFY
        foreign key (ASSOCIATED_POLICY_ID) references RESOURCE_SERVER_POLICY,
    constraint FK_FRSRPAS14XCX4WNKOG82SSRFY
        foreign key (POLICY_ID) references RESOURCE_SERVER_POLICY
);

create index IDX_ASSOC_POL_ASSOC_POL_ID
    on ASSOCIATED_POLICY (ASSOCIATED_POLICY_ID);

create table POLICY_CONFIG
(
    POLICY_ID VARCHAR(36)  not null,
    NAME      VARCHAR(255) not null,
    VALUE     CLOB,
    primary key (POLICY_ID, NAME),
    constraint FKDC34197CF864C4E43
        foreign key (POLICY_ID) references RESOURCE_SERVER_POLICY
);

create index IDX_RES_SERV_POL_RES_SERV
    on RESOURCE_SERVER_POLICY (RESOURCE_SERVER_ID);

create table RESOURCE_SERVER_RESOURCE
(
    ID                   VARCHAR(36)           not null
        primary key,
    NAME                 VARCHAR(255)          not null,
    TYPE                 VARCHAR(255),
    ICON_URI             VARCHAR(255),
    OWNER                VARCHAR(255)          not null,
    RESOURCE_SERVER_ID   VARCHAR(36)           not null,
    OWNER_MANAGED_ACCESS BOOLEAN default FALSE not null,
    DISPLAY_NAME         VARCHAR(255),
    constraint UK_FRSR6T700S9V50BU18WS5HA6
        unique (NAME, OWNER, RESOURCE_SERVER_ID),
    constraint FK_FRSRHO213XCX4WNKOG82SSRFY
        foreign key (RESOURCE_SERVER_ID) references RESOURCE_SERVER
);

create table RESOURCE_ATTRIBUTE
(
    ID          VARCHAR(36) default 'sybase-needs-something-here' not null,
    NAME        VARCHAR(255)                                      not null,
    VALUE       VARCHAR(255),
    RESOURCE_ID VARCHAR(36)                                       not null,
    constraint RES_ATTR_PK
        primary key (ID),
    constraint FK_5HRM2VLF9QL5FU022KQEPOVBR
        foreign key (RESOURCE_ID) references RESOURCE_SERVER_RESOURCE
);

create table RESOURCE_POLICY
(
    RESOURCE_ID VARCHAR(36) not null,
    POLICY_ID   VARCHAR(36) not null,
    primary key (RESOURCE_ID, POLICY_ID),
    constraint FK_FRSRPOS53XCX4WNKOG82SSRFY
        foreign key (RESOURCE_ID) references RESOURCE_SERVER_RESOURCE,
    constraint FK_FRSRPP213XCX4WNKOG82SSRFY
        foreign key (POLICY_ID) references RESOURCE_SERVER_POLICY
);

create index IDX_RES_POLICY_POLICY
    on RESOURCE_POLICY (POLICY_ID);

create index IDX_RES_SRV_RES_RES_SRV
    on RESOURCE_SERVER_RESOURCE (RESOURCE_SERVER_ID);

create table RESOURCE_SERVER_SCOPE
(
    ID                 VARCHAR(36)  not null
        primary key,
    NAME               VARCHAR(255) not null,
    ICON_URI           VARCHAR(255),
    RESOURCE_SERVER_ID VARCHAR(36)  not null,
    DISPLAY_NAME       VARCHAR(255),
    constraint UK_FRSRST700S9V50BU18WS5HA6
        unique (NAME, RESOURCE_SERVER_ID),
    constraint FK_FRSRSO213XCX4WNKOG82SSRFY
        foreign key (RESOURCE_SERVER_ID) references RESOURCE_SERVER
);

create table RESOURCE_SCOPE
(
    RESOURCE_ID VARCHAR(36) not null,
    SCOPE_ID    VARCHAR(36) not null,
    primary key (RESOURCE_ID, SCOPE_ID),
    constraint FK_FRSRPOS13XCX4WNKOG82SSRFY
        foreign key (RESOURCE_ID) references RESOURCE_SERVER_RESOURCE,
    constraint FK_FRSRPS213XCX4WNKOG82SSRFY
        foreign key (SCOPE_ID) references RESOURCE_SERVER_SCOPE
);

create index IDX_RES_SCOPE_SCOPE
    on RESOURCE_SCOPE (SCOPE_ID);

create table RESOURCE_SERVER_PERM_TICKET
(
    ID                 VARCHAR(36)  not null
        primary key,
    OWNER              VARCHAR(255) not null,
    REQUESTER          VARCHAR(255) not null,
    CREATED_TIMESTAMP  BIGINT       not null,
    GRANTED_TIMESTAMP  BIGINT,
    RESOURCE_ID        VARCHAR(36)  not null,
    SCOPE_ID           VARCHAR(36),
    RESOURCE_SERVER_ID VARCHAR(36)  not null,
    POLICY_ID          VARCHAR(36),
    constraint UK_FRSR6T700S9V50BU18WS5PMT
        unique (OWNER, REQUESTER, RESOURCE_SERVER_ID, RESOURCE_ID, SCOPE_ID),
    constraint FK_FRSRHO213XCX4WNKOG82SSPMT
        foreign key (RESOURCE_SERVER_ID) references RESOURCE_SERVER,
    constraint FK_FRSRHO213XCX4WNKOG83SSPMT
        foreign key (RESOURCE_ID) references RESOURCE_SERVER_RESOURCE,
    constraint FK_FRSRHO213XCX4WNKOG84SSPMT
        foreign key (SCOPE_ID) references RESOURCE_SERVER_SCOPE,
    constraint FK_FRSRPO2128CX4WNKOG82SSRFY
        foreign key (POLICY_ID) references RESOURCE_SERVER_POLICY
);

create index IDX_RES_SRV_SCOPE_RES_SRV
    on RESOURCE_SERVER_SCOPE (RESOURCE_SERVER_ID);

create table RESOURCE_URIS
(
    RESOURCE_ID VARCHAR(36)  not null,
    VALUE       VARCHAR(255) not null,
    primary key (RESOURCE_ID, VALUE),
    constraint FK_RESOURCE_SERVER_URIS
        foreign key (RESOURCE_ID) references RESOURCE_SERVER_RESOURCE
);

create table ROLE_ATTRIBUTE
(
    ID      VARCHAR(36)  not null
        primary key,
    ROLE_ID VARCHAR(36)  not null,
    NAME    VARCHAR(255) not null,
    VALUE   NVARCHAR(255),
    constraint FK_ROLE_ATTRIBUTE_ID
        foreign key (ROLE_ID) references KEYCLOAK_ROLE
);

create index IDX_ROLE_ATTRIBUTE
    on ROLE_ATTRIBUTE (ROLE_ID);

create table SCOPE_MAPPING
(
    CLIENT_ID VARCHAR(36) not null,
    ROLE_ID   VARCHAR(36) not null,
    primary key (CLIENT_ID, ROLE_ID),
    constraint FK_OUSE064PLMLR732LXJCN1Q5F1
        foreign key (CLIENT_ID) references CLIENT
);

create index IDX_SCOPE_MAPPING_ROLE
    on SCOPE_MAPPING (ROLE_ID);

create table SCOPE_POLICY
(
    SCOPE_ID  VARCHAR(36) not null,
    POLICY_ID VARCHAR(36) not null,
    primary key (SCOPE_ID, POLICY_ID),
    constraint FK_FRSRASP13XCX4WNKOG82SSRFY
        foreign key (POLICY_ID) references RESOURCE_SERVER_POLICY,
    constraint FK_FRSRPASS3XCX4WNKOG82SSRFY
        foreign key (SCOPE_ID) references RESOURCE_SERVER_SCOPE
);

create index IDX_SCOPE_POLICY_POLICY
    on SCOPE_POLICY (POLICY_ID);

create table USERNAME_LOGIN_FAILURE
(
    REALM_ID                VARCHAR(36)   not null,
    USERNAME                NVARCHAR(255) not null,
    FAILED_LOGIN_NOT_BEFORE INT,
    LAST_FAILURE            BIGINT,
    LAST_IP_FAILURE         VARCHAR(255),
    NUM_FAILURES            INT,
    primary key (REALM_ID, USERNAME)
);

create table USER_ENTITY
(
    ID                          VARCHAR(36)           not null
        primary key,
    EMAIL                       VARCHAR(255),
    EMAIL_CONSTRAINT            VARCHAR(255),
    EMAIL_VERIFIED              BOOLEAN default FALSE not null,
    ENABLED                     BOOLEAN default FALSE not null,
    FEDERATION_LINK             VARCHAR(255),
    FIRST_NAME                  NVARCHAR(255),
    LAST_NAME                   NVARCHAR(255),
    REALM_ID                    VARCHAR(255),
    USERNAME                    NVARCHAR(255),
    CREATED_TIMESTAMP           BIGINT,
    SERVICE_ACCOUNT_CLIENT_LINK VARCHAR(255),
    NOT_BEFORE                  INT     default 0     not null,
    constraint UK_DYKN684SL8UP1CRFEI6ECKHD7
        unique (REALM_ID, EMAIL_CONSTRAINT),
    constraint UK_RU8TT6T700S9V50BU18WS5HA6
        unique (REALM_ID, USERNAME)
);

create table CREDENTIAL
(
    ID              VARCHAR(36) not null
        primary key,
    SALT            BLOB,
    TYPE            VARCHAR(255),
    USER_ID         VARCHAR(36),
    CREATED_DATE    BIGINT,
    USER_LABEL      VARCHAR(255),
    SECRET_DATA     CLOB,
    CREDENTIAL_DATA CLOB,
    PRIORITY        INT,
    constraint FK_PFYR0GLASQYL0DEI3KL69R6V0
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_USER_CREDENTIAL
    on CREDENTIAL (USER_ID);

create table FEDERATED_IDENTITY
(
    IDENTITY_PROVIDER  VARCHAR(255) not null,
    REALM_ID           VARCHAR(36),
    FEDERATED_USER_ID  VARCHAR(255),
    FEDERATED_USERNAME VARCHAR(255),
    TOKEN              CLOB,
    USER_ID            VARCHAR(36)  not null,
    primary key (IDENTITY_PROVIDER, USER_ID),
    constraint FK404288B92EF007A6
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_FEDIDENTITY_FEDUSER
    on FEDERATED_IDENTITY (FEDERATED_USER_ID);

create index IDX_FEDIDENTITY_USER
    on FEDERATED_IDENTITY (USER_ID);

create table USER_ATTRIBUTE
(
    NAME    VARCHAR(255)                                      not null,
    VALUE   NVARCHAR(255),
    USER_ID VARCHAR(36)                                       not null,
    ID      VARCHAR(36) default 'sybase-needs-something-here' not null
        primary key,
    constraint FK_5HRM2VLF9QL5FU043KQEPOVBR
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_USER_ATTRIBUTE
    on USER_ATTRIBUTE (USER_ID);

create index IDX_USER_ATTRIBUTE_NAME
    on USER_ATTRIBUTE (NAME, VALUE);

create table USER_CONSENT
(
    ID                      VARCHAR(36) not null
        primary key,
    CLIENT_ID               VARCHAR(255),
    USER_ID                 VARCHAR(36) not null,
    CREATED_DATE            BIGINT,
    LAST_UPDATED_DATE       BIGINT,
    CLIENT_STORAGE_PROVIDER VARCHAR(36),
    EXTERNAL_CLIENT_ID      VARCHAR(255),
    constraint UK_JKUWUVD56ONTGSUHOGM8UEWRT
        unique (CLIENT_ID, CLIENT_STORAGE_PROVIDER, EXTERNAL_CLIENT_ID, USER_ID),
    constraint FK_GRNTCSNT_USER
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_USER_CONSENT
    on USER_CONSENT (USER_ID);

create table USER_CONSENT_CLIENT_SCOPE
(
    USER_CONSENT_ID VARCHAR(36) not null,
    SCOPE_ID        VARCHAR(36) not null,
    primary key (USER_CONSENT_ID, SCOPE_ID),
    constraint FK_GRNTCSNT_CLSC_USC
        foreign key (USER_CONSENT_ID) references USER_CONSENT
);

create index IDX_USCONSENT_CLSCOPE
    on USER_CONSENT_CLIENT_SCOPE (USER_CONSENT_ID);

create index IDX_USER_EMAIL
    on USER_ENTITY (EMAIL);

create table USER_FEDERATION_PROVIDER
(
    ID                  VARCHAR(36) not null
        primary key,
    CHANGED_SYNC_PERIOD INT,
    DISPLAY_NAME        VARCHAR(255),
    FULL_SYNC_PERIOD    INT,
    LAST_SYNC           INT,
    PRIORITY            INT,
    PROVIDER_NAME       VARCHAR(255),
    REALM_ID            VARCHAR(36),
    constraint FK_1FJ32F6PTOLW2QY60CD8N01E8
        foreign key (REALM_ID) references REALM
);

create table USER_FEDERATION_CONFIG
(
    USER_FEDERATION_PROVIDER_ID VARCHAR(36)  not null,
    VALUE                       VARCHAR(255),
    NAME                        VARCHAR(255) not null,
    primary key (USER_FEDERATION_PROVIDER_ID, NAME),
    constraint FK_T13HPU1J94R2EBPEKR39X5EU5
        foreign key (USER_FEDERATION_PROVIDER_ID) references USER_FEDERATION_PROVIDER
);

create table USER_FEDERATION_MAPPER
(
    ID                     VARCHAR(36)  not null
        primary key,
    NAME                   VARCHAR(255) not null,
    FEDERATION_PROVIDER_ID VARCHAR(36)  not null,
    FEDERATION_MAPPER_TYPE VARCHAR(255) not null,
    REALM_ID               VARCHAR(36)  not null,
    constraint FK_FEDMAPPERPM_FEDPRV
        foreign key (FEDERATION_PROVIDER_ID) references USER_FEDERATION_PROVIDER,
    constraint FK_FEDMAPPERPM_REALM
        foreign key (REALM_ID) references REALM
);

create index IDX_USR_FED_MAP_FED_PRV
    on USER_FEDERATION_MAPPER (FEDERATION_PROVIDER_ID);

create index IDX_USR_FED_MAP_REALM
    on USER_FEDERATION_MAPPER (REALM_ID);

create table USER_FEDERATION_MAPPER_CONFIG
(
    USER_FEDERATION_MAPPER_ID VARCHAR(36)  not null,
    VALUE                     VARCHAR(255),
    NAME                      VARCHAR(255) not null,
    primary key (USER_FEDERATION_MAPPER_ID, NAME),
    constraint FK_FEDMAPPER_CFG
        foreign key (USER_FEDERATION_MAPPER_ID) references USER_FEDERATION_MAPPER
);

create index IDX_USR_FED_PRV_REALM
    on USER_FEDERATION_PROVIDER (REALM_ID);

create table USER_GROUP_MEMBERSHIP
(
    GROUP_ID VARCHAR(36) not null,
    USER_ID  VARCHAR(36) not null,
    primary key (GROUP_ID, USER_ID),
    constraint FK_USER_GROUP_USER
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_USER_GROUP_MAPPING
    on USER_GROUP_MEMBERSHIP (USER_ID);

create table USER_REQUIRED_ACTION
(
    USER_ID         VARCHAR(36)              not null,
    REQUIRED_ACTION VARCHAR(255) default ' ' not null,
    primary key (REQUIRED_ACTION, USER_ID),
    constraint FK_6QJ3W1JW9CVAFHE19BWSIUVMD
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_USER_REQACTIONS
    on USER_REQUIRED_ACTION (USER_ID);

create table USER_ROLE_MAPPING
(
    ROLE_ID VARCHAR(255) not null,
    USER_ID VARCHAR(36)  not null,
    primary key (ROLE_ID, USER_ID),
    constraint FK_C4FQV34P1MBYLLOXANG7B1Q3L
        foreign key (USER_ID) references USER_ENTITY
);

create index IDX_USER_ROLE_MAPPING
    on USER_ROLE_MAPPING (USER_ID);

create table USER_SESSION
(
    ID                   VARCHAR(36)           not null
        primary key,
    AUTH_METHOD          VARCHAR(255),
    IP_ADDRESS           VARCHAR(255),
    LAST_SESSION_REFRESH INT,
    LOGIN_USERNAME       VARCHAR(255),
    REALM_ID             VARCHAR(255),
    REMEMBER_ME          BOOLEAN default FALSE not null,
    STARTED              INT,
    USER_ID              VARCHAR(255),
    USER_SESSION_STATE   INT,
    BROKER_SESSION_ID    VARCHAR(255),
    BROKER_USER_ID       VARCHAR(255)
);

create table CLIENT_SESSION
(
    ID             VARCHAR(36) not null
        primary key,
    CLIENT_ID      VARCHAR(36),
    REDIRECT_URI   VARCHAR(255),
    STATE          VARCHAR(255),
    TIMESTAMP      INT,
    SESSION_ID     VARCHAR(36),
    AUTH_METHOD    VARCHAR(255),
    REALM_ID       VARCHAR(255),
    AUTH_USER_ID   VARCHAR(36),
    CURRENT_ACTION VARCHAR(36),
    constraint FK_B4AO2VCVAT6UKAU74WBWTFQO1
        foreign key (SESSION_ID) references USER_SESSION
);

create index IDX_CLIENT_SESSION_SESSION
    on CLIENT_SESSION (SESSION_ID);

create table CLIENT_SESSION_AUTH_STATUS
(
    AUTHENTICATOR  VARCHAR(36) not null,
    STATUS         INT,
    CLIENT_SESSION VARCHAR(36) not null,
    primary key (CLIENT_SESSION, AUTHENTICATOR),
    constraint AUTH_STATUS_CONSTRAINT
        foreign key (CLIENT_SESSION) references CLIENT_SESSION
);

create table CLIENT_SESSION_NOTE
(
    NAME           VARCHAR(255) not null,
    VALUE          VARCHAR(255),
    CLIENT_SESSION VARCHAR(36)  not null,
    primary key (CLIENT_SESSION, NAME),
    constraint FK5EDFB00FF51C2736
        foreign key (CLIENT_SESSION) references CLIENT_SESSION
);

create table CLIENT_SESSION_PROT_MAPPER
(
    PROTOCOL_MAPPER_ID VARCHAR(36) not null,
    CLIENT_SESSION     VARCHAR(36) not null,
    primary key (CLIENT_SESSION, PROTOCOL_MAPPER_ID),
    constraint FK_33A8SGQW18I532811V7O2DK89
        foreign key (CLIENT_SESSION) references CLIENT_SESSION
);

create table CLIENT_SESSION_ROLE
(
    ROLE_ID        VARCHAR(255) not null,
    CLIENT_SESSION VARCHAR(36)  not null,
    primary key (CLIENT_SESSION, ROLE_ID),
    constraint FK_11B7SGQW18I532811V7O2DV76
        foreign key (CLIENT_SESSION) references CLIENT_SESSION
);

create table CLIENT_USER_SESSION_NOTE
(
    NAME           VARCHAR(255) not null,
    VALUE          VARCHAR(2048),
    CLIENT_SESSION VARCHAR(36)  not null,
    constraint CONSTR_CL_USR_SES_NOTE
        primary key (CLIENT_SESSION, NAME),
    constraint FK_CL_USR_SES_NOTE
        foreign key (CLIENT_SESSION) references CLIENT_SESSION
);

create table USER_SESSION_NOTE
(
    USER_SESSION VARCHAR(36)  not null,
    NAME         VARCHAR(255) not null,
    VALUE        VARCHAR(2048),
    primary key (USER_SESSION, NAME),
    constraint FK5EDFB00FF51D3472
        foreign key (USER_SESSION) references USER_SESSION
);

create table WEB_ORIGINS
(
    CLIENT_ID VARCHAR(36)  not null,
    VALUE     VARCHAR(255) not null,
    primary key (CLIENT_ID, VALUE),
    constraint FK_LOJPHO213XCX4WNKOG82SSRFY
        foreign key (CLIENT_ID) references CLIENT
);

create index IDX_WEB_ORIG_CLIENT
    on WEB_ORIGINS (CLIENT_ID);

